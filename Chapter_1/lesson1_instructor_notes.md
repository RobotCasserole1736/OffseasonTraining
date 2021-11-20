# 1.1 - Github and Git - Getting Software

## Setup

Pull up the software on github to show where it's at.

Have all students pull up https://github.com/RobotCasserole1736/OffseasonTraining

## Talking Points

We're starting by looking at our code in a web browser now. You can click around to see other files, make small modifications. This is a very nice feature of *Github*, the website on the internet where we we keep our software.

However, to run the code, and make bigger changes, you'll want the software on your computer.

This is where we use a piece of software called *Git*. Git moves files and changes to code between a web server and the computer you're currently working on.

### Why use Git and Github at all?

In a team-based software development environment, you rarely start writing all your code from scratch (in fact, exactly one student does this once per build season). 

More frequently, you are starting your development _based on_ the work someone else already did. You take their content, modify it, add your own things, and submit it back for the next person to work on. Multiply this by 10+ people, and you've got a team developing a singular codebase! 

The reason we need tools like Git and Github are to ensure that all people can contribute their changes without "stepping" on each other, using different computers. 

It additionally ensures that, as a team, we have a singular notion of what the "correct" or "most recent" software is. 

All of this is key to ensuring the software we make is high-quality, and defect-free.

## Walkthrough

See lesson1.1.md - walk through it with students

### Explore files

Go poking around inside the new `OffseasonTraining` folder on your computer. You should see many `.java` files containing our source code, some `.md` files for documentation, and a host of other supporting files. Notice that, for now, they're the same as what's online. That helps confirm we got the right code.

### So... what exactly is "Code"?

"code" is the slang term for the .java files we're looking at. They are called "source code" because they're the source of instructions the computer on the robot will eventually execute. 

Code is organized into "blocks" - each block is started and ended with the curly brackets `{` and `}`. We also indent the start of each line to visually separate the blocks from each other.

Within each block, code is executed line by line. That is to say, the computer will execute one line, then the next, then the next, then the next, on and on forever.

We'll dig into more specifics as the year goes on - there's lots of options for what to put into each line, and how to organize and control the order blocks happen in. However, the key takeaway from now: _every behavior you see on the robot is the result of one or more lines of code executing_.

# 1.2 - Initial Software Run

## Setup

Ensure all students have software on their PC cloned, per 1.1.

Show students how to open VSCode and navigate to the files. Show the basic user interface

## Talking Points

### VS Code as an Editor

Before making changes to any software that already exists, you should generally check that it is working and functional, and familiarize yourself with its contents. That's what we'll do now.

To start, we'll need to open *Visual Studio Code* (or, *VSCode*). This is the tool we will use to modify, run, and test our software. It's one of many tools that can do it, but happens to be the "officially supported" one for FRC. So we use it.

After opening VSCode, You should be greeted with a basic user interface. You'll want to go open the folder containing the code we just got from Github.  It's just showing you the folders and files that are on your hard drive, in that folder we selected.

Note the folders on the left side. The folders have useful content , but we can ignore a lot of them for now. We'll focucs on the source folder, containing `Robot.java`.

This is the "entry-point" file - the first file that runs from the code that we write for the robot. A lot of the code writing will happen in this file. Glance through it, notice how it has different chunks of code for `Init`, `Teleop`, and `Autonomous`. 

We will now attempt to run the code on our desktop computer. That's right - our computers, not the robot!

### Why Simulation?

All code we write _could_ be run in two places:

 * On the desktop/laptop computer you are using right now. 
 * On the **roboRIO**, the "controller" computer which is on the robot.

 Ultimately, our code will have to run on the roboRIO. However, running code on your own computer is very useful.
 
There are a number of reasons for this:

1. We only have one robot, but many laptops.
2. The robot will move. Running into people hurts. We want to ensure our code will work safely before risking damage to people or things.
3. Bugs are generally easier to solve on a laptop computer.

### Running Code

Your code is written in Java, which is a language both you and the computer understand. However, the computer is just a bunch of electrical circuits. Not nearly as complex as our human brains are.

In order to actually run the code, the computer must first translate the code into a language its circuitry can work with. This process is referred to as _building_ (or sometimes, less accurately, "compiling").

There's a few ways to tell the computer to build and run your code. The easiest - press F5 on your keyboard.

This will start a sequence of events where the computer takes your source code files and turns them into a `.jar` file, which it attempts to run. This is where you might see some errors if you've written code that the computer can't understand. However, since we haven't changed anything yet, the code should compile without error. 

After the build has finished, you will get a prompt about using different sim extensions. Check the `halsim_gui.dll` box, and hit OK.

As the robot code starts up, you'll see some text start to barf to the `Terminal` window. This is expected and normal. Each line has some meaning, but don't worry too much about understanding each of them now.

### Signs of Code Running

See lesson 1.2 - there's exercises in there to correlate `print` statements to terminal output.

In addition, you should notice the blue bar at the bottom of Visual Studio Code change color to orange. At this point, your robot code is running on your computer! Congratulations!

### Simulation GUI & Robot Modes

Obviously, there's no robot that the software is controlling, but we can still interact with it. 

The WPILib-provided method for this is the blue-and-grey GUI that pops up when the simulation starts. This is the _Input/Output Simulator_. Since we don't have all the real wires and electronics on a roboRIO, we use this GUI interface to simulate what the data on those wires would be. 

We won't worry about all the details now, but note in particular the *Robot State* widget. This is the fundamental mode-switcher on the robot. Robots have three primary modes:

1. Disabled - not moving and safe to handle
2. Autonomous - Robot makes its own decisions and movements without a driver's input. AKA *Auto*.
3. Teleoperated - Robot drives around under the control of human operators. AKA *Teleop*.

During competition, the *Driver Station Software* and the *Field Management System* enable and disable robots automatically during the match. 

For our testing, we will **manually** change the robot's state via that *Robot State* widget.


# 1.3 - Interacting with Robot Code

## Setup

Running code on all students computers, from 1.2.

Web browser up to demonstrate the online interface

## Talking Points

### The website itself

Our software produces a *website* we can view and use to interact with the software. While it's not available to the general public on the "internet", any computer connected to the robot's local network can view it.

Internet Explorer, Microsoft Edge, Opera, Google Chrome, or Firefox are all examples of _web browsers_ which can view the website. Most of our laptops should have Chrome installed.

While running in simulation, the website will be found at `http://localhost:5805`. Here, "localhost" refers to the fact the code is running _on your laptop_ - it'll be different when we get to the robot. `5805` is called the "port" number, and will always be 5805.

Start by opening the `State` link on the webpage. You'll see some variable changing, but not all. We'll come back to this soon.

You can click through the other pages too if you want, though there's not much there now. That'll change soon enough!

### Where website data comes from

Remember from earlier: Every behavior you see comes from some lines of code in the software. This applies both to the physical things moving on the robot, as well as to the website!

Take a look through `Robot.java` - there are some variables declared at the top, which are marked with the `@Signal` *annotation*. This is a Robot Casserole-specific thing - our website knows that when it starts up, everything marked as a `@Signal` is important, and should show up in the website. When you're writing code and you care about how a variable changes over time, you can just mark it as a `@Signal`, and it will show up there.

Of the signals that are already there- there's one counting the number of Teleop Init calls. Every time our robot starts running in Teleoperated mode, it should increase by one.

Go ahead and follow the instructions in the lesson. You'll enable and disable the robot repeatedly, noticing that the value on that variable changes. You'll also see a new message printed to the `terminal`.

Why do both these things happen? Well, there's a lot of reasons. But the core that we care about right now: _there are lines of code in Robot.java that drive it_.

