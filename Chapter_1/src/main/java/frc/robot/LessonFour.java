package frc.robot;

import frc.lib.DataServer.Annotations.Signal;


class LessonFour {

    ////////////////////////////////////////////////
    // Declare new variables after this line...
    @Signal
    double output1 = 0;
    @Signal
    double output2 = 0;

    boolean switch_NC_state = false; //Modify these values for testing Lesson 4.2
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
        // after this line...
        double Middlenumber = FindMiddle(-99,23,6007);

        System.out.println(Middlenumber);
        // ...but before this line.
        ////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////
    // Write your new code to DECLARE new methods 
    // after this line...
    double FindMiddle(double inputA, double inputB, double inputC){
        if(inputA > inputB && inputA > inputC){
            if(inputB > inputC){
                return inputB;
            } else{
                return inputC;
            }
        }   else if(inputB > inputA && inputB > inputC){
                if(inputA > inputC){
                return inputA;
            } else{
                return inputC;   
            }   
        } else{
                if(inputB > inputA){
                    return inputB;
                } else{
                    return inputA;
                }
            }
        }
    }

    


    // ...but before this line.
    ////////////////////////////////////////////////

