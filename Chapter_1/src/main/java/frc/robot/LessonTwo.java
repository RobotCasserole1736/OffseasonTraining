package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;
    
    double val1;
    double val2;
    double val3;
    double val4;
    double val5;
    double val6;

    @Signal
    double result1;

    @Signal
    double result2;

    @Signal
    boolean result3;

    @Signal
    boolean raiseArmNow;

    @Signal
    int result4;

    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonTwoInit(){

        ////////////////////////////////////////////////
        // Write your new code after this line...
        val1= -34;
        val2= 3.5;
        val3= 76;
        result1= val1+val2+val3;

        val4=20;
        val5=11;
        val6=0.25;
        result2 = (val4-val5)/val6;

        
        if(result1 == 45.5 & result2 == 36 ) {
            result3 = true;
        } else {
            result3 = false;
        }
        // ...but before this line.
        ////////////////////////////////////////////////



    }

}