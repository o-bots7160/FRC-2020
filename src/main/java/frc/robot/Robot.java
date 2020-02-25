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
  private Joystick _joystick = new Joystick(0);
  //----------------------//

  //------SUBSYSTEMS------//
  private BallShooter shooter;
  private BallHandler ballHandler;
  private Spinner spinner;
  private Limelight limelight;
  private WestCoastDrive _drive = new WestCoastDrive( m_timer );
  //----------------------//

  private double RPM = 1000.0d;
  //BallShooter shooter = new BallShooter( LEDS );
  

  @Override
  public void robotInit() {
    shooter = new BallShooter(_joystick);
    spinner = new Spinner( _joystick );
    limelight = new Limelight();
    ballHandler = new BallHandler(_joystick);
  }

  @Override
  public void robotPeriodic() {
    limelight.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    spinner.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    //spinner.teleopPeriodic();
    //ballCollector.teleopPeriodic();
    //
    //
    if(_joystick.getRawButton(1)) {
      // Max 4125.0d
      shooter.setRPM( 2200.0d ); //(_joystick.getY()*4500.0d));
    } else {
      shooter.stop();
    }
    //
    //  Set drive
    //
    //
      _drive.arcadeDrive(_joystick.getY() / 0.5d, _joystick.getZ() / 0.5d);

      ballHandler.handler();
    //
    //  Spin turret
    //
    //
    /*
    if ( _joystick.getRawButton(3)){
      shooter.turretOffset( 0.25d );
    } else if (_joystick.getRawButton(4)) {
      shooter.turretOffset( -0.25d );
    } else {
      shooter.turretOff();
    }
    */
    //
    SmartDashboard.putNumber("RPM", shooter.getCurrentRPM());
  }

  // Used for testing purposes 

  public void testPeriodic(){
  }

}