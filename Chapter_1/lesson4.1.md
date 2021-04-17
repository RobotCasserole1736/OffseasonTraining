# Lesson 4.1: Organizing Code into Methods

One way to organize code into meaningful chunks is by creating and calling _methods_. Sometimes called _functions_, these are named blocks of code that can be "called" from other pieces of code.

There are multiple good reasons to move chunks of code into a separate _method_:

1) Reusability. Often, one small piece of code will need to be used in multiple spots. Rather than copying and pasting and duplicating the code, it's better to write it once in a method, then use that method wherever needed.
2) Readability. A 1000-line file is hard to understand. It's better to break it up into meaningful chunks, and give each chunk a _meaningful name_. We'll see simple examples of this as we go.


## Structure of a Method

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

## Execution Flow & Methods

When we want to use a method, it is said that we _call_ the method. We use its name to do this. 

At the point of call, code execution jumps to the method, runs through it from start to finish, then jumps back to the point of call.

To use the method we defined above, you could write the following lines of code:

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


