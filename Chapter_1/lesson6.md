
# Lesson 6 - New Class

It's time to get classy!

## Some Thoughts Before We Begin

There's a tounge-in-cheek methodology for solving problems often called the [Feynman Algorithm](http://wiki.c2.com/?FeynmanAlgorithm), named for the very smart physicist who (appeared) to use it frequently: 

1) Write down the problem
2) Think really hard
3) Write down the solution

...

Yea, if only it were that easy. And yet, often, when we see folks around us problem solving, that's all it appears they're doing. Everyone sees the problem the same way, but somehow, for _some people_ the answer comes out of thin air. 

Of course, no one's good enough to just stare at a problem and magically come up with the answer. Even Richard Feynman, regularly acknowledged as a genious by his peers, didn't just magically creat answers. 

Everyone who appears to do this is actually doing something much more meaningful under the hood. They're accessing their memories, thinking of times when they've seen similar problems. They're breaking the stated problem down into tiny components, solving each individually, and building back up a full solution. They're solving the problem multiple ways, and cross-checking the solution.

If you're _really_ good, you might be able to do that all in your head. More often than not, it requires writing things down. That's ok.

The point is simply: Don't ever get discouraged that others around you seem to be able to see solutions where you do not. They're doing the same thing you're learning to do now: Just applying previous experiences to current problems to solve them.

## Anatomy of a Class

Every `.java` file we've been reading or editing so far defines a single _class_. The _class_ is the name for a fundamental unit of organization in  java. Just as you previously grouped your code into blocks with `if`/`else` statements, and grouped those into methods, multiple methods will be grouped into _classes_. 

A _class_ is the blueprint for an _object_. It's not a concrete thing itself - rather, it's a _description of what that thing might be like_.

For example, let's say you have two pet cats. In java, you'd describe them in two steps. First, you'd make a generic `Cat` class:

```java
class Cat{

    boolean furIsWhite;
    double weight_lbs;
    int numWhiskers;
    boolean hasInstagramAccount;

    void sayMeow(){
        //Todo
    }

    void feed(){
        //Todo
    }

    boolean cleanLitterbox(){
        //Todo
    }

}
```

Then, in a different file, you'd create two objects, both of type `Cat`, but with different names. Then, you can individually manipulate variables inside each object:

```java
void createCats{

    Cat mittens = new Cat();
    Cat spot = new Cat();

    mittens.weight_lbs = 25; //big cat
    spot.weight_lbs = 8; //small cat
}
```

## Some side thoughts

Often in robots, the line between _class_ and _object_ gets blurred, because there's only ever one drivetrain, or one arm, or one elevator. If you were to make a class for one of these (as we usually do), there should only ever be one _object_... and then it doesn't make a ton of sense to really differentiate. We'll talk about that more when we get to _singleton classese_. Don't worry about it too much for now.... but keep it in mind as we go forward.

Java forces everything to be defined inside a class. Not all programming languages make this assumption. It's something that makes java unique. Just like there many different tools you could use to bend a piece of metal, so too there are many different programming languages which can be used to solve programming problems. All make different assumptions and restrictions

### The Constructor

Classes in java have one special method named "The Constructor". It gets called when the class is used to create a new object. It can optionally take arguments, but cannot return anything.

In our above class, the constructor could be:

```java
    public Cat(){
        //Add additional code to initalize your cat here.
        System.out.println("meow");
    }
```

It's not required to have a constructor, but is one convienent spot to put initilization code.

## Rules for making a Class in Java

Java is well known for requiring a lot of "boilerplate" code. That is to say, there's a lot of _required_ things you have to type that don't contain anything unique or useful - you just always have to type them.

When making a new class, the **only** thing you really need is the _name_ you're going to give the class. Start by making a new file, naming it `<classname>.java`

Then, copy-paste this code into the file, and modify per the comments instructions:

```java
package frc.robot; //You may have to change this, if the file is a different folder.

public class EmptyClass {

    public EmptyClass(){
        //This is the constructor. Add new variables as you need to.
    }

}
```

Change the name `EmptyClass` in all locations to the name of your new class. Add logic and arguments to the constructor as needed. Then, add your variables and methods. Finally, use it to instantiate a new object in some other class.

## Problem 1 

This one, you might be able to solve without running anything. Consider the following class, `BallCounter`. It helps keep track of how many gamepiece balls are in some container on the robot. Various sensor conditions could be used detect gamepiece entry and exit, and call the `insertBall` and `ejectBall` methods. We aren't worried about the specifics of the sensor arrangement, we just know our insert/eject methods will get called at the right times. In the middle, `BallCounter` just responds to those calls properly to keep track of the number of gampeieces. 

```java
package frc.robot; 

public class BallCounter {

    int curCount;

    public BallCounter(int initial_count){
        curCount = initial_count;
    }

    public void insertBall(){
        curCount++;
    }

    public void ejectBall(){
        if(curCount > 0){
            curCount--;
        }
    }

    public int getCurCount(){
        return curCount;
    }

}
```

Additionally, consider the following code that uses it:

```java
//... other code...

BallCounter leftHopperCounter  = BallCounter(5);
BallCounter rightHopperCounter = BallCounter(0);

leftHopperCounter.insertBall();
rightHopperCounter.ejectBall();
rightHopperCounter.insertBall();
leftHopperCounter.insertBall();

System.out.println(leftHopperCounter.getCurCount())
System.out.println(rightHopperCounter.getCurCount())
//... other code...
```

When this code runs, what is printed out? Break the code down step-by-step, in order of execution, to try to trace what happens.

If you're not sure, go make some new .java files in the code, copy-paste in the code to the right spots so it will run, and try running it.

## Problem 2

Add a new method to the class from problem 1 called `transfer()`. It should take some other BallCounter as input, remove one ball from the other BallCounter, and add it to the current hopper. 

Add code to indicate transferring two balls from the left hopper to the right hopper.

## Problem 3

Create a new class from scratch to help control an air compressor and air tank.

In general, an air compressor can be "running" or "not running", a boolean. When running, it pushes air into the tank, increasing the pressure. Once the pressure is high, for safety reasons, the compressor should shut off. Otherwise the tank might explode.

Write your class to match the following specs:

1) Constructor should accept a single floating point argument, `double max_pressure`, which sets the highest allowed pressure in the tank.
2) support a method named `setCurrentPressure()`, which will get called with one input argument to set the pressure in the tank. Presumably, this pressure would come from a sensor, but we'll just call it with test values for now.
3) support a method named `shouldCompressorRun()` that returns a boolean that is `true` if it's ok to run the compressor, `false` if not.

The class will have to remember the most recently measured pressure in the tank in order to return the proper value when asked whether the compressor should run or not.

Add code to instantiate two new compressor control classes, one with a very high limit for pressure, and one with a very low pressure.

Set the pressure in both of them to be the same, somewhere between the limits.

Confirm that the high pressure limit controller lets the compressor run, but the low pressure limit does not let the compressor run.


## Notes on How To Use Classes

For robot code, we tend to use classes in a specific way.

Each major component on the robot (drivetrain, elevator, arm, gripper, intake, etc.) will have its own class. Also, special classes for calculating autonomous routines will be created. WPILib provides many special classes to help interact with various physical components on the robot, which we'll cover in more depth soon.

One of the very first things we do, before writing any software, is to draw out what the major components are on the robot, and what sorts of software control they will need. This not only establishes the expected sensors/motors/actuators for each component, but also the information that needs to be communicated between classes and objects in the code.

Generally, in `Robot.java`, you declare variables at the top of the main robot class, one for each object you want to create.

Within `robotInit()`, instantiate each object with the `new` keyword`.

Then, inside the teleop and autonomous methods, call the approprate methods or access the approprate member variables, as needed.

A very similar patter should be applied within each class you create: Declare class-scoped variables at the top, initalize them in the constructor or in some custom `init` method, then access and manipulate them in other methods. 

## The Singleton Pattern

When you have a class which is guarnteed to only ever have exactly one object (ex: the Drivetrain - there is only ever one on a robot), it is sometimes useful to write your class as a _singleton_. This forces only one object to ever be created from that class. Don't worry too much about the details of how or why, but use the following as a reference while creating singletons:

```java
package frc.robot;

public class EmptySingleton {
	// You will want to rename all instances of "EmptySingleton" with your actual class name
	private static EmptySingleton instance = null;

	public static synchronized EmptySingleton getInstance() {
		if(instance == null)
			instance = new EmptySingleton();
		return instance;
	}

	// This is the private constructor that will be called once by getInstance() and it should instantiate anything that will be required by the class
	private EmptySingleton() {

	}

    //Add variables and methods as normal below here
    double sampleMemberVariable = 42.0;
}
```

Then, to use it, you use the following style of code:

```java
// ... other code ...

double value = EmptySingleton.getInstance().sampleMemberVariable;

// ... other code ...
```

The key here: Rather than declaring a new variable of the type of the class and using the `new` keyword, you instead use the _classname_ and the special `.getInstance()` method. Then, after that you can use `.memberVariable` or `.methodName()` as normal to access methods or variabels inside the class. Since the constructor has been declared as `private`, it's not possible to use the `new` keyword on this class and make more than one object from the class.

Don't worry too much if this doesn't entirely make sense now. History has shown that students tend to pick it up pretty fast when they see it in context.