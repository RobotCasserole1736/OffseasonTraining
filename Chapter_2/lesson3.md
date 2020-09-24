## Lesson 3 - Outputs

We're nearing the end of our basic robot software! As a final major component, we're going to add code to actually command and control some output devices on the robot

### Motor Controllers

The majority of the mechanisms on the robot are moved by _motors_. A motor is a device which converts electrical energy into rotational force and motion. By applying more electrical energy (more voltage), you get more force, and therefor more rotation. You can reverse the direction of the voltage to reverse the direction of force and rotation.

Motor Controllers are the electronic devices which sit between the battery and the motor, and are what our software actually controls. They're like little "gate keepers" on the batery's electrical power - releaseing slowly and as-commanded to the motor.

Motor controllers can communicate with the roboRIO in different ways. There are two main classes: PWM and CAN.

#### PWM-Based 

PWM motor controllers get connected to the 

#### CAN-Based "Smart" 

### Other

#### Servo Motors

#### Pneumatics

A "pneumatic system" is a set of components which utilize compressed air to cause mechanisms to move. They pack a lot of power into a small space, but are less controlla

Compressor/Tank/Pressure Sensor

"Solenoids" & Pneumatic Valves take and redirect this pressure to different sides of the cylender. This in turn exerts a force on mechanisms, which causes it to move.

In software, we have the ability to control the solenoids attached to these valves, which in turn triggers all the downstream motion. For this reason, when you're writing software, you'll see classes named after the solenoids, as that's the actual point of control we have. But, don't get too bent out of shape about it - it's all related to the pneumatics, and is still useful for controlling downstream motion.


You may notice there are actually two types of solenoid classes, `Single` and `Double`. A `Single` solenoid has two states (Rev/Fwd), and `Double` solenoid has three states (Rev/Off/Fwd). However, we almost never need that off state, and can usually ignore it. Therefor, unless mechanical dreams up some super fancy-schmancy mechanism, you can just ignore the differences between `Single` and `Double` solenoids. Just ask electrical team which type they're installing on the robot, and use that info to pick between the two.



