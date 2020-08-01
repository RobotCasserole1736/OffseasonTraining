
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

A _class_ is the blueprint for an _object_. It doesn't describe a specific thing, but rather _what that thing might be like_.

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

Often in robots, the line between _class_ and _object_ gets blurred, because there's only ever one drivetrain, or one arm, or one elevator. If you were to make a class for one of these (as we usually do), there should only ever be one _object_... and then it doesn't make a ton of sense to really differentiate. We'll talk about that more when we get to _singleton classese_. Don't worry about it too much for now.... but keep it in mind as we go forward.

## Rules for making a Class in Java

TODO

## Problem 1 

TODO make a new class

instantiate multiple instances of it
