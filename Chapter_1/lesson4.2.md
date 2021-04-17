# Lesson 4.2 - Methods Application - Parity Switch

## Background

On a robot, we use switches to detect when mechanisms have reached certain positions, and change robot behavior. These switches go by many names, including "limit switches" and "micro switches". 

[Watch this video to understand how they work on the inside.](https://www.youtube.com/watch?v=q6nP1FjxAMU) Note how there are two outputs, "Normally-Open"(NO) and "Normally-Closed"(NC).

Usually we only hook up one of the NC/NO terminals on a robot. However, for cases where safety and redundancy are important, we might hook up both.

In this case, we'll have two booleans associated with a single switch: `switch_NC_state` and `switch_NO_state`

In normal operation, exactly one of these will be `true` at all times. Which one is `true` indicates whether the switch is pressed or not. If it's noticed that both are the same (both `true` or both `false`), we would have to assume that something is broken. Maybe the switch itself, maybe the wiring, it depends. However, we do know for sure we can't trust the switch's state. 

Having both lines hooked up allows us detect "broken" in software. With this info, we could turn on a light to alert the pit crew that something is wrong. Additionally, we could put the robot in a "safe state" (usually, just don't run the motors). 

## Task

We will create new functions to calculate the following pieces of info:
* Is the switch pressed or not?
* Is the switch broken or not?

Write two new functions. Both functions should take two inputs. Each input is a boolean. The inputs should represent the states of the NO and NC contacts on the switch.

Both functions should return boolean values. 

Call the functions, passing in the two boolean variables `switch_NC_state` and `switch_NO_state` as inputs. Ensure the return value for the approprate function gets placed into the `switch_pressed` and `switch_faulted` variables.

Look at the present values for `switch_NC_state` and `switch_NO_state`. What do you expect the `pressed` and `faulted` values to be?

Run your code, go to the website, and confirm.

How many different combinations of `switch_NC_state` and `switch_NO_state` are possible? Re-run the code to try all of them, calculating what the  `pressed` and `faulted` values should be each time, and confirming reality and your expectations match.

