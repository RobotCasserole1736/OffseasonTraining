package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import frc.lib.DataServer.Signal;

class CubeGrabberControl {

    VictorSP mainMotor;

    double elevHeight_ft = 0;

    double curMotorCmd = 0;

    Signal cubeIntakeMotorCmd_sig;

    final double INTAKE_MOTOR_CMD = 0.75;
    final double EJECT_MOTOR_CMD = -0.95;


    public CubeGrabberControl(){
        mainMotor = new VictorSP(5);
        cubeIntakeMotorCmd_sig = new Signal("Cube Intake Motor Cmd", "cmd");
    }

    public void setIntakeDesired(){
        curMotorCmd = INTAKE_MOTOR_CMD;
    }

    public void setEjectDesired(){
        curMotorCmd = EJECT_MOTOR_CMD;
    }

    public void setStopDesired(){
        curMotorCmd = 0.0;
    }

    public void update(){
        mainMotor.set(curMotorCmd);
    }

    public void updateTelemetry(double time){
        cubeIntakeMotorCmd_sig.addSample(time, curMotorCmd);
    }


}