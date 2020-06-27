package frc.lib.DataServer;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;

import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.File;


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


/**
 * DESCRIPTION: <br>
 * Provides an API for FRC 1736 Robot Casserole datalogging on the robot during testing or matches.
 * Will write lines into a CSV file with a unique name between calls to init() and close().
 * output_dir is hardcoded to point to a specific 2016 folder on a flash drive connected to the
 * roboRIO. <br>
 * <br>
 * USAGE:
 * <ol>
 * <li>Instantiate Class</li>
 * <li>Create global variables containing arrays of strings to represent the column (data vector)
 * names and units</li>
 * <li>During teleop init or autonomous init, call the init() function to start logging data to a
 * new file.</li>
 * <li>Once per loop, call the writeData() method with the full list of values to write to file (all
 * must be converted to doubles).</li>
 * <li>During DisabledInit, call the close() method to close out any file which was being written to
 * while the robot was doing something.</li>
 * <li>Post-match or -practice, extract the data logs from the USB drive(maybe using FTP?) and view
 * with excel or your favourite software.</li>
 * </ol>
 * 
 * 
 */

public class SignalFileLogger {

    String log_name = null;
    final String OUTPUT_DIR_RIO = "/U/data_captures/"; // USB drive is mounted to /U on roboRIO
    final String OUTPUT_DIR_LOCAL = "./sim_data_captures/"; //local directory for log files in sim.

    //Handle to the actual file being logged to
    BufferedWriter log_file = null;

    Hashtable<Signal, Integer> logIdxLookup;

    double curTimestamp = -1.0;
    String[] logLine;

    Lock fileLoggerStateLock;
    boolean loggingActive = false;

    BlockingQueue<DataSample> sampleQueue;

    ///////////////////////////////////////////////////////////////////
    // Public API
    ///////////////////////////////////////////////////////////////////

    /**
     * Constructor
     */
    public SignalFileLogger(){
        sampleQueue = new LinkedBlockingQueue<DataSample>();

        fileLoggerStateLock = new ReentrantLock();

		// Kick off monitor in brand new thread.
	    // Thanks to Team 254 for an example of how to do this!
	    Thread monitorThread = new Thread(new Runnable() {
	        @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
    
                    fileLoggerStateLock.lock();
                    try{
                        if(loggingActive){
                            DataSample samp = sampleQueue.poll();
                            while(samp != null){
                                writeLogData(samp);
                                samp = sampleQueue.poll();
                            }
                        }
                    } finally {
                        fileLoggerStateLock.unlock();
                    }
                }
            }
        });
        
	    //Set up thread properties and start it off
	    monitorThread.setName("DataServer File Logger Task");
	    monitorThread.setPriority(Thread.MIN_PRIORITY);
	    monitorThread.start();
    }

    public void startLoggingAuto(){
        fileLoggerStateLock.lock();
        try{
            if(!loggingActive){
                init("AUTO");
                sampleQueue.clear();
                loggingActive = true;
            }
        } finally {
            fileLoggerStateLock.unlock();
        }
    }

    public void startLoggingTeleop(){
        fileLoggerStateLock.lock();
        try{
            if(!loggingActive){
                init("TELEOP");
                sampleQueue.clear();
                loggingActive = true;
            }
        } finally {
            fileLoggerStateLock.unlock();
        }
    }

    public void stopLogging(){
        fileLoggerStateLock.lock();
        try{
            if(loggingActive){
                loggingActive = false;
                forceSync();
                close();
            }
        } finally {
            fileLoggerStateLock.unlock();
        }
    }

    //But this method may be called from any thread
    public void addSample(DataSample samp_in){
        if(loggingActive){
            sampleQueue.add(samp_in);
        }
    }

    public int getSampleQueueLength(){
        return sampleQueue.size();
    }

    ///////////////////////////////////////////////////////////////////
    // Private logging impelmentation
    ///////////////////////////////////////////////////////////////////

    private void writeLogData(DataSample samp_in){
        double timestamp_s = samp_in.getSampleTime_ms()/1000.0;
        double val = samp_in.getVal();
        Integer idx = logIdxLookup.get(samp_in.getParentSignal());

        if(idx != null){
            if(curTimestamp == -1.0){
                //Very first call for this log. Mark the initial timestamp
                curTimestamp = timestamp_s;
            }

            if(timestamp_s == curTimestamp){
                //Still logging the present timestamp. Add value into the logLine array.
                logLine[idx] = Double.toString(val);
            } else {
                //We've moved on to the next timestamp. Write the line to disk and then add the valuel to the logLine array
                writeData(); 
                Arrays.fill(logLine,"");
                curTimestamp = timestamp_s;
                logLine[idx] = Double.toString(val);
            }
        } else {
            System.out.println("Developer Error - Attempt to log value from signal \"" + samp_in.getParentSignal().display_name + "\", which was not instantiated prior to opening the log. It is not allowed to instantiate new signals while a log is being recorded!");
        }
    }

    /**
     * Determines a unique file name, and opens a file in the data captures directory and writes the
     * initial lines to it.
     * 
     * @param data_fields A set of strings for signal names to write into the file
     * @param units_fields A set of strings for signal units to write into the file
     * @return 0 on successful log open, -1 on failure
     */
    private int init(String logPrefix) {

        //Sample the current set of signals from the DataServer
        Signal[] allSigs = CasseroleDataServer.getInstance().getAllSignals();
        int numSigs = allSigs.length;
        logIdxLookup = new Hashtable<Signal, Integer>(numSigs*2);

        //Set up local lists of data names and units
        String[] data_fields = new String[numSigs];
        String[] units_fields = new String[numSigs];

        for(int sigIter = 0; sigIter < numSigs; sigIter++){
            data_fields[sigIter] = allSigs[sigIter].getDisplayName();
            units_fields[sigIter] = allSigs[sigIter].getUnits();
            logIdxLookup.put(allSigs[sigIter], sigIter);
        }

        logLine = new String[numSigs];
        Arrays.fill(logLine,"");

        curTimestamp = -1.0;

        try {

            String folderName;
            // Determine a unique file name
            if(Robot.isReal()){
                log_name = OUTPUT_DIR_RIO + "log_" +DriverStation.getInstance().getEventName()+"_"
                +DriverStation.getInstance().getMatchType()+"_"
                +Integer.toString(DriverStation.getInstance().getMatchNumber())+"_"
                +  getDateTimeString() + "_" + logPrefix + ".csv";
                folderName = OUTPUT_DIR_RIO;
            } else {
                log_name = OUTPUT_DIR_LOCAL + "log_" +  getDateTimeString() + "_" + logPrefix + ".csv";
                folderName = OUTPUT_DIR_LOCAL;
            }

            File dir = new File(folderName);
            if (!dir.exists()) dir.mkdirs();

            System.out.println("Initalizing Log file " + log_name);

            //Confirm file not already opened
            if(log_file != null){
                System.out.println("Warning: attempt to open an already opened log!");
                return -1;
            }

            // Open File
            FileWriter fstream = new FileWriter(log_name, true);
            log_file = new BufferedWriter(fstream);

            //First column is always time
            log_file.write("TIME,");

            // Write user-defined header line
            for (String header_txt : data_fields) {
                log_file.write(header_txt + ",");
            }
            // End of line
            log_file.write("\n");

            //First column is always in seconds
            log_file.write("sec,");

            // Write user-defined units line
            for (String header_txt : units_fields) {
                log_file.write(header_txt + ",");
            }
            // End of line
            log_file.write("\n");

        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error initializing log file: " + e.getMessage());
            return -1;
        }
        return 0;

    }



    /**
     * Write a list of doubles to the output file, assuming it's open. Creates a new line in the
     * .csv log file.
     * 
     * @return 0 on write success, -1 on failure.
     */
    private int writeData() {
        String line_to_write = "";

        try {

            //First column is always the timestamp
            line_to_write = line_to_write.concat(Double.toString(curTimestamp) + ",");

            // Write user-defined data
            for (String data_val : logLine) {
                line_to_write = line_to_write.concat(data_val + ",");
            }

            // End of line
            line_to_write = line_to_write.concat("\n");

            // write constructed string out to file
            if(log_file != null){
                log_file.write(line_to_write);
            } else {
                System.out.println("Warning: attempt to write to a closed log!");
            }


        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
            return -1;
        }

        return 0;
    }



    /**
     * Clears the buffer in memory and forces things to file. Generally a good idea to use this as
     * infrequently as possible (because it increases logging overhead), but definitely use it
     * before the roboRIO might crash without a proper call to the close() method (during brownout,
     * maybe?)
     * 
     * @return Returns 0 on flush success or -1 on failure.
     */
    private int forceSync() {
        try {
            if(log_file != null){
                log_file.flush();
            } else {
                System.out.println("Warning: attempt to flush a closed log!");
            }
        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error flushing IO stream file: " + e.getMessage());
            return -1;
        }

        return 0;

    }



    /**
     * Closes the log file and ensures everything is written to disk. init() must be called again in
     * order to write to a new file.
     * 
     * @return -1 on failure to close, 0 on success
     */
    private int close() {

        try {
            if(log_file != null){
                log_file.close();
                log_file = null;
                System.out.println("Log file closed.");
            } else {
                System.out.println("Warning: attempt to close an already-closed log!");
            }
        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error Closing Log File: " + e.getMessage());
            return -1;
        }
        return 0;

    }


    private String getDateTimeString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        df.setTimeZone(TimeZone.getTimeZone("US/Central"));
        return df.format(new Date());
    }

}
