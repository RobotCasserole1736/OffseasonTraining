package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
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

    DigitalInput topSwitchA;
    DigitalInput topSwitchB;
    DigitalInput bottomSwitch;

    @Signal(units = "bool")
    boolean topSwitchAState = false;
    @Signal(units = "bool")
    boolean topSwitchBState = false;
    @Signal(units = "bool")
    boolean bottomSwitchState = false;


    public ElevatorControl(){
        mainMotor = new VictorSP(6);
        spoolEncoder = new Encoder(3,4);
        spoolEncoder.setDistancePerPulse(1.0/1024.0); //1024 pulses per foot

        topSwitchA = new DigitalInput(0);
        topSwitchB = new DigitalInput(1);
        bottomSwitch = new DigitalInput(2);
    }

    public void setRaiseLowerManualCmd(double cmd){
        curRaiseLowerCmd = cmd;
    }

    public void update(){
        elevHeight_ft = spoolEncoder.getDistance();
        topSwitchAState = topSwitchA.get();
        topSwitchBState = topSwitchB.get();
        bottomSwitchState = bottomSwitch.get();

        double motorCmd = curRaiseLowerCmd;

        if(topSwitchAState & topSwitchBState){
            if(motorCmd > 0){
                motorCmd = 0;
            }
        } else if (bottomSwitchState){
            if(motorCmd < 0){
                motorCmd = 0;
            }
        }

        mainMotor.set(curRaiseLowerCmd);
    }


}