
# Lesson 6.1 - Dividing Code into Classes

It's time to get classy!

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
        System.out.println("Meow");
    }

    void feed(){
        weight_lbs += 0.25;
    }

    void cleanLitterbox(){
        // Todo
    }

}
```
The above code would have to be contained in its own, dedicated file named `Cat.java`.

Then, in a different `.java` file, you'd create two objects, both of type `Cat`, but with different names. Then, you can individually manipulate variables inside each object:

```java
void createCats{

    Cat mittens = new Cat();
    Cat spot = new Cat();

    mittens.weight_lbs = 25; //big cat
    spot.weight_lbs = 8; //small cat
}
```

## Some side thoughts

Often in robots, the line between _class_ and _object_ gets blurred, because there's only ever one drivetrain, or one arm, or one elevator. If you were to make a class for one of these (as we usually do), there should only ever be one _object_... and then it doesn't make a ton of sense to really differentiate. We'll talk about that more when we get to _singleton classes_. Don't worry about it too much for now.... but keep it in mind as we go forward.

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

It's not required to have a constructor, but is one convenient spot to put initialization code.

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

