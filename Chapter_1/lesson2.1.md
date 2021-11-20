
# Lesson 2.1: Variables and Math

## Facts You'll Need to Know

### Variables & Types

A _variable_ is a named "storage container" for information

Variables have a _type_, which defines the types of information they hold

A `boolean` stores a value which can only be _True_ or _False_.

An `int` or "integer" type stores whole-number values.

A `double` or "double-precision floating point" type stores a real number, like `-2.5` or `3.14159`. 

### Declaring Variables

Variables need to be _declared_ before they can be used.

Variables get declared using the following _syntax_:

`<type> <name> = <initial value>;`

For example:

`double shooterSpeed_RPM = 20;`

### The Meaning of `=`

In software, `=` has a more narrowly-defined meaning than you are used to in normal math.

A single equals sign (`=`) is the "assignment" operator. It tells the computer to take the thing on the _right side_, and put it into the thing on the _left side_. 

### Doing Math 

Variables are combined and manipulated using basic math operations you are probably already familiar with.

`+`, `-`, `*`, and `\` represent addition, subtraction, multiplication, and division.

Just like normal math, you can use `(` and `)` parenthesis to group operations together, changing the order of operations.

In software, always express calculations on the **Right-hand side** of the equation. The left-hand side should only have the *variable* where the result is to be stored.

<details>
<summary> Note </summary>
Chris learned the acronym PEMDAS (pronounced "paehm-dahs") to describe the Math order of operations. (Parenthesis, then exponentiation, then multiplication, then division, then addition, then subtraction). Chris also learned the trigonometric ratios and functions with the pneumonic not as our Native American friend SOH-CAH-TOA, but as "Some Old Hippie Caught Another Hippie Tripping On Acid" This explains a lot about Chris. We'll assume none of these are commonly-used pneumonic devices, and avoid their usage. But if you happen to know them, well, you can assume you'll turn out like Chris.
</details>


For example:

```java
result = 10 *  20 + 30;  
// result is now 230: First, 10*20 is 200. Then, 200+30 is 230.
result = 10 * (20 + 30); 
// result is now 500: First, 20+30 is 50. Then, 50*10 is 500.
```

### Code Comments

A comment is a line in code intended only for humans, and ignored by the computer.

Use them to explain the meaning and intent of different pieces of code.

```java

// Two leading slashes (//) in the line means "this line is a comment"

boolean thisIsActualCode = true;

// But this is another comment.

If we forget to put slashes in front, the computer will complain and throw "syntax" errors on this line.

int someMoreRealCode = 23; // Comments can also happen after real code.

```

<details>
<summary> Note </summary>

```java
/* Slash-star sequences surrounding text is also a comment */

double anotherPieceOfRealCode = 123.456;

/**
 * Code comments may
 * also take up more than
 * one line if you use the
 * slash-star format
 */ 

```
</details>

In particular, in each lesson `.java` file, you'll see things like this:

```java

    ////////////////////////////////////////////////
    // Declare new variables after this line...

    int yourNewVariable = 42;

    // ...but before this line.
    ////////////////////////////////////////////////
```


## Problem 0 - Starting off.

Open a windows file explorer in the folder for the training repo we _cloned_ last lessons. Right click in the white-space, and select "Git Extensions".

![](doc/gitext_clone.png)

In the git extensions window that pops up, right click on the latest node you have checked out, and select "Create new branch here".

![](doc/gitext_new_branch.png)

Create a unique name for your branch. `yourname_training` is a good way to start, but feel free to get creative!

Default options are fine. Select all the "happy" buttons, hitting "Create" and "OK" until you see your new branch:

![](doc/gitext_new_branch_created.png)

## Problem 1 - Basic math operations.

Open the file `lessonTwo.java`. 

Find the comment blocks which indicate `Declare new variables after this line...`, and `... but before this line`.

Between those comment,: create three new variables, named `val1`, `val2`, and `val3`. 

All should be the "floating point" type, `double`. Pick any initial values you want, but make sure at least one is negative (ie, `-1736`), and one has some fractional part (ie, `10.5`).

Find the comment blocks which indicate `Add your problem-solution code after this line...`, and `... but before this line`. 

Write **one** new line of code to calculate the **sum** of `val1`, `val2`, and `val3`, and store the result into `result1`. 

Before you run your code, think through what it should do. What number do you expect the result to be?

Run your code, verifying no syntax errors occur in the Terminal output window. Fix any that do happen, and re-run.

Visit the website, and confirm the result matches your expectations.

## Problem 2 - More math operations.

Create a new line of code which uses `val1`, `val2`, and `val3`, and does some math on them. It should also use parenthesis, division, and subtraction. Store the result into `result2`. Constant values (like `23.67`) are allowed. 

Ensure that, with your choice of math operations, `result2` ends up having a value between 40.0 and 50.0.

Calculate the value you expect your line to be. Run your code, visit the website, and confirm the result matches your expectations.
