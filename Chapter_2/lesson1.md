
## Lesson 1 - Getting Inputs from Drivers

### High Level Roadmap

Welcome to the wonderful world of writing code for robots! We've learned all the basics we'll need to get started already, so it's time to start taking the training wheels off!

Here in chapter 2, we're going to split up the task of writing robot code into a few steps. For practice, everyone will be writing _all_ the code for the robot - this is unusual and not the way code is generally done during the season. So, this will still be _slightly_ different than the actual season. But, much closer than we were in Chapter 1. 

At a high level, we're going to work from Inputs to Outputs. We'll start with the driver's inputs in this lesson, sensors in the next. Then, we'll go subsystem by subsystem to complete the implementation. The whole way through, we'll be testing our code and ensuring it's working properly.

Let's jump in!

### Getting Inputs from Operators

#### Background

This lesson, we're going to focus on how we get input _commands_ from the driver.

Generally, the driver will be holding some sort of a video game controller. They will move joysticks and push buttons to indicate a _desire_ or _command_ for the robot to do something. In software, we have to write code to read the data off of the video game controller, and _interpret_ it into a _command_ from the driver.

Often the transform is trivial: "button pushed" is the same as "intake desired". However, the change of name is signifigant. In doing so, we add a layer of _abstraction_. We isolate the _specific_ button on the controller from the _desire_ the operator has. 

This allows us to write our code in a more flexible way. For example, if we want to move the intake command from the A button to the B button, it's simple - just change the function that calcualtions the "intake desired" command to look at a different button. Everything else downstream stays the same.

Note, you don't technically _have_ to do this just to get robot code "working". This is a particular _architecture_ choice we make to isolate _what_ driver wants, from _how_ they specifically provide the input. We make this decision conciously: The answer to "What" is driven by the sorts of things that are on the robot - this is unlikely to change. However, the answer to "How" is driven by driver preferences and available controllers. This is much more likely to change over time. By keeping the translation from specific input method to generic _desire_ in its own isolated class, we reduce the difficulty of making changes later on.

#### The USB Controller

In order to really be able to test our code, we're going to start to need a bit of hardware. We'll need some  USB game controller just like the driver would use to test our code. This is because, unfortunately, there is no built-in "virtualization" solution yet.

You can use any windows-compatible USB controller you ahve around the house. If you don't have one, you could buy one - they're not super expensive, and useful for things beyond robots. Here's three options:

https://www.amazon.com/Controller-EasySMX-Joystick-Dual-Vibration-Trigger/dp/B06XBX1R55/ref=sr_1_6?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-6

https://www.amazon.com/SQDeal-Joystick-Controller-Vibration-Feedback/dp/B01GR9ZZTS/ref=sr_1_14?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-14

https://www.amazon.com/Extreme-3D-Pro-Joystick-Windows/dp/B00009OY9U/ref=sr_1_2?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-2

Or, the $0.00 option, install and configure vJoy - http://vjoystick.sourceforge.net/site/. This will _emulate_ the hardware. It's a bit clunky, but will allow you to confirm basic functionality.

#### Picking Buttons and Axes for Driver Inputs

Normally, as a team, we'll go throught the following throught process around week 1 or 2:

1) What sorts of information do the driver(s) need to communicate to the robot?
2) How would it be best to map those inputs to intuitive buttons, knobs, switches, and joysticks?

This often requires some back-and-forth discussion with the drive team and various design teams. It might change as drivers try out different schemes and develope opinions.

For the sake of brevity, we have the result of this discussion, on an XBOX 360 controller:

1) The Y axis of the left-hand joystick will indicate drivetrain forward/reverse motion command
2) The X axis of the right-hand joystick will indicate drivetrain rotation command
3) The triggers on the back of the back of the joystick indicate command for raising or lowering the elevator.
4) The A button indicates that cube intake is commanded.
5) The B button indicates that cube eject is commanded.

#### The Driver Input Class

We'll now create the class to converts information from a USB controller into these _commands_.

We're going to assume you have an XBox360 controller, since that's what we most commonly use on Casserole. You'll see other more generic classes avaialble too.

First, start by making a new `.java` file to hold our new class: `DriverInterface`.

Add the necessary lines of code to define our new class.

Add code to implement the following functions:

```java
    /**
     * Returns 1 when the driver wants the elevator to go up, 
     * -1 when they want it to go down,
     * 0 when they want it to stop.
     */
    double getElevatorRaiseLowerCmd(){
        return 0; //TODO return the actual value
    }

    /**
     * Returns True if the driver wants to intake a cube
     * False otherwise
     */
    boolean getCubeIntakeDesired(){
        return false; //TODO return the actual value
    }

    /**
     * Returns True if the driver wants to eject a cube
     * False otherwise
     */
    boolean getCubeEjectDesired(){
        return false; //TODO return the actual value
    }

    /**
     * Returns 1 when the driver wants the robot to move forward
     * -1 when they want it to go backward
     * 0 when they want it to stop.
     */
    double getFwdRevCmd(){
        return 0; //TODO return the actual value
    }

    /**
     * Returns 1 when the driver wants the robot to rotate right
     * -1 when they want it to rotate left
     * 0 when they want it no rotation
     */    
    double getRotateCmd(){
        return 0; //TODO return the actual value
    }
```

You'll also create a `public void update()` method to read the joystick and interpret the buttons and joysticks into the values those functions should return. You can expect that `update()` will get called every periodic loop.

Add signals to track the values of each driver command.

# How to Test

Declare a new instance of `DriverInterface` in `Robot.java`'s, and instantiate it in the `RobotInit` method. Call the update method in the periodic loops.

Run the code in simulation, open the web browser interface, navigate to the `State` page to view the signals.

Ensure that each of your new signals is present in the interface.

Enable the robot via the simulation control GUI, and move each input one by one. Ensure that manipulating an input on the USB controller causes a corresponding change in the signal which describes the driver command you are manipulating.
