/*
 *******************************************************************************************
 * Copyright (C) 2020 FRC Team 1736 Robot Casserole - www.robotcasserole.org
 *******************************************************************************************
 *
 * This software is released under the MIT Licence - see the license.txt
 *  file in the root of this repo.
 *
 * Non-legally-binding statement from Team 1736:
 *  Thank you for taking the time to read through our software! We hope you
 *   find it educational and informative! 
 *  Please feel free to snag our software for your own use in whatever project
 *   you have going on right now! We'd love to be able to help out! Shoot us 
 *   any questions you may have, all our contact info should be on our website
 *   (listed above).
 *  If you happen to end up using our software to make money, that is wonderful!
 *   Robot Casserole is always looking for more sponsors, so we'd be very appreciative
 *   if you would consider donating to our club to help further STEM education.
 */

package frc.lib.Util;

import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.Timer;

public class ExecutionTimeTracker{

    String name_prefix = "";

    double threshold_sec;
    int num_slow_calls;

    public ExecutionTimeTracker(String name_prefix_in, double threshold_sec_in){
        name_prefix = name_prefix_in;
        threshold_sec = threshold_sec_in;
    }

    public void reset(){
        num_slow_calls = 0;
    }

    public void logAndReset(){
        CrashTracker.logGenericMessage("[ExecTimeTracker]: " + name_prefix + ": " + Integer.toString(num_slow_calls) + " calls exceeded threshold.");
        reset();
    }

    public void run(Object objectToUpdate, Method methodToRun){
        double startTime = Timer.getFPGATimestamp();
        try{
            methodToRun.invoke(objectToUpdate);
        } catch(Exception e){
            CrashTracker.logThrowableCrash(e);
        }
        double endTime = Timer.getFPGATimestamp();
        double elapsedTime = endTime - startTime;

        if(elapsedTime > threshold_sec){
            num_slow_calls++;
        }
    }
}