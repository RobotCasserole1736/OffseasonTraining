## Lesson 2 - Simulation vs. Real Robots

You've probably noticed that we were able to write a _lot_ of code without ever needing a physical robot. In this lesson, we'll delve a little into how that works, and why we do it.

### Motivation: Hardware is Hard

For those of you who haven't seen it before - having only the real hardware to test on can be difficult. 

The basic cycle of software development is typing code, building and running it, doing some testing, and getting results back. We want this cycle to be as fast as possible, to ensure we go from "blank sheet" to "functional software" as quickly as possible.

When part of the cycle involves loading software onto a real robot, positioning the robot, making sure batteries are charged, everyone is clear and out of the way, driving around, risking breaking things by non-functional software.... well, you get the drift. Especially when you consider that the robot won't be available usually till at least week three (and not full-time until at least week five), waiting for real robot hardware isn't exactly an option.

In recent years, WPILib started providing tools to help us solve this.

### Simulation: What Is It

In a nutshell: "Simulation" is a set of technologies that allow us to run our code without having a robot. This involves two primary fascets:

#### Part 1 - Running roboRIO code on Something Else

The first step in being able to simulate our code is to be able to get our .java files, which are ultimately intended for running on a roboRIO, up and running on a desktop PC.

This, thankfully, is actually not that hard. Java itself has a "write once, run anywhere" philosophy. That makes our job quite a bit easier. With a bit of care, libraries and instructions can be set up in such a way that whatever they would have done on the roboRIO, they do something functionalyl equivilant on your desktop.

There are portions of WPILib which are implemented in C++, and which make some assumptions about direct interactions with various pieces of the roboRIO's unique hardware (the pieces which drive motor controllers, read sensors, and talk on the CAN bus). There are some special tricks required for them to do to ensure these portions of the code are doing something meaningful on a desktop. However, thankfully, they've already done most of those.

The act of re-implementing some of the hardware-related software functions is called "Stubbing". Alternate, "Stub" implementations of certain functions and libraries are created to allow for meaningful behavior without the actual roboRIO present.

The stub implemention of software functions (see above) allows us to access the meaningful information passed into or out of the missing-in-simulation hardware. This in turn allows us to do part 2.

#### Part 2 - Replicating Hardware that Doesn't Exist

This is tied in with the first portion, but is technically distinct. 

At many levels, there is going to be hardware which exists on the robot, but doesn't exist on your desktop computer. Some of this is related to electronic components - motor controllers, the Power Distribution Panel, the Pneumatics Control Module, etc. It also involves the physical mechanisms (drivetrain, elevators, etc.) which are on the robot. 

Our software that we write for the robot makes assumptions about how its outputs will impact the robot, and the relationships those outputs have with how inputs change. In order to keep our robot software happy, and properly test it, we need those relationships to be maintained.

Enter the punchline from Part 1: _we expose the meaningful information related to non-simulation-hardware interaction for usage_. This allows us to _write additional software_ to simulate what the robot's mechanisms do.

If you look in Chapter 2, you'll find a `sim` folder under the robot code. If you haven't yet, take a quick peek there. You'll see the "inverse" of a lot of the code you implemented. These include "sim" specific motor controller and sensor classes which allow us to access the state of those (othewise-non-existant) hardware pieces, do some math to simulate how that hardware would be working, and populate sensor data with info about its motion.

The calculations of motion are done the same way you'd do it in physics class: First principles, F=ma sort of stuff. You can also "fudge" the math quite a bit. The simulation only has to meet the assumptions of the other robot code, not perfectally duplicate real-world behavior.

### Considerations while Developing on a Real Robot

#### Code Deployment

One key difference for real-robot development: From your desktop PC, rather than simply "Building" then "Running" the code, there are extra steps involved. Namely, after the code is built, your PC connects to the roboRIO over the wifi & ethernet network on the robot. It stops any existing code, copies new files over to the robot, and restarts the new code.

Thankfully, all of this is automated and (as long as you're on the robot's Wifi network, not the warehouse's), it'll all be pretty straightforward. However, it's good to be aware of the process.

#### Web Addresses

Since the roboRIO is a different computer than your laptop, it has a different IP address.

When you are running simulation, you'll notice that the website you have to go to is:

```
http://localhost:5805
```

The `localhost` portion is a keyword which means "same IP address as this computer". This makes sense in simulation, as the code (hence the website) is indeed running on your own computer. 

When running on the real robot, you swap it out for the robot's IP address. In almost all cases, this means:

```
http://10.17.36.2:5805
```

The `10.17.36.2` portion is the roboRIO's fixed IP address. It is formed by the following:

 * `10` is fixed, always
 * `17` and `36` is our team number, split in half.
 * `2` is the roboRIO.

You'll notice other devices on the robot have siimlar IP addresses, with only the last number changing.

For example, `10.17.36.1` is the wifi radio & router. `10.17.36.11` is usually a vision processor.

#### Safety

The biggest thing to keep in mind while developing on the robot: Be safe. If code hasn't been tested, assume it is going to be dangerous. Make sure people have their hands out of the way, and aren't anywhere in the "line of fire" if a robot mechanism moves unexpectedly.

Watch the robot signal light yourself, and train other to watch it. If it's blinking, hands need to be back.



