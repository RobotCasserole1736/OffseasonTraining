package frc.robot;

import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;

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

    double val1 = -1736;
    double val2 = 20.3;
    double val3 = 40;

    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonTwoInit(){

        ////////////////////////////////////////////////
        // Add your problem-solution code after this line...
        
        result1 = val1 + val2 + val3;

        result2 = (val1 * -val2) / (val3 * 20);

        // ...but before this line.
        ////////////////////////////////////////////////



    }

}