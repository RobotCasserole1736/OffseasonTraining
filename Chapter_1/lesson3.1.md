
# Lesson 3.1: Comparison

Booleans can be *created* by comparing two `double`'s or two `int`'s.

`A == B` returns true if A and B are exactly the same, false otherwise.

`A <= B` returns true if A is less than or the same as B, false otherwise.

`A < B` returns true if A is less than B, false otherwise.

`>` and `>=` do exactly what you'd expect them to.

Note the subtle difference between `=` and `==` - it's a common mistake to mix them up. THe way to remember: `=` assigns, `==` compares. It's just something to memorize. 

## Comparison Example

To illustrate the usage, consider the following problem:

Create a boolean named `shooterRunning` which is `True` when the shooter wheel is spinning fast enough, and false otherwise.

Critical to this, we have to define the speed at which we are running "fast enough". This number should come from the mechanical team that's prototyping the shooter. It might be verified by software team doing empirical testing on the final robot. Regardless, we'd create a variable to describe it:

`final double SHOOTER_RUNNING_RPM = 3200.0;`

NOTE: The ALL_CAPITAL_LETTERS format and presence of `final` indicates this is a _constant_ number. It's a promise to the roboRIO that we'll never change the numerical value of `SHOOTER_RUNNING_RPM` - it's a _constant_ value.

Assume we have some measurement of shooter speed stored into the variable `shooterSpeed_RPM`. We can create our boolean:

`shooterRunning = (shooterSpeed_RPM >= SHOOTER_RUNNING_RPM);`

This will meet our requirement: `True` when the shooter is "up to speed", `False` otherwise.


