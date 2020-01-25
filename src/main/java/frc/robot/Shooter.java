package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;

class Shooter {
    

    private final Timer m_timer;
    private int rpm = 0;
    private int target = 0;
    private double last;
    private final double kP            = 0.5d;
    private final double kI            = 0.0d;
    private final double kD            = 0.0d;
    private final PIDController rpmPID = new PIDController(kP, kI, kD);
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
    }
    /*
     *
     * This function is called periodically no matter the mode.
     */
    public void robotPeriodic() {
        double current = m_timer.get();
        double elapsed = current - last;
        rpm = (int) (45.0d / elapsed);
        if ( ! atSpeed() )
        {
            if ( target > rpm )
            {
                // increase motor power
            }
            else
            {
                // decrease motor power
            }
        }
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
        set( 300 );
    }
    /*
     *
     * This function shuts off the shooter motor.
     */
    public void off(){
        // stop motor
    }
    /*
     *
     * This function sets the target rpm for the shooter.
     */
    public void set(int rpm){
        target = rpm;
        rpmPID.reset      (       );
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