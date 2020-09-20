package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.DataServer.Signal;

class ElevatorControl {

    VictorSP mainMotor;

    Encoder spoolEncoder;

    double elevHeight_ft = 0;

    double curRaiseLowerCmd = 0;

    Signal elevHeight_sig;
    Signal topSwitchA_sig;
    Signal topSwitchB_sig;
    Signal bottomSwitch_sig;

    DigitalInput topSwitchA;
    DigitalInput topSwitchB;
    DigitalInput bottomSwitch;

    boolean topSwitchAState = false;
    boolean topSwitchBState = false;
    boolean bottomSwitchState = false;


    public ElevatorControl(){
        mainMotor = new VictorSP(6);
        elevHeight_sig = new Signal("Elevator Height", "ft");
        topSwitchA_sig = new Signal("Elevator Top Switch A State", "bool");
        topSwitchB_sig = new Signal("Elevator Top Switch B State", "bool");
        bottomSwitch_sig = new Signal("Elevator Bottom Switch State", "bool");
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

    public void updateTelemetry(double time){
        elevHeight_sig.addSample(time, elevHeight_ft);
        topSwitchA_sig.addSample(time, topSwitchAState);
        topSwitchB_sig.addSample(time, topSwitchBState);
        bottomSwitch_sig.addSample(time, bottomSwitchState);
    }


}