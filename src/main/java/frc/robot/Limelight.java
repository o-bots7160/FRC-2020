/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

  static NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    static NetworkTableEntry tx = limelightTable.getEntry("tx");
    static NetworkTableEntry tvert = limelightTable.getEntry("tvert");
    static double x = 0;
    static double height = 0;
    static double dist = 0;
    final double heightPixelConstant = 262.9565217391; //conversion factor of pixels to inches
    final double heightInchConstant = 5.75; //actual height of object in inches

    private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightShooterCommand = 0.0;
    private double m_LimelightTurretCommand = 0.0;

    public Limelight() {
    }


  public static Double getOffSet_InDegrees(){ 
    return tx.getDouble(0.0d);
}

  public void lightOff(){
    limelightTable.getEntry("ledMode").setNumber(1);
  }


  public void Update_Limelight_Tracking()
  {
        // These numbers must be tuned for your Robot!  Be careful!
        final double TURRET_K = 0.03;                     // how hard to turn toward the target
        final double SHOOTER_K = 0.26;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 13.0;          // Area of the target when the robot reaches the wall
        final double MAX_TURRET = 0.25d;
        final double MAX_SHOOT = 0.7d;                    // Simple speed limit so we don't drive too fast

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

        if (tv < 1.0)
        {
          m_LimelightHasValidTarget = false;
          m_LimelightShooterCommand = 0.0;
          m_LimelightTurretCommand = 0.0;
          return;
        }

        m_LimelightHasValidTarget = true;

        // Start with proportional steering
        double turret_cmd = tx * TURRET_K;
        if (turret_cmd > MAX_TURRET)
        {
          turret_cmd = MAX_TURRET;
        }
        m_LimelightTurretCommand = turret_cmd;

        // try to drive forward until the target area reaches our desired area
        double shooter_cmd = (DESIRED_TARGET_AREA - ta) * SHOOTER_K;

        // don't let the robot drive too fast into the goal
        if (shooter_cmd > MAX_SHOOT)
        {
          shooter_cmd = MAX_SHOOT;
        }
        m_LimelightShooterCommand = shooter_cmd;
  }


}