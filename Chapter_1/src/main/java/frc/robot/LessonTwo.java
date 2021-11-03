package frc.robot;

import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...

    double val1 = -18;
    double val2 = 6.9;
    double val3 = 21;

    boolean val4 = true;
    boolean val5 = false;
    boolean val6 = true;

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

    // ...but before this line.
    ////////////////////////////////////////////////

    void lessonTwoInit(){

        ////////////////////////////////////////////////
        // Add your problem-solution code after this line...
result1 = val1 + val2 + val3;

result2 = (val2 + val3) / val1 *(-29);

result3 = val4 | val5;

raiseArmNow = val4 & !val5;

        // ...but before this line.
        ////////////////////////////////////////////////



    }

}