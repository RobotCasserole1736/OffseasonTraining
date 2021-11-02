package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class DriverInterface {
    
    XboxController driverController;

    @Signal
    double curFwdRevCmd;
    @Signal
    double curRotCmd

    public DriverInterface(){

        

    }
}
