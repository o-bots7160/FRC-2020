/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

public class Limelight {

  Joystick _joystick = new Joystick(0);

  boolean targetInSight = false;
  double horizAngle;
  double vertAngle;
  double percentArea;

  public void robotInit() {
    
   
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
  }

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

  public void autonomousInit() {
  }

  public void autonomousPeriodic() {
  }

  public void teleopInit() {

    SmartDashboard.putNumber("LimelightX", horizAngle);
    SmartDashboard.putNumber("LimelightY", vertAngle);
    SmartDashboard.putNumber("LimelightArea", percentArea);

  }

  public void teleopPeriodic() {
    
  }

  public void testInit() {
  }

  public void testPeriodic() {
  }

  public boolean istargetinsight() {
return targetInSight;













  }








}

  