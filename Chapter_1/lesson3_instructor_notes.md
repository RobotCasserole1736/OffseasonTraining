# Lesson 3.1: Comparison

## Talking Points

Booleans can be *created* by comparing two `double`'s or two `int`'s. Either the comparison is "true", or it is "not true".

A few different compariosn operators are provided:

`A == B` returns true if A and B are exactly the same, false otherwise.

`A <= B` returns true if A is less than or the same as B, false otherwise.

`A < B` returns true if A is less than B, false otherwise.

`>` and `>=` do exactly what you'd expect them to.

Note the subtle difference between `=` and `==` - it's a common mistake to mix them up. The way to remember: `=` assigns, `==` compares. It's just something to memorize. 

## Problem Set Notes

The ALL_CAPITAL_LETTERS format and presence of `final` indicates this is a _constant_ number. It's a promise to the roboRIO that we'll never change the numerical value of `SHOOTER_RUNNING_RPM` - it's a _constant_ value.

# Lesson 3.2: Making Decisions with `if` and `else`.

## `if` - `else if` - `else` Statements

Recall that java code is organized into "blocks", using the `{` and `}` characters.

```java
{
    //This is a block of code
}

{ 
    //This is a different block of code.
}
```

You can use "if-statements" to _control whether a block of code is executed or skipped_.

This is a very powerful concept: if/else allows you to use booleans to "turn on" or "turn off" certain code from executing. This is the basic mechanism for pieces of software to "make decisions" between alternatives.

The sequence must always be:

1) `if(<condition>)`
2) `else if(<condition>)` (optional)
3) `else` (optional)

Each `<condition>` must be (eventually) a boolean. Whether this is explicitly some `boolean` variable, or just a boolean created by comparison of two `int` or `double` values... doesn't quite matter. All that matters is that the thing inside the `()` can be interpreted as a `boolean`.

Note that both the `else if` and `else` statements are technically optional. You may or may not need them, depending on your situation.

Additionally, you may have as many `else if` statements as you want. Anywhere between 0 and infinity (actually, as many as your PC's memory will allow ... which is "a lot". ).

The blocks of code are always evaluated top-down. FIrst, the first `if` statement is checked. If its `<condition>` is `True`, its code block is run, and all the others are skipped. 

If the first statement's `<condition>` is `False`, it is skipped, and execution goes to the next `else if(<condition>)`. If _that_ one's `<condition>` is true, it is run. Otherwise, execution moves on to the next `else if(<condition>)`.

Finally, if there is an `else` statement and no prior `<condition>` has returned `True`, the `else` block is run.

Here's an example of the syntax:

```java

boolean cond1 = False;
boolean cond2 = True;

if(cond1){
    System.out.println("Ran IF");
} else if (cond2) {
    System.out.println("Ran ELSE IF");
} else {
    System.out.println("Ran ELSE");
}
```
### Problem Set Notes

As students go through the problems, introduce the concept as needed that blocks of code can be _nested_.