# Lesson 6.3 - Some Special-Cases with Class Usage

For robot code, we tend to use classes in a specific way.

Each major component on the robot (drivetrain, elevator, arm, gripper, intake, etc.) will have its own class. Also, special classes for calculating autonomous routines will be created. WPILib provides many special classes to help interact with various physical components on the robot, which we'll cover in more depth soon.

One of the very first things we do, before writing any software, is to draw out what the major components are on the robot, and what sorts of software control they will need. This not only establishes the expected sensors/motors/actuators for each component, but also the information that needs to be communicated between classes and objects in the code.

Generally, in `Robot.java`, you declare variables at the top of the main robot class, one for each object you want to create.

Within `robotInit()`, instantiate each object with the `new` keyword`.

Then, inside the teleop and autonomous methods, call the appropriate methods or access the appropriate member variables, as needed.

A very similar patter should be applied within each class you create: Declare class-scoped variables at the top, initialize them in the constructor or in some custom `init` method, then access and manipulate them in other methods. 

## The Singleton Pattern

When you have a class which is guaranteed to only ever have exactly one object (ex: the Drivetrain - there is only ever one on a robot), it is sometimes useful to write your class as a _singleton_. This forces only one object to ever be created from that class. Don't worry too much about the details of how or why, but use the following as a reference while creating singletons:

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

The key here: Rather than declaring a new variable of the type of the class and using the `new` keyword, you instead use the _classname_ and the special `.getInstance()` method. Then, after that you can use `.memberVariable` or `.methodName()` as normal to access methods or variables inside the class. Since the constructor has been declared as `private`, it's not possible to use the `new` keyword on this class and make more than one object from the class.

Don't worry too much if this doesn't entirely make sense now. History has shown that students tend to pick it up pretty fast when they see it in context.