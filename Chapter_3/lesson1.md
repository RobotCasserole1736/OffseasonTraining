## Lesson 1 - PID Control

This will serve as a basic introduction to a topic which seems to show up every year in the robot - PID control.

### What It Is

A "PID Controller" is a piece of functionality that plays the same role as our "bang-bang" controller did previously. Namely, as inputs, it takes as input an "actual" measurement of some physical quantity on the robot, a "desired" command for what that quantity should be. It outputs a single "control effort" command which can go to a motor, or some other actuator. Once properly designed and calibrated, it is usually quite effective at getting the "actual" value to match the "desired" value.

### How It Works

All PID controllers start by computing the difference between the actual and desired values. This difference is referred to as the "error".

The "PID" portion of the name is an acrynom for "Proportional-Integral-Derivative". These are some fancy-schmancy calculus vocab words to describe the three main components of the calculation. 

A PID controller is made up of three "terms" which are added together to get the control effort output.

The terms are just numbers, calculated as the following:

#### Proportional (P) Term

This term is equal to the error, multiplied by some tuned value "kP".

It gets very big when the error is big, and goes to zero as the error approaches zero.

#### Integral (I) Term

This term is equal to the sum of the error values over many previous loops, multipled by some tuned constant "kI".

It gets very big when the error has been non-zero for a _long time_. 

It is related to the calculus concept of the _integral_ of the error over some fixed previous time.

#### Derivative (D) Term

This term is equal to the difference between the current and previous error, multipled by some tuned constant "kD".

It gets very big when the error is changing rapidly.

It is related to the calculus concept of the _derivative_ (or instantaneous rate-of-change) of the error.

#### Tuning

A PID controller requires tuning to work well. This is the process of finding good values for kP, kI, and kD which cause good behavior. Too small, and the actual value will get "stuck" before it gets to the desired value. Too big, and the system will oscillate and and never converge to the right desired value (and, sometimes, self-destruct in the process).

Tuning generally requires having the real hardware available. There are some techniques for guessing the kP/kI/kD values beforehand, but generally final values need to be at least checked on real hardware.

Any time we decide to use a PID controller on the robot, we have to plan to ensure we have the time to tune it on the real robot.

### Common Applications

PID controllers show up in a number of different places. The most common one is on the drivetrain: For autonomous, we have to be able to move the wheels at certain pre-determined speeds to accomplish certain precise movements.

### In Software

PID controllers are implemented in software one of two ways:

 1. Using a library. CasseroleLib and WPILib both have `PIDController` classes to implement the required logic nicely.
 2. Using a motor controller. "Smart" motor controllers (Talon SRX, Spark MAX, etc.) can be configured to run their output under PID control with provided calibration values and specified sensors as their Actual value inputs. These generally are higher performance than a library running on the roboRIO, and are the preferred solution.

### Going Further

Additional information is available in a 5-part series at www.trickingrockstothink.com. This includes a hands-on walkthrough of tuning kP/kI/kD values on two common robot subsystems.


