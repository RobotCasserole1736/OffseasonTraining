package frc.lib.DataServer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import frc.lib.Util.CrashTracker;

public class Signal {

    String id;
    String display_name;
    String units;

    LinkedList<DataSample> samples;
    HashSet<AcqSpec> acqSpecs;

    /**
     * Class which describes one line on a plot
     * 
     * @param name_in  String of what to call the signal (human readable)
     * @param units_in units the signal is in.
     */
    public Signal(String name_in, String units_in) {
        display_name = name_in;
        id = IDGen.makeUUID(name_in);
        units = units_in;

        samples = new LinkedList<DataSample>();
        acqSpecs = new HashSet<AcqSpec>();

        CasseroleDataServer.getInstance().registerSignal(this);
    }

    /**
     * Adds a new sample to the signal queue. It is intended that the controls code
     * would call this once per loop to add a new datapoint to the real-time graph.
     * 
     * The boolean version converts true to 1.0 and false to 0.0.
     * 
     * @param time_in
     * @param value_in
     */
    public void addSample(double time_in_ms, boolean value_in) {
        this.addSample(time_in_ms, value_in?1.0:0.0);
    }

    /**
     * Adds a new sample to the signal queue. It is intended that the controls code
     * would call this once per loop to add a new datapoint to the real-time graph.
     * 
     * @param time_in
     * @param value_in
     */
    public void addSample(double time_in_ms, double value_in) {
        DataSample newSample = new DataSample(time_in_ms, value_in, this);
        if (dataSamplingNeeded()) {
            synchronized (samples) {
                samples.add(newSample);
            }
        }

        CasseroleDataServer.getInstance().logger.addSample(newSample);
    }

    /**
     * Register an acquisition spec with the signal. This allows the signal to know
     * how long to hang on to its samples before discarding.
     * 
     * @param spec_in
     */
    public void addAcqSpec(AcqSpec spec_in) {
        synchronized (acqSpecs) {
            if (!acqSpecs.contains(spec_in)) {
                acqSpecs.add(spec_in);
            } else {
                CrashTracker.logAndPrint("[Signal] Warning: DataServer: Cannot add AcqSpec " + spec_in.toString()
                        + " - it has already been added to signal " + id);
            }
        }

    }

    /**
     * Remove an acquisition spec from the signal. Indicates an acqList no longer
     * needs to know about the samples in this signal.
     * 
     * @param spec_in
     */
    public void rmAcqSpec(AcqSpec spec_in) {
        synchronized (acqSpecs) {
            if (acqSpecs.contains(spec_in)) {
                acqSpecs.remove(spec_in);
            } else {
                CrashTracker.logAndPrint("[Signal] Warning: DataServer: Cannot remove AcqSpec " + spec_in.toString()
                        + " - it is not in signal " + id);
            }
        }
    }

    /**
     * Returns an array of all the samples currently in the queue, and then clears
     * it. Samples are returned with the oldest sample at index 0, newest at the end
     * of the array. The array of samples is downsampled to the requested rate if
     * needed. It is intended that the webserver would call this to transmit all
     * available data from previous iterations.
     */
    public DataSample[] getSamples(AcqSpec spec_in, double req_time_ms) {
        ArrayList<DataSample> retval;
        synchronized (samples) {
            int max_size = samples.size();
            retval = new ArrayList<DataSample>(max_size);

            // Start at newest sample
            int sample_idx = samples.size() - 1;

            while (sample_idx >= 0) {
                double sample_time_ms = samples.get(sample_idx).getSampleTime_ms();
                double earliest_sample_time_to_tx = req_time_ms - spec_in.getTxRate_ms();

                if (sample_time_ms >= earliest_sample_time_to_tx) {
                    if (spec_in.getSamplePeriod_ms() > 0) {
                        /* Need to downsample the data returned */

                        /* Working backward, find the next time that we need to tx a sample at. */
                        double next_sample_time_to_tx = spec_in.getTimestampOfMostRecentTXedSample_ms()
                                - spec_in.getSamplePeriod_ms();

                        if (sample_time_ms >= next_sample_time_to_tx) {
                            /* time to snag another sample */
                            retval.add(0, samples.get(sample_idx));
                            spec_in.setTimestampOfMostRecentTXedSample_ms(sample_time_ms);
                        }
                    } else {
                        /* return samples at native period */
                        retval.add(0, samples.get(sample_idx));
                    }

                    // Move on to next-older sample
                    sample_idx--;

                } else {
                    break; // we're done transmitting
                }
            }
        }

        trimSamples(req_time_ms);

        DataSample[] retArray = new DataSample[retval.size()];
        retval.toArray(retArray);
        return retArray;
    }

    /**
     * Discard samples which are too old to be useful
     */
    public void trimSamples(double time_now_ms) {
        double oldest_rqd_timestamp = time_now_ms;
        int trim_start_idx = 0;

        if (!dataSamplingNeeded()) {
            /* If nothing is being sampled, clear it all out and do nothing else */
            forceClearSamples();
            return;
        }

        /*
         * Determine, amongst all acq specs registered, the oldest sample that should be
         * maintained
         */
        for (AcqSpec spec : acqSpecs) {
            oldest_rqd_timestamp = Math.min(spec.getOldestRqdTimestamp_ms(time_now_ms), oldest_rqd_timestamp);
        }

        synchronized (samples) {
            /*
             * Go through the samples in the list until we find one which should be trimmed
             */
            for (trim_start_idx = samples.size() - 1; trim_start_idx >= 0; trim_start_idx--) {
                if (samples.get(trim_start_idx).getSampleTime_ms() < oldest_rqd_timestamp) {
                    break;
                }
            }

            /* Remove this sample and all prior ones */
            for (int i = trim_start_idx; i >= 0; i--) {
                samples.remove(i);
            }
        }

        return;
    }

    /**
     * Discards all samples from the buffer
     */
    public void forceClearSamples() {
        synchronized (samples) {
            samples.clear();
        }
    }

    /**
     * @return The name of the signal
     */
    public String getID() {
        return id;
    }

    /**
     * @return The User-friendly name of the signal
     */
    public String getDisplayName() {
        return display_name;
    }

    /**
     * @return The name of the units the signal is measured in.
     */
    public String getUnits() {
        return units;
    }

    boolean dataSamplingNeeded() {
        return acqSpecs.size() > 0;
    }

    public JSONObject getJSONProperties() {
        JSONObject retval = new JSONObject();
        retval.put("id", id);
        retval.put("display_name", display_name);
        retval.put("units", units);
        return retval;
    }
}
