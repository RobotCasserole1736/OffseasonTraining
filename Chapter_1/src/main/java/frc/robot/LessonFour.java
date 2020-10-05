package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;


class LessonFour {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    @Signal
    double output1 = 0;
    @Signal
    double output2 = 0;

    boolean switch_NC_state = false; //Modify these values for testing problem 2
    boolean switch_NO_state = true;

    @Signal(units = "bool")
    boolean switch_pressed;

    @Signal(units = "bool")
    boolean switch_faulted;

    // ...but before this line.
    ////////////////////////////////////////////////
    void lessonFourInit(){
        //Nothing here yet....
    }

    void lessonFourEnabledUpdate(){
        ////////////////////////////////////////////////
        // Write your new code to CALL methods 
        // for PROBLEM 1 after this line...


        // ...but before this line.
        ////////////////////////////////////////////////

        ////////////////////////////////////////////////
        // Write your new code to CALL methods 
        // for PROBLEM 2 after this line...


        // ...but before this line.
        ////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////
    // Write your new code to DECLARE methods 
    // for PROBLEM 1 after this line...


    // ...but before this line.
    ////////////////////////////////////////////////

    ////////////////////////////////////////////////
    // Write your new code to DECLARE methods 
    // for PROBLEM 2 after this line...


    // ...but before this line.
    ////////////////////////////////////////////////

}