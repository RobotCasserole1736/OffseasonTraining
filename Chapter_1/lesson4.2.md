# Lesson 4.2 - Methods Application - Parity Switch

## Background

[Watch this video to understand how switches work on the inside.](https://www.youtube.com/watch?v=q6nP1FjxAMU) 

Assume this is a "critical" application where we have hooked up both on our robot.

In normal operation, only one input should be `True` at any time. Both inputs being `True` (or both inputs being `False`) indicates an electrical issue.

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

