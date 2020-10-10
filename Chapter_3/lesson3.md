## Lesson 3 - Autonomous Design

We've made mention of things that have to happen during the autonomous period, and have so far structured our code to isolate command inputs (from driver or elswhere) and actual subsystem functionality.

The payoff to this is in how our robot runs during auto. By drawing a clean line between code that needs to be running always, versus code that has to be different between teleop and auto, we make our impelmention of auto much easier.

This lesson will serve to introduce a few of the tools in our toolbag for designing consistent, solid, advanced autononmous routines.

### Where do Commands Come From?

During autonomous mode, we have to generate a similar set of commands to what the driver provides, but without the driver there. This can be as simple as just calling the `set*()` methods for subsystems with constant values. Even better, if we call them with changing values _at the right times_, we can actively maneuver the robot very similarly to how the driver would have.

To help organize this, CasseroleLib provides a set of `AutoSequencer` and `AutoEvent` classes. The Sequencer creates a "timeline" out of custom-defined Event classes. During autonomous, it goes through each event one after another.

The idea is that, as a software team, we create many custom, reusable `AutoEvent`'s which cause the robot to do something meaningful. These might be things like "Raise the arm" or "Shoot three balls". 

Internally, each event has its own `init`/`update` structure, along with special ways to indicate when it is "done". The Sequencer steps through the events, advancing to each new one as the current one declares it is finished. There are additional hooks that coordinate shutting the event down if the robot exists autonomous mode in the middle of one of the events - a very common thing that happens.

### Drivetrain Nuances

The drivetrain tends to be fairly unique during autonomous. This is because, unfortunately, our code doesn't have the same level of visual feedback that the driver does while operating. Simply providing "fwd/rev" or "rotate" commands works for very simple movements, but will lack precision.

A more robust method is to define "Waypoints" on the field - X/Y/Rotation sets of information which indicate where we want the robot to be. In turn, a set of libraries called "Pathfinder" are used to convert these waypoints into a set of _wheel speed commands_ for the left and right sides of the drivetrain. Additionally, it provides an expected heading for the robot which, when combined with gyroscope data (And another PID controller), provides a good way to keep the robot on track.

There are additional methods that we'll want to investigate in the future, but for now, the big thing to keep in mind - autonomous provides commands for motor velocity (in RPM) and heading. Still commands, but different ones than what the driver would provide.