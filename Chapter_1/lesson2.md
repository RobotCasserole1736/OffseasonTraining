
# Lesson 2

## Some Thoughts Before We Start

There are two broad categories of tasks we will perform while developing software: "Turnkey" tasks, and "Creative" tasks.

"Turnkey" tasks are things that "you just have to do." They're things like installing software, loading code on the robot, making a `for` loop, committing/pushing code in git, or copying files from old code. There shouldn't be a ton of creative thought involved. You just know there's a certain sequence of steps to do, a bunch of stuff that happens behind the scenes that you don't have to think about, and something useful that happens at the end. Kinda like turning the key in your car to start it: A simple set of actions, a lot of complexity behind the scenes, but the useful thing gets done at the end.

The key to success at turnkey tasks is just _memorizing_ how to do them. Ideally they're only a step or two, so it's easy to memorize. Worst case, write down the steps in a document, and memorize where you keep that document.

"Creative" tasks are ones that require creative thought and planning to execute. Designing software is a key example of this. Naming variables is another. Choosing between two sensors often requires creativity. This is often the work that at least _looks_ harder. Even professionals spend days agonizing over how to design just a few lines of code.

The key to success at creative tasks is two-fold: _breaking the problem into smaller problems_ and _pattern matching_. Most problems requiring creative solutions first have to be broken down into smaller problems. Then, the smaller problems are solved by finding similar problems with known solutions. Finally, you build up a big solution from all your smaller ones.

Creative problem solving is the hardest to teach, because it involves teaching _how to think_, rather than simply _what to think_. 

For the offseason, we tend to focus more on the turnkey tasks, as they tend to be the basis of the solutions to creative tasks. We'll try to introduce parts of the creative thinking process as well, but keep in mind a lot of this education will occur "on-the-job" during the season. Be ready to be "swept along" with the ever-educational current as the team takes off on the design and construction process.

**The mindset to keep**: It's not important to understand _everything_ about programming to be a good programmer. We're all learning and improving all the time. If you work hard and learn all you can during this offseason, we'll guarantee you'll make meaningful contributions during the build season. And every build season and project you work on after, you'll only get better and better!

## Concept Review

If you know the concepts from today's in-person lesson well, you can probably skip this. But just in case you forgot....

Recall the following concepts:

### Variables & Types

A _variable_ is a named "storage container" that holds some piece of information. Often, this information is a numeric quantity.

Though not identical to the variables you know from Algebra, you can often think of them the same way.

Variables have a _type_, which defines the sorts of values they might hold.

Simple types include `boolean`, `int` and `double`. 

A `boolean` stores a value which can only be _True_ or _False_. It's good for things like switches or logical conditions - things which can _only_ ever be "this" or "that".

An `int` or "integer" type stores whole-number values. It's good for when you want to count "how many".

A `double` or "double-precision floating point" type stores a real number, like `-2.5` or `3.14159`. It's good for expressing measurements, like the speed of a wheel (in RPM). 

Always choose the "simplest" type to describe your variable. Not only will this make the code run faster, but it will help communicate the _meaning_ behind your variable. 

Variables need to be _declared_ before they can be used. Variables get declared using the following _syntax_:

`<type> <name> = <initial value>;`

`<initial value>` is technically optional, but is highly recommended, especially for any robot code.

### Math 

Variables are combined and manipulated using basic math operations you are probably already familiar with.

`+`, `-`, `*`, and `\` represent addition, subtraction, multiplication, and division.

The `=` equals sign means "calculate the value on the right side, and store it into the variable on the left". Though this often looks like the usual math expressions (`4 = 3 + 1;`), keep in mind direction is important. Calculate right, assign left. That's why it's called the _assignment operator_.

Programming languages have an "order of operations", just in normal math. Just like normal math, you can use `(` and `)` parenthesis to group operations together, changing the order of operations.


<details>
<summary> Note</summary>
Chris learned the acronym PEMDAS (pronounced "paehm-dahs") to describe the Math order of operations. (Parenthesis, then exponentiation, then multiplication, then division, then addition, then subtraction). Chris also learned the trigonometric ratios and functions with the pneumonic not as our Native American friend SOH-CAH-TOA, but as "Some Old Hippie Caught Another Hippie Tripping On Acid" This explains a lot about Chris. We'll assume none of these are commonly-used pneumonics, and avoid their usage. But if you happen to know them, well, you can assume you'll turn out like Chris.
</details>


For example:

```java
result = 10 *  20 + 30;  
// result is now 230: First, 10*20 is 200. Then, 200+30 is 230.
result = 10 * (20 + 30); 
// result is now 500: First, 20+30 is 50. Then, 50*10 is 500.
```

### Logic

Those math operations work well with `int` and `double` types. `boolean` types have different, special operators.

`&`, or "and", returns true _if both inputs are true_.

`|`, or "or", returns true _if one or both of its inputs are true_.

`^`, or "xor", returns true _if exactly one of its inputs is true_.

`!`, or "not", returns true _if the its input is false_. It's different because it takes one input, where as all the rest take two.

Some examples:

```java

False = False & False;
False = False & True;
False = True  & False;
True  = True  & True;

False = False | False;
True  = False | True;
True  = True  | False;
True  = True  | True;

False = False ^ False;
True  = False ^ True;
True  = True  ^ False;
False = True  ^ True;

True  = !False;
False = !True;

```

## Problem 0 - Starting off.

Open a windows file explorer in the folder for the training repo we _cloned_ last practice. Right click in the white-space, and select "Git Extensions Browse".

![](doc/gitext_clone.png)

In the git extensions window that pops up, right click on the latest node you have checked out, and select "Create new branch here".

![](doc/gitext_new_branch.png)

Create a unique name for your branch. `yourname_training` is a good way to start, but feel free to get creative!

Default options are fine. Select all the "happy" buttons, hitting "Create" and "OK" until you see your new branch:

![](doc/gitext_new_branch_created.png)

## Problem 1 - Basic math operations.

Create three new variables, named `val1`, `val2`, and `val3`. All should be the type "floating point". Pick any initial values you want, but make sure at least one is negative, and one has some fractional part (ie, `10.5`).

Calculate the sum of `val1`, `val2`, and `val3`, and store the result into `result1`. 

What do you expect the result to be?

Run your code, visit the website, and confirm the result matches your expectations.

## Problem 2 - More math operations.

Create a new line of code which uses `val1`, `val2`, and `val3`, and does some math on them. It should also use parenthesis, division, and subtraction. Store the result into `result2`. Constant values (like `23.67`) are allowed.

Ensure that, with your choice of line of operations, `result2` ends up having a value between 40.0 and 50.0.

Calculate the value you expect your line to be. Run your code, visit the website, and confirm the result matches your expectations.

## Problem 3 - Basic Booleans

Create three new variables, `val4`, `val5`, and `val6`. All of them should be boolean values. Pick any starting values you want, as long as at least 1 of them is `False`.

Create some line of code that uses two of the values and the `|` operator, and produces a result which is `True`. Store that value into `result3`.

Run your code and ensure the output is `True`.

## Problem 4 - Boolean Application.

Assume that `val4` represents whether the robot driver has pushed a button that's labeled "arm raise". Assume further that `val5` represents whether the driver has pushed a button that's labeled "Emergency Stop". `True` means the button is pressed, `False` means the button is released.

Your task is to calculate the value of the output boolean `raiseArmNow`. When the value is set to `True`, the arm on the robot will move upward. When the value is `False`, the arm will stop movement.

The code to _actually_ move the arm has already been written by someone else. You just need to write the code to provide the _command_ to their code to say whether the arm should go up, or stay still.

Remember both `val4` and `val5` _could_ be either `True` or `False` - their values will change based on where the operator's fingers are at. You need to ensure your answer accounts for all _permutations_ of inputs.

What do you want `raiseArmNow` to do? How should it be related to its inputs?

<details>
<summary> Hint 1 </summary>
"Emergency Stop" means "don't move at all!". If that button is pressed, motion should never occur.
</details>

<details>
<summary> Hint 2 </summary>
The solution involves using a NOT operator on one of the inputs, then using a single boolean operator to combine that result with the other input. 
</details>


Write a line of code to calculate the value for `raiseArmNow`. Run the code, open the website, and check the results. Change initial values vor `val3` and `val4` to check different _permutations_ of inputs from the user.

## Experiment 1 - Integer Division

NOTE: This problem could be googled, but can also be determined by designing experiments and running them. We're hoping you do the latter. It's not super-important for knowing how to program robots, but is designed to help you understand techniques for writing code.

Create two new variables, `val7` and `val8`. Make both the `int` type. Start by assigning to any values you like, but be sure that `val7` is greater than `val8`.

Add the following line of code:

`result4 = val7/val8;`

What do you expect the result to be? What would it be if they were both doubles?

Run the code, visit the website, and observe the result.

Try a few new values for val7 and val8, repeating the expected/actual experiment. What is the pattern? What's going on here?

<details>
<summary> Explanation </summary>
In almost all programming languages, integer division _rounds down_ to the nearest lower integer. There are historical processor-hardware-related reasons for this. It's important to know, but generally shouldn't be _leveraged_ as part of making your code work. Or, if you do, definitely put in some comment to indicate that you _rely_ on the round-down integer division behavior.
</details>

## Challenge 1 - XOR from AND/OR/NOT

NOTE: This challenge problem is more of a "think about it and test" thing. It's not super important for programming robots, but definitely a worthwhile exercise, especially for those who want to expand their programming knowledge. If you're reading this, it means you probably have time to solve it, so continue on!

Create a few lines of code which calculates `C = A ^ B`, without using the `^` operator? Use only `!`, `|`, and `&`. Prove it works the same way as the `^` operator.

Fun fact: This proves that having an XOR operator isn't _necessary_, but is a very nice thing to have to make code look _cleaner_. Clean code is good because it more clearly indicates the _intent_ of what the code is supposed to do, and is easier to maintain and fix.

