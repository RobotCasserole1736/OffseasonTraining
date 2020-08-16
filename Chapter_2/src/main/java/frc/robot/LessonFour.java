package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Signal;

class LessonFour {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    double output1 = 0;
    double output2 = 0;

    boolean switch_NC_state = false; //Modify these values for testing problem 2
    boolean switch_NO_state = true;


    boolean switch_pressed;
    boolean switch_faulted;

    // ...but before this line.
    ////////////////////////////////////////////////

    Signal ouput1_sig = new Signal("L4 - output1", "val");
    Signal ouput2_sig = new Signal("L4 - output2", "val");
    Signal switch_pressed_sig = new Signal("L4 - switch_pressed", "bool");
    Signal switch_faultedsig = new Signal("L4 - switch_faulted", "bool");

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

    void telementyUpdate(){
        double sampleTime_ms = Timer.getFPGATimestamp()*1000;
        ouput1_sig.addSample(sampleTime_ms, output1);
        ouput2_sig.addSample(sampleTime_ms, output2);
    }

}