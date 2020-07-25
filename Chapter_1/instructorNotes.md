# Instructor Notes

## Overall Methodology

The following is just a written-down set of thoughts relating to how these lessons can or should be taught.

In general, the lessons get less hand-holdey over time. THis is intentional: Early on, students need to have easy success to instill the idea that software is within their grasp. 

As they progress, it is desireable for them to start to make mistakes. They should learn that just because they wrote something they _thought_ should work, doesn't mean that it actually will. They'll have to think critically about what they've created, understanding how it does or does not meet the objective. 

Ideally, the "training wheels" should be designed to keep them close enough to the correct solution as to not become discouraged into believeing the solution is impossible. As a mentor, you are also a key in keeping them closely aligned with a functional solution. The point is not that they don't make mistakes, but that their mistakes don't drive them so far from the real solution as so that they start to believe a solution is out of their grasp.

The goal is to keep the training wheels on just long enough to master concepts, start to take them off as the concepts get applied to real code, wiht the goal of being "fully off" by the time build season starts. 

## Lesson 1 outcomes
* Anyone can learn to write software for robots!
  * Real-time controls software, written in a team, is a different beast than what otherwised-experienced students may have seen in the past.
* Basic terminology
* Software source code vs. binary on the robot
* Source code is stored on github, and pulled to computers for editing
* vsCode is the environment used to view & edit software.
* Software source code is nothing special - just some particularly-formatted text files.
* Software is built and run in order to validate functionality.
* The robot website is a key tool for understanding how code is functioning.
* `println` is another available mechanism for "instrumenting" code behavior.

## Lesson 2 outcomes
* Basic software math and boolean operations
* "Nominal" git branch usage
* Code can be edited to add or change functionality.
* Basic testing process starts with creating a theory of what you expect the behavior to be, then validating that the behavior is present.
* Variables carry real-world meaning (ex: is button pressed?)
* Determining a simple boolean expression from a description of real-world behavior.
* Thinking about safe operation of robots (emergency-stop), and how code impacts that.
* Experiment 1 => If you don't quite know what a piece of code does, you can sometimes just run it and "play around" with it to determine behavior.
* Challenge 1 => Knowledge that there is more than one way to solve even a simple problem. Knowledge of a "boolean-complete" set of operations.
* Reading code comments (they provide clear instruction as to where to put the code)

## Lesson 3 outcomes
* Comparison operations produce booleans from numbers
* Basic name formating conventions - CAPITALIZATION CarriesMeaning
* Code is organized into blocks dilimted by `{}`
* Basic usage of indentation for visual separation of blocks of code
* Code is executed by default. `if` `else if` `else` statements can use booleans to dynamically control skipping blocks of code.
* Basic introduction to strings. Students shouldn't need advanced knowledge on manipulating them, simply a passing knowledge that they exist.
* Software math can be applied to mathematical principles and calculate meaningful values.
* Basic introduction to the problem of motor control:
  * We do not directly control speed of motors, only applied voltage
  * Often we want motors running at a certain speed, and need software to accomplish this
  * Bang-bang is a simple way to do it, and involves only IF statements
  * FRC Motor speeds are usually between 0 and 6000RPM
  * FRC motor commands are, by convention, -1.0 to 1.0.
  * Sensors and motor controllers are important, but the code "in-between" is also important. And, ideally, seperable from the hardware-specific code.
* For time-varying code, you have to click the "Teleop Enable" button to get it to run.

## Lesson 4 outcomes
* Code is further organized into methods (ie functions)
* Methods can be called with inputs and produce 0 to 1 outputs
* Methods must be _declared_ once, and _called_ 1 or more times.
* Methods help prevent needing to copy-paste large hunks of code
* Methods help hide complex functionality behind a meaningful name, making the code easier to understand.
* There are multiple valid ways to solve a given problem (ex: "middle" can be calculated in a lot of different ways). Your solution doesn't have to look like your neighbor's solution to work.
* Basic applications and usage of "parity".