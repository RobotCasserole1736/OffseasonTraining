## Lesson 3 - Outputs

### Motor Controllers

#### PWM-Based 

#### CAN-Based "Smart" 

### Other

#### Servo Motors

#### Pneumatics

A "pneumatic system" is a set of components which utilize compressed air to cause mechanisms to move. They pack a lot of power into a small space, but are less controlla

Compressor/Tank/Pressure Sensor

"Solenoids" & Pneumatic Valves take and redirect this pressure to different sides of the cylender. This in turn exerts a force on mechanisms, which causes it to move.

In software, we have the ability to control the solenoids attached to these valves, which in turn triggers all the downstream motion. For this reason, when you're writing software, you'll see classes named after the solenoids, as that's the actual point of control we have. But, don't get too bent out of shape about it - it's all related to the pneumatics, and is still useful for controlling downstream motion.


You may notice there are actually two types of solenoid classes, `Single` and `Double`. A `Single` solenoid has two states (Rev/Fwd), and `Double` solenoid has three states (Rev/Off/Fwd). However, we almost never need that off state, and can usually ignore it. Therefor, unless mechanical dreams up some super fancy-schmancy mechanism, you can just ignore the differences between `Single` and `Double` solenoids. Just ask electrical team which type they're installing on the robot, and use that info to pick between the two.



