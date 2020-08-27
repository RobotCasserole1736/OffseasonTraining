package frc.sim;

import edu.wpi.first.wpilibj.Timer;

public class RobotModel {

    private ElevatorModel elev;
    private CubeGrabberModel cg;
    private DrivetrainModel dt;

    public RobotModel(){
        elev = new ElevatorModel();
        cg = new CubeGrabberModel();
        dt = new DrivetrainModel();
    }

    public void reset(){
        elev.modelReset();
        cg.modelReset();
        dt.modelReset();
    }

    public void update(){
        elev.update();
        cg.update();
        dt.update();

        double curTime_ms = Timer.getFPGATimestamp() * 1000;
        elev.updateTelemetry(curTime_ms);
        cg.updateTelemetry(curTime_ms);
        dt.updateTelemetry(curTime_ms);
    }

}