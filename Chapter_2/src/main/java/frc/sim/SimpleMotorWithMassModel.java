package frc.sim;

import frc.lib.DataServer.Signal;

class SimpleMotorWithMassModel {

    double speedAct_RPM;
    double speedDes_RPM;
    double current_A;

    Signal motorSpeedRPM_sig;
    Signal motorCurrentA_sig;

    double max_speed_rpm = 0;
    double accel_time_constant = 0; 
    double stall_current_A = 0; 

    final double NOMINAL_SUPPLY_VOLTAGE = 12.5;
    

    /**
     * This class is a very VERY rough approximation of a set of motors, a gearbox, and a load modeled as a rotating mass.
     * It is designed to be dirt simple, directionally correct, and keep software happy. 
     * It should not be considred accurate for any phsyics-modeling purposes.
     * @param name_in Unique name for this motor (for signals and whatnot.)
     * @param max_speed_rpm_in Maximum speed the rotating mass achieves under no load.
     * @param accel_time_constant_in Some number between 1.0 and 0.0 which impacts how quickly the mass accelerates. 1.0 = instantaneous acceleration, 0.0 = no movement.
     * @param stall_current_A Current draw of the mechanism while stalled.
     */
    public SimpleMotorWithMassModel(String name_in, double max_speed_rpm_in, double accel_time_constant_in, double stall_current_A_in){
        max_speed_rpm = max_speed_rpm_in;
        accel_time_constant = accel_time_constant_in;
        stall_current_A = stall_current_A_in;
        motorSpeedRPM_sig = new Signal("SIM "+ name_in +" speed", "RPM");
        motorCurrentA_sig = new Signal("SIM "+ name_in +" current", "A");
    }

    /**
     * Set the motor back to zero speed and zero current draw.
     */
    public void modelReset(){
        speedAct_RPM = 0;
        speedDes_RPM = 0;
        current_A = 0;
    }

    /**
     * Step through one loop of simulation for the motor
     * @param supplyVoltage_in Present battery supply voltage to the controller. Nominally 12.5 or so, but reduce to simulatle a dying battery
     * @param motorCommand_in Speed controller command - 1.0 = full fwd, 0.0 = stop, -1.0 = full reverse
     * @param loadFactor External load present on the system. 0.0 = no load, 1.0 = motor stalled
     */
    public void update(double supplyVoltage_in, double motorCommand_in, double loadFactor){

        // VERY VERY rough approximation of how a motor and gearbox and some spinning mass work. 
        // Looks kinda exponential, which is what we're goin for.
        speedDes_RPM = supplyVoltage_in/NOMINAL_SUPPLY_VOLTAGE * motorCommand_in * max_speed_rpm * (1.0 - loadFactor);
        double spdErr = speedDes_RPM - speedAct_RPM;
        speedAct_RPM += spdErr * accel_time_constant;
        current_A = (spdErr/max_speed_rpm)*stall_current_A * (supplyVoltage_in/NOMINAL_SUPPLY_VOLTAGE);

    }

    public void updateTelemetry(double time){
        motorSpeedRPM_sig.addSample(time, speedAct_RPM);
        motorCurrentA_sig.addSample(time, current_A);
    }

    /**
     * 
     * @return The present speed of the rotating mass
     */
    double getSpeed_RPM(){
        return speedAct_RPM;
    }

    /**
     * 
     * @return The present current draw of the mechanism
     */
    double getCurrent_A(){
        return current_A;
    }


}