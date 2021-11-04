package frc;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import frc.lib.DataServer.Signal;

public class PoseTelemetry {

    public static Field2d field = new Field2d();
    
    //Desired Position says where path planning logic wants the
    // robot to be at any given time. 
    Signal xPosDesFtSig;
    Signal yPosDesFtSig;
    Signal tRotDesDegSig;

    //Estimated position says where you think your robot is at
    // Based on encoders, motion, vision, etc.
    Signal xPosEstFtSig;
    Signal yPosEstFtSig;
    Signal tRotEstDegSig;

    //Actual position defines wherever the robot is actually at
    // at any time. It is unknowable in real life. The simulation
    // generates this as its primary output.
    Signal xPosActFtSig;
    Signal yPosActFtSig;
    Signal tRotActDegSig;

    Pose2d actualPose = new Pose2d();
    Pose2d desiredPose = new Pose2d();
    Pose2d estimatedPose = new Pose2d();

    /* Singleton stuff */
    private static PoseTelemetry inst = null;
    public static synchronized PoseTelemetry getInstance() {
        if(inst == null)
            inst = new PoseTelemetry();
        return inst;
    }


    private PoseTelemetry(){
        xPosDesFtSig     = new Signal("botDesPoseX", "ft");
        yPosDesFtSig     = new Signal("botDesPoseY", "ft");
        tRotDesDegSig    = new Signal("botDesPoseT", "deg");

        xPosEstFtSig     = new Signal("botEstPoseX", "ft");
        yPosEstFtSig     = new Signal("botEstPoseY", "ft");
        tRotEstDegSig    = new Signal("botEstPoseT", "deg");

        xPosActFtSig     = new Signal("botActPoseX", "ft");
        yPosActFtSig     = new Signal("botActPoseY", "ft");
        tRotActDegSig    = new Signal("botActPoseT", "deg");

        SmartDashboard.putData("Field", field);

    }

    public void setActualPose(Pose2d act){
        actualPose = act;
    }
    public void setDesiredPose(Pose2d des){
        desiredPose = des;
    }
    public void setEstimatedPose(Pose2d est){
        estimatedPose = est;
    }

    public void update(){
        double time = Timer.getFPGATimestamp() * 1000;
        //Random adds/multiplies to account for difference in reference frame for this website
        xPosActFtSig.addSample(time,  Units.metersToFeet(-1.0*actualPose.getTranslation().getY()) + 13.75);
        yPosActFtSig.addSample(time,  Units.metersToFeet(actualPose.getTranslation().getX()));
        tRotActDegSig.addSample(time, actualPose.getRotation().getDegrees() + 90.0);

        xPosDesFtSig.addSample(time,  Units.metersToFeet(-1.0*desiredPose.getTranslation().getY()) + 13.75);
        yPosDesFtSig.addSample(time,  Units.metersToFeet(desiredPose.getTranslation().getX()));
        tRotDesDegSig.addSample(time, desiredPose.getRotation().getDegrees() + 90.0);

        xPosEstFtSig.addSample(time,  Units.metersToFeet(-1.0*estimatedPose.getTranslation().getY()) + 13.75);
        yPosEstFtSig.addSample(time,  Units.metersToFeet(estimatedPose.getTranslation().getX()));
        tRotEstDegSig.addSample(time, estimatedPose.getRotation().getDegrees() + 90.0);

        field.getObject("DesPose").setPose(desiredPose);
        field.getObject("Robot").setPose(actualPose);
        field.getObject("EstPose").setPose(estimatedPose);
    }




    
}