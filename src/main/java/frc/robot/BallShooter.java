package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;

class BallShooter{
    // Shooter Motor Controllers

    private WPI_TalonFX _shotMain = new WPI_TalonFX(RobotMap._shotMain);
    // PID
    private final double kP = 0.0004;
    private final double kI = 0.001;
    private final double kD = 0;
    private PIDController RPMPID = new PIDController(kP, kI, kD);

    boolean controlling = false;
  
    double setPoint = 0.0;
    // Target RPM
    private double targetRPM = 300;

    
    // Sets up the motor controller for use
    public void robotInit(){
        final int kTimeoutMs = 30;
        //_shotFoll.follow(_shotMain);
        _shotMain.configFactoryDefault();
        _shotMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor ,0,kTimeoutMs);
        RPMPID.setTolerance(50.0d);
        _shotMain.setInverted( true  );
   }

   // Get called by teleop periodic and tells the motor controller to reach or maintain a rpm value
   public void teleopPeriodic(){
     if ( controlling) {
    //_shotFoll.follow( _shotMain );
    setPoint = RPMPID.calculate(getCurrentRPM());
    System.out.print("new target is: " + setPoint);
    _shotMain.set( setPoint);
       //_shotMain.set(0.10);
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
 

}