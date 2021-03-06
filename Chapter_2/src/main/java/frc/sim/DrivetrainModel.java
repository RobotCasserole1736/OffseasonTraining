package frc.sim;

import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Twist2d;
import frc.lib.DataServer.Signal;

class DrivetrainModel {

    Pose2d drivetrainPosition;

    PWMSim lhMotorCtrl1;
    PWMSim lhMotorCtrl2;
    PWMSim lhMotorCtrl3;
    PWMSim rhMotorCtrl1;
    PWMSim rhMotorCtrl2;
    PWMSim rhMotorCtrl3;

    final double DT_TRACK_WIDTH_FT = 2.5;
    final double DT_MAX_SPEED_FT_PER_SEC = 16.0;
    final double WHEEL_RADIUS_FT = 6.0/2.0/12.0; //six inch diameter wheels

    SimpleMotorWithMassModel lhSide;
    SimpleMotorWithMassModel rhSide;

    Signal xPosFtSig;
    Signal yPosFtSig;
    Signal tRotDegSig;

    Signal xPosDesFtSig;
    Signal yPosDesFtSig;
    Signal tRotDesDegSig;

    Field2d field;

    public DrivetrainModel(){
        lhMotorCtrl1 = new PWMSim(0);
        lhMotorCtrl2 = new PWMSim(1);
        lhMotorCtrl3 = new PWMSim(2);
        rhMotorCtrl1 = new PWMSim(3);
        rhMotorCtrl2 = new PWMSim(4);
        rhMotorCtrl3 = new PWMSim(5);
        lhSide = new SimpleMotorWithMassModel("DT Left", FtPerSectoRPM(DT_MAX_SPEED_FT_PER_SEC), 0.1, 250);
        rhSide = new SimpleMotorWithMassModel("DT Right", FtPerSectoRPM(DT_MAX_SPEED_FT_PER_SEC), 0.1, 250);
        xPosFtSig  = new Signal("botActPoseX", "ft");
        yPosFtSig  = new Signal("botActPoseY", "ft");
        tRotDegSig = new Signal("botActPoseT", "deg");
        xPosDesFtSig  = new Signal("botDesPoseX", "ft");
        yPosDesFtSig  = new Signal("botDesPoseY", "ft");
        tRotDesDegSig = new Signal("botDesPoseT", "deg");

        drivetrainPosition = new Pose2d();
        field = new Field2d();
    }

    public void modelReset(){
        drivetrainPosition = new Pose2d();
        lhSide.modelReset();
        rhSide.modelReset();
    }

    public void update(){
        // Eeh, kinda. Current draw won't be accurate if the student accidentally boogers up the motor controller outputs.
        double lhMotorCmd = (lhMotorCtrl1.getSpeed() + lhMotorCtrl2.getSpeed() + lhMotorCtrl3.getSpeed())/3;
        double rhMotorCmd = (rhMotorCtrl1.getSpeed() + rhMotorCtrl2.getSpeed() + rhMotorCtrl3.getSpeed())/3;

        lhSide.update(12.5, lhMotorCmd, 0.0);
        rhSide.update(12.5, rhMotorCmd, 0.0);

        double lhWheelDeltaFt = RPMtoFtPerSec(lhSide.getSpeed_RPM())*0.02;
        double rhWheelDeltaFt = RPMtoFtPerSec(rhSide.getSpeed_RPM())*0.02;

        Twist2d dtTwist = new Twist2d((lhWheelDeltaFt + rhWheelDeltaFt)/2, 0, (lhWheelDeltaFt - rhWheelDeltaFt) / (DT_TRACK_WIDTH_FT));

        drivetrainPosition = drivetrainPosition.exp(dtTwist);
    }

    public double RPMtoFtPerSec(double rot_spd_in){
        return rot_spd_in * (1.0/60.0) * (2.0 * Math.PI * WHEEL_RADIUS_FT);
    }

    public double FtPerSectoRPM(double lin_spd_in){
        return lin_spd_in / (1.0/60.0) / (2.0 * Math.PI * WHEEL_RADIUS_FT);
    }

    public void updateTelemetry(double time){
        lhSide.updateTelemetry(time);
        rhSide.updateTelemetry(time);
        xPosFtSig.addSample(time,  drivetrainPosition.getTranslation().getX());
        yPosFtSig.addSample(time,  drivetrainPosition.getTranslation().getY());
        tRotDegSig.addSample(time, drivetrainPosition.getRotation().getDegrees());
        xPosFtSig.addSample(time,  0);
        yPosFtSig.addSample(time,  0);
        tRotDegSig.addSample(time, 0);
        field.setRobotPose(drivetrainPosition);
    }

}