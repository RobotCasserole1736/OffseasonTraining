package frc.lib.LoadMon;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import frc.lib.DataServer.Signal;
import frc.lib.Util.CrashTracker;
import frc.robot.LoopTiming;
import frc.robot.Robot;

/**
 * DESCRIPTION: <br>
 * Metric-gathering library to help track CPU load and memory load on-RIO, rather than just relying on the
 * driver station logs. This enables users to do many things, including logging the loads along with other
 * signals at runtime. This enables a more advanced level of debugging (ex: tracking load over many software releases,
 * or noticing a correlation between RIO loads and physical values/states of the robot).
 * <br>
 * <br>
 * Utilizes the fancy-schmancy virtual filesystem provided by the Linux kernel. "Files" under the /proc 
 * can be read, and provide raw data indicating what the CPU times and memory usage are. Add in a bit of
 * string parsing and math, and we're able to derive the metrics we care about from known-good sources.
 * <br>
 * <br>
 * I do suppose this is kinda redundant with the metrics gathered by the driver station, but I would feel very
 * warm and fuzzy just seeing the load metrics in a log along with all the other data. Just a personal preference.
 * <br>
 * <br>
 * USAGE:
 * <ol>
 * <li>Instantiate Class. This will start periodic updates to the calculations of the 
 * load metrics in the background./li>
 * <li>Call getters to get the most recent CPU and memory load
 * </li>
 * </ol>
 * 
 * 
 */

public class CasseroleRIOLoadMonitor {
	
	/** Rate of update of the load variables in milliseconds. 1s should be enough? */
	public static final int UPDATE_RATE_MS = 1000;
	
	/** Overall (all-cpu) load percentage (non-idle time) */
	public double totalCPULoadPct = 0;
	/** Memory used percentage */
	public double totalMemUsedPct = 0;
	/** JVM memory used percentage */
	public double totalJVMMemUsedPct = 0;
	
	//To ensure we only calculate load between the last measurement and this one, we must store the
	//previous values measured from the kernel, since the kernel reports aggregate time counts
	double prevUserTime = 0;
	double prevNicedTime = 0;
	double prevSystemTime = 0;
	double prevIdleTime = 0;
	
	
	//Set to true if we can't read the file (wrong os, or something else weird)
	//Will prevent burning processor cycles if we can't actually get any info
	boolean giveUp = false;
	
	// These "files" contain the load info on a linux system
	static final String CPU_LOAD_VIRT_FILE = "/proc/stat";
	static final String MEM_LOAD_VIRT_FILE = "/proc/meminfo";

	//Telemetry
	Signal rioCPULoad;
    Signal rioMemLoad;
    Signal rioJVMMemLoad;
	
	/**
	 * Constructor. Initalizes measurement system and starts a slow
	 * background thread to gather load info
	 */
	public CasseroleRIOLoadMonitor(){

		if(Robot.isSimulation()){
			return; //nothing to do for simulation.
		}
		
		//Reset give up flag
		giveUp = false;
		
		// Kick off monitor in brand new thread.
	    // Thanks to Team 254 for an example of how to do this!
	    Thread monitorThread = new Thread(new Runnable() {
	        @Override
	        public void run() {
	            try {
	            	while(!Thread.currentThread().isInterrupted()){
	            		periodicUpdate();
	            		Thread.sleep(UPDATE_RATE_MS);
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	        }
		});
		
		rioCPULoad = new Signal("roboRIO CPU Load", "pct");
        rioMemLoad = new Signal("roboRIO Sys Memory Load", "pct"); 
        rioJVMMemLoad = new Signal("roboRIO JVM Memory Load", "pct"); 
	    
	    //Set up thread properties and start it off
	    monitorThread.setName("CasseroleRIOLoadMonitor");
	    monitorThread.setPriority(Thread.MIN_PRIORITY);
	    monitorThread.start();
	}
	
	/**
	 * Updates the present loads based on info from the /proc virtual
	 * filesystem. Should be called in the background. This takes up
	 * some number of resources (opening and closing files), so it's
	 * worthwhile not running it super fast. will be called internally
	 * by the thread started in the constructor
	 */
	private void periodicUpdate(){

		String CPUTotalLoadRawLine = new String();
		File file;

		double sample_time_ms = LoopTiming.getInstance().getLoopStartTimeSec()*1000;
		
		if(giveUp == false){
			
			//////////////////////////////////////////////////////////////////////////////
			//// CPU LOAD PARSING & CALCULATION
			//////////////////////////////////////////////////////////////////////////////
			//Get meaningful line from CPU load virtual file
			file = new File(CPU_LOAD_VIRT_FILE);
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				CPUTotalLoadRawLine = br.readLine();
				while(CPUTotalLoadRawLine != null){
					if(CPUTotalLoadRawLine.startsWith("cpu ")){
						break;
					}
					CPUTotalLoadRawLine = br.readLine();
				}
				br.close();
			} catch(IOException e){
				CrashTracker.logAndPrint("[RIO Load Mon] WARNING: cannot get raw CPU load data. Giving up future attempts to read.");
				e.printStackTrace();
				giveUp = true;
			}
			
			//line now contains cpu load text info
			//separated by spaces in the format:
			// "cpu <user> <nice> <system> <idle> ..."
			// Time units are system dependent, but we don't care
			// since we are calculating a percentage.
			String[] tokens = CPUTotalLoadRawLine.split(" ");

			double curUserTime = 0;
			double curNicedTime = 0;
			double curSystemTime = 0;
			double curIdleTime = 0;
			try{
				curUserTime = Double.parseDouble(tokens[2]); //Start at 2, because RIO parses an extra empty token at 1
				curNicedTime = Double.parseDouble(tokens[3]);
				curSystemTime = Double.parseDouble(tokens[4]);
				curIdleTime = Double.parseDouble(tokens[5]);
			}catch(Exception e) {
				CrashTracker.logAndPrint("[RIO Load Mon] WARNING: cannot parse CPU load. Giving up future attempts to read.");
				e.printStackTrace();
				giveUp = true;
			}
			
			//Calculate change in time counters since last measurement
			double deltaUserTime = curUserTime - prevUserTime;
			double deltaNicedTime = curNicedTime - prevNicedTime;
			double deltaSystemTime = curSystemTime - prevSystemTime;
			double deltaIdleTime = curIdleTime - prevIdleTime;
			
			prevUserTime = curUserTime;
			prevNicedTime = curNicedTime;
			prevSystemTime = curSystemTime;
			prevIdleTime = curIdleTime;
			
			//Add up totals
			double totalInUseTime = (deltaUserTime + deltaNicedTime + deltaSystemTime);
			double totalTime = totalInUseTime + deltaIdleTime;
			
			//Calculate CPU load to nearest tenth of percent
			totalCPULoadPct = ((double)Math.round(totalInUseTime/totalTime * 1000.0))/10.0;
			
			
			
			//////////////////////////////////////////////////////////////////////////////
			//// MEMORY LOAD PARSING & CALCULATION
			//////////////////////////////////////////////////////////////////////////////
			String memTotalStr = new String();
			String memFreeStr =  new String();
			String line = new String();
			
			//Get meaningful line from CPU load virtual file
			file = new File(MEM_LOAD_VIRT_FILE);
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				line = br.readLine();
				while(line != null){
					if(line.startsWith("MemTotal: ")){
						memTotalStr = line;
					} else if(line.startsWith("MemFree:")){
						memFreeStr = line;
						break;
					}
					line = br.readLine();
				}
				br.close();
			} catch(IOException e){
				CrashTracker.logAndPrint("[RIO Load Mon] WARNING: cannot get raw memory load data. Giving up future attempts to read.");
				e.printStackTrace();
				giveUp = true;
			}
			
			//Split up the string which should be in the format
			// "<title> <value> <units>" and we only care about value.
			String[] memTotalTokens = memTotalStr.split("\\s+");
			String[] memFreeTokens = memFreeStr.split("\\s+");
			
			//Parse values from proper tokens
			double curTotalMem = 0;
			double curFreeMem = 0;
			try{
				curTotalMem = Double.parseDouble(memTotalTokens[1]);
				curFreeMem = Double.parseDouble(memFreeTokens[1]);
			}catch(Exception e) {
				CrashTracker.logAndPrint("[RIO Load Mon] WARNING: cannot parse memory load. Giving up future attempts to read.");
				e.printStackTrace();
				giveUp = true;
			}
			
			//Mathy math math
			totalMemUsedPct = ((double)Math.round((1.0 - curFreeMem/curTotalMem) * 1000.0))/10.0;
			
			
		} 
		

		if(giveUp == true){
			//Indicate we're not getting the load values
			totalCPULoadPct = -1;
			totalMemUsedPct = -1;
		}

		//Grab JVM memory (outside of give-up loop)
		double jvmTotalMem = Runtime.getRuntime().totalMemory();
		double jvmFreeMem = Runtime.getRuntime().freeMemory();
		totalJVMMemUsedPct = (jvmTotalMem - jvmFreeMem)/(jvmTotalMem) * 100.0;

		//Update Telemetry
		rioCPULoad.addSample(sample_time_ms,this.getCPULoadPct());
        rioMemLoad.addSample(sample_time_ms,this.getMemLoadPct());
        rioJVMMemLoad.addSample(sample_time_ms,this.getJVMMemLoadPct());
		
		
	}
	
	/**
	 * Getter for load percentage on CPU. Aggregate of all cores on the system, including
	 * both system and user processes.
	 * @return percentage of non-idle time, or -1 if percentage unavailable
	 */
	public double getCPULoadPct(){
		return totalCPULoadPct;
	}
	
	/**
	 * Getter for the load percentage on memory. 
	 * @return percentage of available system RAM, or -1 if percentage unavailable.
	 */
	public double getMemLoadPct(){
		return totalMemUsedPct;
	}

	/**
	 * Getter for the load percentage on memory in the Java Virtual Machine. 
	 * @return percentage of available JVM RAM, or -1 if percentage unavailable.
	 */
	public double getJVMMemLoadPct(){
		return totalJVMMemUsedPct;
	}
    

}
