package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.DataServer.Annotations.Signal;

class DrivetrainControl {

    Spark lhMotor1;
    Spark lhMotor2;
    Spark lhMotor3;
    Spark rhMotor1;
    Spark rhMotor2;
    Spark rhMotor3;

    
    double curFwdRevCmd;
    double curRotateCmd;

    @Signal(units = "cmd")
    double lhSideCmd = 0;
    @Signal(units = "cmd")
    double rhSideCmd = 0;

    public DrivetrainControl(){
        lhMotor1 = new Spark(0);
        lhMotor2 = new Spark(1);
        lhMotor3 = new Spark(2);
        rhMotor1 = new Spark(3);
        rhMotor2 = new Spark(4);
        rhMotor3 = new Spark(5);
    }

    public void setFwdRevCmd(double cmd){
        curFwdRevCmd = cmd;
    }

    public void setRotateCmd(double cmd){
        curRotateCmd = cmd;
    }

    public void update(){
        lhSideCmd = limitRange(curFwdRevCmd + curRotateCmd);
        rhSideCmd = limitRange(curFwdRevCmd - curRotateCmd);

        lhMotor1.set(-1.0 * lhSideCmd);
        lhMotor2.set(-1.0 * lhSideCmd);
        lhMotor3.set(-1.0 * lhSideCmd);
        rhMotor1.set(rhSideCmd);
        rhMotor2.set(rhSideCmd);
        rhMotor3.set(rhSideCmd);
    }

    public double limitRange(double in){
        if(in > 1.0){
            return 1.0;
        } else if (in < -1.0){
            return -1.0;
        } else {
            return in;
        }
    }

}