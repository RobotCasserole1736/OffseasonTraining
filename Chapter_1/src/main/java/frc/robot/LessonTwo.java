package frc.robot;

import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;
    double val1 = -5;
    double val2 = 2.5;
    double val3 = 7;
    boolean val4 = true;
    boolean val5 = false;
    boolean val6 = false;
    int val7 = 25;
    int val8 = 5;

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
        // Add your problem-solution code after this line...
        result1 = val1 + val2 + val3;
        result2 = val1 * -2 + (val2 / 5 * 70);
        result3 = val4 | val5;
        raiseArmNow = val5 & val4;
        result4 = val7 / val8;

    
        // ...but before this line.
        ////////////////////////////////////////////////



    }

}