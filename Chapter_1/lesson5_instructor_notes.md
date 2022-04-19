# Lesson 5.1 - Using the Debugger

## Setup 

Have lesson 5 java file up, and ready to be debugged.

## Talking Points

Up till now, the pieces of code we've written are pretty small. Small enough that _by just staring at the code_, you can probably predict how it will work.

Ideally, as we write more and more code, we'll keep it as organized as possible, to help keep this "inspectability". However, inevitably, bigger projects create more complex problems, that need more advanced tools to unravel.

We've already looked at one of these tools: inserting `System.out.println()` calls at critical portions of your code allow you to check multiple things. This includes order of execution, and values of variables at various times.

You've also seen the website utilities, which allow you to see values of variables as they change over time.

We will introduce one additional tool that you can use to help investigate problems. It's called the _single step debugger_, or simply the "debugger".

The debugger allows you to stop code execution on certain lines, "step" through your code's execution line by line, and print out values of variables and expressions at runtime. The biggest advantage of the debugger is that it doesn't require injecting any additional lines of code into your software - it _just works_. (usually :D).

When running code locally on a desktop, the debugger is automatically running in the background - nothing special you have to do to start it.

Regardless of what debug tool you happen to be using, the technique for solving problems is generally the same:

1) Look at the code, and decide how you are expecting it to work.
2) Investigate the code as it executes from start to end.
3) Find the first place where the behavior of the code deviates from your expectations.

The important thing to keep in mind: When you see errors, or software that doesn't work like you'd expect, don't get discouraged! There is a solution, it will just take time and technique to find it. Practicing this technique, over and over again, is how you get better and better at creating software!

Note: The same debugger can be used to inspect code behavior on both the robot and in simulation. We'll start on the simulation, since it's easier.

### Launching the Debugger

The debugger is automatically enabled when running software on your PC. Just press `F5` on your keyboard like normal, select the HAL sim GUI .dll, and let it run. You should see the sim GUI pop up. The debugger is now running in the background, ready to be used to analyze code behavior!

Note the debugger controls at the top, along with the debug menu at the right side.

The orange bar at the bottom of the window indicates debugging is active right now (blue indicates nothing is running). Additionally, you can see that we launched the `Desktop Debug` routine, as expected.

Take a quick look at the code in the  `LessonFive` class. You can see it has a method named `lessonFiveEnabledUpdate()`, which is getting called inside `Robot.java`'s `teleopPeriodic()` method. Therefor, to "active" or "run" the lesson five code, we know we'll need to have the robot in the Teleop enabled mode.

### Setting Breakpoints

Remember that a computer executes a program line by line, starting at the top, going through method calls as needed, calculating and assigning values for each line, then moving on to the next line.

A "breakpoint" is a flag sent to the program to tell it to stop execution once code reaches the start of a certain line. "Setting" a breakpoint is the act of setting that flag on a particular line of code.

There should be a bright red circle that appears next to the line. Nothing else will happen at this point, because we're _not yet calling this method_.

With our breakpoint set, let's go ahead and enable the robot code. Do this by selecting `Teleooperated` from the `Robot State` pane in the Robot Simulation GUI.

As soon as you do this, `lessonFiveEnabledUpdate()` will get called, and we should _hit the breakpoint_. 

The most obvious indicator is the yellow highlights on the breakpoint and line. It indicates the program is currently stopped on this line of code.

You'll additionally see our previous "Pause" icon in the debugger controls has changed to a play button. Since the code is now stopped, we have a few options for what we can do, including continuing execution until the next breakpoint.