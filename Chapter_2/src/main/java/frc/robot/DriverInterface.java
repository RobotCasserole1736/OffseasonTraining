package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.lib.DataServer.Signal;

class DriverInterface {

    XboxController driverCtrl;

    double elevatorRaiseLowerCmd;
    boolean cubeIntakeDesired;
    boolean cubeEjectDesired;
    double fwdRevCmd;
    double rotateCmd;


    Signal elevatorRaiseLowerCmdSig;
    Signal cubeIntakeDesiredSig;
    Signal cubeEjectDesiredSig;
    Signal fwdRevCmdSig;
    Signal rotateCmdSig;


    public DriverInterface(){
        driverCtrl = new XboxController(0);
        elevatorRaiseLowerCmdSig = new Signal("Driver Input Elevator Raise Lower Cmd", "cmd");
        cubeIntakeDesiredSig = new Signal("Driver Input Cube Intake Desired", "cmd");
        cubeEjectDesiredSig = new Signal("Driver Input Cube Eject Desired", "cmd");
        fwdRevCmdSig = new Signal("Driver Input Drivetrain FwdRev Cmd", "cmd");
        rotateCmdSig = new Signal("Driver Input Drivetrain Rotate Cmd", "cmd");
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


    public void updateTelemetry(double time){
        elevatorRaiseLowerCmdSig.addSample(time, elevatorRaiseLowerCmd);
        cubeIntakeDesiredSig.addSample(time, cubeIntakeDesired);
        cubeEjectDesiredSig.addSample(time, cubeEjectDesired);
        fwdRevCmdSig.addSample(time, fwdRevCmd);
        rotateCmdSig.addSample(time, rotateCmd);
    }


}