package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.DataServer.Annotations.Signal;

class CubeGrabberControl {

    Spark lhMotor;
    Spark rhMotor;

    @Signal(units = "cmd")
    double curMotorCmd = 0;

    final double INTAKE_MOTOR_CMD = 0.75;
    final double EJECT_MOTOR_CMD = -0.95;


    public CubeGrabberControl(){
        lhMotor = new Spark(7);
        rhMotor = new Spark(8);
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
        lhMotor.set(-1.0 * curMotorCmd);
        rhMotor.set(curMotorCmd);
    }


}