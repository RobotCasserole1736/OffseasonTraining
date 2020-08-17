
## Lesson 1 - Getting Inputs from Drivers

### Background - the Theoretical Robot We will be Programming

For the 2018 game

Looks very similar to our 2018 bot, because it is.

Electronics reworked

#### Drivetrain

#### Elevator

#### Cube Grabby Arms


### Getting inputs from Operators

#### To start- the Controller

In order to really be able to test our code, we're going to start to need a bit of hardware.

Use any USB controller you ahve around the house

Maybe buy one, they're useful for lots of things, and fairly cheap.

https://www.amazon.com/Controller-EasySMX-Joystick-Dual-Vibration-Trigger/dp/B06XBX1R55/ref=sr_1_6?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-6

https://www.amazon.com/SQDeal-Joystick-Controller-Vibration-Feedback/dp/B01GR9ZZTS/ref=sr_1_14?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-14

https://www.amazon.com/Extreme-3D-Pro-Joystick-Windows/dp/B00009OY9U/ref=sr_1_2?dchild=1&keywords=usb+joystick&qid=1597606693&sr=8-2

Or, the $0.00 option, install and configure vJoy - http://vjoystick.sourceforge.net/site/. This will _emulate_ the hardware. It's a bit clunky, but will allow you to confirm basic functionality.


#### The Driver Input Class

Converts information from a USB controller into Commands

We're going to assume you have an XBox360 controller, which you can generally do. There's more generic classes available too.

Welcome to the wonderful world of writing code for robots!

"Command": generic desire from the operator. The goal is to abstract away the details about the controller, and extract: _what does the driver mean to tell us_.

# How to Test
