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

public class Limelight {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private boolean targetInSight = false;
  private double  horizAngle;
  private double  vertAngle;
  private double  percentArea;
  private final double h1 =  2.55d;
  private final double h2 =  8.0d;
  private final double a1 = 45.0d;

  public Limelight() {
    SmartDashboard.putNumber("LimelightX", horizAngle);
    SmartDashboard.putNumber("LimelightY", vertAngle);
    SmartDashboard.putNumber("LimelightArea", percentArea);
  }

  public void robotPeriodic() {
    

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
  public double horizAngle()  {
    return horizAngle;
  }
   public double vertAngle() {
     return vertAngle;
   }
   public double percentArea(){
    return percentArea;
   }
   public double distancetoTarget(){
     return (h2-h1)/Math.tan(a1+vertAngle);
   }
}