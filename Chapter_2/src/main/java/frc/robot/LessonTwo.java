package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;

    double result1;
    double result2;
    boolean result3;
    boolean raiseArmNow;
    int result4;
    // ...but before this line.
    ////////////////////////////////////////////////

    Signal result1_sig = new Signal("L2 - result1", "double");
    Signal result2_sig = new Signal("L2 - result2", "double");
    Signal result3_sig = new Signal("L2 - result3", "bool");
    Signal result4_sig = new Signal("L2 - result4", "int");
    Signal raiseArmNow_sig = new Signal("L2 - raiseArmNow", "bool");

    void lessonTwoInit(){

        ////////////////////////////////////////////////
        // Write your new code after this line...


        // ...but before this line.
        ////////////////////////////////////////////////



    }

    void telemetryUpdate(){
        double sampleTime_ms = Timer.getFPGATimestamp()*1000;
        result1_sig.addSample(sampleTime_ms, result1);
        result2_sig.addSample(sampleTime_ms, result2);
        result3_sig.addSample(sampleTime_ms, result3);
        result4_sig.addSample(sampleTime_ms, result4);
        raiseArmNow_sig.addSample(sampleTime_ms, raiseArmNow);
    }

}