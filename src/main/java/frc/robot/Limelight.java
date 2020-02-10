/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Limelight extends TimedRobot {

  Joystick _joystick = new Joystick(0);

  boolean targetInSight = false;
  double horizAngle;
  double vertAngle;
  double percentArea;

  @Override
  public void robotInit() {
    
   
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
  }

  @Override
  public void robotPeriodic() {
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    if ( table.getEntry("tv").getDouble( 0.0) != 0 )
    {
      targetInSight = true;
      horizAngle    = table.getEntry("tx").getDouble( 0.0);
      vertAngle     = table.getEntry("ty").getDouble( 0.0);
      percentArea   = table.getEntry("ta").getDouble( 0.0 );
    } else {
      targetInSight = false;
      horizAngle    = 0.0d;
      vertAngle     = 0.0d;
      percentArea   = 0.0d;

      
    }
 }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {

    SmartDashboard.putNumber("LimelightX", horizAngle);
    SmartDashboard.putNumber("LimelightY", vertAngle);
    SmartDashboard.putNumber("LimelightArea", percentArea);

  }

  
  @Override
  public void teleopPeriodic() {
    
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public boolean istargetinsight() {
return targetInSight;













  }








}

  