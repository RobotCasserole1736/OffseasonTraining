# Instructor Notes

## Goals:

1) Teach the toolbox of software development tools in FRC
2) Teach how those tools relate to on-robot concepts
3) Teach how to apply the tools to problem-solving

And, do it all at the same time, in 3 months, in only 6 hours a week.

Yup, it's hard.

## Overall Methodology

The following is just a written-down set of thoughts relating to how these lessons can or should be taught.

In general, the lessons get less hand-holdey over time. This is intentional: Early on, students need to have easy success to instill the idea that software is within their grasp. 

As they progress, it is desireable for them to start to make mistakes. They should learn that just because they wrote something they _thought_ should work, doesn't mean that it actually will. They'll have to think critically about what they've created, understanding how it does or does not meet the objective. 

Ideally, the "training wheels" should be designed to keep them close enough to the correct solution as to not become discouraged into believeing the solution is impossible. As a mentor, you are also a key in keeping them closely aligned with a functional solution. The point is not that they don't make mistakes, but that their mistakes don't drive them so far from the real solution as so that they start to believe a solution is out of their grasp.

The goal is to keep the training wheels on just long enough to master concepts, start to take them off as the concepts get applied to real code, wiht the goal of being "fully off" by the time build season starts. 

### Lesson Contents, Layout, and Pace

Each written lesson should have a few parts:

1) Basic words of encouragement to help set the tone and mindset of the lesson.
2) Written critical information for students who prefer to learn by reading.
3) Problems or Activites to allow students to apply what they learned themselves.

Chapter 1 focuses on core principles and usage of the tools. Chapter 2 focuses on applying those to creating software for a specific robot.

In all cases, 15-30 minutes of lecturing should be prepared to ensure students see other people presenting the information, and at least 30 minutes reserved for question answering. Ongoing Q/A should continue via Discord or some equivilant service, in an attempt to keep students engaged and used to the "problem-solve, ask, get answer" cycle.

To complete all lessons, students will need to work at a pace of about 1.5 lessons per week. As of the 2020 offseason, it's not quite certain how feasible this is... and it will change year to year, depending on the student population. The tentative strategy is to target two lessons per week, but as students encounter difficult concepts, insert "catch-up days" every other week to even things out.

## Zen of Teaching Robotics Programming

Ramping a student from zero to productive in an FRC team presents unique challenges over traditional software instructional techniques.

### Toolset-First

Casserole uses the standard Java FRC devleopment environment, which uses a standard set of libraries (WPILib) and development environment (vsCode). Students do not have to understand the _why_ of this to be productive, nor should be expected to be environment polyglots from the get-go. Starting in the environment, with all its assumptions and nuances, reduces "wheel spin" from adjusting to a robot-specific environment from a more general one.

### Modify-First

With the exception of the student who first creates the robot project, students are always picking up a large codebase, making modifications, and submitting them back into the broad project. Traditional programming classes assume students white-paper design every program - this is simply not how FIRST programming works. Training should focus on handing students an existing large codebase, instructing them on how to make and test changes at specific locations within that codebase. 

### Debug-First

A side-effect of whitepaper training exercises is that the student has written all their code in a small area, and likely can "hold" the full operation of the program in their head at once. FRC programs quickly grow to where this is not possible. Ensuring students start with a modularization mindset is critical, and teaching them the skills to analyze and debug code in this modular fashion is key to success.

Additionally, for retaining students, it is imperitive that each see that the tools exist to solve their problems. It's very discouraging for a student hit to a problem, be unable to comprehend the solution immedeately, but see a fellow student solve it with ease. Instilling the notion that debugging is both a _learned thought process_, as well as a toolset, is critical for retaining student interest.

## Desired Outcomes - Chapter 1

### Lesson 1 
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

### Lesson 2 
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

### Lesson 3 
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

### Lesson 4 
* Code is further organized into methods (ie functions)
* Methods can be called with inputs and produce 0 to 1 outputs
* Methods must be _declared_ once, and _called_ 1 or more times.
* Methods help prevent needing to copy-paste large hunks of code
* Methods help hide complex functionality behind a meaningful name, making the code easier to understand.
* There are multiple valid ways to solve a given problem (ex: "middle" can be calculated in a lot of different ways). Your solution doesn't have to look like your neighbor's solution to work.
* Basic applications and usage of "parity".

### Lesson 5 

* Main controls and methodologies for manipulating and inspecting robot code exeuction through the built-in single step debugger.
* Basic thought processes for methodical debugging should mirror the scientific process: Make a theory about what your code should do. Design an experiement. Test. Analyze results. Repeat.

### Lesson 6 

* Everything in Java is organized into a class
* A class is a blueprint for how to make an object
* Multiple objects can be made from one class
* We divide the robot's physical parts and functions into classes
* Special classes called "singletons" represent objects where we are guarnteeded there will only ever be _one_ of them.
* Basic air compressor operation
* Java requires a good chunk of "boilerplate" code to get a class defined. Just copy-paste it from the examples.

### Lesson 7 

* Signals are a casserole-specific way to interrogate robot behavior. 
* Signals fall one layer "higher" in the hierarchy of debug methods than using the single-step debugger from lesson 5.
* Definition of a "timeseries" data. Application of the mathematical "function" concept to data.
* Signals are viewed in real-time on the website, or after-the-fact from data recordings (.csv files).

### Lesson 8 

* Software "Architecture" is a dileberate set of choices and constraints on our code to organize it better.
* Using other people's code usually requires to conform to the assumptiosn of their architecture. FIRST and WPILib push some of these assumptions on to us.
* Our robot must operate in 3 main modes: Disabled, Teleop, Autonomous
* Each mode has a 1-time `init` function and a periodic `update` function. Our code gets called from within these.
* One-time power-on function called `robotInit` is also available.

## Lesson Outcomes - Chapter 2

### Lesson 1

* Driver inputs are the primary way humans pass their intentions to the robot
* How to read values from a USB joystic
* Driver input class provides the formal abstraction from "axis on a joystick" to "command of driver intention"
* Increased autonomy of making a brand new class from scratch with fairly little input.

### Lesson 2

* Sensors are the other main form of input the robot gets
* Introduction to the different types of sensors available
* How to write code to read values from common sensors
* Sensor values should be converted to physically-meaningful units (like ft or RPM).

### Lesson 3 

* Basics of the types of output devices that are available
* How to write code for some PWM-based motor controllers
* Creatively thinking through how to combine math & IF statements to convert arcade-drive style inputs to motor commands.

### Lesson 4

* Reinforcement of lesson 3 output creation
* Combining sensor inputs and driver commands to create motor outputs
* Additional freeform class creation with little specific guidance

### Lesson 5

* Carefully-designed software can help reduce the cogantive load on the driver, allowing the robot to operate faster and the driver to focus more on strategy.
* Using sensor feedback in a closed-loop fashion to move the elevator to preset heights

### Lesson 6

* Avoiding magic-constants in code
* Usage of Calibrations for increasing development velocity

## Lesson Outcomes - Chapter 3

For now, Chapter 3 is almost an appendix to the other training sessions. There's little hands-on content: it's more of a data-download to wet the appetite of the hungry student.

Hopefully we can come up with some more interactive things in the future. But, for now, the goal is moreso to introduce enough topics so that, when the opportunity arises on the robot, the students will have a basic idea where to look for answers.