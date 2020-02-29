package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

class BallShooter{

    // Shooter Motor Controllers
    private WPI_TalonFX _shotMain = new WPI_TalonFX(RobotMap._shotMain);
    private WPI_TalonSRX _turret = new WPI_TalonSRX(RobotMap._turret );
    
    // PID
    private final double kP = 0.00002;
    private final double kI = 0.001;
    private final double kD = 0;
    private PIDController RPMPID = new PIDController(kP, kI, kD);

    boolean controlling = false;
  
    double percentVoltage = 0.0;
    private double shootPower = 0.65d;
    // Target RPM
    private double targetRPM = 0.d;
    private Joystick MINIPJOY_1;
    private Joystick MINIPJOY_2;
    private Joystick DRIVEJOY;
    private Timer autonTimer;

    
    // Sets up the motor controller for use
    public BallShooter(Timer autonTimer, Joystick MINIPJOY_1, Joystick MINIPJOY_2, Joystick DRIVEJOY){
        
        this.autonTimer = autonTimer;
        this.DRIVEJOY = DRIVEJOY;
        this.MINIPJOY_1 = MINIPJOY_1;
        this.MINIPJOY_2 = MINIPJOY_2; 
        final int kTimeoutMs = 30;
        //_shotFoll.follow(_shotMain);
        _shotMain.configFactoryDefault();
        _shotMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor ,0,kTimeoutMs);
        RPMPID.setTolerance(50.0d);
        _shotMain.setInverted( true  );
        
   }

   public void autonomousPeriodic (){
    setRPM(3300);
    percentVoltage = RPMPID.calculate(getCurrentRPM());
    System.out.print("new target is: " + percentVoltage);
      if(autonTimer.get() <= 10){
        _shotMain.set( 0.65);
      }else{
        _shotMain.set(0.0d);
      }
   }
   // Get called by teleop periodic and tells the motor controller to reach or maintain a rpm value
   public void teleopPeriodic(){

      if(DRIVEJOY.getRawButton(9)){
        shootPower = 0.65d;
      }else if(DRIVEJOY.getRawButton(10)){
        shootPower = 0.70d;
      }

        // Manual shooter
        if(MINIPJOY_1.getRawButton(InputMap.SHOOTBUTTON)){
          //percentVoltage = RPMPID.calculate(getCurrentRPM());
          //System.out.print("new target is: " + percentVoltage);

          _shotMain.set( shootPower);
          System.out.println(_shotMain.get());
        }else{
          _shotMain.set(0.0);
        }

        // Manual turret
        if(MINIPJOY_2.getRawButton(InputMap.TURRET_RIGHT)){
          _turret.set(0.25);
          System.out.println("Positive");
        }else if(MINIPJOY_2.getRawButton(InputMap.TURRET_LEFT)){
          _turret.set(-0.25);
          System.out.println("Negitive");
        }else{
          _turret.set(0);
        }

   }

   // Calculter the current RPM based on values from the encoder
   public double getCurrentRPM(){

      double magVel_UnitsPer100ms = _shotMain.getSelectedSensorVelocity(0);
      double magVelRPM = Math.abs( magVel_UnitsPer100ms * 600 / 2048 );
//    System.out.print("Mag encoder is: " + magVelRPM);


      return magVelRPM;
   }

   // Sets the PID's setpointto the RPM value desired by the user
   public void setRPM(double RPM){
      controlling = true;
      targetRPM = RPM;
      RPMPID.setSetpoint( targetRPM );

   }

   // Will return whether or not we are at the desired RPM
   public boolean atRPM(){
        return (getCurrentRPM() == targetRPM);
   }
   public void stop() {
      controlling = false;
      _shotMain.set( 0.0d );
      RPMPID.reset();
  }


  public void turretOffset(double offset) {
      _turret.set( offset );
  }

}