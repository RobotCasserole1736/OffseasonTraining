package frc.robot;

public class RobotConstants {


    //This class should contain a bunch of static-public-final constant values
    // which other classes can reference. Use it for things that would otherwise
    // be hardcoded, to give your values meaningful names.

    //////////////////////////////////////////////////////////////////////////////////////////////////
    //CAN ID's - MISC
    static public final int POWER_DISTRIBUTION_PANEL_CAN_ID = 0; 
    static public final int PNEUMATICS_CONTROL_MODULE_CAN_ID = 0;
    static public final int TOF_CAN_ID = 16; //UNKNOWN



    //////////////////////////////////////////////////////////////////////////////////////////////////
    //CAN ID's - Spark MAX's
    //static public final int RESERVED_DO_NOT_USE = 0;
    //static public final int UNUSED = 1;
    static public final int DT_LEFT_NEO_1_CAN_ID = 2;
    static public final int DT_RIGHT_NEO_1_CAN_ID = 3;
    static public final int HOPPER_NEO_LEFT_CAN_ID=4; //AKA Hopper 2
    static public final int INTAKE_MOTOR_CAN_ID = 5;
    static public final int DT_LEFT_NEO_2_CAN_ID = 6;
    //static public final int UNUSED = 7;
    static public final int DT_RIGHT_NEO_2_CAN_ID = 8;
    static public final int HOPPER_NEO_RIGHT_CAN_ID=9; //AKA Hopper 1
    static public final int SHOOTER_MOTOR_2 = 10;
    static public final int SHOOTER_MOTOR_1 = 11;
    static public final int CONTROL_PANEL_MANIPULATOR_CAN_ID = 12;
    //static public final int UNUSED = 13;
    //static public final int UNUSED = 14; 
    //static public final int UNUSED = 15; 
    //static public final int UNUSED = 16;
    //static public final int UNUSED = 17; 
    //static public final int UNUSED = 18;
    //static public final int UNUSED = 19;
    //static public final int UNUSED = 20;

    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //PWM Outputs
    static public final int CLIMBER_SPARK_PORT = 0;
    static public final int CONVEYOR_MOTOR = 1;
    //static public final int UNUSED = 2;
    //static public final int UNUSED = 3;
    //static public final int UNUSED = 4;
    //static public final int UNUSED = 5;
    //static public final int UNUSED = 6;
    //static public final int UNUSED = 7;
    //static public final int UNUSED = 8;
    static public final int LED_CONTROLLER_PORT = 9;


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //PDP Current Measurements 
    static public final int DRIVETRAIN_MOTOR_RIGHT_1_PDP_CHANNEL = 0;
    static public final int DRIVETRAIN_MOTOR_RIGHT_2_PDP_CHANNEL = 1;
    static public final int DRIVETRAIN_MOTOR_LEFT_1_PDP_CHANNEL = 2;
    static public final int DRIVETRAIN_MOTOR_LEFT_2_PDP_CHANNEL = 3;
    static public final int HOPPER_SPARK_LEFT_PDP_CHANNEL = 4;
    static public final int HOPPER_SPARK_RIGHT_PDP_CHANNEL = 5;
    static public final int INTAKE_MOTOR_PDP_CHANNEL=6;
    static public final int CONVEYOR_MOTOR_PDP_CHANNEL = 7;
    static public final int UPPER_BOARD_AUX_PDP_CHANNEL = 8;
    static public final int CONTROL_PANEL_MANIPULATOR_PDP_CHANNEL = 9;
    //static public final int SHOOTER_MOTOR_3_PDP_CHANNEL = 10; //Reserved
    static public final int COOLING_FANS_PDP_CHANNEL = 11;
    static public final int CLIMBER_SPARK_1_PDP_CHANNEL=12;
    static public final int CLIMBER_SPARK_2_PDP_CHANNEL=13;
    static public final int SHOOTER_MOTOR_1_PDP_CHANNEL=14;
    static public final int SHOOTER_MOTOR_2_PDP_CHANNEL=15;


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //Digital IO
    static public final int CLIMBER_LIMIT_LOWER_NO_DIO_PORT=0;
    static public final int CLIMBER_LIMIT_LOWER_NC_DIO_PORT=1;
    static public final int CLIMBER_LIMIT_UPPER_NO_DIO_PORT=2;
    static public final int CLIMBER_LIMIT_UPPER_NC_DIO_PORT=3;
    static public final int CONVEYOR_TO_SHOOTER_SENSOR_DIO_PORT = 4;
    static public final int CONVEYOR_ENCODER_A_DIO_PORT = 5;
    static public final int CONVEYOR_ENCODER_B_DIO_PORT = 6;
    static public final int VISON_LED_RING_RELAY_DIO_PORT = 7;
    //static public final int UNUSED = 9;


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //Pneumatics Control Module
    static public final int CLIMBER_SOLENOID_PCM_PORT = 0;
    static public final int INTAKE_SOLENOID_PCM_PORT = 1; 
    //static public final int UNUSED = 2;
    //static public final int UNUSED = 3;
    //static public final int UNUSED = 4;
    //static public final int UNUSED = 5;
    //static public final int UNUSED = 6;
    //static public final int UNUSED = 7;


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //Analog Ports
    static public final int ANALOG_PRESSURE_SENSOR_PORT = 0;
    //static public final int UNUSED = 1;
    //static public final int UNUSED = 2;
    //static public final int UNUSED = 3;


    //////////////////////////////////////////////////////////////////////////////////////////////////
    // Robot Physical Parameters
	static public final double WHEEL_ROLLING_RADIUS_FT = 3.0/12.0;
	static public final double ROBOT_TRACK_WIDTH_FT = 23.0/12.0;
    static public final double MAIN_LOOP_Ts = 0.02; //Nominal. Meh.
    static public final double SHOOTER_GEAR_RATIO = 1.0;
    static public final double DRIVETRAIN_GEAR_RATIO = 1.0/10.0;
    static public final double CONVEYOR_BELT_RATIO = 1.0/1.0;
    static public final double CONTROL_PANEL_MANIPULATOR_RATIO = (1.0/9.0)*(1.0)*(16.0); //Gear Ratio of Gear box * Radius of Wheel in inches(circumference/(2*pi)) * Radius of Control Panel in inches
    
    static public final int CAN_TIMEOUT = 10;

}