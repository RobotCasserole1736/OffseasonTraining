# Chapter 2 - The First Robot

In Chapter 1, we introduced some of the basics of software writing and the toolset we're using.

We'll move on to applying those concepts to making software for an actual robot that is at the warehouse!

### Background - the "Theoretical" Robot We will be Programming

This background info is pertenant to all lessons in this Chapter, so we put it here.

The robot we will be programming is designed to play (most of) the 2018 game. If you haven't seen it, at least check out [the sumamry release video here](https://www.youtube.com/watch?v=HZbdwYiCY74), and maybe the [full game manual](https://firstfrc.blob.core.windows.net/frc2018/Manual/2018FRCGameSeasonManual.pdf) if interested.

Our robot will be able to do everythin in the 2018 game _except climb_. Looks very similar to our 2018 bot, because it is. 

![2018_robot.jpg](https://robotcasserole.org/assets/img/general/2018_robot.jpg)

It's had the climber removed for safety and simplicity, and the electronics swapped out with cheaper alternatives. Therefor, though you can _reference_ the 2018 code for information on how to operate the robot, copying-and-pasting code directly is likely to _not work_. 

And, that's intentional :). The goal of this lesson is to help you learn to write code for a paritcular robot, but without completely "throwing you to the wolves" on something completely unknown.

#### Drivetrain

The drivetrain follows a classic "Skid Steer" system in FRC. There are six wheels - three on the left, three on the right. Each side has all three wheels chained together so they rotate at the same speed.

This setup is also often referred to "Tank Drive" - this name comes from the fact that [army tanks (at least the non-turret portion) are controled and maneuvered in the same way](https://www.youtube.com/watch?v=u1mH-_h3_1Q). 

Each side is driven by two motors. This means we have four motors to control. We call them:

* Left Front
* Left Rear
* Right Front
* Right Rear

The left motors should always recieve the same command, and the right motors should always recieve the same command (otherwise we start to grind the gears inside the gearbox).

To move the robot forward, both left and right side motors need to be commanded forward. Similarly for reverse, you command both sides backward.

To turn to the _left_, you command the right side forward, and the left side in reverse. Similarly to turn to the _right_, command the right side in reverse, and the left side forward.

You can combine these two maneuvers to get more complex motion. For example, keeping the left side still while turning the right side forward will _arc_ to the left.

[Here's a simple example of a small robot with a similar propulsion system.](https://www.youtube.com/watch?v=rpiNZSJoHKw)

<iframe width="560" height="315" src="https://www.youtube.com/embed/rpiNZSJoHKw" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

If you're super interested, [there's a lot of math](http://matwbn.icm.edu.pl/ksiazki/amc/amc14/amc1445.pdf) that can be done to analyze exactly how these systems work. Especially with rubber tires, it's far from straightfowrad when you dig into the details. However, understanding the math isn't required to get the basics working.

#### Elevator

#### Cube Grabby Arms


Now, complete these Lessons in Order, as each one builds on the next.

* [Lesson 1 - Getting Input from the Driver](./lesson1.md)
* [Lesson 2 - Getting Input from the Outside World](./lesson2.md)
* [Lesson 3 - Controlling Outputs](./lesson3.md)
