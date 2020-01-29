package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.*;

class BallCollector {
    private final Timer m_timer;
    private final double mtrOnPwr = 0.3d;
    private final WPI_TalonSRX  cage = new WPI_TalonSRX(RobotMap._collectrID);
    private enum CollectorModes {
                                    COLLECT_OFF,   // Off and won't turn motors on
                                    COLLECT_ON,    // On motors on to grab ball
                                    COLLECT_IDLE,  // Ball collected, waiting for hopper
                                    COLLECT_UNLOAD // Push ball into hopper
                                };
    private boolean ballSensor = false;    
    private CollectorModes mode = CollectorModes.COLLECT_OFF;
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
        cage.set( 0.0d );
    }
    /*
     *
     * This function is called periodically no matter the mode.
     */
    public void robotPeriodic() {
        switch ( mode )
        {
            case COLLECT_OFF:
                cage.set( 0.0d );
                break;
            case COLLECT_IDLE:
                cage.set( 0.0d );
                break;
            case COLLECT_ON:
                if ( ballSensor ) {
                    mode = CollectorModes.COLLECT_IDLE;
                } else {
                    cage.set( mtrOnPwr );
                }
                break;
            case COLLECT_UNLOAD:
                if ( ! ballSensor ) {
                    mode = CollectorModes.COLLECT_ON;
                }
                break;
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
        mode = CollectorModes.COLLECT_OFF;
        cage.set( 0.0d );
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
    public void collect() {
        mode = CollectorModes.COLLECT_ON;
    }
    /*
     *
     * Turn collector off to stop collecting balls.
     */
    public void off() {
        mode = CollectorModes.COLLECT_OFF;
    }
    /*
     *
     * Unload collected ball into hopper
     */
    public void unload() {
        if ( mode == CollectorModes.COLLECT_IDLE ) {
            mode = CollectorModes.COLLECT_UNLOAD;
            cage.set( mtrOnPwr );
        }
    }
    /*
     *
     * Returns true if there is a ball in the collector (idle) or
     * being loaded in the hopper (unload)
     */
    public boolean isLoaded() {
        if ( ( mode == CollectorModes.COLLECT_IDLE   ) ||
             ( mode == CollectorModes.COLLECT_UNLOAD ) ) {
            return true;
        }
        return false;
    }
  }