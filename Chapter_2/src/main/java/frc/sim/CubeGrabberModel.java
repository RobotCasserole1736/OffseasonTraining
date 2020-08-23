package frc.sim;

import edu.wpi.first.hal.sim.PWMSim;

class CubeGrabberModel {

    PWMSim simMotorController;
    SimpleMotorWithMassModel intakeMotors;

    final int MOTOR_CONTROLLER_PORT = 5;
    final double INTAKE_MAX_SPEED_RPM = 1500;
    final double ACCEL_TIME_CONSTANT = 0.1; //Fudge to taste to roughly simulate the intertia/friction/whateves that changes the rate at which the mechanism approaches steady state.
    final double STALL_CURRENT_A = 150; // Roughly analogous to 2 775Pro Motors
    

    public CubeGrabberModel(){
        intakeMotors = new SimpleMotorWithMassModel("Cube Grabber", INTAKE_MAX_SPEED_RPM, ACCEL_TIME_CONSTANT, STALL_CURRENT_A);
        simMotorController = new PWMSim(MOTOR_CONTROLLER_PORT);
    }

    public void modelReset(){
        intakeMotors.modelReset();
    }

    public void update(){
        intakeMotors.update(12.5, simMotorController.getSpeed(), 0.0);
    }

    public void updateTelemetry(double time){
        intakeMotors.updateTelemetry(time);
    }


}