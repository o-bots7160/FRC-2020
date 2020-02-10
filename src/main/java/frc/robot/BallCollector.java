package frc.robot;

import edu.wpi.first.wpilibj.Timer;

class BallCollector {
    private final Timer m_timer;
    /*
     *
     * This function is called periodically during test mode.
     */
    public BallCollector( final Timer ref_timer) {
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
     * Turn collector on to start collecting balls.
     */
    public void on() {
    }
    /*
     *
     * Turn collector off to stop collecting balls.
     */
    public void off() {
    }
  }