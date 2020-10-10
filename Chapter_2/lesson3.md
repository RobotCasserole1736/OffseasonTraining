## Lesson 3 - Outputs (Part 1)

We're nearing the end of our basic robot software! As a final major component, we're going to add code to actually command and control some output devices on the robot

### Motor Controllers

The majority of the mechanisms on the robot are moved by _motors_. A motor is a device which converts electrical energy into rotational force and motion. By applying more electrical energy (more voltage), you get more force, and therefor more rotation. You can reverse the direction of the voltage to reverse the direction of force and rotation.

Motor Controllers are the electronic devices which sit between the battery and the motor, and are what our software actually controls. They're like little "gate keepers" on the batery's electrical power - releaseing slowly and as-commanded to the motor.

Motor controllers can communicate with the roboRIO in different ways. There are two main classes: PWM and CAN.

#### PWM-Based 

PWM motor controllers get connected to the PWM bank of ports on the RIO. The communication is one-way: the RIO provides commands to the motor controller by rapidly turning voltage on the white wire on and off. The ratio of "on" time to "off" time indicates the command. The motor controller will react by changing the voltage to the motor and its LED color. But otherwise, it provides no feedback to the RIO.

"Spark" and "Victor SP" are names for two common PWM-based motor controllers.

#### CAN-Based "Smart" 

CAN-based motor controllers communicate with the RIO over the twisted yellow and green wires which make up the CAN bus. The chain of motor controllers is plugged into the CAN bus port on the roboRIO.

CAN devices can exchange information in a two-way format: The RIO can both send and recieve messages with the controller. This allows the controller to feed info back to the RIO, including information about battery voltage, motor current draw, speed, etc. In general, CAN-based motor controllers are more capiable than their PWM counterparts. They are also more expensive.

"Spark MAX" and "Talon SRX" are names for two common PWM-based motor controlelrs. 

### Other

There are a handful of infrequently-used styles of output. We'll mention them here for awareness.

#### Servo Motors

Servo motors are small motors with gearing and feedback mechanisms that allow them to achieve precise motion over a small range of degrees (usually ~180 or less). They're quite small and don't have a ton of pushing and pulling power, but can be good for small repeatable motion (like unlatching a lock on a mechanism).

#### Pneumatics

A "pneumatic system" is a set of components which utilize compressed air to cause mechanisms to move. They pack a lot of power into a small space, but are less controlla

Compressor/Tank/Pressure Sensor

"Solenoids" & Pneumatic Valves take and redirect this pressure to different sides of the cylender. This in turn exerts a force on mechanisms, which causes it to move.

In software, we have the ability to control the solenoids attached to these valves, which in turn triggers all the downstream motion. For this reason, when you're writing software, you'll see classes named after the solenoids, as that's the actual point of control we have. But, don't get too bent out of shape about it - it's all related to the pneumatics, and is still useful for controlling downstream motion.


You may notice there are actually two types of solenoid classes, `Single` and `Double`. A `Single` solenoid has two states (Rev/Fwd), and `Double` solenoid has three states (Rev/Off/Fwd). However, we almost never need that off state, and can usually ignore it. Therefor, unless mechanical dreams up some super fancy-schmancy mechanism, you can just ignore the differences between `Single` and `Double` solenoids. Just ask electrical team which type they're installing on the robot, and use that info to pick between the two.

### Adding Outputs to Our Code

We'll now go add some code for outputs to our robot. This will allow our inputs and sensors to start to provide commands to motors, and actually cause motion. Note that there will be a small bit of logic to write to actually perform the calculation of output values - we'll tackle that as well now, since it's pretty small.

### Drivetrain

We'll start with the subsystem requiring the most complex set of processing and outputs.

#### Code Changes

To add output control logic to the drivetrain, we'll first create the objects to command the motor controllers.

Find a class approprate to the spark motor controllers in the [WPILib Docs](https://first.wpi.edu/FRC/roborio/release/docs/java/). Instantiate six of them in the drivetrain class - three for the left, three for the right.

Add logic to recieve command inputs. The two things we can command our drivetrain to do are go forward and backward, or rotate. Something like this:

```java
    public void setFwdRevCmd(double cmd){
        //Assume 1.0 = go forward, 0.0 = stop, -1.0 = go reverse
        //TODO: store cmd somewhere to be used during the update() function
    }

    public void setRotateCmd(double cmd){
        //Assume 1.0 = rotate right,  0.0 = stop, -1.0 = rotate left
        //TODO: store cmd somewhere to be used during the update() function
    }
```

We know all the left-hand motors will be commanded to the same speed. Likewise, all the right-hand motors will be commanded the same way.

Add two new variables to the drivetrain class to track the left and right side commands. Make them signals as well:

```java
    //1.0 = left side full speed fwd, 0.0 = left side stop, -1.0 = left side full speed reverse
    @Signal(units = "cmd")
    double lhSideCmd = 0; 

    //1.0 = right side full speed fwd, 0.0 = right side stop, -1.0 = right side full speed reverse
    @Signal(units = "cmd")
    double rhSideCmd = 0;
```

Now, we'll add code to the `update()` method to perform the calculations required to convert our two input commands.

[Here's a quick video showing the types of motion the drivetrain can do as a response to both fwd/rev and rotate commands](https://www.youtube.com/watch?v=UCY77DnNxvA).

Think through how different values of your left/right side commands should be set in order to achieve motion. Think about how the different input commands determine what motion should be happening.

In `update()`, create a set of math statements and if/else statements to calculate the left/right side commands from the input commands.

Add logic to `Robot.java` to do the following:

1. Instantiate your drivetrain class
2. Call its update function periodically in teleop.
3. Read the driver commands and pass them to the drivetrain.

#### Testing

Run the code, open the website. On your USB controller, sweep the joysticks forward and backward, left and right. Do you see the signals changing value in the website?

Do the commands work correctly when applied independently? Do they work correctly when applied together?



