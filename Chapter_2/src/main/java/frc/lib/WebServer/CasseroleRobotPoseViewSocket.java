package frc.lib.WebServer;

import java.awt.geom.Point2D;

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

import java.io.IOException;
import java.util.TimerTask;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * DESCRIPTION: <br>
 * Private socket definition class that Jetty wants me to make public even though it doesn't
 * actually have to be. Don't use this for anything unless you know precisely what you are doing.
 */

public class CasseroleRobotPoseViewSocket extends WebSocketAdapter {
    private java.util.Timer updater = new java.util.Timer("State Webpage Update");
    private int updatePeriodMS = 20; 
    volatile int test_data;


    /**
     * Set the time between server broadcasts of current state. Faster update
     * rates bog down both server and network.
     * 
     * @param in_period_ms Broadcast period in milliseconds.
     */
    public void setUpdatePeriod(int in_period_ms) {
        updatePeriodMS = in_period_ms;
    }


    @Override
    public void onWebSocketText(String message) {
        if (isConnected()) {
            System.out.printf("Got client's message: [%s]%n", message);
        }
    }


    @Override
    public void onWebSocketConnect(Session sess) {

        super.onWebSocketConnect(sess);
        // On client connect, begin new task to broadcast data at 1 second intervals
        broadcastInitData();
        updater.scheduleAtFixedRate(new dataBroadcastTask(), 0, updatePeriodMS);
    }


    @Override
    public void onWebSocketClose(int statusCode, String reason) {

        super.onWebSocketClose(statusCode, reason);
        // On client disconnect, close down broadcast task
        updater.cancel();
    }

    /**
     * send socket data out to client
     */
    @SuppressWarnings("unchecked")
	public void broadcastInitData() {
        if (isConnected()) {
            try {
                JSONObject full_obj = new JSONObject();
                JSONArray field_point_array = new JSONArray();

                // Package all data array elements into a JSON array
                
                //Package field polygon
                for (Point2D.Double point : CasseroleRobotPoseView.fieldPolygon) {
                	JSONObject point_obj = new JSONObject();
                	point_obj.put("x", point.getX());
                	point_obj.put("y", point.getY());
                	field_point_array.add(point_obj);
                }
                
                //Package bot dimensions
            	JSONObject robot_dims = new JSONObject();
            	robot_dims.put("w", CasseroleRobotPoseView.bot_w);
            	robot_dims.put("h", CasseroleRobotPoseView.bot_h);

                // package all things into object
            	full_obj.put("step", "init");
                full_obj.put("field_polygon", field_point_array);
                full_obj.put("bot_dims", robot_dims);
                getRemote().sendString(full_obj.toJSONString());

            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * send socket data out to client
     */
    @SuppressWarnings("unchecked")
	public void broadcastPose() {
        if (isConnected()) {
            try {
                JSONObject full_obj = new JSONObject();

                // package all fields into object
                JSONObject robot_pose = new JSONObject();
                robot_pose.put("x", CasseroleRobotPoseView.x);
                robot_pose.put("y", CasseroleRobotPoseView.y);
                robot_pose.put("theta", CasseroleRobotPoseView.theta);
                
                full_obj.put("step", "updt");
                full_obj.put("robot_pose", robot_pose);
                getRemote().sendString(full_obj.toJSONString());

            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * Timer task to periodically broadcast data to the client. Java multithreading magic here, do
     * not touch! If you touch this, you will face the wrath of Chitulu, god of data streaming
     * servers. May the oceans of 1's and 0's rise to praise him.
     * 
     * @author Chris Gerth
     *
     */
    private class dataBroadcastTask extends TimerTask {
        public void run() {
            broadcastPose();
        }
    }

}
