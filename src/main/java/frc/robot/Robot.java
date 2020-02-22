/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  private final Timer m_timer = new Timer();


  private BallShooter shooter = new BallShooter();
  private BallCollector ballCollector = new BallCollector();
  private Joystick _joystick = new Joystick(0);
  private Spinner spinner = new Spinner( _joystick );
  private Limelight limelight = new Limelight();
  private WestCoastDrive _drive = new WestCoastDrive( m_timer );
  private double RPM = 1000.0d;
  //BallShooter shooter = new BallShooter( LEDS );
  

  @Override
  public void robotInit() {
    shooter.robotInit();
    spinner.robotInit();
    limelight.robotInit();
    ballCollector.robotInit();
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
    //
    //  Set intake
    //
    //
    if(_joystick.getRawButton(7)){
      ballCollector.intakeOn();
    }else{
      ballCollector.intakeOff();
    }
    //
    //  Set hopper
    //
    //
    if(_joystick.getRawButton(8)){
      ballCollector.hopperOn();
    }else{
      ballCollector.hopperOff();
    }
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

  public void testPeriodic(){

    //_drive.arcadeDrive(_joystick.getY() * -0.5, _joystick.getZ() * 0.5d);
    if(_joystick.getRawButton(8)){
      ballCollector.hopperOn();
    }else{
      ballCollector.hopperOff();
    }
    
    if(_joystick.getRawButton(7)){
      ballCollector.intakeOn();
    }else{
      ballCollector.intakeOff();
    }

  }

}