/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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

  BallShooter shooter = new BallShooter();
  Joystick _joystick = new Joystick(0);

  @Override
  public void robotInit() {
    shooter.robotInit();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {

    shooter.teleopPeriodic();

    System.out.println("Current RPM : " + shooter.getCurrentRPM());
    
    /*if (targetInSight) {
      _diffDrive.arcadeDrive((_joystick.getY()*-1)*0.5, ( _joystick.getZ() + ( horizAngle / 27.0d) ));
    } else {
      _diffDrive.arcadeDrive((_joystick.getY()*-1)*0.5, _joystick.getZ()*0.75);
    }*/
    if(_joystick.getRawButton(1)) {
      shooter.setRPM( 4125.0d ); //(_joystick.getY()*4500.0d));
    } else {
      shooter.stop();
    }
    SmartDashboard.putNumber("RPM", shooter.getCurrentRPM());
  } 
  
  

  @Override
  public void testInit() {
    
  }

  @Override
  public void testPeriodic() {

    shooter.teleopPeriodic();

    System.out.println("Current RPM : " + shooter.getCurrentRPM());

    if(_joystick.getRawButton(1)) {
      shooter.setRPM( 6250.0d );
    } else {
      shooter.stop();
    }
    SmartDashboard.putNumber("RPM", shooter.getCurrentRPM());
  } 
  
}