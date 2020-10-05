package frc.lib.DataServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*
 *******************************************************************************************
 * Copyright (C) 2018 FRC Team 1736 Robot Casserole - www.robotcasserole.org
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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import edu.wpi.first.wpilibj.Timer;
import frc.lib.Util.CrashTracker;

/**
 * DESCRIPTION: <br>
 * Basic controls for a customized Jetty embedded data server, serving up
 * flexible set of data samples, organized into acquisition lists and served at
 * reasonable, requested rates. <br>
 * ASSUMPTIONS: <br>
 * (none yet?) <br>
 * USAGE:
 * <ol>
 * <li>Instantiate class</li>
 * <li>On init, assign content to web pages.</li>
 * <li>Call startServer just before the robot enters disabled mode for the first
 * time.</li>
 * </ol>
 * 
 *
 */

public class CasseroleDataServer {

    /* Singleton infrastructure */
    private static CasseroleDataServer instance;

    public static CasseroleDataServer getInstance() {
        if (instance == null) {
            instance = new CasseroleDataServer();
        }
        return instance;
    }

    private Server server;
    private HashMap<String, Signal> signalList;

    public SignalFileLogger logger;

    private CasseroleDataServer() {
        signalList = new HashMap<String, Signal>();
        logger = new SignalFileLogger();
    }

    /**
     * Adds a new, manually-sampled signal
     * 
     * @param newSig
     */
    void registerSignal(Signal newSig) {
        signalList.put(newSig.getID(), newSig);
    }

    Signal getSignalFromId(String id) {
        return signalList.get(id);
    }

    /**
     * 
     * @return a simple array of all registered signals
     */
    public Signal[] getAllSignals() {
        Signal[] retval = new Signal[signalList.size()];
        signalList.values().toArray(retval);
        return retval;
    }

    public int getTotalStoredSamples() {
        int retval = 0;
        for (HashMap.Entry<String, Signal> entry : signalList.entrySet()) {
            retval += entry.getValue().samples.size();
        }
        return retval;
    }

    /**
     * Starts the web server in a new thread. Should be called at the end of robot
     * initialization.
     */
    public void startServer() {

        // New server will be on the robot's address plus port 5806
        server = new Server(5806);

        // Set up classes which will handle web requests
        // Mostly this is the JSON data streams for displaying state data and config and
        // stuff.
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // RT Plot Streamer - broadcasts things which can be plotted in real-time
        ServletHolder dataServerHolder = new ServletHolder("ds", new Servlet());
        context.addServlet(dataServerHolder, "/ds");

        // Kick off server in brand new thread.
        // Thanks to Team 254 for an example of how to do this!
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CrashTracker.logAndPrint("[Data Server]: Starting server");
                    server.start();
                    server.join();
                    CrashTracker.logAndPrint("[Data Server]: Server shut down");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        serverThread.setName("CasseroleDataServerThread");
        serverThread.setPriority(10);
        serverThread.start();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // The below methods and functions are all for handling signals
    // generated by annotation (ie, putting "@Signal" above a variable)

    /** Set of all auto-discovered signals from @Signal annotations */
    Set<AutoDiscoveredSignal> autoSig;
    /** Set of all java Reflection objects that we've analyzed for whether they have @Signal annotation or not
     *  In the event that two objects reference each other, this should help break an infinite-recursion case,
     *  since each unique object only needs to be checked once.
     */
    Set<Object> checkedObjects;

    

     /**
      *  Recursively-called signals-finding function.
      *   Starting at some object `root` it traverses each field (ie, variable or object or whatever)
      *   declared withing the class of that object, finds the objects of each of those fields, 
      *   and does none of two things:
      *       1) Check if it's annotated to be a signal
      *       2) Otherwise, call the function recursively on the new "child" object.
      *   Recursion stops if we hit #1, or if the object's class is not "frc.robot" - this should help
      *   keep search time reasonable, since this happens at runtime at robot init.
      *   Note this should NOT be called at periodic runtime.... because a) it's not tested that way and b) recursion.

      * @param root
      * @param prefix
      */
    public void findAllAnnotatedSignals(Object root, String prefix){
        Class rootClass = root.getClass();
        Package rootPkg = rootClass.getPackage();

        if(rootPkg != null && rootPkg.toString().contains("frc.robot")){
            //If we've got a valid package name inside of frc.robot, go through all the fields in the associated class.
            for(Field field : rootClass.getDeclaredFields()){

                //As we recurse, keep track of the full-name for the object as a "."-separated path of sorts.
                String newName = prefix + (prefix.length() > 0 ? "." : "") + field.getName(); 

                if(field.isAnnotationPresent(frc.lib.DataServer.Annotations.Signal.class)){
                    //Case #1 - we found a @signal annotation - create a new AUtoDiscoveredSignal
                    frc.lib.DataServer.Annotations.Signal ann = field.getAnnotation(frc.lib.DataServer.Annotations.Signal.class);
                    autoSig.add(new AutoDiscoveredSignal(field, root, newName,ann.units()));

                } else {
                    // No signal annotation - we should see if we can recurs on the object associated with this field
                    // First attempt to get the object and make it accessable.
                    Object childObj = null;
                    try{
                        field.setAccessible(true);
                        childObj = field.get(root);
                    }  catch(IllegalAccessException e) {
                        // Not 100% sure how this could get thrown. If so, print a warning, but move on without error.
                        System.out.println("WARNING: skipping " + field.getName());
                        System.out.println(e);
                    }

                    if(childObj != null && !checkedObjects.contains(childObj)){
                        checkedObjects.add(childObj);
                        findAllAnnotatedSignals(childObj, newName);
                    } //else, we either couldn't get a reference to the object, or we already checked it - stop recursion
                }
            } //End FOR

        } //else, rootPkg wasn't in frc.robot - stop recursion
    }



    /**
     * Special thanks to oblarg and his oblog for help on impelmenting this.
     * Entrypoint to set up required variable sets, and traverse rootContainer and
     * its children to find all @Signal-annottated fields, and add a new signal for each of them.
     * Call this in robotInit(), and after all classes which contain @Signals have been instantiated.
     * @param rootContainer Object to start the traversal on. Usually just "this" for when called in Robot.java. 
     */
    public void registerSignals(Object rootContainer) {
        autoSig = new HashSet<>();
        checkedObjects = new HashSet<>();
        findAllAnnotatedSignals(rootContainer, "");
        CrashTracker.logAndPrint("[Data Server]: Registered " + Integer.toString(autoSig.size()) + " signals from annotations");
    }

    /**
     * Periodic call function to sample a single value from all annotation-created Signals
     * Should be called at the end of each periodic function.
     */
    public void sampleAllSignals(){
        double sampleTime = Timer.getFPGATimestamp() * 1000;
        for(AutoDiscoveredSignal sig : autoSig){
            sig.addSample(sampleTime);
        }
    }




}
