## Lesson 6 - Constants and Calibrations

We've been able to not only have a functional robot, but we've added some extra logic to help make the robot's operation easier for the driver. We'll now look at one way we can make the robot easier to work with while we're testing on the practice field and the competition fields.

### Constants and their Usage

Consider the code you wrote for automating the elevator. We stated the cube would have to be released at 3ft, and grabbed at 0.5ft. How did you capture those two numeric values? Perhaps you just typed `3` and `0.5` into your code. This would be fine - it's certanly functional. However, it's generally discouraged, as it would be called a "magic constant". If someone were to come back in a few months looking through the code, it might not be immedeately clear why `3` needs to be in the code. 

It's generally good to at least have a comment. Even better, make a `final` variable with a meaningful name. Something like this:

```java
    final double UPPER_PRESET_HEIGHT_FT = 3.0;
```

The addition of the word `final` is a hint to the Java runtime environment that the value is never going to change ever. This allows it to optimize the code more. The ALL_CAPITAL_LETTERS is a reminder to us that the value is indeed a constant.

You'll notice we do this a lot for describing the ports and CAN ID's used by sensors and motor controllers. We'll even make a special class called `RobotConstants` to hold them all in one handy spot.

However, there's another mechanism we have at Casserole that would be even more approprate: a `Calibration`.

### Calibrations

A `Calibration` is a value which _should_ be constant while the robot is running, but might have to be tweaked up and down as we're testing and tuning the robot. 

Unlike something like "Port a motor controller is plugged into", the height at which we have to release the cube might not be a number which we know _exactly_ at the time we write the software. Maybe the real feild will be slightly different than our practice field. The height _should_ be constant once we've got the robot built and on the real field.... but we'll need to "tune" that value to be just right.

A `Calibration` shows up on the website, providing us a way to tweak the value quickly while the robot is running. By not requiring us to change code and re-deploy, we can achieve faster tuning "cycles" - as we try things by trial and error, we can adjust on the fly till we like how the robot works. Then, as a one-time final step, we transfer the new values into software to becaome the new defaults.

Each `Calibration` allows the software team the ability to provide a nice English description, a default value, and a range of allowable values to help prevent a "silly" calibration value from harming the robot.

### Activity

We're going to modify our Elevator Control class from last time to change the upper/lower preset positions to be calibratable.

#### Code Changes

First, declare and instantiate two new `Calibration` objects in the elevator control class. Set their default values to the same 3.0/0.5 values you had before, and pick a reasonable allowable calibration range based on the size of the elevator.

Here's a sample of how to instantiate a Calibration:

```java
    //New calibration with default value of 1.0, minimum value of 0.0, and maximum value of 50.0
   myNewCal = new Calibration("Name of the Calibration", 1.0, 0.0, 50.0); 
```

Then, wherever you had previously hard-coded constant values before, instead use the `.get()` method from your new calibrations to read the value.

#### Testing

Run the code and launch the website. Go to the Calibrations page and ensure your new calibrations show up there.

Enable the robot, and press the buttons to make the elevator go to the preset heights. Ensure it goes to 3.0 and 0.5 as before. Disable the robot.

Go to the calibrations page, and change both the preseet heights to different values. 

Enable the robot again, and perform the same test. Ensure the elevator still goes to preset height values, but to the new heights you just calibrated.

