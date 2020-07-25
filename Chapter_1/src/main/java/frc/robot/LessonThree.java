package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Signal;

class LessonThree {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    String dayStr = "Mon"; //Other values could be Sun, Tue, Thr, Wed, Sat, Fri, etc.
    int ASideLen = 3;
    int BSideLen = 4;
    int CSideLen = 5;

    double actualSpeed_RPM = 0;
    double desiredSpeed_RPM = 2000;

    int dayOfWeek;
    double motorCmd = 0;
    // ...but before this line.
    ////////////////////////////////////////////////

    Signal dayOfWeek_sig = new Signal("L3 - dayOfWeek", "day idx");
    Signal actualSpeed_sig = new Signal("L3 - actualSpeed", "RPM");
    Signal desiredSpeed_sig = new Signal("L3 - desiredSpeed", "RPM");
    Signal motorCmd_sig = new Signal("L3 - motorCmd", "cmd");


    void lessonThreeInit(){

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 1 after this line...


        // ...but before this line.
        ////////////////////////////////////////////////

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 2 after this line...


        // ...but before this line.
        ////////////////////////////////////////////////
    }

    void lessonThreeEnabledUpdate(){
        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 3 after this line...

        motorCmd = 1.0; //Incorrect - Motor always runs. You'll need to change this.

        // ...but before this line.
        ////////////////////////////////////////////////
        simplePlantUpdate();
    }

    void lessonThreeDisabledUpdate(){
        motorCmd = 0;
        simplePlantUpdate();
    }

    void simplePlantUpdate(){
        final double SHOOTER_MAX_RPM = 5800;
        final double SHOOTER_ACCEL_RPM_PER_SEC = 2000;
        final double SHOOTER_DECEL_RPM_PER_SEC = 3000;

        double shooterRunoutRPM = SHOOTER_MAX_RPM * Math.max(Math.min(1.0,motorCmd),-1.0);
        double errFrac = (shooterRunoutRPM - actualSpeed_RPM)/SHOOTER_MAX_RPM;
        if(errFrac > 0.0){
            actualSpeed_RPM += SHOOTER_ACCEL_RPM_PER_SEC * 0.02 * errFrac;
        } else {
            actualSpeed_RPM += SHOOTER_DECEL_RPM_PER_SEC * 0.02  * errFrac;
        }
    }

    void telementyUpdate(){
        double sampleTime_ms = Timer.getFPGATimestamp()*1000;
        dayOfWeek_sig.addSample(sampleTime_ms, dayOfWeek);
        actualSpeed_sig.addSample(sampleTime_ms, actualSpeed_RPM);
        desiredSpeed_sig.addSample(sampleTime_ms, desiredSpeed_RPM);
        motorCmd_sig.addSample(sampleTime_ms, motorCmd);
    }

}