## Lesson 7 - CAN Bus and Other IO

In this lesson, we'll cover the basics of the CAN bus, along with some of the other useful connected IO devices.

### What is the CAN Bus?

CAN stands for "Controlled Area Network". That doesn't matter to you at all.

On the robot, you'll be able to recognize the CAN bus because it has special wires. Small yellow and green wires, twisted together, jump from one device to another in a "daisy-chain" fashion. The two wires always carry opposite signals: Whenever one is high, the other is low. This makes them very robust against noise and (in a pinch) one of the lines getting disconnected.

The CAN bus has a special signalling design such that all the devices on it can talk to each other, and they all manage to mutually take turns. The roboRIO tends to initiate most conversations, driving commands to and reading status back from each device.

"Smart" motor controllers are the main thing on the CAN bus that we will be talking too. Less frequently, but often just as important, are the Pneumatics Control Module and the Power Distribution Panel.

You'll probably never have to directly interact with the CAN bus - all the major devices we've used on it have libraries which take care of this. However, it's good to know the basics of how it functions when debugging the robot.

### What sorts of things can it do?

Rapid, bidirectional communication is the main advantage of CAN devices. Where as a PWM motor controller can only communicate one value (how fast?) in one direction, CAN motor controllers support a much more diverse set of messages and configurations.

### Common Motor Controller Settings

Aside from the normal "set this speed" commands, we'll most commonly configure the motor controllers to do some sort of closed-loop control. Usually, we'll tell them to use some encoder or sensor attached directly to the controller as the "feedback" sensor. 

Limit switches and encoders can be connected, and their values read back over the CAN bus.

Smart motor controllers can measure current draw, bus voltage, and also control motors to these parameters. They can also be set up to limit the total current flowing through them (which helps reduce battery load).

The biggest downside of them: They're more expensive. To help keep robot cost down, we like to try to only use them when absolutely required. However, the advantages using them is usually worth it.

### Other IO Devices and Usage

#### PCM

The Pneumatics Control Module is the dedicated IO box which is used to control both the pneumatics compressor, all pneumatic solenoids, and read the pressure switch to prevent over-pressurizing the system.

Whenever we make a solenoid trigger or change direction, we're actually sending CAN bus messages out to the PCM.

The compressor is usually pretty self-sufficent: It will turn on and off as needed to try to maintain pressure. We usually do provide a manual "force-off" switch to the driver, which (mostly) gets used in the pit to deactivate the compressor while the robot's getting worked on and the noise gets annoying.

It's worthwhile to note that the analog "how-many-PSI" sensor does _not_ go through the PCM - it's usually completely separate.

#### PDP

The Power Distribution Panel also connects over CAN bus, though it has nothing we can configure. We only read back information from it.

The information we read back is the _electrical current draw_ of each motor. We rarely use this for any feedback-style control in software, but we do like to log it. Both electrical and mechanical team can use it to help diagnose issues and ensure the robot is working properly.

We can also read back the battery voltage and current draw. Historically we've used these to help determine battery state of charge. It's not critical for us to do so, but it's proven useful in previous years.
