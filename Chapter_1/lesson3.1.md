# Lesson 3.1: Comparison

Comparing two `double`'s or two `int`'s will create a boolean value. Either the comparison is "true", or it is "not true".

```java

int A =  5; 
int B = -3;
boolean ans;

ans = (A == B); // Checks if A and B are exactly the same. In this case, 5 is not equal to -3, so ans is False.
ans = (A <= B); // Checks if A is less than or equal to B. In this case, ans is False.
ans = (A >  B); // Checks if A and B are exactly the same. In this case, ans is True.
```

Key thing to memorize:

`==` performs **comparison**.

`=` performs **assignment**.

## Problem 1

Create a boolean named `shooterRunning` which is `True` when the shooter wheel is spinning fast enough, and false otherwise.

Critical to this, we have to define the speed at which we are running "fast enough". This number should come from the mechanical team that's prototyping the shooter. It might be verified by software team doing empirical testing on the final robot. Regardless, we'd create a variable to describe it:

`final double SHOOTER_RUNNING_RPM = 3200.0;`

Assume we have some measurement of shooter speed stored into the variable `shooterSpeed_RPM`. We can create our boolean:

`shooterRunning = (shooterSpeed_RPM >= SHOOTER_RUNNING_RPM);`

This will meet our requirement: `True` when the shooter is "up to speed", `False` otherwise.


