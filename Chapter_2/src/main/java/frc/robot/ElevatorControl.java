package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.DataServer.Signal;

class ElevatorControl {

    VictorSP mainMotor;

    Encoder spoolEncoder;

    double elevHeight_ft = 0;

    double curRaiseLowerCmd = 0;

    Signal elevHeight_sig;


    public ElevatorControl(){
        mainMotor = new VictorSP(6);
        elevHeight_sig = new Signal("Elevator Height", "ft");
        spoolEncoder = new Encoder(3,4);
        spoolEncoder.setDistancePerPulse(1.0/1024.0); //1024 pulses per foot
    }

    public void setRaiseLowerManualCmd(double cmd){
        curRaiseLowerCmd = cmd;
    }

    public void update(){
        elevHeight_ft = spoolEncoder.getDistance();
        mainMotor.set(curRaiseLowerCmd);
    }

    public void updateTelemetry(double time){
        elevHeight_sig.addSample(time, elevHeight_ft);
    }


}