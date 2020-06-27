package frc.lib.DataServer;

public class DataSample {
    double value;
    double sample_time_ms;
    Signal parentSig; // The signal that this sample belongs to.

    public DataSample(double time_ms_in, double val_in, Signal parentSig_in) {
        value = val_in;
        sample_time_ms = time_ms_in;
        parentSig = parentSig_in;
    }

    public double getSampleTime_ms() {
        return sample_time_ms;
    }

    public double getVal() {
        return value;
    }

    public Signal getParentSignal() {
        return parentSig;
    }

}
