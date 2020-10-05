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


    public DriverInterface(){
        driverCtrl = new XboxController(0);
    }

    public void update(){
        elevatorRaiseLowerCmd = driverCtrl.getTriggerAxis(Hand.kLeft) - driverCtrl.getTriggerAxis(Hand.kRight);
        fwdRevCmd = -1.0 * driverCtrl.getY(Hand.kLeft);
        rotateCmd = driverCtrl.getX(Hand.kRight);

        cubeIntakeDesired = driverCtrl.getAButton();
        cubeEjectDesired = driverCtrl.getBButton();
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


}