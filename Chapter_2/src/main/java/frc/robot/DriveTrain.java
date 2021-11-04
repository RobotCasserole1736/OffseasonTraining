package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.DataServer.Annotations.Signal;

public class DriveTrain {
    
    @Signal
    double curFwdRevCmd = 0; 
    
    @Signal
    double curRotateCmd = 0;

    Spark motorCtrl_left1;
    Spark motorCtrl_left2;
    Spark motorCtrl_left3;

    Spark motorCtrl_right1;
    Spark motorCtrl_right2;
    Spark motorCtrl_right3;

    //Constructor
    public DriveTrain(){
       
        motorCtrl_left1 = new Spark(7);
        motorCtrl_left2 = new Spark(8);
        motorCtrl_left3 = new Spark(9);

        motorCtrl_right1 = new Spark(4);
        motorCtrl_right2 = new Spark(5);
        motorCtrl_right3 = new Spark(6);
    }

    public void update(){
        double leftMotorSpeed = 0;
        double rightMotorSpeed = 0;
        
        leftMotorSpeed = curFwdRevCmd - curRotateCmd;
        rightMotorSpeed = curFwdRevCmd + curRotateCmd;

        motorCtrl_left1.set(leftMotorSpeed);
        motorCtrl_left2.set(leftMotorSpeed);
        motorCtrl_left3.set(leftMotorSpeed);

        motorCtrl_right1.set(rightMotorSpeed);
        motorCtrl_right2.set(rightMotorSpeed);
        motorCtrl_right3.set(rightMotorSpeed);
    }

    public void setFwdRevCmd(double cmd_in){
        curFwdRevCmd = cmd_in;

    }

    public void setRotateCmd(double cmd_in){
        curRotateCmd = cmd_in;
    }

}
