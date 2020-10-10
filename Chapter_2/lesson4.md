## Lesson 4 - Outputs (Part 2)

### Elevator

The elevator is the next major system we'll tackle. 

#### Code Changes

We'll start with some basic logic, and add more complex logic in the future.

For now, our code will simply read a raise or lower command from the operator, and send it to a motor.

We'll need to add new logic to `ElevatorControl.java` to do the following:

In the constructor, instantiate a new motor controller object approprate to the type being used on the robot (see the readme.md file for more info).

In the `update()` function, use the values read in from the limit switches and the value passed into the `setRaiseLowerManualCmd()` method to calculate the command for the motor. Consider the description of the system in the readme.md file to determine how to calcualte the motor command.

Add code to pass the value you calculate for the motor command to the new motor controller you instantiated.

Add logic as needed in `Robot.java` to ensure that `update()` is getting called, and the value from the driver command is passed into `ElevatorControl`'s `setRaiseLowerManualCmd()` input function.

#### Testing

Push the joystick for the elevator up, ensure the motor turns such that the elevator moves upward. Push the joystic for the elevator down, ensure the motor turns such that the elevator moves downward.

Run the elevator all the way to the top and bottom of the range of travel - ensure the motor turns off when you hit the upper/lower stops.

### Intake (ie "CubeGrabber)

This is probably the simplest subsystem to write code for. 

#### Code Changes

We'll have to make a new class, since we don't have one yet. Make a new class named `CubeGrabberControl`, with the following methods:

```java
    public void setIntakeDesired(){
        //TODO: record that the intake should be running in the inward direction.
    }

    public void setEjectDesired(){
        //TODO: record that the intake should be running in the outward direction.
    }

    public void setStopDesired(){
        //TODO: record that the intake should stop running
    }

    public void update(){
        //TODO: Calculate the motor command and send it to the motor.
    }
```

Instantiate new motor controllers approprate to the hardware on the robot (see readme.md).

Add code inside each of the methods to fulfill the `//TODO` comments. Add signals as needed to help ensure the code you write is functional.

Add code to `Robot.java` to instantiate the new class. Pass commands from the driver to the `CubeGrabberControl` class in the approprate `*Periodic()` methods. Add code to call the `CubeGrabberControl`'s `update()` function at the approprate times.

#### Testing

Run the code, ensure any new signals you created show up in the website.

Ensure that when enabled, normally, the intake is not running. Ensure that when you press the intake and eject buttons on the joystick, the CubeGrabber motors run in the correct directions.

What happens when you press both buttons at the same time? Is it what you expect? What _should_ the robot do when both buttons are pressed?

