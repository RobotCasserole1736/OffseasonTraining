
# Lesson 2.3: Optional Exploration

The following are little activities for you to try on your own time. They're not directly relevant for _most_ robot software, but are worthwhile to expand your knowledge of software in general. These answers could be googled, but can also be determined by designing experiments and running them. We're hoping you do the latter. 

## Experiment - Integer Division

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

## Challenge - XOR from AND/OR/NOT

NOTE: This challenge problem is more of a "think about it and test" thing. It's not super important for programming robots, but definitely a worthwhile exercise, especially for those who want to expand their programming knowledge. If you're reading this, it means you probably have time to solve it, so continue on!

The "Exclusive OR" operator is another commonly-defined logic operation.

`&`, or "XOR", returns true _if exactly one input is true_.

Create a few lines of code which calculates `result5 = val9 ^ val10`, without using the `^` operator. Use only basic logic operators (`!`, `|`, `&`) and grouping symbols `(` and `)`. Prove it works the same way as the `^` operator.

Fun fact: This proves that having an XOR operator isn't _necessary_, but is a very nice thing to have to make code look _cleaner_. Clean code is good because it more clearly indicates the _intent_ of what the code is supposed to do, and is easier to maintain and fix.

