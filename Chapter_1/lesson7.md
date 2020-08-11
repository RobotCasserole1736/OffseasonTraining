
# Lesson 7 - Signals

On Robot Casserole, we use a special set of classes in a common, "evergreen" library that is reused year to year. The most commonly used classes involve "signals", something you've already had exposure to. We'll dig into these in a bit more detail now.

## What is "Timeseries Data"

Signals are used to track timeseries data, soemthing which is very common on robots. "Timeseries data" is simply a set of values that have changed over time, as well as _the time at which the value occurred at_. 

For example, say you're on a roadtrip. Every time you pass a milemarker sign on the side of the road, you write down the mile number, as well as the time on the clock. Huzzah! You have created timeseries data!

The fact that both _distance_ and _the time at which the distance was achieved_ were recorded is very powerful. It allows you to accurately calculate velocity, or genereate plots of how your distance changed over time. 

Values with timestamps are, in a way, a function. Given some time `t`, you can look up some recorded data value `y`. The set of all `t/y` pairs could be described as the function `f`, which takes in a time, and produces the data value.

`y = f(t)`, if you will.

On the robot, the easiest way to debug lots of problems it to track values in software as they change over time. We want to both view this data in real-time (ie, on the website), and record it to file to review later. This allows us to analyze robot behavior in a very detailed way.

To facilitate this, we created and continue to maintain `Signal` related classes, which allow code to easily and cleanly define timeseries data at robot runtime. In turn, the libraries consume this timeseries data and present it to the user via the website or data log files.

## How we get data into the website

You've already seen the primary ways of seeing data changing over time on the website.

The **Robot State** page allows you to see the latest value of every signal present in the code.

The **RT Plot** page allows you to make a timeseries plot (time on the X axis, value on the Y) of one or more signals. 

State is good for quick checks, though RT Plots is generally needed for more advanced tuning and analysis.

These websites work through a protocl named [websockets](https://en.wikipedia.org/wiki/WebSocket). There are really two servers running on the robot. One is used to provide the HTML/Javascript/css files associated with the webpages. Additionally, a different one sources signal data over websockets. When your web browser on your computer visits the webpage, it first loads the site from the first server, then connects to the second to start _streaming_ data back over the websockets connection. 

The details of this aren't important to use the website, of course. However, they're good to have in the back of your mind while debugging issues, as they can help point you toward properlying interpreting errors and troubleshooting.

## Log files - how we get data back off

In addition to the website, all signals will dump their data to .csv, or "comma-separated value" files. They have a human readable format where each value is separated by a comma. The first row is the name of each signal, the second row is the units, and all other lines contain data. Each "column" is dilimted by the comma characters, and each row must have the same number of columns. The first column always represents the time in seconds.

For example:

```csv
TIME,signal1Name,signal2Name,signal3Name,
sec,RPM,bool,NA
0.01,100,0,3
0.02,124,0,3
0.03,125,1,3
0.04,245,1,4
...
```

These files get stored to a USB drive plugged into the roboRIO. You can get access to them in one of two ways:

1) Power off the robot, pull out the USB drive, plug it into your laptop, copy the files over, place the drive back on the robot.
2) Run the "log file snagger" script, which connects over the network to the roboRIO and copies the files for you.

The snagger script is definitely preferrable to ensure people don't forget to plug the drive back in. But, there's nothing really special about it - it just copies files over the ethernet network to your computer.

Once you have the files on your computer, you can open them in any text editor or excel. However, they're best viewed through the log file viewer local website (.html file). It will present a very similar interface to the robot website, but allows you to load log files from past runs (rather than streaming data in real time).

Again, the details aren't super important here, but good to keep in mind.

# Problem 1 - Add a new signal

Look at the patterns for signals in the other files. Add one new signal within `LessonTwo.java` which outputs the _sum_ of `result1`, `result2`, and `result4`. Visit the website and confirm the value shows up, and has the expected value. Be sure to toggle the 

# Problem 2 - Analyzing the log file

After problem 1, there should be a new .csv file created for every time you start running in teleop. Confirm these show up under the `sim_data_captures` folder. You can use the timestamps in the file names to confirm when the files were started.

With the code running, Open the RT Plots webpage for the robot, and find any signal whos value changes over time when telop is enabled.

After disabling the robot, find the assocaited log file from that run. Double-click the `log_viewer/dataViewer.html` file to load it in a web browser, and then load up the .csv file from your most recent teleop sesson. Confirm that the changes in the "moving" signal you found in the website appear the same in the .csv file viewed through `dataViewer.html`.

