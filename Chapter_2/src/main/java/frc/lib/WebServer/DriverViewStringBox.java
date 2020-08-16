package frc.lib.WebServer;

import org.json.simple.JSONObject;

public class DriverViewStringBox extends DriverViewObject {

    /** JSON object for initializing the Stringbox */
    private JSONObject sbJsonInitObj;

    /** JSON object for Updating the Stringbox */
    private JSONObject sbJsonUpdateObj;

    String name;
    String value;

    public DriverViewStringBox(String name_in) {

        name = Utils.nameTransform(name_in);
        value = "N/A";

        // Create new object
        sbJsonInitObj = new JSONObject();
        sbJsonInitObj.put("type", "stringbox");
        sbJsonInitObj.put("name", name);
        sbJsonInitObj.put("displayName", name_in);
        sbJsonInitObj.put("value", value);

        // Create new object
        sbJsonUpdateObj = new JSONObject();
        sbJsonUpdateObj.put("type", "stringbox");
        sbJsonUpdateObj.put("name", name);
        sbJsonUpdateObj.put("value", value);

    }

    @Override
    public JSONObject getInitJsonObj() {
        return sbJsonInitObj;
    }

    @Override
    public JSONObject getUpdJsonObj() {
        return sbJsonUpdateObj;
    }

    public void setVal(String value_in) {
        value = value_in;
        sbJsonUpdateObj.put("value", value);
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
