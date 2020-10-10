## Lesson 5 - Automation

By this point, we should have basic robot functionality accomplished. This is good! We could go to competition and be able to the main tasks of the game.

Our robot is very manual though - it requires some good skill and cognative effort by the driver to operate well. Reducing stress on the driver is one key to success. The more time they can be thinking about strategy, the better.

Sometimes, we can write software to help make that easier. In this lesson, we're going to do that to the Elevator subsystem.

### Background

In the game, the cube is picked up at about 0.5 ft off the ground, but released at about 3 ft off the ground. Rather than having the driver manually position the elevator each time, we're going to write code to automatically make the elevator go to those two positions.

### Logic Overview

Here's the basic outline of how we will get the elevator to the preset height positions: 

1) Based on button presses, calculate the desired height
2) Read the encoder to detect the actual height
3) If you are too high, go down.
4) Else If you are too low, go up.
5) Else, if you're pretty close, stop.

Note the core of this is a "Bang-bang" controller. The "bang-bang" controller is the same as what we had implemented in Chapter 1.

We'll now go start performing these updates to the various subsystems.

### Code Updates - Driver Input

#### What to Change

Add new logic to read the X/Y buttons and interpret as elevator fixed-position commands. 

Add new signals and "getter" functions. The new `get*()` functions should be named after the commands that the X/Y buttons represent.

#### What to Test

Run the code, and pull up the website. Ensure pressing the buttons causes the new signals to change approprately.

### Code Updates - Elevator

For now, comment out (ie, "disconnect") your logic which set motor commands based on joystick values. We'll come back to it later.

Add logic to implement the bang-bang controller above. The net output should use the boolean inputs to calculate a desired height, then set the motor command based on the difference between actual height and desired height.

Add signal for desired height (and ensure there's still one for the motor command).

#### What to Test

Run the code, make sure your new signals show up in the website, and then press the buttons.

Ensure the elevator starts moving in the correct direction.

Ensure it stops at the 3ft and 0.5 ft marks.

### Code Changes - Arbitration

The driver may still need to manually adjust the height of the elevator. The old driver input should be able to do this.

We have to _arbitrarte_ between these two inputs. There's tons of ways to do this, but we'll choose the following logic:

1) Usually, the automatic height control is "in power".
2) However, any time the driver manual command is _non-zero_, it should take control
3) When in manual mode, set the desired height to the actual height every loop

#3 is designed to ensure that when the control mode flips back from manual to automatic, the automatic control keeps the elevator in place (and doesn't attempt to return to a different height)

Arbitration between multiple command sources is a thing that happens quite a bit on robots. We're walking you through this logic now because we want to show you an exmple of how to think through the problem. Likely, on the real robot, the problem statement will be different and we'll have to think through it a lot. Don't worry too much about it, we'll think through it as a team and define a clear strategy, which in turn will help you write the code.

#### What to Test

Run the code, and pull up the website.

Manually move the elevator up and down to ensure you still have control. 

Move the elevator to the middle. 

Hit one of the preset buttons, make sure the elevator goes to the preset height.

Repeat for the other preset button.

