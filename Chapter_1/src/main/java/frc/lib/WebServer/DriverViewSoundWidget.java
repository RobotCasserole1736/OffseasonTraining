package frc.lib.WebServer;

import org.json.simple.JSONObject;

public class DriverViewSoundWidget extends DriverViewObject {

    /** JSON object for initializing the Boolean indicator */
    private JSONObject boolJsonInitObj;

    /** JSON object for Updating the Boolean indicator */
    private JSONObject boolJsonUpdateObj;

    String name;
    String fname;
    boolean value;

    public DriverViewSoundWidget(String name_in, String fname_in) {

        name = Utils.nameTransform(name_in);
        fname = fname_in;
        value = false;

        // Create new objects
        boolJsonInitObj = new JSONObject();
        boolJsonInitObj.put("type", "soundWidget");
        boolJsonInitObj.put("name", name);
        boolJsonInitObj.put("displayName", name_in);
        boolJsonInitObj.put("file", fname);

        boolJsonUpdateObj = new JSONObject();
        boolJsonUpdateObj.put("type", "soundWidget");
        boolJsonUpdateObj.put("name", name);
        boolJsonUpdateObj.put("value", "False");

    }

    @Override
    public JSONObject getInitJsonObj() {
        // TODO Auto-generated method stub
        return boolJsonInitObj;
    }

    @Override
    public JSONObject getUpdJsonObj() {
        // TODO Auto-generated method stub
        return boolJsonUpdateObj;
    }

    public void setVal(boolean value_in) {
        value = value_in;
        if (value) {
            boolJsonUpdateObj.put("value", "True");
        } else {
            boolJsonUpdateObj.put("value", "False");
        }
    }

    @Override
    public void setCommandObj(Object state) {
        return; // nothing to do.
    }

    @Override
    public String getName() {
        return name;
    }

}
