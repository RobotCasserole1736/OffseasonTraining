# Lesson 4 - Making Methods

## Some Thoughts Before We Begin

What does it take to get "good" at programming?

There's a lot of ways to answer this, but my top answer is: _practice_.

Maybe you play a musical instrument, or have a friend who does. Maybe you/they are really good at it too! Consider the following: _professional_ musicians, who are arguably _really_ good at their instrument, spend 4-5 hours playing their instrument a day. That's 4-5 hours, above and beyond anything "required" for gigs, performances, rehearsal, recording... Just 4-5 hours honing their skills and having fun at it.

Now, programming and playing an instrument isn't an apples-to-apples comparison. So, we shouldn't say everyone should spend 4-5 hours per day on programming, and then they'll be at a professional level. However, keep in mind that scale - to be good at something, you'll likely have to practice for _hours_ on a daily basis, not _minutes_. 

Now, for robotics, we definitely don't need everyone to be a professional programmer to make a good robot. Far from it. In fact, there's no real time commitment outside of robotics at all. And, if you're heavily invested and engaged while you're here, you'll probably get _pretty darn good_ at doing this engineering thing.

However, if you desire to grow even faster, how do you go about doing that? _Practice_. Create personal projects, pick an end goal, lead and guide them yourself, and see how close to your goal you can get! If you need ideas or help on getting started, let any of the mentors or returning students know, they'll have ideas for sure!

## Methods

One way to organize code into meaningful chunks is by creating and calling _methods_. Sometimes called _functions_, these are named blocks of code that can be referenced from other pieces of code.

Methods are helpful to prevent copy-pasting the same code many times throughout the codebase, which in turn helps prevent the spread of bugs.

Every method can optionally take _inputs_, called _arguments_. Additionally, they can output a single value, called a _return value_.

Functions take the following form:

```java
<return type> <function Name>(<arg 1 type> <arg 1 name>, <arg 2 type> <arg 2 name>, <...>){
    <function contents>
}
```
For a very simple example, here's an example of a function that adds two numbers:

```java
double addTwoNumbers(double firstNumber, double secondNumber){
    return firstNumber + secondNumber;
}
```

Note, you've already been using methods in the code you've written. 

For one, all of the code you've written lives inside specific methods, which we know will get called in a specific sequence while the code is running. 

Also, `System.out.println("some string")` is a special-named method which takes its argument (a string) and does some "behind the scenes magic" to print it to the console. You don't have to worry about any of the complexity of how this happens, you just call the method, and it _happens_. That's the beauty.

We're just introducing the concept formally now, and showing you how to start making and using your own custom methods.

## Problem 1

TODO

###############################################################
