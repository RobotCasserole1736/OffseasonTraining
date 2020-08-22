# Lesson 8 - Basic Robot Code Architecture

## Some Thoughts Before We Begin

Just some humor today. You don't have to read it all now - maybe just bookmark it for a rainy day.

[How to Write Unmaintainable Code](https://github.com/Droogans/unmaintainable-code)

AKA how to make everyone around you hate you.

Want to be a good software developer? Just do the opposite of everything in that link :).

## The Match Cycle

Robots undergo a very specific sequence of events on the field, and our code needs to conform to that sequence. You've probably already seen the specially named functions inside `Robot.java`, and maybe even poked at the code in them. This lesson, we'll go through the purpose of each of these, and how to use them.

What we're about to learn is what we'd call a particular software _architecture_. It's a set of rules and assumptions that govern how we lay out our desired functionality, and organize our lines of code. There are lots of possible choices for how to design software architecture. Some are better for certain scenarios than others. Sometimes we can make our own choices, other times they're passed to us. 

You'll get a lot better of an idea about what "good" and "bad" architecture look like as we go through. At this point though, just keep in mind: The things we're learning are _just the way things are_. It's a carefully-chosen, but technically arbitrary set of constraints, above and beyond what the java programming language enforces.

### Autonomous, Teleop, Disabled

There are three fundamental modes the robot can be in:

#### Disabled

This is the default state of the robot. When you first power it on, or if the driver station ever disconnects, whenever you Emergency-Stop (E-stop), the robot goes to **Disabled**.

In Disabled, the robot signal light is lit solid - it doesn't blink. Additionally, the roboRIO will _force_ all outputs to be inactive. No matter what you try to do to your outputs in software, it won't matter. Every motor and solenoid goes to a de-energized state. This is for safety - it ensures that no matter how bad we screw up our code, if we disable the robot, _it will stop moving_.

We can still run code while the robot is disabled - this can be useful to ensure sensors are still working and the website stays up to date. But, there's no need to try to push outputs.... but also no harm if we do!

#### Teleop

Teleoperated mode is the state that the robot spends most of the match in. It's the normal "running" or "activated" state. 

In **Teleop**, the robot signal list blinks twice per second. Outputs are fully enabled, so software can control them. In addition, the human drivers will have their hands on the joysticks and buttons, providing commands to software. 

The code we write has to interpret those driver inputs, combine them with sensor inputs, and calculate what the outputs need to be. We've explored a tiny bit of what that's like, and we'll continue to dig into it as the weeks go forward.

#### Autonomous

Autonomous mode lasts for the first fifteen seconds of each match. It's almost the same as teleop - with one critical exception. The human operators cannot touch the controller. For that reason, we have to swap out our operator with special logic that _immitates_ what an operator would command... automatically! If we do it right, the robot drives itself!

### Init & Update

The the functionality of every mode is split between two functions: `init` and `update`. This is a very common architecture used for software that controls physical things.

These functions get called by WPILib, the other code that's written for us, and comes "for free" with the robot. WPILib provides a ton of functionality and is definitely a good idea to use it. With extremely few exceptions, every FRC team does. But, to use it, you have to play by its rules - work within its assumptions.

The core of that assumption is that all the user robot code gets called and run by the associated `init` or `update` function. WPILib promises the following: 

* Right before we actually start a new mode, its `init` function will get called.
* Every 20ms, the `update` function will get called.

Note that if our code inside of `update` doesn't complete within 20 milliseconds, it can't get called again. WPI will be forced to wait until we finish. For this reason, we want to ensure we keep our `update` code very _fast_.

The usual issue that new students will hit: You never want to use a loop that _sits and waits_ for a condition. Rather, just check the condition once, take a single action (or do nothing), then move on. 

For example, consider the following code snippet:

```java

boolean activationCondition = false; // Assume some other code will change this value at some point.

void update(){

    while(!activationCondition){
        // ... wait for condition
    }

    activateSomeOutput();
}
```

Once `update` is called, this code will _sit and wait_ for the condition to be true, before activating the output. However, for robot code _this is bad_. We need our `update()` function to complete very quickly. We don't know how long we'll sit in that `while` loop for, which is _bad_. 

Instead, the correct way to accomplish the same end goal:

```java

boolean activationCondition = false; // Assume some other code will change this value at some point.

void update(){

    if(activationCondition){
        activateSomeOutput();
    }
}
```

Now, the code never "hangs" waiting for the activation condition. Instead, it simply "flows through" when the condition is false, or activates the output when true.

When you're writing code that runs in the `update()` function, keep in mind - _the `update` function is already called inside a `while` loop_. You very rarely, if ever, need to use your own.

This action is often called "unrolling" the loop.

### `robotInit()`

There is one more special function called `robotInit()`. This is called one time, right as our code starts running (soon after power is applied to the whole system). Things which need to happen once (ex: init and start the website) will get called here.

## Problem 1

This week's problem is pretty small.

Add some new code inside of `Robot.java`. The code should do the following:

1. Create a new signal with a unique name that starts with `L8 - <your name here>`
2. Make a new class-scope integer, have the signal report its value every loop.
3. Set the integer to be 1 when in autonomous, 2 in teleop, and 3 if disabled.

Run the code, open the webiste, confirm the signal changes in value as you flip between the modes.

