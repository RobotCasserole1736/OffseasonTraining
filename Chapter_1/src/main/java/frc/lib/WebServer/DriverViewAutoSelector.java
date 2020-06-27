package frc.lib.WebServer;

import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DriverViewAutoSelector extends DriverViewObject {

    /** JSON object for initializing the ... thing... */
    private JSONObject asJsonInitObj;

    private JSONObject asJsonUpdateObj;

    /** String for keeping track of present value */
    private String current_selected_id = "";

    String name;
    String displayName;
    String[] selection_ids;
    static volatile Hashtable<String, String> idToNameTable = new Hashtable<String, String>();

    public DriverViewAutoSelector(String name_in, String[] options) {
        name = Utils.nameTransform(name_in);
        displayName = name_in;

        if (options.length == 0) {
            System.out.println("Error: Auto Selector " + name_in + " has no options. Not adding.");
            return;
        }

        selection_ids = new String[options.length];

        for (int i = 0; i < options.length; i++) {
            selection_ids[i] = Utils.nameTransform(options[i]);
            idToNameTable.put(selection_ids[i], options[i]);
        }

        // Create new objects
        asJsonInitObj = new JSONObject();
        asJsonInitObj.put("type", "autosel");
        asJsonInitObj.put("name", name);
        asJsonInitObj.put("displayName", displayName);

        JSONArray optsArrObj = new JSONArray();
        for (String id : selection_ids) {
            JSONObject tmp = new JSONObject();
            tmp.put("id", id);
            tmp.put("displayName", idToNameTable.get(id));
            optsArrObj.add(tmp);
        }
        asJsonInitObj.put("options", optsArrObj);

        // Create the JSON object for defining the update data for the autoshift
        // selector.
        asJsonUpdateObj = new JSONObject();
        asJsonUpdateObj.put("type", "autosel");
        asJsonUpdateObj.put("name", name);

        current_selected_id = selection_ids[0]; // Default to the first one. Meh. This _happens_ to match the js
                                                // implementation. I do not like. But it is functional, and there are
                                                // bigger fish to fry.

        asJsonUpdateObj.put("val", getNameFromId(selection_ids[0]));

    }

    @Override
    public JSONObject getInitJsonObj() {
        return asJsonInitObj;
    }

    @Override
    public void setCommandObj(Object cmd_in) {
        current_selected_id = (String) cmd_in;
        asJsonUpdateObj.put("val", getNameFromId(current_selected_id));
    }

    public String getNameFromId(String id) {
        return idToNameTable.get(id);
    }

    public String getVal(String name) {
        if (current_selected_id != "")
            return getNameFromId(current_selected_id);
        else
            return "";

    }

    @Override
    public JSONObject getUpdJsonObj() {
        return asJsonUpdateObj;
    }

    @Override
    public String getName() {
        return name;
    }

}
