/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.lib.Calibration.CalWrangler;
import frc.lib.DataServer.CasseroleDataServer;
import frc.lib.DataServer.Annotations.Signal;
import frc.lib.LoadMon.CasseroleRIOLoadMonitor;
import frc.lib.WebServer.CasseroleWebServer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // Website utilities
    CasseroleWebServer webserver;
    CalWrangler wrangler;
    CasseroleDataServer dataServer;
    LoopTiming loopTiming;
    CasseroleRIOLoadMonitor loadMon;

    LessonTwo l2;
    LessonThree l3;
    LessonFour l4;
    LessonFive l5;

    @Signal
    int teleopInitCounter = 0;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        /* Init website utilties */
        webserver = new CasseroleWebServer();
        wrangler = new CalWrangler();
        dataServer = CasseroleDataServer.getInstance();
        loadMon = new CasseroleRIOLoadMonitor();

        l2 = new LessonTwo();
        l2.lessonTwoInit();

        l3 = new LessonThree();
        l3.lessonThreeInit();

        l4 = new LessonFour();
        l4.lessonFourInit();

        l5 = new LessonFive();
        l5.lessonFiveInit();

        dataServer.registerSignals(this);
        dataServer.startServer();
        webserver.startServer();

        System.out.println("Robot Init completed!");
    }

  /**
   * This function is called once as the robot enters autnonmous mode.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
      telemetryUpdate();
  }

  /**
   * This function is called once as the robot enters teleop mode
   */
  @Override
  public void teleopInit() {
    dataServer.logger.startLoggingTeleop();
    teleopInitCounter++;
    System.out.println("Teleop Init completed!");
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
      l3.lessonThreeEnabledUpdate();
      l5.lessonFiveEnabledUpdate();
      telemetryUpdate(); 
  }

  /**
   * This function is called once as the robot enters disabled mode
   */
  @Override
  public void disabledInit() {
    dataServer.logger.stopLogging();
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
      l3.lessonThreeDisabledUpdate();
      telemetryUpdate();
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  void telemetryUpdate(){
    dataServer.sampleAllSignals();
  }
}
