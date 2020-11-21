package frc.robot;

import org.photonvision.LEDMode;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;
import org.photonvision.PhotonUtils;

import edu.wpi.first.wpilibj.Spark;
import frc.lib.Calibration.Calibration;
import frc.lib.DataServer.Annotations.Signal;

class DrivetrainControl {

    Spark lhMotor1;
    Spark lhMotor2;
    Spark lhMotor3;
    Spark rhMotor1;
    Spark rhMotor2;
    Spark rhMotor3;

    PhotonCamera camera;

    @Signal(units = "cmd")
    double curFwdRevCmd;
    @Signal(units = "cmd")
    double curRotateCmd;
    @Signal(units = "cmd")
    boolean curVisionAutoAlignCmd;

    Calibration rotateKp;
    Calibration fwdRevKp;
    
    Calibration shootSetpointAngle;
    Calibration shootSetpointRange;

    static final double kCameraHeight = 2.5; // feet
    static final double kCameraPitch = 0.0;   // degrees
    static final double kTargetHeight = 16.0; // feet

    @Signal(units = "deg")
    double curAngleToTgt = 0;
    @Signal(units = "ft")
    double curRangeToTgt = 0;

    @Signal(units = "bool")
    boolean targetVisible;

    @Signal(units = "cmd")
    double lhSideCmd = 0;
    @Signal(units = "cmd")
    double rhSideCmd = 0;

    public DrivetrainControl(){
        lhMotor1 = new Spark(7);
        lhMotor2 = new Spark(8);
        lhMotor3 = new Spark(9);
        rhMotor1 = new Spark(4);
        rhMotor2 = new Spark(5);
        rhMotor3 = new Spark(6);

        rotateKp = new Calibration("Drivetrain Vision Rotation kP", 0.025);
        fwdRevKp = new Calibration("Drivetrain Vision FwdRev kP", 0);
        shootSetpointAngle = new Calibration("Drivetrain Vision Rotation Setpoint", 0, -90, 90);
        shootSetpointRange = new Calibration("Drivetrain Vision Range Setpoint (ft)", 25, 0, 90);

        camera = new PhotonCamera("MainVisionCamera");
    }

    public void setFwdRevCmd(double cmd){
        curFwdRevCmd = cmd;
    }

    public void setRotateCmd(double cmd){
        curRotateCmd = cmd;
    }

    public void setVisionAutoAlignCmd(boolean cmd){
        curVisionAutoAlignCmd = cmd;
    }

    public void update(){

        PhotonPipelineResult result = camera.getLatestResult();
        targetVisible = result.hasTargets();
        camera.getLEDMode(); //Need to do this to ensure our set's always work below.
        if(curVisionAutoAlignCmd){

            if(targetVisible){
                curAngleToTgt = result.getBestTarget().getYaw();
                curRangeToTgt = PhotonUtils.calculateDistanceToTargetMeters(
                    kCameraHeight, kTargetHeight, Math.toRadians(kCameraPitch), Math.toRadians(result.getBestTarget().getPitch()));


                double rotateError = curAngleToTgt - shootSetpointAngle.get();
                double rangeError  = curRangeToTgt - shootSetpointRange.get();

                curRotateCmd = rotateError * rotateKp.get();
                //curFwdRevCmd = rangeError * fwdRevKp.get();

            } else {
                curRotateCmd = 0;
            }
            camera.setLED(LEDMode.kOn);

        } else {
            // keep rotate/fwd-rev cmds the same
            curAngleToTgt = 0;
            curRangeToTgt = 0;
            camera.setLED(LEDMode.kOff);
        }

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