package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import com.ctre.phoenix.motorcontrol.can.*;

class Shooter {
    

    private final Timer m_timer;
    private double rpm                 = 0.0d;
    private double target              = 0.0d;
    private final double kP            = 0.5d;
    private final double kI            = 0.0d;
    private final double kD            = 0.0d;
    private final PIDController rpmPID = new PIDController(kP, kI, kD);
    private final WPI_TalonSRX  fling  = new WPI_TalonSRX( RobotMap._shooterID );
    /*
     *
     * This function is called periodically during test mode.
     */
    public Shooter( final Timer ref_timer) {
        m_timer = ref_timer;
        m_timer.get();
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void robotInit() {
        set( 0.0d );
    }
    /*
     *
     * This function is called periodically no matter the mode.
     */
    public void robotPeriodic() {

        rpm = ( fling.getSelectedSensorVelocity() * 600.0d ); // converts from X/millisec to X/minute
        double newPwr = rpmPID.calculate( rpm );
        fling.set( newPwr );
    }
    /*
     *
     * This function is run once each time the robot enters autonomous mode.
     */
    public void autonomousInit() {
    }
    /*
     *
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic() {
    }
    /*
     *
     * This function is called once each time the robot enters teleoperated mode.
     */
    public void teleopInit() {
    }
    /*
     *
     * This function is called periodically during teleoperated mode.
     */
    public void teleopPeriodic() {
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void testPeriodic() {
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledInit() {
        fling.set( 0.0d );
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledPeriodic() {
    }
    /*
     *
     * This function starts spinning the shooter motor at an idle speed.
     */
    public void idle(){
        set( 300.0d );
    }
    /*
     *
     * This function shuts off the shooter motor.
     */
    public void off(){
        set( 0.0d );
    }
    /*
     *
     * This function sets the target rpm for the shooter.
     */
    public void set( double rpm ){
        target = rpm;
        rpmPID.reset      (     );
        rpmPID.setSetpoint( rpm );
    }
    /*
     *
     * This function returns true when shooter is ready to shoot.
     */
    public boolean atSpeed(){
        if ( Math.abs( target - rpm ) < 100.0d )
        {
           return true;
        }
        else{
           return false;
        }
    }
  }