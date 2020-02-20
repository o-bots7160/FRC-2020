package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;

class BallCollector {
    private final Timer m_timer;
    private CANSparkMax   _lowFeed = new CANSparkMax(RobotMap._lowFeed, MotorType.kBrushless);
    private CANSparkMax   _upFeed  = new CANSparkMax(RobotMap._upFeed,  MotorType.kBrushless);
    private WPI_VictorSPX _intake  = new WPI_VictorSPX(RobotMap._intake);

    /*
     *
     * This function is called periodically during test mode.
     */
    public BallCollector( final Timer ref_timer) {
        m_timer = ref_timer;
        m_timer.get();
    }
    public void robotInit() {
    }
    public void robotPeriodic() {
    }
    public void autonomousInit() {
    }
    public void autonomousPeriodic() {
    }
    public void teleopInit() {
    }
     public void teleopPeriodic() {
    }
    public void testPeriodic() {
    }
    public void disabledInit() {
    }
    public void disabledPeriodic() {
    }
    public void on() {
    }
    public void off() {
    }
    public void intake(){
  
    }
    public void collectBalls(){
   
    }
    public void backFeed(){
   
    }
 
}