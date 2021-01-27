package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.DataServer.Annotations.Signal;

class LessonTwo {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    boolean sampleBool = true;
    int sampleInt = 42;
    double sampleDouble = -1736.930;
    int val1= 6;
    int val2= -3;
    double val3 = 5.5;
    boolean val4= true;
    boolean val5= true;
    boolean val6= false;
    int val7= 50;
    int val8= 25;

}

@Signal
double result1;
double result1= (val1+val2+val3);

@Signal
double result2;
double result2= ((val1/val2)*val3)*-4;

@Signal
boolean result3;
boolean result3= val4||val6;

@Signal
boolean raiseArmNow;
boolean raiseArmNow=false;

@Signal
int result4;
int result4= val7/val8;

// ...but before this line.
////////////////////////////////////////////////

void lessonTwoInit(){

    ////////////////////////////////////////////////
    // Write your new code after this line...
    
    
    if(val6);
        {
        raiseArmNow =false;
        }if(val5 |val4);
        raiseArmNow = true;
        }
    // ...but before this line.
    ////////////////////////////////////////////////



}

}