package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.DataServer.Signal;

class ElevatorControl {

    Spark mainMotor;

    double elevHeight_ft = 0; //TODO: get elevator height from encoder.

    double curRaiseLowerCmd = 0;

    Signal elevHeight_sig;


    public ElevatorControl(){
        mainMotor = new Spark(4);
        elevHeight_sig = new Signal("Elevator Height", "ft");
    }

    public void setRaiseLowerManualCmd(double cmd){
        curRaiseLowerCmd = cmd;
    }

    public void update(){
        mainMotor.set(curRaiseLowerCmd);
    }

    public void updateTelemetry(double time){
        elevHeight_sig.addSample(time, elevHeight_ft);
    }


}