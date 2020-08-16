package frc.lib.WebServer;

import org.json.simple.JSONObject;

public class DriverViewWebcam extends DriverViewObject {

    /** JSON object for initializing the Webcam */
    private JSONObject webcamJsonInitObj;

    /** JSON object for Updating the Webcam */
    private JSONObject webcamJsonUpdateObj;

    String name;
    String url;
    double rotation_deg;
    double marker_x;
    double marker_y;

    public DriverViewWebcam(String name_in, String url_in, double marker_x_in, double marker_y_in,
            double img_rotate_deg_in) {

        name = Utils.nameTransform(name_in);
        url = url_in;
        marker_x = marker_x_in;
        marker_y = marker_y_in;
        rotation_deg = img_rotate_deg_in;

        // Create JSON object for initalizing the webcam on the driver view
        webcamJsonInitObj = new JSONObject();
        webcamJsonInitObj.put("type", "webcam");
        webcamJsonInitObj.put("name", name);
        webcamJsonInitObj.put("url", url);
        webcamJsonInitObj.put("rotation_deg", rotation_deg);
        webcamJsonInitObj.put("marker_x", marker_x);
        webcamJsonInitObj.put("marker_y", marker_y);

        // Create JSON object for updapting the webcam on the driver view
        webcamJsonUpdateObj = new JSONObject();
        webcamJsonUpdateObj.put("type", "webcam");
        webcamJsonUpdateObj.put("name", name);
        webcamJsonUpdateObj.put("marker_x", marker_x);
        webcamJsonUpdateObj.put("marker_y", marker_y);
        return;

    }

    @Override
    public JSONObject getInitJsonObj() {
        return webcamJsonInitObj;
    }

    @Override
    public JSONObject getUpdJsonObj() {
        return webcamJsonUpdateObj;
    }

    public void setCrosshairs(double x_pct, double y_pct) {
        marker_x = Math.min(100.0, Math.max(0.0, x_pct));
        marker_y = Math.min(100.0, Math.max(0.0, y_pct));
        webcamJsonUpdateObj.put("marker_x", marker_x);
        webcamJsonUpdateObj.put("marker_y", marker_y);

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
