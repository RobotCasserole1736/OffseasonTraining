package frc.lib.DataServer;

import java.io.IOException;

/*
 *******************************************************************************************
 * Copyright (C) 2018 FRC Team 1736 Robot Casserole - www.robotcasserole.org
 *******************************************************************************************
 *
 * This software is released under the MIT License - see the license.txt
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

import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import frc.lib.Util.CrashTracker;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * DESCRIPTION: <br>
 * Private socket definition class that Jetty wants me to make public even
 * though it doesn't actually have to be. Don't use this for anything unless you
 * know precisely what you are doing.
 * 
 */

public class Socket extends WebSocketAdapter {

    HashSet<AcqList> acqLists;

    @Override
    public void onWebSocketText(String message) {
        JSONParser parser = new JSONParser();
        try {
            handleIncoming((JSONObject) parser.parse(message));
        } catch (ParseException e) {
            CrashTracker.logAndPrint("[Data Server] Warning: Error parsing message from client " + message);
            e.printStackTrace();
        }
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        CrashTracker.logAndPrint("[Data Server]: Socket Connection from " + sess.getRemoteAddress());
        acqLists = new HashSet<AcqList>();

    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        CrashTracker.logAndPrint(
                "[Data Server]: Socket Connection close from " + getSession().getRemoteAddress() + " due to " + reason);
        handleClose();
        acqLists = null;
    }

    public void handleClose() {
        handleStopReq();
    }

    public void handleIncoming(JSONObject in) {
        /* Process the JSON command */
        String cmd = (String) (in.get("cmd"));

        if (cmd.equals("addDaq")) {
            String id = (String) in.get("id");
            String tx_period_ms = (String) in.get("tx_period_ms");
            String samp_period_ms = (String) in.get("samp_period_ms");
            JSONArray sig_id_list = (JSONArray) in.get("sig_id_list");
            String[] id_list_array = new String[sig_id_list.size()];
            sig_id_list.toArray(id_list_array);
            handleNewDAQ(id, Double.parseDouble(tx_period_ms), Double.parseDouble(samp_period_ms), id_list_array);
        } else if (cmd.equals("start")) {
            handleStartReq();
        } else if (cmd.equals("stop")) {
            handleStopReq();
        } else if (cmd.equals("getSig")) {
            handleSignalListReq();
        } else {
            CrashTracker.logAndPrint(
                    "[Data Server]: Remote " + getSession().getRemoteAddress() + " sent unrecognized cmd " + cmd);
        }
    }

    public void handleNewDAQ(String id, double tx_period, double samplePeriod_ms, String[] signal_uuids) {
        CrashTracker.logAndPrint("[Data Server]: Remote " + getSession().getRemoteAddress() + " requested new DAQ");
        acqLists.add(new AcqList(id, tx_period, samplePeriod_ms, signal_uuids, getRemote()));
    }

    public void handleStartReq() {
        CrashTracker.logAndPrint("[Data Server]: Remote " + getSession().getRemoteAddress() + " requested TX start");
        for (AcqList daq : acqLists) {
            daq.startTransmit();
        }
    }

    public void handleStopReq() {
        CrashTracker.logAndPrint("[Data Server]: Remote " + getSession().getRemoteAddress() + " requested TX stop");
        for (AcqList daq : acqLists) {
            daq.stopTransmit();
        }
    }

    public void handleSignalListReq() {
        JSONArray sig_arr = new JSONArray();
        CrashTracker.logAndPrint("[Data Server]: Remote " + getSession().getRemoteAddress() + " requested signal list");
        for (Signal sig : CasseroleDataServer.getInstance().getAllSignals()) {
            sig_arr.add(sig.getJSONProperties());
        }

        JSONObject tx_obj = new JSONObject();

        tx_obj.put("type", "sig_list");
        tx_obj.put("signals", sig_arr);

        try {
            getRemote().sendString(tx_obj.toJSONString());
        } catch (IOException e) {
            CrashTracker.logAndPrint("Error: Cannot send signal list");
            e.printStackTrace();
        }
    }

}
