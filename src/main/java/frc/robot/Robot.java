/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Team 7160, Ludington Obots                                                 */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  
  //------WPISYSTEMS------// 
  private final Timer m_timer = new Timer();
  private final Timer autontimer = new Timer();
  private final Joystick DRIVEJOY = new Joystick(InputMap.DRIVEJOY);
  private final Joystick MINIPJOY_1 = new Joystick(InputMap.MINIPJOY_1);
  //----------------------//

  //------SUBSYSTEMS------//
  private BallShooter shooter;
  private BallHandler ballHandler;
  private Spinner spinner;
  private Limelight limeLight;
  private WestCoastDrive _drive;
  private Led LEDS = new Led();
  private Lift_Leveler liftLeveler;
  //----------------------//

  private double RPM = 1000.0d;
  //BallShooter shooter = new BallShooter( LEDS );
  

  @Override
  public void robotInit() {
    limeLight.lightOff();
    autontimer.reset();
    shooter = new BallShooter(MINIPJOY_1);
    spinner = new Spinner( LEDS , MINIPJOY_1 );
    limeLight = new Limelight();
    ballHandler = new BallHandler(MINIPJOY_1);
    _drive = new WestCoastDrive( m_timer, DRIVEJOY );
    liftLeveler = new Lift_Leveler(MINIPJOY_1);
  }

  @Override
  public void robotPeriodic() {
    limeLight.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    autontimer.start();
  }

  @Override
  public void autonomousPeriodic() {
    if(autontimer.get()<=2){
      _drive.arcadeDrive(.25, 0);
    }else{
      _drive.arcadeDrive(0, 0);
    }
  }

  @Override
  public void teleopInit() {
    spinner.teleopInit();
    shooter.setRPM(3300);
  }

  @Override
  public void teleopPeriodic() {
    
    _drive.teleopPeriodic();
    shooter.teleopPeriodic();
    ballHandler.telopPeriodic();
    //liftLeveler.telopPeriodic();
    
    SmartDashboard.putNumber("RPM", shooter.getCurrentRPM());

  }

  

  public void testPeriodic(){
    liftLeveler.telopPeriodic();
  }

}