package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.DataServer.Annotations.Signal;

class ElevatorControl {

    VictorSP mainMotor;

    Encoder spoolEncoder;

    @Signal(units = "ft")
    double elevHeight_ft = 0;

    @Signal(units = "cmd")
    double curRaiseLowerCmd = 0;

    public ElevatorControl(){
        mainMotor = new VictorSP(6);
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


}