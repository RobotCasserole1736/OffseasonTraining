package frc.robot;

class LessonFive {

    int counter = 0;

    int value1;
    double value2;
    boolean value3;

    void lessonFiveInit(){
        counter= 0;
    }

    double getRamp(int counter_in){
        int rampValue = counter_in % 15;
        return (double) rampValue;
    }

    void lessonFiveEnabledUpdate(){

        value1 = counter / 5;
        value2 = getRamp(value1);

        if(value2 > 5){
            value3 = true;
        } else {
            value3 = false;
        }

        counter++;
    }

}