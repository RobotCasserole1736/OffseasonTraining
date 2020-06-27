package frc.lib.SignalMath;

import edu.wpi.first.wpilibj.Timer;

/*
 *******************************************************************************************
 * Copyright (C) 2017 FRC Team 1736 Robot Casserole - www.robotcasserole.org
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


/**
 * Class which implements a simple discrete-time derivative calculation. Output is equal to the rate
 * of change of the input signal in units of <signal> per second.
 *
 */
public class DerivativeCalculator {
    private double prev_time;
    private double prev_signal_val;


    /**
     * Constructor for derivative calculator. Note that you should instantiate one of these classes
     * per signal we wish to take the derivative of.
     */
    public DerivativeCalculator() {
        prev_time = Timer.getFPGATimestamp();
        prev_signal_val = 0;
    }


    /**
     * Given a new input value, output its derivative based on the last time the method was invoked.
     * The idea is you would read a signal, then call this method with this new signal value, so you
     * have the derivative right there. Then use it later on.
     * 
     * @param in input to take the derivative of
     * @return the derivative of the signal "in" in units of in/sec.
     */
    public double calcDeriv(double in) {
        double cur_time = Timer.getFPGATimestamp();
        double output = (in - prev_signal_val) / (cur_time - prev_time);
        prev_time = cur_time;
        prev_signal_val = in;
        return output;

    }

}
