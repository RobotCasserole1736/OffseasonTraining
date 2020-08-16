package frc.lib.DataServer;

public class AcqSpec {

    double tx_period_ms;
    double sample_period_ms;
    double timestamp_of_most_recent_txed_sample_ms;

    /**
     * Declare a specification for how an acquisition list ought to be returning
     * samples to the requesting client. A signal may use this info as well to
     * determine how long to keep samples around
     * 
     * @param tx_period_ms_in     Period of data transmission to the client
     * @param sample_period_ms_in Period of data sampling from the data source
     */
    public AcqSpec(double tx_period_ms_in, double sample_period_ms_in) {
        tx_period_ms = tx_period_ms_in;
        sample_period_ms = sample_period_ms_in;
        timestamp_of_most_recent_txed_sample_ms = 0;
    }

    public double getSamplePeriod_ms() {
        return sample_period_ms;
    }

    public double getTxRate_ms() {
        return tx_period_ms;
    }

    public void setTimestampOfMostRecentTXedSample_ms(double ts_in) {
        timestamp_of_most_recent_txed_sample_ms = ts_in;
    }

    public double getTimestampOfMostRecentTXedSample_ms() {
        return timestamp_of_most_recent_txed_sample_ms;
    }

    public double getOldestRqdTimestamp_ms(double time_now_ms) {
        return time_now_ms - tx_period_ms - sample_period_ms;
    }

    public boolean discardSample(double time_now_ms, double sample_time_ms) {
        if (sample_time_ms < getOldestRqdTimestamp_ms(time_now_ms)) {
            /* A conservative estimate as to when a sample can be discarded */
            return true;
        } else {
            return false;
        }
    }

}
