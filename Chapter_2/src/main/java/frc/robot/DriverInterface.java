package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.lib.DataServer.Annotations.Signal;

public class DriverInterface {

    XboxController driverController;
    @Signal
    double curFwdRevCmd;
    @Signal
    double curRotCmd;

    public DriverInterface(){

        driverController = new XboxController(0);

    }

    public void update(){
        curFwdRevCmd = -1 * driverController.getY(Hand.kLeft);
        curRotCmd = -1 * driverController.getX(Hand.kRight);
    }
    

    double getFwdRevCmnd(){
        return curFwdRevCmd;
     /**
     * The variables return a number between -1 and 1.
     * 1.0 means "fast as posssible forward."
     * 0.0 means going nowhere (AKA "stop").
     * -1.0 means "fast as possible reversing."
     */
    }

    double getRotateCmnd(){
        return curRotCmd;
     /**
     * The variables return a number between -1 and 1.
     * 1.0 means "fast as posssible to the left."
     * 0.0 means going nowhere (AKA "stop").
     * -1.0 means "fast as possible to the right."
     */
    }

    public void setFwdRevCmd(double cmd){
        //1.0 is go forward, 0.0 is stop, -1.0 is reverse
        //To do = store cmd somewhere to be used during update()
    }
    
    public void setRotateCmd(double cmd){
        //1.0 is left, 0.0 is stop, -1.0 is right
        //To do = store cmd somewhere to be used during update()
    }
  
}
