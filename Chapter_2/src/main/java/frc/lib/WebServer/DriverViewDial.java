package frc.lib.WebServer;

import org.json.simple.JSONObject;

public class DriverViewDial extends DriverViewObject {

    static final String VAL_DISPLAY_FORMATTER = "%5.2f";

    /** JSON object for initializing the Dial */
    private JSONObject dialJsonInitObj;

    /** JSON object for Updating the Dial */
    private JSONObject dialJsonUpdateObj;

    /** Properties of this dial */
    private String name;
    private double min;
    private double max;
    private double step;
    private double min_acceptable;
    private double max_acceptable;

    public DriverViewDial(String name_in, double min_in, double max_in, double step_in, double min_acceptable_in,
            double max_acceptable_in) {

        // Sanitize user inputs
        if (min_in >= max_in) {
            System.out
                    .println("WARNING: new dial " + name_in + " has min value greater than maximum. Not adding dial.");
            return;
        }
        if (min_acceptable_in >= max_acceptable_in) {
            System.out.println("WARNING: new dial " + name_in
                    + " has an acceptable min value greater than maximum. Not adding dial.");
            return;
        }
        if (max_in - min_in <= step_in) {
            System.out.println("WARNING: new dial " + name_in
                    + " has too small a step size. Make sure the step size is smaller than the range of the dial. Not adding dial.");
            return;
        }

        // save data
        name = Utils.nameTransform(name_in);
        min = min_in;
        max = max_in;
        step = step_in;
        min_acceptable = min_acceptable_in;
        max_acceptable = max_acceptable_in;

        // Create the JSON object for defining the init data for the dial
        dialJsonInitObj = new JSONObject();
        dialJsonInitObj.put("type", "dial");
        dialJsonInitObj.put("name", name);
        dialJsonInitObj.put("displayName", name_in);
        dialJsonInitObj.put("min", min);
        dialJsonInitObj.put("max", max);
        dialJsonInitObj.put("min_acceptable", min_acceptable);
        dialJsonInitObj.put("max_acceptable", max_acceptable);
        dialJsonInitObj.put("step", step);

        // Create the JSON object for defining the update data for the dial
        dialJsonUpdateObj = new JSONObject();
        dialJsonUpdateObj.put("type", "dial");
        dialJsonUpdateObj.put("name", name);
        dialJsonUpdateObj.put("value", String.format(VAL_DISPLAY_FORMATTER, min_in));
        return;
    }

    /**
     * Comparison function to evaluate if a given name of a dial matches this dial
     * 
     * @return true if the names match, false otherwise
     */
    public boolean nameEquals(String name_in) {
        return Utils.nameTransform(name_in).equals(name);
    }

    // Public get/set functions to fulfill the object interface
    @Override
    public JSONObject getInitJsonObj() {
        return dialJsonInitObj;
    }

    @Override
    public JSONObject getUpdJsonObj() {
        return dialJsonUpdateObj;
    }

    public void setVal(double value) {
        dialJsonUpdateObj.put("value", String.format(VAL_DISPLAY_FORMATTER, value));
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
