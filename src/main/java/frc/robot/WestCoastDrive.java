package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

class WestCoastDrive {
    private final double TICKS_PER_FOOT                = 1000.0d;
    private final double kP                            = 0.5d;
    private final double kI                            = 0.0d;
    private final double kD                            = 0.0d;
    private double currentLocation                     = 0.0d;
    private double currentAngle                        = 0.0d;
    private double startLocation                       = 0.0d;

    // By feet
    private double autonomousLocation                  = 0.0d;
    // By degrees
    private double autonomousAngle                     = 0.0d;
    // By seconds
    private final Timer m_timer;

    private final ADXRS450_Gyro gyro                  = new ADXRS450_Gyro( );
    private final PIDController anglePID              = new PIDController(kP, kI, kD);
    private final WPI_TalonFX _rghtMain               = new WPI_TalonFX(RobotMap._rghtMain);
    private final WPI_TalonFX _rghtFol1               = new WPI_TalonFX(RobotMap._rghtFol1);
    private final WPI_TalonFX _leftMain               = new WPI_TalonFX(RobotMap._leftMain);
    private final WPI_TalonFX _leftFol1               = new WPI_TalonFX(RobotMap._leftFol1);
    
    DifferentialDrive m_robotDrive = new DifferentialDrive(_leftMain, _rghtMain);

    /*
     *
     * This function constructs the object with appropriate references.
     */
    public WestCoastDrive( final Timer ref_timer) {
        m_timer = ref_timer;
        m_timer.get();
        _rghtFol1.follow( _rghtMain  );
        _leftFol1.follow( _leftMain  );

        
        _rghtMain.setInverted( false  );
        _leftMain.setInverted( false );
     }
     /*
     *
     * This function is called periodically during test mode.
     */
    public void robotInit() {
        anglePID.setTolerance( 1.0d );
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
     * This function handles autonomous drive movement.
     */
    public void autonomousPeriodic() {

        /*double velocity , rotation;

        

        rotation = anglePID.calculate( currentAngle );

        m_robotDrive.arcadeDrive( velocity, rotation );*/
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
        m_robotDrive.stopMotor(); // stop robot
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledPeriodic() {
    }
    /*
     *
     * This function returns true when a robot successfully moves autonomously.
     */
    public boolean atLocation()
    {
        currentLocation = _rghtMain.getSelectedSensorPosition() / TICKS_PER_FOOT;

        return ( currentLocation >= autonomousLocation );
    }
    /*
     *
     * This function returns true when the robot successfully turns autonomously.
     */
    public boolean atAngle()
    {
        currentAngle = gyro.getAngle( ) % 360.0d;
        return ( Math.abs( currentAngle - autonomousAngle ) < 10.0 );
    }
    /*
     *
     * This function accepts joystick input to move the robot.
     */
    public void arcadeDrive( final double y, final double x) {
        m_robotDrive.arcadeDrive( y, x);
    }
    /*
     *
     * This function stops the robot.
     */
    public void stop(){
        m_robotDrive.stopMotor(); // stop robot
    }
    /*
     *
     * This function autonomously moves the robot.
     */
    public void autonomousMove( final double feet)
    {
        startLocation      = _rghtMain.getSelectedSensorPosition() / TICKS_PER_FOOT;
        currentLocation    = startLocation;
        autonomousLocation = feet;
    }
    /*
     *
     * This function rotates the robot a specified number of degrees.
     */
    public void autonomousRotate( final double angle)
    {
        autonomousAngle = angle;
        anglePID.reset      (       );
        anglePID.setSetpoint( angle );
        currentAngle    = gyro.getAngle( ) % 360.0d;
    }
}