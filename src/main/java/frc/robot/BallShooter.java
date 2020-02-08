
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;

class BallShooter{

    WPI_TalonFX _shotMain = new WPI_TalonFX(30);
    WPI_TalonFX _shotFoll = new WPI_TalonFX(31);
    Joystick joy = new Joystick(0);
    double kP = 1;
    double kI = 0;
    double kD = 0;

    private PIDController RPMPID = new PIDController(kP, kI, kD);

    public void robotInit(){
        _shotFoll.follow(_shotMain);
   }

   public void teleopPeriodic(){
       _shotMain.set(RPMPID.calculate(getCurrentRPM));
   }

   public double getCurrentRPM(){

    double magVel_UnitsPer100ms = _shotMain.getSelectedSensorVelocity(0);
    double magVelRPM = magVel_UnitsPer100ms * 600 / 4096;
    System.out.print("Mag encoder is: " + magVelRPM);


       return magVelRPM;
   }

   public void setRPM(double RPM){
    
        RPMPID.setSetpoint(RPM);

   }
   public boolean atRPM(){



        return false;
   }
   public void testInit(){
    final int kTimeoutMs = 30;
    _shotMain.configFactoryDefault();
    _shotMain.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative ,0,kTimeoutMs);
  }
  public void testPeriodic(){
    if(joy.getRawButton(1)) {
        _shotMain.set(ControlMode.PercentOutput, -1 * joy.getY());
     }
    else {
        _shotMain.set(0);
    }
    double magVel_UnitsPer100ms = _shotMain.getSelectedSensorVelocity(0);
    double magVelRPM = magVel_UnitsPer100ms * 600 / 4096;
    System.out.print("Mag encoder is: " + magVelRPM);
  }
}