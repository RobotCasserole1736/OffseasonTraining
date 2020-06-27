package frc.lib.WebServer;

/*
 *******************************************************************************************
 * Copyright (C) 2017 FRC Team 1736 Robot Casserole - www.robotcasserole.org
 *******************************************************************************************
 *
 * This software is released under the MIT Licence - see the license.txt
 *  file in the root of this repo.
 *
 * Non-legally-binding statement from Team 1736:
 *  Thank you for taking the time to read through our software! We hope you
 *   find it educational and informative! 
 *  Please feel free to snag our software for your own use in whatever project
 *   you have going on right now! We'd love to be able to help out! Shoot us 
 *   any questions you may have, all our contact info should be on our website
 *   (listed above).
 *  If you happen to end up using our software to make money, that is wonderful!
 *   Robot Casserole is always looking for more sponsors, so we'd be very appreciative
 *   if you would consider donating to our club to help further STEM education.
 */

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * DESCRIPTION: <br>
 * Driver View webpage definition class. Adds new things to the driver view
 * webpage (webcam mjpeg videos or dials). Has methods to update the dial
 * displayed values. <br>
 * ASSUMPTIONS: <br>
 * Be sure the casserole webserver is started at some point, otherwise the
 * webpage won't be displayed. <br>
 * USAGE:
 * <ol>
 * <li>Instantiate class</li>
 * <li>On init, call newDial and newWebcam once per object to be displayed on
 * the driver view webpage.</li>
 * <li>During runtime, call setDialValue to update the dial value display</li>
 * </ol>
 * 
 *
 */

public class CasseroleDriverView {
    /**
     * The list of objects which are broadcast. Must be volatile to ensure atomic
     * accesses
     */
    static volatile Hashtable<String, DriverViewDial> dialObjects = new Hashtable<String, DriverViewDial>();
    static volatile Hashtable<String, DriverViewWebcam> webcamObjects = new Hashtable<String, DriverViewWebcam>();
    static volatile Hashtable<String, DriverViewBoolean> booleanObjects = new Hashtable<String, DriverViewBoolean>();
    static volatile Hashtable<String, DriverViewSoundWidget> soundWidgetObjects = new Hashtable<String, DriverViewSoundWidget>();
    static volatile Hashtable<String, DriverViewAutoSelector> asObjects = new Hashtable<String, DriverViewAutoSelector>();
    static volatile Hashtable<String, DriverViewStringBox> stringBoxObjects = new Hashtable<String, DriverViewStringBox>();

    /**
     * Create a new dial to display on the driver view webpage. Should be called at
     * init, as new dials cannot be added at runtime. Dials are designed for two
     * purposes: providing detailed value information at runtime, as well as
     * at-a-glance indication of acceptable/unacceptable. The numbers and units
     * presented on the dial provide the first, while the red/green outline around
     * the arc of travel shows the operator whether the value is acceptable without
     * reading any numbers. Use the min/max acceptable limit arguments to define
     * this range.
     * 
     * @param name_in           Name of the value to display. Also used to reference
     *                          the value when updating it.
     * @param min_in            Minimum value displayed on the dial.
     * @param max_in            Maximum value displayed on the dial.
     * @param step_in           Step value between dial tick marks.
     * @param min_acceptable_in Lower limit of green display area on drawn dial.
     * @param i
     */
    public static void newDial(String name_in, double min_in, double max_in, double step_in, double min_acceptable_in, double max_acceptable_in) {

        DriverViewDial newDial = new DriverViewDial(name_in, min_in, max_in, step_in, min_acceptable_in,
                max_acceptable_in);
        dialObjects.put(name_in, newDial);
    }

    /**
     * Add a new webcam to the driver view webpage. Should be called at init, as new
     * webcams cannot be added at runtime.
     * 
     * @param url_in         Web address of the motion JPEG stream from the camera.
     * @param marker_x       X draw position of the crosshairs (in percent - 0% is
     *                       full left, 100% is full right).
     * @param marker_y       Y draw position of the crosshairs (in percent - 0% is
     *                       full top, 100% is full bottom).
     * @param name_in        Name of the web stream. Internal uses only,
     *                       currently...
     */
    public static void newWebcam(String name_in, String url_in, double marker_x, double marker_y) {

        DriverViewWebcam newWebcam = new DriverViewWebcam(name_in, url_in, marker_x, marker_y, 0); //Rotation is currently broken
        webcamObjects.put(name_in, newWebcam);

    }

    /**
     * Create a new String to display on the driver view webpage. Should be called
     * at init, as new string values cannot be added at runtime. Strings are hard to
     * read and should be used sparingly. If at all possible, use a boolean or dial.
     * 
     * @param name_in Name of the value to display. Also used to reference the value
     *                when updating it.
     */
    public static void newStringBox(String name_in) {

        DriverViewStringBox newStringbox = new DriverViewStringBox(name_in);
        stringBoxObjects.put(name_in, newStringbox);
    }

    /**
     * Create a new Boolean indicator to display on the driver view webpage. Should
     * be called at init, as new indicators values cannot be added at runtime.
     * Designing with these indicators should follow a "dark console" philosophy: or
     * normal operations, all indicators should be dark. Red indicators should
     * illuminate for very bad abmormailites (pressure low, batt voltage low, etc).
     * Yellow is for less severe conditions which degrade performance. Green is for
     * conditions which are not usually present, but are good. (ex: ball in intake).
     * 
     * @param name_in  Name of the value to display. Also used to reference the
     *                 value when updating it.
     * @param color_in Color to display. Currently, only supported values are "red",
     *                 "yellow", and "green".
     */
    public static void newBoolean(String name_in, String color_in) {

        DriverViewBoolean newBoolean = new DriverViewBoolean(name_in, color_in);
        booleanObjects.put(name_in, newBoolean);
    }


    /**
     * Create a new Sound Widget. This will play the specified file on the
     * user's PC on the false->true transitions of value
     * 
     * @param name_in  Name of the value to display. Also used to reference the
     *                 value when updating it.
     * @param fname_in File name to play. Should be something supported by web browsers, like .mp3
     */
    public static void newSoundWidget(String name_in, String fname_in) {

        DriverViewSoundWidget newSW = new DriverViewSoundWidget(name_in, fname_in);
        soundWidgetObjects.put(name_in, newSW);
    }

    /**
     * 
     * @param name_in
     * @param options_in
     */
    public static void newAutoSelector(String name_in, String[] options_in) {

        DriverViewAutoSelector newAutoSelector = new DriverViewAutoSelector(name_in, options_in);
        asObjects.put(name_in, newAutoSelector);
    }

    /**
     * Display a new value on an existing dial at runtime
     * 
     * @param name_in  Name of the dial to update
     * @param value_in Value to display on the dial. Should be in the min/max range
     *                 assigned for the dial, or the displayed value will be
     *                 truncated.
     */
    // might be called from different threads, but all calls go to the web server
    // thread.
    public static synchronized void setDialValue(String name_in, double value_in) {
        if (dialObjects.containsKey(name_in)) {
            dialObjects.get(name_in).setVal(value_in);
        } else {
            System.out.println(
                    "Warning: Driverview web server: No dial named " + name_in + " exists yet. No value set. ");
        }
    }

    /**
     * Display a new value on an existing string box at runtime
     * 
     * @param name_in  Name of the dial to update
     * @param value_in Value to display on the dial. Should be in the min/max range
     *                 assigned for the dial, or the displayed value will be
     *                 truncated.
     */
    // might be called from different threads, but all calls go to the web server
    // thread.
    public static synchronized void setStringBox(String name_in, String value_in) {
        if (stringBoxObjects.containsKey(name_in)) {
            stringBoxObjects.get(name_in).setVal(value_in);
        } else {
            System.out.println(
                    "Warning: Driverview web server: No stringbox named " + name_in + " exists yet. No value set. ");
        }
    }

    /**
     * Display a new value on an existing boolean display
     * 
     * @param name_in  Name of the indicator to update
     * @param value_in Value to display on the indicator.
     */
    // might be called from different threads, but all calls go to the web server
    // thread.
    public static synchronized void setBoolean(String name_in, boolean value_in) {
        if (booleanObjects.containsKey(name_in)) {
            booleanObjects.get(name_in).setVal(value_in);
        } else {
            System.out.println(
                    "Warning: Driverview web server: No boolean named " + name_in + " exists yet. No value set. ");
        }
    }

    /**
     * Changes the play-desired value of a sound widget
     * 
     * @param name_in  Name of the widget to update
     * @param value_in true = play sound, false = stop
     */
    // might be called from different threads, but all calls go to the web server
    // thread.
    public static synchronized void setSoundWidget(String name_in, boolean value_in) {
        if (soundWidgetObjects.containsKey(name_in)) {
            soundWidgetObjects.get(name_in).setVal(value_in);
        } else {
            System.out.println(
                    "Warning: Driverview web server: No sound widget named " + name_in + " exists yet. No value set. ");
        }
    }

    /**
     * Update the location of the crosshairs on the webcam stream
     * 
     */
    // might be called from different threads, but all calls go to the web server
    // thread.
    public static synchronized void setWebcamCrosshairs(String name_in, double x_pct, double y_pct) {
        if (webcamObjects.containsKey(name_in)) {
            webcamObjects.get(name_in).setCrosshairs(x_pct, y_pct);
        } else {
            System.out.println(
                    "Warning: Driverview web server: No webcam named " + name_in + " exists yet. No value set. ");
        }
    }

    /**
     * 
     * @param name_in
     * @param options_in
     */
    public static String getAutoSelectorVal(String name_in) {

        if (asObjects.containsKey(name_in)) {
            return asObjects.get(name_in).getVal(name_in);
        } else {
            System.out.println("Warning: Driverview web server: No Auto Selector named " + name_in
                    + " exists yet. No value available. ");
            return "";
        }
    }

    /**
     * @return a set of DriverViewObjects which can be used for working on the set
     *         of all things on the driver view.
     */
    public static ArrayList<DriverViewObject> getAllObjects() {
        ArrayList<DriverViewObject> tmp_list = new ArrayList<DriverViewObject>();

        tmp_list.addAll(dialObjects.values());
        tmp_list.addAll(webcamObjects.values());
        tmp_list.addAll(booleanObjects.values());
        tmp_list.addAll(soundWidgetObjects.values());
        tmp_list.addAll(stringBoxObjects.values());
        tmp_list.addAll(asObjects.values());

        return tmp_list;
    }

}
