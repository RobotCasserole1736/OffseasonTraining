package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;

class LessonThree {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    String dayStr = "Mon"; //Other values could be Sun, Tue, Thr, Wed, Sat, Fri, etc.
    public String[] DOW= {"Mon","Tue","Wed","Thr","Fri","Sat"};
    Boolean isADayOfWeek= false;


    double ASideLen = 3;
    double ASideLenSqr = ASideLen * ASideLen;
    double BSideLen = 4;
    double BSideLenSqr= BSideLen * BSideLen;
    double CSideLen = 5;
    double CSideLenSqr= CSideLen*CSideLen;

    @Signal(units = "RPM")
    double actualSpeed_RPM = 0;
    @Signal(units = "RPM")
    double desiredSpeed_RPM = 2000;

    @Signal
    int dayOfWeek;
    @Signal
    double motorCmd = 0;
    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonThreeInit(){

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 1 after this line...
        for (String test: DOW){
            if(dayStr == test){
                isADayOfWeek = true;
                break;
            }

        }
        if(isADayOfWeek==true){
            if(dayStr.equals("Sun")){
                dayOfWeek = 0;
            } else if(dayStr.equals("Mon")){
                dayOfWeek = 1;
            } else if(dayStr.equals("Tue")){
                dayOfWeek = 2;
            } else if(dayStr.equals("Wed")){
                dayOfWeek = 3;
            } else if(dayStr.equals("Thr")){
                dayOfWeek = 4;
            } else if(dayStr.equals("Fri")){
                dayOfWeek = 5;
            } else if(dayStr.equals("Sat")){
                dayOfWeek = 6;
            }
        } else {
            System.out.println("Error input was not a day of Week");
        }
        // ...but before this line.
        ////////////////////////////////////////////////

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 2 after this line...
        if(ASideLen>0 && BSideLen>0 && CSideLen>0){
            if (CSideLen>BSideLen && CSideLen>BSideLen){
                if(CSideLenSqr == BSideLenSqr+ ASideLenSqr){
                    System.out.println("It's a right triangle");
                }
            }
        } else {
            System.out.println("How is this even a triangle");
        }

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


}