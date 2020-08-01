# Lesson 5 - Using the Debugger

## Some Thoughts Before We Begin

Don Knuth is one of the great pioneers of software engineering. He once stated something very true: 

_Software is, first and foremost, meant to be read by human beings. Only incidentally does it get executed by a machine._

This is something that is good to keep in mind for any software writing, but especially when writing code in a team environment. The software is intended to _communicate to other team members_ what your _intent_ for the robot's functionality is. It just so happens we use this "java" thing to express that intent, so that the robot can eventually run that software too.

Intent is best-expressed by:

1) Choosing good, meaningful names for your variables and methods.
2) Applying liberal code comments to explain the "why" 
3) Keeping "like" code in one spot (ie: Don't put arm code into the shooter.java file)
4) Splitting out code into meaningful sub-chunks (ie: break long segements of code into methods with cohesive outcomes)
5) Don't Repeat Yourself (DRY) (ie: use methods to re-use code chunks, don't copy-paste)

The good news is you've already learned most of the organizational tools needed to write good code. The bad news is that it still takes practice to become proficient at using those tools. But fear not! The very fact you are reading this means you're already on the path to becoming more proficient! Keep up the hard work, and you'll be amazed where it will take you!

## The Debugger

Up till now, the pieces of code we've written are pretty small. Small enough that by just staring at them, you can probably predict how they'll work.

Ideally, as we write more and more code, we'll keep it as organized as possible, to help keep this "inspectability". However, inevitably, bigger projects create more complex problems, that need more advanced tools to unravel.

We've already looked at one of these tools: inserting `System.out.println()` calls at critical portions of your code allow you to check multiple things. This includes order of execution, and values of variables at various times.

You've also seen the website utilities, which allow you to see values of variables as they change over time.

We will introduce one additional tool that you can use to help investigate problems. It's called the _single step debugger_, or simply the "debugger".

The debugger allows you to stop code execution on certain lines, "step" through your code's execution line by line, and print out values of variables and expressions at runtime. The biggest advantage of the debugger is that it doesn't require injecting any additional lines of code into your software - it _just works_. (usually :D).

When running code locally on a desktop, the debugger is automatically running in the background - nothing special you have to do to start it.

Regardless of what debug tool you happen to be using, the technique for solving problems is generally the same:

1) Look at the code, and decide how you are expecting it to work.
2) Investigate the code as it executes from start to end.
3) Find the first place where the behavior of the code deviates from your expectations.

## Walk-Through

### Launching the Debugger

### Setting Breakpoints

### Stepping through lines of code

#### Over vs. Into

### Viewing Variable Values

### Changing Variable Values

### Problem Solving

Here's what we expect the code to do. Step through it. Observe it doesn't do it. Use debugger to figure out why. Change code so it does do it. Test.

Problem.... maybe a misnaming between variables?

