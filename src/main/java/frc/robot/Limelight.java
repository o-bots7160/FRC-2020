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
    static NetworkTableEntry tvert = limelightTable.getEntry("tvert");
    static double x = 0;
    static double height = 0;
    static double dist = 0;
    final double heightPixelConstant = 262.9565217391; //conversion factor of pixels to inches
    final double heightInchConstant = 5.75; //actual height of object in inches

    private final static NetworkTableEntry tx = limelightTable.getEntry("tx");
    private final static NetworkTableEntry ty = limelightTable.getEntry("ty");
    private final static NetworkTableEntry ta = limelightTable.getEntry("ta");
    private final static NetworkTableEntry camMode = limelightTable.getEntry("camMode");
    private final static NetworkTableEntry ledMode = limelightTable.getEntry("ledMode");
    private final static NetworkTableEntry pipline = limelightTable.getEntry("pipeline");
    private final static NetworkTableEntry tv = limelightTable.getEntry("tv");

    private boolean m_LimelightHasValidTarget = false;
    private double m_LimelightShooterCommand = 0.0;
    private double m_LimelightTurretCommand = 0.0;

    public Limelight() {
    }

    // Return a value between -27 and 27 degrees
  public static Double getOffSet_InDegrees(){ 
    return tx.getDouble(0.0d);
  }

  // Turns the limelight leds off
  public static void limeLightLEDOff(){
    ledMode.setNumber(1);
  }

  // Returns true if the limelight thinks it has a target
  public static boolean hasValidTarget(){
    if(tv.getDouble(0) >= 1.0d){
      return true;
      
    }else{
      return false;
    }
  }

}