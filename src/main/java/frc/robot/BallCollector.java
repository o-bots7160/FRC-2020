package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


 class BallCollector {
    
    private CANSparkMax   _lowFeed = new CANSparkMax(RobotMap._lowFeed, MotorType.kBrushless);
    private CANSparkMax   _upFeed  = new CANSparkMax(RobotMap._upFeed,  MotorType.kBrushless);
    private WPI_VictorSPX _intake  = new WPI_VictorSPX(RobotMap._intake);

    /*
     *
     * This function is called periodically during test mode.
     */
    public BallCollector( ) {
   
    }
    public void robotInit() {
        _lowFeed.setInverted(true);
        _upFeed.setInverted(true);
    }
    public void intakeOn() {
        _intake.set( 0.25d );
    }
    public void intakeOff() {
        _intake.stopMotor();
    }
    public void hopperOn() {
        _lowFeed.set( 0.25d );
        _upFeed.set( 0.25d );
    }
    public void hopperOff() {
        _lowFeed.stopMotor();
        _upFeed.stopMotor();
    }
 
}