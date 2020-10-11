## Lesson 6 - Merging, Releasing, and Keeping Track of Versions

This lesson, we'll discuss a bit more about how we use Git to keep track of our development.

Git is a tool - a very flexible one. Just like any tool, there's multiple ways to use it. You can use it well to make your life easy, or use it carelessly and cause a lot of problems. Our goal this lesson is to talk through some of the good ways to use it, hopefully to make our lives easier.

### Branch Strategy

As you've probably seen so far, we've been having folks always do development on a unique branch, named both with their own name, and what they're working on.

By keeping the branch unique to individuals and "hunks" of work that are being done, we help ensure other people don't mess up each other's work. And, we help ensure that we have a rough organization to when features were added or bugs were fixed.

We would classify all of these as _development_ branches - whatever software changes are on them are hopefully meaningful, but aren't guarnteed to have been reviewed, tested, or otherwise proven to have any level of "correctness". That's fine - they're the little sandboxes where students and mentors can play and figure things out.

However, at some point, the work has to be done. Testing is completed, code changes are reviewed, and the code changes become "blessed". At this point, it's time to **merge** to an _integration_ branch.

An _integration_ branch is usually just "master". It's the sum total of all tested or "blessed" content that we've developed as a team so far. In general, the latest on the integration branch is the latest software that could be loaded on to the robot and put into competition.

### Merging

`git merge` is the command that gets used to take one branch, and combine it into another branch. Git has a bunch of really nifty internal logic that will attempt to create a _union_ of the changes on both branches. It will detect conflicts and attempt to resolve them. 

However, if two different developers changed the _same line_ in the _same file_ in two different ways, Git will flag that change and request a human to help resolve it.

If we're carefull, these usually aren't too bad to walk through. Sometimes it is fairly nasty, and we like to avoid these. The main ways we avoid these really bad non-trivial merges:

 1. Ensure development branches always start from "latest on master". 
 2. Break the software up into logical files and classes, to ensure that unrelated activities will, for the most part, not touch the same files.
 3. Ensure that only one developer is working on each file at one time. 

To help with #3, we use Github's "issues" list to keep track of what needs to be done, and who is doing it. Really though, it just takes solid team communication.

### Tagging

Once we have some testing done on robot code and some level of confidence, we may want to mark that particular version of software as being "good". This helps us remember which version of software we tested with so we can go back to it as needed in the future.

`git tag` is the command used to apply a "tag" to the particular version of software we just finished testing. Usually, we'll use the following convention to name our tags:

`major.minor.maintenence.maturity`

Each of those is just a number, which starts at `0`, and increases upward.

`major` are for _major_ updates to software - big, non-backward-compatible changes. 

`minor` is the most-commonly updated field - small (normal-development) changes, feature additions, and bug fixes will incriment the minor field. Every time the `major` field incriments, we reset `minor` back to zero. Otherwise, minor just keeps increasing.

`maintenence` remains at `0` except in special cases - don't worry about it too much for now. 

`maturity` also is usually blank, though we might mark it as `DEV` occasionally.

This strategy is very similar a common one called "Semantic Versioning".

#### Tag Usage

During early development, when we want to mark intermedeate steps in software but don't yet have all the features in, it will be `0`. Once we have all required features in place, we'll change it to a `1`, and reset minor to `0`.

If we again completely reset the code or fully change architecture (very very rare), we might go to a `2.0.0` or `3.0.0` release. However, on the timescales for our robots (~6 months of development time at most), this is an exceedlingly rare occurance.

For all other changes, we'll just keep incrimenting the `minor` number.

The exact meaning of `maturity` is a bit fuzzy. Generally, we'll use `DEV` (or "development") to mark commits which we thought were important enough to track, but which haven't been fully tested. This often comes up for 

#### Maintainence

Occasionally, we'll have to go back to an old tag, make some changes, and pump out a new tag. This most commonly happens if software development has advanced beyond where the robot is at, but the version of software on the robot needs bugfixes.

In this case, we'll go back to the revision (for example, say `1.7.0`), make a **new** integration branch (named `master_1.7.x` or similar), start our develompent and tagging while keeping `1.7` constant, and incrimenting the maintainence number. (ie: `1.7.0`, `1.7.1`, `1.7.2`, etc.). 

This may eventually get merged back to the "mainline" main integration branch, or it might not. The key is that a non-zero maintainence number means that we went back in time and made changes to old code for some specific purpose.

### Sample Tag Progression

Here's a quick sample of how the rules above usually work:

```
0.1.0
0.2.0
0.3.0
1.0.0
1.1.0
1.2.0.DEV
1.2.0
1.3.0
1.4.0
1.3.1
1.3.2
1.5.0
1.6.0
...
```

### Why do any of this?

It's all about organization and communication. If you were working on your own, on a smaller by-yourself project, all of this branching and tagging would just be unnecessary overhead.

However, when working with lots of other people, it's important to remain organized. Clearly communicating what work you have and have not done is very important to ensure the team doesn't re-do work, or put accidentally something untested onto the competition field. Having defined markers in different versions of software provides a common vocabulary which the team can use to talk about "what changed?" from one version of software to another.

Of course, none of these reasons force use to use _the particular tagging and branching schemes that we do_. If you go out in the world, you'll see a lot of different strategies, each with their own pros and cons. The key is not that you pick the "one true answer" (Spoiler: there isn't one). The key is that everyone on the team agrees to the strategy and does the same thing. That way, we're all talking the same language, and can communicate most effectively.
