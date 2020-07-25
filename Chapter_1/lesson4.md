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

Every method can optionally take _inputs_, called _arguments_. Additionally, they can output a single value, called a _return value_.

Methods are written in the following form:

```java
<return type> <method Name>(<arg 1 type> <arg 1 name>, <arg 2 type> <arg 2 name>, <...>){
    <method> contents>
}
```

For a very simple example, here's an example of a method that adds two numbers:

```java
double addTwoNumbers(double firstNumber, double secondNumber){
    return firstNumber + secondNumber;
}
```

Note that this code only _declares_ that a method with the name `addTwoNumbers` exists in your code. It doesn't actually cause it to get _used_ anywhere.

### Execution Flow

When we want to use a method, it is said that we _call_ the method. We use its name to do this. At the point of call, code execution jumps to the method, runs through it from start to finish, then jumps back to the point of call.

To use this method, you could write the following lines of code:

```java
//...

double result;

result = addTwoNumbers(3, 5);

System.out.println(result);

//...
```

What do you expect to be printed out from running this code?

Trace the execution: First, the values 3 and 5 are passed into our method, `addTwoNumbers`. Inside that method, the two values are added and the result is returned (in this case, 8). That returned value ends up in the variable named `result`, which is then printed to the screen. Therefor, we expect `8` to be printed to the screen.

Note, you've already been using methods in the code you've written. 

For one, all of the code you've written lives inside specific methods, which we know will get called in a specific sequence while the code is running. 

Also, `System.out.println("some string")` is a special-named method which takes its argument (a string) and does some "behind the scenes magic" to print it to the console. You don't have to worry about any of the complexity of how this happens, you just call the method, and it _happens_. That's the beauty.

### But, Why use methods in my own code?

Well, there's a number of reasons.

For one, it helps keep things organized. Grouping pieces of related functionality helps to visually separate ideas, which makes your code easier for others (and yourself!) to read. Carefully named fundtions are a good way to understand chunks of code at a high level, without _having_ to keep in mind the details.

Additionally, it helps prevent copying-and-pasting code. Though copy-paste is very useful, it's risky to do it to big chunks of code. Aside from taking up more lines, imagine if you had a bug in the code before you started copying and pasting it to many spots. Now, to fix that bug, you have to go to every place you copy-pasted it to. Can you be sure you remember every one of those locations? It's hard! 

By using methods to reuse code, you avoid some of the risk of bugs propagating throughout the code.

We're just introducing the concept formally now, and showing you how to start making and using your own custom methods. We'll see more concrete examples of how and when to use them later

### Common examples of Custom Methods in Robot Code

There's a few common patterns you'll see for how methods get organized in robot code.

`update` methods perform all the actions required to happen _every 20 ms_ to maintain control of some portion of the robot.

`init` methods get called once before repeated calls `periodic`, to do one-time setup operations.

`get` methods return meaningful values - think of them as the "output" points of a hunk of code.

`set` methods take in meaningful values for a hunk of code. Think of them as the "input" points.

`telemetryUpdate`broadcasts key pieces of data to the robot website for debug help.

These names are chosen somewhat arbitrarily, and could technically be anything. The key is that usage and patterns remain as consistent as possible throughout the code, to make it easier to understand.

## Problem 1

Create a new method which takes three `double`'s as input arguments. We'll call them `inputA`, `inputB`, and `inputC`. The function should also return a `double`.

Inside the method, calculate which value is the _middle_ value. That is to say, if you ordered the three inputs from highest to lowest, return the value that would be in the middle of the list. 

Expect to use a few if/else statements, and boolean comparisons from last time.

Call your new method twice, with two different sets of numbers. Ensure the result ends up in the `output1` and `output2` variables.

Based on the numbers you put into your code, what value do you expect `output1` and `output2` to have?

Run the code, visit the website, and confirm that both `output1` and `output2` have those values. Fix the code if not.

## Problem 2

### Background

On a robot, we use switches to detect when mechanisms have reached certain positions, and change robot behavior. These switches go by many names, including "limit switches" and "micro switches". 

[Watch this video to understand how they work on the inside.](https://www.youtube.com/watch?v=q6nP1FjxAMU) Note how there are two outputs, "Normally-Open"(NO) and "Normally-Closed"(NC).

Usually we only hook up one of the NC/NO terminals on a robot. However, for cases where safety and redundancy are important, we might hook up both.

In this case, we'll have two booleans associated with a single switch: `switch_NC_state` and `switch_NO_state`

In normal operation, exactly one of these will be `true` at all times. Which one is `true` indicates whether the switch is pressed or not. If it's noticed that both are the same (both `true` or both `false`), we would have to assume that something is broken. Maybe the switch itself, maybe the wiring, it depends. However, we do know for sure we can't trust the switch's state. 

Having both lines hooked up allows us detect "broken" in software. With this info, we could turn on a light to alert the pit crew that something is wrong. Additionally, we could put the robot in a "safe state" (usually, just don't run the motors). 

### Task

We will create new functions to calculate the following pieces of info:
* Is the switch pressed or not?
* Is the switch broken or not?

Write two new functions. Both functions should take two inputs. Each input is a boolean. The inputs should represent the states of the NO and NC contacts on the switch.

Both functions should return boolean values. 

Call the functions, passing in the two boolean variables `switch_NC_state` and `switch_NO_state` as inputs. Ensure the return value for the approprate function gets placed into the `switch_pressed` and `switch_faulted` variables.

Look at the present values for `switch_NC_state` and `switch_NO_state`. What do you expect the `pressed` and `faulted` values to be?

Run your code, go to the website, and confirm.

How many different combinations of `switch_NC_state` and `switch_NO_state` are possible? Re-run the code to try all of them, calculating what the  `pressed` and `faulted` values should be each time, and confirming reality and your expectations match.


