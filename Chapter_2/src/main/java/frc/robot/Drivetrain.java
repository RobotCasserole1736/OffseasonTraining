package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.DataServer.Annotations.Signal;

public class Drivetrain {

    @Signal
    double curFwdRevCmd = 0;
    @Signal
    double curRotateCmd = 0;

    Spark motorCtrl_Left1;
    Spark motorCtrl_Left2;
    Spark motorCtrl_Left3;

    Spark motorCtrl_Right1;
    Spark motorCtrl_Right2;
    Spark motorCtrl_Right3;

    // Constructor
    public Drivetrain(){

        motorCtrl_Left1 = new Spark(7);
        motorCtrl_Left2 = new Spark(8);
        motorCtrl_Left3 = new Spark(9);

        motorCtrl_Right1 = new Spark(4);
        motorCtrl_Right2 = new Spark(5);
        motorCtrl_Right3 = new Spark(6);

    }

    public void update(){
        double leftMotorSpeed = 0;
        double rightMotorSpeed = 0;

        leftMotorSpeed  = curFwdRevCmd - curRotateCmd;
        rightMotorSpeed = curFwdRevCmd + curRotateCmd;
     
        motorCtrl_Left1.set(leftMotorSpeed);
        motorCtrl_Left2.set(leftMotorSpeed);
        motorCtrl_Left3.set(leftMotorSpeed);
        motorCtrl_Right1.set(rightMotorSpeed);
        motorCtrl_Right2.set(rightMotorSpeed);
        motorCtrl_Right3.set(rightMotorSpeed);
    }

    public void setFwdRevCmd(double cmd_in){
        curFwdRevCmd = cmd_in;
    }

    public void setRotateCmd(double cmd_in){
        curRotateCmd = cmd_in;
    }
    
}
