package frc.sim;

import edu.wpi.first.wpilibj.Timer;

public class RobotModel {

    private ElevatorModel elev;
    private CubeGrabberModel cg;

    public RobotModel(){
        elev = new ElevatorModel();
        cg = new CubeGrabberModel();
    }

    public void reset(){
        elev.modelReset();
        cg.modelReset();
    }

    public void update(){
        elev.update();
        cg.update();

        double curTime_ms = Timer.getFPGATimestamp() * 1000;
        elev.updateTelemetry(curTime_ms);
        cg.updateTelemetry(curTime_ms);
    }

}