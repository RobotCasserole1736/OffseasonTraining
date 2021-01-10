package frc.sim;

import edu.wpi.first.wpilibj.simulation.PWMSim;

class CubeGrabberModel {

    PWMSim lhMotorCtrl;
    PWMSim rhMotorCtrl;
    SimpleMotorWithMassModel lhIntakeMotor;
    SimpleMotorWithMassModel rhIntakeMotor;

    final int LH_MOTOR_CTRL_PORT = 7;
    final int RH_MOTOR_CTRL_PORT = 8;
    final double INTAKE_MAX_SPEED_RPM = 1500;
    final double ACCEL_TIME_CONSTANT = 0.1; //Fudge to taste to roughly simulate the intertia/friction/whateves that changes the rate at which the mechanism approaches steady state.
    final double STALL_CURRENT_A = 150; // Roughly analogous to 2 775Pro Motors
    

    public CubeGrabberModel(){
        lhIntakeMotor = new SimpleMotorWithMassModel("Cube Grabber Left", INTAKE_MAX_SPEED_RPM, ACCEL_TIME_CONSTANT, STALL_CURRENT_A);
        rhIntakeMotor = new SimpleMotorWithMassModel("Cube Grabber Right", INTAKE_MAX_SPEED_RPM, ACCEL_TIME_CONSTANT, STALL_CURRENT_A);
        lhMotorCtrl = new PWMSim(LH_MOTOR_CTRL_PORT);
        rhMotorCtrl = new PWMSim(RH_MOTOR_CTRL_PORT);
    }

    public void modelReset(){
        lhIntakeMotor.modelReset();
    }

    public void update(){
        lhIntakeMotor.update(12.5, -1.0 * lhMotorCtrl.getSpeed(), 0.0);
        rhIntakeMotor.update(12.5, rhMotorCtrl.getSpeed(), 0.0);
    }

    public void updateTelemetry(double time){
        lhIntakeMotor.updateTelemetry(time);
        rhIntakeMotor.updateTelemetry(time);
    }


}