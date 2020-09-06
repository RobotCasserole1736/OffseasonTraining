## Lesson 4 - Automation

### Background

Reducing stress on the driver is key. The more time they can be thinking about strategy, the better. We can help this by automating some robot tasks.

In the game, the cube is picked up at about 0.5 ft off the ground, but released at about 3 ft off the ground. 

We'll add the ability for the driver to command the elevator to go to one of these two fixed positions. We assume pushing X or Y indicates these commands. We'll add logic to move the elevator to that position.

### Bang Bang controller

See (the other chapter 1 lesson you did on this). The logic will be basic:

1) Based on button presses, calculate the desired height
2) Read the encoder to detect the actual height
3) If you are too high, go down.
4) Else If you are too low, go up.
5) Else, if you're pretty close, stop.

### Code Updates - Driver Input

#### What to Change

Add new logic to read the X/Y buttons and interpret as elevator fixed-position commands

Add new signals and getters.

#### What to Test

Verify that the signals reflect the input commands from the driver

### Code Updates - Elevator

comment out (Disconnect) your logic from the old joystick-method to start.

Add logic to implement the bang-bang controller above.

Add signal for desired height.

#### What to Test

Run the code, press the buttons.

Ensure the elevator starts moving in the right direction

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

Manually move the elevator up and down to ensure you still have control. 

Move the elevator to the middle. 

Hit one of the preset buttons, make sure the elevator goes to the preset height.

Repeat for the other preset button.

