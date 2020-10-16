package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;

    double val1 = 5;
    double val2 = 5.2;
    double val3 = -5;

    boolean val4 = true;
    boolean val5 = true;
    boolean val6 = false;

    int val7 = 20;
    int val8 = 15;

    @Signal
    double result1 = val1+val2+val3;

    @Signal
    double result2 = (val1*(val2/val3))*-10-4;

    @Signal
    boolean result3 = val4 || val6;

    @Signal
    boolean raiseArmNow = false;
    
    @Signal
    int result4 = val7/val8;

    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonTwoInit(){

        ////////////////////////////////////////////////
        // Write your new code after this line...

        if(val5){
            raiseArmNow = false;
        }else if(val4){
            raiseArmNow=true;
        }else{
            raiseArmNow=false;
        }

        // ...but before this line.
        ////////////////////////////////////////////////



    }

}