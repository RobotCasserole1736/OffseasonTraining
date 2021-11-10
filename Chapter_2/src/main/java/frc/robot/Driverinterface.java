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
        curFwdRevCmd = driverController.getY(Hand.kLeft);
        curRotCmd= driverController.getX(Hand.kRight);
    }
    /**
     * Returns 1 when the driver wants the robot to move forward
     * -1 when they want it to go backward
     * 0 when they want it to stop.
     */  
    double getFwdRevCmd(){
        return curFwdRevCmd*-0.72; //todo
    }
   /**
     * Returns 1 when the driver wants the robot to rotate right
     * -1 when they want it to rotate left
     * 0 when they want it no rotation
     */   
    double getRotateCmd(){
        return curRotCmd;//todo
    }
}
