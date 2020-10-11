## Lesson 4 - Vision Processing

Vision Processing is one of the most hotly-discussed topics in FRC robotics. The concept is pretty simple: Use a camera to measure where your robot is at, relative to some known target on the field. This, in turn, can be used to move the drivetrain into a known position where shooting or scoring gamepieces is easier.

The devil is in the details though. There's a lot that goes into it, and a lot that goes into doing it well. As before, this will be a brief overview of how such systems work.

### Hardware

A vision processing system will consist of a few components:

#### The Co-Processor

With few exceptions, most vision processing tasks require an extra computer on the robot. The Raspberry Pi line of single-board computers has been the most common answer of recent days. This provides additional computational "horsepower" over what the roboRIO can provide, and helps separate time-sensitive tasks for camera work away from the main robot code. It also helps in ensuring the two systems can be developed relatively independently, then joined together on the final robot.

#### The Camera

Of course, an actual camera is needed to capture the images. A USB web cam is one common option. There are also more low-level cameras which have faster response time, but require you're using certain computers to be able to hook to properly.

High resolution is rarely needed - actually, something like 300x200-ish pixels is pretty standard. There are reasons to deviate from this, but it's the usual starting point.

#### The LED's

FRC-style targets are _retro-reflective_ - they pass light that hits them back in the same direction it came from. This means that if you put a bright light right next to a camera, point the whole thing at a target, the target will glow very bright in the image. This makes it particularly easy to recognize the very-bright target from its relatively-dark background.

### Software

There's a lot of different options for how to actually perform the vision processing. There's a few pre-written answers out there, as well as some customized solutions that students have developed on our team in past years.

All of them will do the same general sequence of steps:

For every picture that is delivered from the camera, the software goes through it pixel by pixel, and determines which ones are probably part of a target. Usually, this means bright pixels, and sometimes ones that are the right color.

Of the pixels that are part of the target, the next step is to group them together into big clumps called "contours". The contour is a set of lines that surround the bright clump of pixels. You can look at the size and location of the contour to infer that same info about the target you're looking at.

Then, can filter down the contours to only realistic looking ones.

Finally, based on the most-likely contour, there's two options for interpreting the data. One is to just use some simple trigonometery to figure out your angle to the target. Especially for shooters, this is often sufficent. Alternatively, there's an algorithm called "solvePNP" where you give the 3-d coordinates of different parts of the target, and 2-d coordinates of where you saw those parts in the image. The algoritm uses a bunch of fancy-pants matrix math to use that input and spit out your 3-d coordinates (relative to the target).

Ultimately, this "where are you relative to the target" information gets passed back to the roboRIO via some mechanism (usually the ethernet network). This allows the drivetrain or mechanisms to use it as part of some PID or other feedback controller to achieve the proper position.

### Integration

It's admiteddly a lot of work to create one of these systems from scratch. Thankfully, there's been a lot of good options over the past few years. 

**Limelight** is one option which is fairly expensive, but comes pre-integrated with the great hardware and software tuned to work together really well. Very little work for the end team to have to do.

**PhotonVision** is another more-recent option which does a lot of the hard software work, but requires you bring your own hardware.

Depending on the year, and the skillset on the team, different options might be better than others. It'll just depend.

### Hands-On Demonstration

Grab yourself the following things:

 1. Something bright, colorful, and uniform - like a post-it note!
 2. A computer with a webcam
 3. [Install GRIP](https://docs.wpilib.org/en/stable/docs/software/vision-processing/grip/introduction-to-grip.html), a tool for experimenting with vision processing algorithms

Fire up GRIP, and put together a pipline to detect your bright colored thing.

If you're looking for a good starting point, use:
 
 1. Webcam Input
 2. HSV Threshoold
 3. Find Contours
 4. Filter Contours

Verify output from start to end, debugging as you go. HSV Threshold should be tuned to detect just the color you're looking for. Filter the contours to detect only the thing you're looking for.

Try with other objects, and use more complex detection strategies and pipelines.

GRIP isn't 100% useful for on-robot work, but is a good platform to explore some of the basics of how vision processing works.