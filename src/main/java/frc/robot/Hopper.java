package frc.robot;

import edu.wpi.first.wpilibj.Timer;

class Hopper {
    private enum HopperModes { HOPPER_IDLE, HOPPER_LOAD, HOPPER_EJECT, HOPPER_PRIME };

    private final Timer m_timer;
    private HopperModes mode = HopperModes.HOPPER_IDLE;
    private int ballCount    = 3;
    /*
     *
     * This function is called periodically during test mode.
     */
    public Hopper( final Timer ref_timer) {
        m_timer = ref_timer;
        m_timer.get();
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void robotInit() {
        idle();
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
        mode = HopperModes.HOPPER_LOAD;
    }
    /*
     *
     * This function is called periodically during teleoperated mode.
     */
    public void teleopPeriodic() {
        switch ( mode ) {
            case HOPPER_IDLE:
                // stop motors
                break;
            case HOPPER_LOAD:
               if ( ballCount > 4 ) {
                  // shutoff loader
               } else {
                  // turn on loader
               }
               break;
            case HOPPER_PRIME:
               //if ( balls in hopper AND balls not at front of hopper ) {
               //   turn on belts
               //}
               // 
               break;
            case HOPPER_EJECT:
               //if ( ball available ) {
               //   shove ball in shooter
               //} else {
               //   move up hopper
               //}
               // 
               break;
        }
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
        idle();
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledPeriodic() {
    }
    /*
     *
     * This starts the system to load balls into the hopper
     */
    public void load(){
        mode = HopperModes.HOPPER_LOAD;
    }
    /*
     *
     * This function feeds a ball from the hopper to the shooter.
     */
    public void eject(){
        mode = HopperModes.HOPPER_EJECT;
    }
    /*
     *
     * This moves the balls from the back of the hopper until a ball
     * is ready to be ejected.
     */
    public void prime(){
        mode = HopperModes.HOPPER_PRIME;
    }
    /*
     *
     * This function sets the hopper to idle
     */
    public void idle(){
        //stop motors
        mode = HopperModes.HOPPER_IDLE;
    }
    /*
     *
     * This function returns the number of balls in the hopper.
     */
    public short count(){
        return 0;
    }
  }