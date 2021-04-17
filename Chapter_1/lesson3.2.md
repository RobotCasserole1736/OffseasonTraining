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

## Problem 1

Copy and paste that if/else example code just into the `LessonThree.java` file (between the approprate comment blocks).

What text do you expect to show up in the Terminal? 

<details>
<summary> Hint </summary>

Here's how to break down the question:

1) What does `System.out.println()` do?
2) How does that relate to the question?
3) What does `if / else if / else` do?
4) Given the values of `cond1` and `cond2`, which blocks of code should be executed? Which ones should be skipped?
5) Combine the answers from all the above.

</details>

Run the code to see if it matches your expectations.

<details>
<summary> Answer </summary>
 "Ran ELSE IF". 
 
 This is because of the following: `cond1` is `False`, so the first statement is skipped. However, `cond2` is `True`, so it's code block gets run. Finally, since the `else if`'s block got run, there is no need to do the default `else` block.

</details>

## Problem 2 - Right Triangles

We're going to do some basic geometry for this problem. For those of you who haven't taken geometry yet, there's just a few facts to know:

1) A triangle always has 3 sides, and 3 angles.
2) A "right" triangle has one angle which measures exactly 90 degrees.
3) Assuming the length of the sides of the triangle are variables A, B, and C - you can determine if a triangle is "right" or not by checking the following three facts are true:
    1) All sides are longer than 0.
    2) Side C is longer than both A and B
    3) The _square_ of side C's length equals the square of A's length, plus the square of B's length. (IE, `C^2 = A^2 + B^2`). 

Given these three integer inputs:

```java
int ASideLen
int BSideLen
int CSideLen
```

Write code that prints out "It's a right triangle!" when the three side lengths meet the properties above for being a "right" triangle. Don't print anything extra for when it's not a right triangle.

Look at the initial values for the A/B/C Side Lengths'. Do you expect 3/4/5 to be a right triangle?

Run your code and see if the print out in the console window matches your expectations. 

Change the initial values for A/B/C Side Lengths and try again. Test with your own inputs as you like. 

Using your code: is 0/0/0 a right triangle? is 3/4/6 a right triangle?
