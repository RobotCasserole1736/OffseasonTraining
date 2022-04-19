# Lesson 4.1: Organizing Code into Methods

## Talking Points


One way to organize code into meaningful chunks is by creating and calling _methods_. Sometimes called _functions_, these are named blocks of code that can be "called" from other pieces of code.

There are multiple good reasons to move chunks of code into a separate _method_:

1) Reusability. Often, one small piece of code will need to be used in multiple spots. Rather than copying and pasting and duplicating the code, it's better to write it once in a method, then use that method wherever needed.
2) Readability. A 1000-line file is hard to understand. It's better to break it up into meaningful chunks, and give each chunk a _meaningful name_. We'll see simple examples of this as we go.

Every method can optionally take _inputs_, called _arguments_. Additionally, they can output a single value, called a _return value_.


When we want to use a method, it is said that we _call_ the method. We use its name to do this. 

At the point of call, code execution jumps to the method, runs through it from start to finish, then jumps back to the point of call.

Within the `()`, pass in any arugments the method needs.

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

# Lesson 4.2 - Methods Application - Parity Switch

## Talking Points

On a robot, we use switches to detect when mechanisms have reached certain positions, and change robot behavior. These switches go by many names, including "limit switches" and "micro switches". 

[Watch this video to understand how switches work on the inside.](https://www.youtube.com/watch?v=q6nP1FjxAMU) Note how there are two outputs, "Normally-Open"(NO) and "Normally-Closed"(NC).


Usually we only hook up one of the NC/NO terminals on a robot. However, for cases where safety and redundancy are important, we might hook up both.

In this case, we'll have two booleans associated with a single switch: `switch_NC_state` and `switch_NO_state`

In normal operation, exactly one of these will be `true` at all times. Which one is `true` indicates whether the switch is pressed or not. If it's noticed that both are the same (both `true` or both `false`), we would have to assume that something is broken. Maybe the switch itself, maybe the wiring, it depends. However, we do know for sure we can't trust the switch's state. 

Having both lines hooked up allows us detect "broken" in software. With this info, we could turn on a light to alert the pit crew that something is wrong. Additionally, we could put the robot in a "safe state" (usually, just don't run the motors).

Now go take a look at the problem in 4.2 with the students, and walk through it with them as needed.