package frc.lib.WebServer;

import org.json.simple.JSONObject;

public abstract class DriverViewObject {

    /**
     * Inteface to get the sent JSON object when the webpage asks for the initial
     * defintion of what will be on the driverview
     * 
     * @return
     */
    public abstract JSONObject getInitJsonObj();

    /**
     * Interface to get the sent JSON object when it is time to update the values
     * displayed on the webpage
     * 
     * @return
     */
    public abstract JSONObject getUpdJsonObj();

    /**
     * Interface to set an object when the website delivers an update
     * 
     * @return
     */
    public abstract void setCommandObj(Object cmd);

    /**
     * 
     * @return the assigned name for the object
     */
    public abstract String getName();
}
