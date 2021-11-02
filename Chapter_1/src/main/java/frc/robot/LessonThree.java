package frc.robot;

import frc.lib.DataServer.Annotations.Signal;

class LessonThree {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    int ASideLen = 3;
    int BSideLen = 4;
    int CSideLen = 5;

    @Signal(units = "RPM")
    double actualSpeed_RPM = 0;
    @Signal(units = "RPM")
    double desiredSpeed_RPM = 2000;

    @Signal
    double motorCmd = 0;
    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonThreeInit(){

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 1 after this line...
        boolean cond1 = false;
        boolean cond2 = true;

        if(cond1){
            System.out.println("Ran IF");
        } else if (cond2) {
            System.out.println("Ran ELSE IF");
        } else {
            System.out.println("Ran ELSE");
    }

        // ...but before this line.
        ////////////////////////////////////////////////

        ////////////////////////////////////////////////
        // Write your new code for PROBLEM 2 after this line...

        

        // ...but before this line.
        ////////////////////////////////////////////////
    }

    void lessonThreeEnabledUpdate(){
        ////////////////////////////////////////////////
        // Write your new code for Lesson 3.3 after this line...

        motorCmd = 1.0; //Incorrect - Motor always runs. You'll need to change this.

        // ...but before this line.
        ////////////////////////////////////////////////

        simplePlantUpdate();
    }

    ////////////////////////////////////////////////
    // The code after this line is to help make lesson 3.3
    // act like a realistic robot. You shouldn't try to
    // change it, and can ignore it for now.

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