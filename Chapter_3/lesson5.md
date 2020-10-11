## Lesson 5 - Using CasseroleLib

In this lesson, we'll talk through the basics of the re-usable content from year to year.

### CasseroleLib - What and Why

CasseroleLib is a set of `.java` files which are designed to be reused every year. They're generic and flexible, and not designed around any robot in particular.

In most robot projects, you'll find them under the `src/main/java/frc/lib` folder. There's a repo in our github area named CasseroleLib, but as of fall 2020 it's fairly out of date. Just reference a recent robot project for the files.

Ordinarily, "libraries" like these would be stored in a separate repository, built into binary files, and released separately from the main robot project. However, since we often like to make updates and improvements to the content, we've adopted the strategy of just keeping the source files in the robot repo. This makes for easy modification.

Additionally, we don't _really_ mean for these files to be consumed by any robot other than our own. We've had other teams reference the content and occasionally copy files. However, we've not put ourselves in the business of supporting the libraries in a more "general purpose" sense. 

### CasseroleLib - the How

There's a few main points to keep in mind when using CasseroleLib.

Most files are either standalone, or just reference other .java files within that `lib` folder. Best practice we've found so far is to just copy all the files over - as far as we can tell having a few extra java files doesn't hurt anything.

The webserver does reference some other libraries - namely some Google JSON libraries, and Jetty (a Java-based webserver library). To properly run these files, you'll need to modify the build.gradle file in the root of the repository. 

Find the `dependencies` block, and add the following:

```gradle
    //Casserole WebServer Deps
    compile 'com.googlecode.json-simple:json-simple:1.1.1'
    compile group: 'org.eclipse.jetty', name: 'jetty-http', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty', name: 'jetty-server', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty', name: 'jetty-servlet', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty', name: 'jetty-util', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty', name: 'jetty-security', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty.websocket', name: 'websocket-server', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty.websocket', name: 'websocket-client', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty.websocket', name: 'websocket-servlet', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty.websocket', name: 'websocket-api', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty.websocket', name: 'websocket-common', version: '9.3.9.v20160517'
    compile group: 'org.eclipse.jetty', name: 'jetty-io', version: '9.3.9.v20160517'
    compile group: 'javax.servlet', name: 'javax.servlet-api', version: '3.1.0'
```

This should go right before the `simulation` statement near the end of the block.

The purpose of these statements is to tell gradle, the tool which builds our software, that it needs to go to the internet and get extra things before it can build the webserver content.

Finally, you'll need to copy the `.html`/`.css`/`.js` files into the `src/main/deploy` folder. You'll see a `www` folder in there which contains the files. When the webserver is running, whenever a client requests the website, its the files that are in this folder which get used to satisfy the request.

FYI: The reason they're in the `deploy` folder is that, at code deployment time, gradle will copy over the whole folder without modification to a folder on the roboRIO's filesystem.

### A Brief Overview of the Contents of CasseroleLib

Here's a brief description of what we have implemented in common libraries - all just so you have an overview of what you shouldn't have to re-implement from scratch.

#### AutoSequencer

This set of classes defines the event/timeline architecture we use for creating autonomous routines.

#### Calibration

This set of classes implements the logic for calibratable values, both as they are used in the robot code and on the website.

#### CasserolePID

These are a set of classes which implement a PID controller with a few more "bells and whistles" than the standard WPILib one.

#### DataServer

This set of classes provides the implemenatation for Signals, and the web-based services which allow web browsers to request the robot to stream signal data back to them.

#### LoadMon

This is a small set of classes which monitors CPU and memory load on the roboRIO. This is useful for ensuring we don't take up too many resources in our code.

#### SignalMath

This is a set of classes which implement some common operations on timeseries values - values which change over time.

They include calculating the integral and the derivative of signals by various methods, remembering a set of values in a "circular buffer", and calculating a "rolling average" of the past couple samples. All of these are useful operations for manipulating sensor data.

#### PathPlanner

These are classes associated with converting sets of waypoints into wheel velocity commands.

#### Utilities

These are miscelaneous classes for doing arbitrary 2D lookups, sensor debouncing operations, and tracking code crashes in a nice logging format.

#### WebServer

These are classes associated with serving the robot's website to computers which connect to it.
