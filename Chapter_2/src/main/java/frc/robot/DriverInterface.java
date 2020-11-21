package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.lib.DataServer.Annotations.Signal;

class DriverInterface {

    XboxController driverCtrl;

    @Signal(units = "cmd")
    double elevatorRaiseLowerCmd;
    @Signal(units = "cmd")
    boolean cubeIntakeDesired;
    @Signal(units = "cmd")
    boolean cubeEjectDesired;
    @Signal(units = "cmd")
    double fwdRevCmd;
    @Signal(units = "cmd")
    double rotateCmd;
    @Signal(units = "cmd")
    boolean visionAlignCmd;


    public DriverInterface(){
        driverCtrl = new XboxController(0);
    }

    public void update(){
        elevatorRaiseLowerCmd = driverCtrl.getTriggerAxis(Hand.kLeft) - driverCtrl.getTriggerAxis(Hand.kRight);
        fwdRevCmd = applyDeadband( -1.0 * driverCtrl.getY(Hand.kLeft));
        rotateCmd = applyDeadband( driverCtrl.getX(Hand.kRight));

        

        cubeIntakeDesired = driverCtrl.getAButton();
        cubeEjectDesired = driverCtrl.getBButton();

        visionAlignCmd = driverCtrl.getBumper(Hand.kRight);
    }

    public double applyDeadband(double in){
        boolean inputIsNegative = (in<0);
        double DEADBAND_HALF_WIDTH = 0.2;

        in = Math.abs(in);
        if(in < DEADBAND_HALF_WIDTH){
            in = 0;
        } else {
            in = (1.0) * (in - DEADBAND_HALF_WIDTH)/(1.0 - DEADBAND_HALF_WIDTH);
        }

        return inputIsNegative ? -1.0*in : in; 
    }


    double getElevatorRaiseLowerCmd(){
        return elevatorRaiseLowerCmd;
    }
    boolean getCubeIntakeDesired(){
        return cubeIntakeDesired;
    }
    boolean getCubeEjectDesired(){
        return cubeEjectDesired;
    }
    double getFwdRevCmd(){
        return fwdRevCmd;
    }
    double getRotateCmd(){
        return rotateCmd;
    }
    boolean getVisionAlignCmd(){
        return visionAlignCmd;
    }


}