# Lesson 6.2 - Making a Compressor Control Class

This lesson will focus on designing a new class. It consists solely of this exercise:

# Problem

Create a new class from scratch to help control an air compressor and air tank.

In general, an air compressor can be "running" or "not running", a boolean. When running, it pushes air into the tank, increasing the pressure. Once the pressure is high, for safety reasons, the compressor should shut off. Otherwise the tank might explode.

Write your class to match the following specs:

1) Constructor should accept a single floating point argument, `double max_pressure`, which sets the highest allowed pressure in the tank.
2) support a method named `setCurrentPressure()`, which will get called with one input argument to set the pressure in the tank. Presumably, this pressure would come from a sensor, but we'll just call it with test values for now.
3) support a method named `shouldCompressorRun()` that returns a boolean that is `true` if it's ok to run the compressor, `false` if not.

The class will have to remember the most recently measured pressure in the tank in order to return the proper value when asked whether the compressor should run or not.

Add code to instantiate two new compressor control classes, one with a very high limit for pressure, and one with a very low pressure.

Set the pressure in both of them to be the same, somewhere between the limits.

Confirm that the high pressure limit controller lets the compressor run, but the low pressure limit does not let the compressor run.

