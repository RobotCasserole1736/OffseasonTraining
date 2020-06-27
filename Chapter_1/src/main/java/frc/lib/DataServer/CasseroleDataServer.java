package frc.lib.DataServer;

import java.util.HashMap;

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

}
