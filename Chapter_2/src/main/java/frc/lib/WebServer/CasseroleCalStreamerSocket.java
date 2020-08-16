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

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import frc.lib.Calibration.Calibration;
import frc.lib.Calibration.CalWrangler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * DESCRIPTION: <br>
 * Private socket definition class that Jetty wants me to make public even
 * though it doesn't actually have to be. Don't use this for anything unless you
 * know preciisely what you are doing.
 */

public class CasseroleCalStreamerSocket extends WebSocketAdapter {
    volatile int test_data;

    @Override
    public void onWebSocketText(String message) {
        if (isConnected()) {
            if (message.equals("save")) {
                if (CalWrangler.saveCalValues() != 0) {
                    broadcastMsg("Error! Cannot write to cal file.");
                } else {
                    broadcastMsg("Success! Cal file re-written.");
                }
            } else {
                String[] messageParts = message.split(":");
                // Parse 3-part messages
                if (messageParts.length == 3) {
                    String cmd = messageParts[0];
                    String name = messageParts[1];
                    double val = Double.parseDouble(messageParts[2]);
                    if (cmd.equals("set")) {
                        Calibration cal_to_update = CalWrangler.getCalFromName(name);
                        if (!Double.isFinite(val)) {
                            System.out.println("ERROR: CalStreamer: Invalid value recieved " + Double.toString(val));
                        } else {
                            cal_to_update.setOverride(val);
                        }
                    }
                    // Parse 2-part messages
                } else if (messageParts.length == 2) {
                    String cmd = messageParts[0];
                    String name = messageParts[1];
                    if (cmd.equals("reset")) {
                        Calibration cal_to_update = CalWrangler.getCalFromName(name);
                        cal_to_update.reset();
                    }
                } else {
                    System.out.println("ERROR: CalStreamer: Client returned garbage message " + message);
                }
            }
            broadcastData();
        }
    }

    @Override
    public void onWebSocketConnect(Session sess) {

        super.onWebSocketConnect(sess);
        // On client connect, broadcast the current set of calibrations.
        broadcastData();
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {

        super.onWebSocketClose(statusCode, reason);
    }

    /**
     * send a string message over the socket to notify the user of something.
     */
    @SuppressWarnings("unchecked")
    public void broadcastMsg(String msg) {
        try {
            JSONObject full_obj = new JSONObject();
            // package array into object
            full_obj.put("type", "msg");
            full_obj.put("msg_text", msg);
            getRemote().sendString(full_obj.toJSONString());

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * send socket data out to client
     */
    @SuppressWarnings("unchecked")
    public void broadcastData() {
        if (isConnected()) {
            Calibration[] allCals = CalWrangler.registeredCals
                    .toArray(new Calibration[CalWrangler.registeredCals.size()]);

            try {
                JSONObject full_obj = new JSONObject();
                JSONArray data_array = new JSONArray();

                // Package all Cal array elements into a JSON array
                for (Calibration cal : allCals) {
                    JSONObject single_obj = new JSONObject();
                    single_obj.put("name", cal.name);
                    single_obj.put("dflt_val", Double.toString(cal.getDefault()));
                    single_obj.put("min_val", Double.toString(cal.min_cal));
                    single_obj.put("max_val", Double.toString(cal.max_cal));
                    single_obj.put("ovrdn", Boolean.toString(cal.overridden));
                    single_obj.put("cur_val", Double.toString(cal.get()));
                    data_array.add(single_obj);
                }

                // package array into object
                full_obj.put("type", "cal_vals");
                full_obj.put("cal_array", data_array);
                getRemote().sendString(full_obj.toJSONString());

            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

}
