package frc.lib.DataServer;

import java.util.HashSet;
import java.util.TimerTask;
import java.util.Timer;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import frc.lib.Util.CrashTracker;

public class AcqList {

    HashSet<Signal> signals;
    AcqSpec acquisitionSpec;
    String id;
    RemoteEndpoint client;

    private Timer updater = null;

    public AcqList(String id_in, double tx_period, double sample_period, String[] signal_uuids,
            RemoteEndpoint client_in) {
        signals = new HashSet<Signal>();
        acquisitionSpec = new AcqSpec(tx_period, sample_period);
        id = id_in;
        client = client_in;

        for (String sig_id : signal_uuids) {
            addSignal(CasseroleDataServer.getInstance().getSignalFromId(sig_id));
        }

    }

    public void addSignal(Signal in) {
        signals.add(in);
    }

    public void startTransmit() {
        for (Signal sig : signals) {
            sig.addAcqSpec(acquisitionSpec);
        }

        updater = new java.util.Timer("DataServer DAQ Transmit for " + id);
        updater.scheduleAtFixedRate(new dataAcqTask(), 0, Math.round(acquisitionSpec.getTxRate_ms()));
    }

    public void stopTransmit() {
        for (Signal sig : signals) {
            sig.rmAcqSpec(acquisitionSpec);
        }

        updater.cancel();
    }

    public JSONObject getData(double req_time_ms) {
        JSONObject tx_obj = new JSONObject();
        JSONArray signal_array = new JSONArray();

        // Build up JSON structure of samples recorded.
        for (Signal sig : signals) {

            JSONArray sample_arr = new JSONArray();

            DataSample[] samples = sig.getSamples(acquisitionSpec, req_time_ms);
            if (samples != null) {
                for (DataSample samp : samples) {
                    if (samp != null) {
                        JSONObject sample_obj = new JSONObject();
                        sample_obj.put("time", samp.getSampleTime_ms());
                        sample_obj.put("val", samp.getVal());
                        sample_arr.add(sample_obj);
                    }
                }
            }

            JSONObject signal_obj = new JSONObject();
            signal_obj.put("id", sig.getID());
            signal_obj.put("samples", sample_arr);

            signal_array.add(signal_obj);
        }

        // package array into object
        tx_obj.put("type", "daq_update");
        tx_obj.put("daq_id", id);
        tx_obj.put("signals", signal_array);

        return tx_obj;

    }

    /**
     * Timer task to periodically broadcast data to the client. Java multithreading
     * magic here, do not touch! If you touch this, you will face the wrath of
     * Chitulu, god of data streaming servers. May the oceans of 1's and 0's rise to
     * praise him.
     * 
     * @author Chris Gerth
     *
     */
    private class dataAcqTask extends TimerTask implements WriteCallback {
        public void run() {
            client.sendString(getData(edu.wpi.first.wpilibj.Timer.getFPGATimestamp()*1000.0).toJSONString(), this);
        }

        @Override
        public void writeFailed(Throwable arg0) {
            CrashTracker.logAndPrint("[AcqList] Endpoint for acq list " + id + " threw error.");
            CrashTracker.logAndPrint(arg0.getMessage());
            stopTransmit();
        }

        @Override
        public void writeSuccess() {
            return; // yay writes are happy
        }

    }

}
