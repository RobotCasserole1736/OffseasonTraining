# Lesson 4.1: Organizing Code into Methods

Using methods helps us give meaningful names to groups of instructions.

This help us make our code reusable, reducing development and maintenance effort.

## Structure of a Method

Methods are written in the following form:

```java
<return type> <method Name>(<arg 1 type> <arg 1 name>, <arg 2 type> <arg 2 name>, <...>){
    <method> contents>
}
```

Here's a simple example for adding two numbers:

```java
double addTwoNumbers(double firstNumber, double secondNumber){
    return firstNumber + secondNumber;
}
```

We have defined the method's functionality - we will _use_ it later on.

## Execution Flow & Methods

To run a method, use it's name, followed by `()`.

Pass in arguments as needed.

```java
//...

double result;

result = addTwoNumbers(3, 5);

System.out.println(result);

//...
```

What do you expect to be printed out from running this code?

Trace the execution: First, the values 3 and 5 are passed into our method, `addTwoNumbers`. 

Inside that method, the two values are added and the result is returned (in this case, 8). That returned value ends up in the variable named `result`, which is then printed to the screen. 

Therefor, we expect `8` to be printed to the screen.

Note: Using methods is something you've been doing since the start of these lessons. `System.out.println("some string")` is a method!

`get`, `set`, `init`, and `update` are common methods which you will have to implement within the robot code you write.

## Problem 1

Create a new method which takes three `double`'s as input arguments. We'll call them `inputA`, `inputB`, and `inputC`. The function should also return a `double`.

Inside the method, calculate which value is the _middle_ value. That is to say, if you ordered the three inputs from highest to lowest, return the value that would be in the middle of the list. 

Expect to use a few if/else statements, and boolean comparisons from last time.

Call your new method twice, with two different sets of numbers. Ensure the result ends up in the `output1` and `output2` variables.

Based on the numbers you put into your code, what value do you expect `output1` and `output2` to have?

Run the code, visit the website, and confirm that both `output1` and `output2` have those values. Fix the code if not.


