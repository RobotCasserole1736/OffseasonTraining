package frc.lib.WebServer;

import org.json.simple.JSONObject;

public class DriverViewBoolean extends DriverViewObject {

    /** JSON object for initializing the Boolean indicator */
    private JSONObject boolJsonInitObj;

    /** JSON object for Updating the Boolean indicator */
    private JSONObject boolJsonUpdateObj;

    String name;
    String color;
    boolean value;

    public DriverViewBoolean(String name_in, String color_in) {

        name = Utils.nameTransform(name_in);
        color = color_in;
        value = false;

        // Create new objects
        boolJsonInitObj = new JSONObject();
        boolJsonInitObj.put("type", "boolean");
        boolJsonInitObj.put("name", name);
        boolJsonInitObj.put("displayName", name_in);
        boolJsonInitObj.put("color", color_in);

        boolJsonUpdateObj = new JSONObject();
        boolJsonUpdateObj.put("type", "boolean");
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
