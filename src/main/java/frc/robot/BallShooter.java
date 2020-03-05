package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class BallShooter{

    // Shooter Motor Controllers
    private WPI_TalonFX _shotMain = new WPI_TalonFX(RobotMap._shotMain);
    private WPI_TalonSRX _turret = new WPI_TalonSRX(RobotMap._turret );
    
    // PID
    private final double kP = 0.0035;
    private final double kI = 0.000000;
    private final double kD = 0;
    private PIDController RPMPID = new PIDController(kP, kI, kD);

    boolean controlling = false;
  
    double percentVoltage = 0.0;
    private double shootPower= 0.65d;
    // Target RPM
    private double targetRPM = 0.d;
    private Joystick MINIPJOY_1;
    private Joystick MINIPJOY_2;
    private Joystick DRIVEJOY;
    private Timer autonTimer;
    private SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration();
    private boolean hasShot = false;
    private boolean limeControl = false;
    
    
    // Sets up the motor controller for use
    public BallShooter(Timer autonTimer, Joystick MINIPJOY_1, Joystick MINIPJOY_2, Joystick DRIVEJOY){
        config.enable = true;
        config.triggerThresholdCurrent = 35.0d;
        config.triggerThresholdTime = 0.10d;


        this.autonTimer = autonTimer;
        this.DRIVEJOY = DRIVEJOY;
        this.MINIPJOY_1 = MINIPJOY_1;
        this.MINIPJOY_2 = MINIPJOY_2; 
        final int kTimeoutMs = 30;
        //_shotFoll.follow(_shotMain);
        _shotMain.configFactoryDefault();
        _shotMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor ,0,kTimeoutMs);
        RPMPID.setTolerance(5.0d);
        _shotMain.setInverted( true  );
        _shotMain.configGetSupplyCurrentLimit(config);
   }

   public void autonomousPeriodic (){
    System.out.print("new target is: " + percentVoltage);
      if(autonTimer.get() <= 5){
        _shotMain.set( 0.52);
      }else{
        hasShot = true;
        _shotMain.set(0.0d);
      }
   }

   public boolean getShot(){
      return hasShot;
   }
   // Get called by teleop periodic and tells the motor controller to reach or maintain a rpm value
   public void teleopPeriodic(){
      if(DRIVEJOY.getRawButton(5)){
        shootPower -= 0.01;
      }else if(DRIVEJOY.getRawButton(6)){
        shootPower += 0.01d;
      }

        SmartDashboard.putNumber("PercentValue", shootPower);
        SmartDashboard.putNumber("RPM", getCurrentRPM());
      
        // Manual shooter
        if(MINIPJOY_1.getRawButton(InputMap.SHOOTBUTTON)){
          //percentVoltage = RPMPID.calculate(getCurrentRPM());

          _shotMain.set(shootPower);
          System.out.println(getCurrentRPM());
        }else{
          RPMPID.reset();
          _shotMain.set(0.0);
        }
        if(!MINIPJOY_1.getRawButton(InputMap.LIMELIGHT_ON)){
          limeControl = false;
          // Manual turret
          if(MINIPJOY_2.getRawButton(InputMap.TURRET_LEFT)){
            _turret.set(0.15);
            System.out.println("Positive");
          }else if(MINIPJOY_2.getRawButton(InputMap.TURRET_RIGHT)){
            _turret.set(-0.15);
            System.out.println("Negitive");
          }else{
            _turret.set(0);
          }
        }else{
          limeControl = true;
        }

   }

   public void turretTurn(double turnRate){
     if(limeControl)
      _turret.set(turnRate);
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