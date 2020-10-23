package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;

class LessonThree {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    String dayStr = "Mon"; //Other values could be Sun, Tue, Thr, Wed, Sat, Fri, etc.
    int ASideLen = 3;
    int BSideLen = 4;
    int CSideLen = 5;

    boolean cond1 = false;
    boolean cond2 = true;

    @Signal(units = "RPM")
    double actualSpeed_RPM = 0;
    @Signal(units = "RPM")
    double desiredSpeed_RPM = 2500;

    @Signal
    int dayOfWeek;
    @Signal
    double motorCmd = 0;
    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonThreeInit(){

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 1 after this line...

        if(dayStr == "Sun"){
            dayOfWeek = 0;
        }else if(dayStr == "Mon"){
            dayOfWeek = 1;
        }else if(dayStr == "Tue"){
            dayOfWeek = 2;
        }else if(dayStr == "Wed"){
            dayOfWeek = 3;
        }else if(dayStr == "Thr"){
            dayOfWeek = 4;
        }else if(dayStr == "Fri"){
            dayOfWeek = 5;
        }else if(dayStr == "Sat"){
            dayOfWeek = 6;
        }else{
            System.out.println("Invalid input for day of the week. Please input a day of the week.");
        }

        // ...but before this line.
        ////////////////////////////////////////////////

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 2 after this line...

        if(CSideLen > 0 && BSideLen > 0 && ASideLen > 0 && CSideLen != ASideLen && CSideLen != BSideLen){
            if(Math.sqrt(CSideLen) == Math.sqrt(ASideLen) + Math.sqrt(BSideLen)){
                System.out.println("It's a right triangle!");
            }
        }

        // ...but before this line.
        ////////////////////////////////////////////////
    }

    void lessonThreeEnabledUpdate(){
        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 3 after this line...

        if(actualSpeed_RPM < desiredSpeed_RPM){
            motorCmd = 1.0;
        }else{
            motorCmd = 0;
        }

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


}