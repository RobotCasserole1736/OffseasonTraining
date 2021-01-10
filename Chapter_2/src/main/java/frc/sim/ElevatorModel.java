package frc.sim;

import java.util.NoSuchElementException;

import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.PWMSim;

class ElevatorModel {

    PWMSim motorController;
    DIOSim upperLimitSwitchA;
    DIOSim upperLimitSwitchB;
    DIOSim lowerLimitSwitch;
    EncoderSim spoolEnc;

    SimpleMotorWithMassModel elevMotor;

    double elevVertSpeed_ftps; //positive is up, negative is down
    double elevPos_ft; //0 is at bottom, positive is rasied up.

    final int MOTOR_CONTROLLER_PORT = 4;
    final double SPOOL_MAX_SPEED_RPM = 180;
    final double ACCEL_TIME_CONSTANT = 0.3; //Fudge to taste to roughly simulate the intertia/friction/whateves that changes the rate at which the mechanism approaches steady state.
    final double STALL_CURRENT_A = 95; // Roughly analogous to 1 CIM motor
    
    final double ELEV_MAX_HEIGHT_FT = 6.5; //Upper mechanical stop. Bottom assumed to be zero.

    final double ELEV_SPOOL_RADIUS_FT = 2.0/12.0; //2 inch radius.

    final double ELEV_TOP_STAGE_LS_HEIGHT_FT    = 6.2; //height at which the middle stage hits the limit switch
    final double ELEV_MID_STAGE_LS_HEIGHT_FT    = 4.5; //height at which the middle stage hits the limit switch
    final double ELEV_BOTTOM_STAGE_LS_HEIGHT_FT = 0.1; //height at which the bottom limit switch gets hit

    final double ELEV_ENC_POS_TO_COUNTS = 1024; //Feet to counts

    public ElevatorModel(){
        elevMotor = new SimpleMotorWithMassModel("Elevator", SPOOL_MAX_SPEED_RPM, ACCEL_TIME_CONSTANT, STALL_CURRENT_A);

        motorController = new PWMSim(MOTOR_CONTROLLER_PORT);
        upperLimitSwitchA = new DIOSim(0);
        upperLimitSwitchB = new DIOSim(1);
        lowerLimitSwitch = new DIOSim(2);
        try{
            spoolEnc = EncoderSim.createForChannel(0);
        } catch (NoSuchElementException e){
            System.out.println("No spool encoder has been instantiated in code, so its values won't be reported.");
            spoolEnc = null;
        }
    }

    public void modelReset(){
        elevMotor.modelReset();
        elevVertSpeed_ftps = 0;
        elevPos_ft = 0;
    }

    public void update(){

        double spdCmd = motorController.getSpeed();

        //Handle mechanical stops
        double loadFactor = 0.0;
        if(elevPos_ft >= ELEV_MAX_HEIGHT_FT & spdCmd > 0){
            loadFactor = 1.0; //At upper mechanical stop
            elevPos_ft = ELEV_MAX_HEIGHT_FT;
        } else if (elevPos_ft <= 0.0 & spdCmd < 0) {
            loadFactor = 1.0; //At lower mechanical stop
            elevPos_ft = 0.0;
        } else if( elevVertSpeed_ftps > 0){
            loadFactor = 0.2; //Going up and fighting gravity.
        } //Else - going down or stopped, nominal behavior at 0.0 load factor.

        elevMotor.update(12.5, spdCmd, loadFactor);

        elevVertSpeed_ftps = elevMotor.getSpeed_RPM() * 1.0/60.0 * 2*Math.PI*ELEV_SPOOL_RADIUS_FT; //Min->sec * Rev-> linear Ft

        elevPos_ft += elevVertSpeed_ftps * 0.02; //Ts = 20ms

        //Handle limit switches
        upperLimitSwitchA.setValue(elevPos_ft >= ELEV_TOP_STAGE_LS_HEIGHT_FT);
        upperLimitSwitchB.setValue(elevPos_ft >= ELEV_MID_STAGE_LS_HEIGHT_FT);
        lowerLimitSwitch.setValue(elevPos_ft <= ELEV_BOTTOM_STAGE_LS_HEIGHT_FT);

        //Handle Encoder
        if(spoolEnc != null){
            spoolEnc.setCount((int)(elevPos_ft * ELEV_ENC_POS_TO_COUNTS));
            spoolEnc.setPeriod( (Math.abs(elevVertSpeed_ftps) > 0.01) ? (1.0/(elevVertSpeed_ftps * ELEV_ENC_POS_TO_COUNTS)) : (0.0) );
            spoolEnc.setDirection((elevVertSpeed_ftps > 0));
        }


    }

    public void updateTelemetry(double time){
        elevMotor.updateTelemetry(time);
    }



}