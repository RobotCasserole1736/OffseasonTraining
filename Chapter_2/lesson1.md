
## Lesson 1 - Getting Inputs from Drivers


### Getting inputs from Operators

#### Background

This lesson, we're going to focus on how we get input _commands_ from the driver.

Generally, the driver will be holding some sort of a video game controller. They will move joysticks and push buttons to indicate a _desire_ or _command_ for the robot to do something. In software, we have to write code to read the data off of the video game controller, and _interpret_ it into a _command_ from the driver.

Often the transform is trivial: "button pushed" is the same as "intake desired". However, the change of name is signifigant. In doing so, we add a layer of _abstraction_. We isolate the _specific_ button on the controller from the _desire_ the operator has. 

This allows us to write our code in a more flexible way. For example, if we want to move the intake command from the A button to the B button, it's simple - just change the function that calcualtions the "intake desired" command to look at a different button. Everything else downstream stays the same.

Note, you don't technically _have_ to do this just to get robot code "working". This is a particular _architecture_ choice we make to isolate _what_ driver wants, from _how_ they specifically provide the input. We make this decision conciously: The answer to "What" is driven by the sorts of things that are on the robot - this is unlikely to change. However, the answer to "How" is driven by driver preferences and available controllers. This is much more likely to change over time. By keeping the translation from specific input method to generic _desire_ in its own isolated class, we reduce the difficulty of making changes later on.

#### To start - the Controller

In order to really be able to test our code, we're going to start to need a bit of hardware. We'll need some  USB game controller just like the driver would use to test our code. This is because, unfortunately, there is no built-in "virtualization" solution yet.

You can use any windows-compatible USB controller you ahve around the house. If you don't have one, you could buy one - they're not super expensive, and useful for things beyond robots. Here's three options:

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
