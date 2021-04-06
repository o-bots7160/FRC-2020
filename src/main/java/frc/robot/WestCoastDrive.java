package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

class WestCoastDrive {
    private final double kP                            = 0.015d; // old 0.06
    private final double kI                            = 0.00005d;
    private final double kD                            = 0.0d;
    public AHRS navX;
    // By seconds
    //private final Timer autonTimer;

    public final PIDController anglePID              = new PIDController(kP, kI, kD);
    private final WPI_TalonFX _rghtMain               = new WPI_TalonFX(RobotMap._rghtMain);
    private final WPI_TalonFX _rghtFol1               = new WPI_TalonFX(RobotMap._rghtFol1);
    private final WPI_TalonFX _leftMain               = new WPI_TalonFX(RobotMap._leftMain);
    private final WPI_TalonFX _leftFol1               = new WPI_TalonFX(RobotMap._leftFol1);

    private double rotRate = 0.0d;

    private Joystick driveJoy;

    SlewRateLimiter filter = new SlewRateLimiter(1);
    private final DifferentialDrive mainDrive = new DifferentialDrive(_leftMain, _rghtMain);

    public WestCoastDrive( Timer autonTimer, Joystick driveJoy) {
        try{
            navX = new AHRS(SPI.Port.kMXP);
        }catch(RuntimeException e){
            e.printStackTrace();
        }


        this.driveJoy = driveJoy;

        _rghtFol1.follow( _rghtMain  );
        _leftFol1.follow( _leftMain  );
        _rghtMain.setSafetyEnabled(false);
        _rghtFol1.setSafetyEnabled(false);
        _leftMain.setSafetyEnabled(false);
        _leftFol1.setSafetyEnabled(false);
    
        _rghtMain.setInverted( false  );
        _leftMain.setInverted( false );
        _rghtMain.configOpenloopRamp(2);
        _leftMain.configOpenloopRamp(2);
        anglePID.setTolerance( 1.0d );
        _rghtMain.getSensorCollection().setIntegratedSensorPosition(0.0, 0);

     }

     public void resetRightMotor(){
        _rghtMain.getSensorCollection().setIntegratedSensorPosition(0.0, 0);
     }


     public int getRightEncoderReading(){
         return _rghtMain.getSelectedSensorPosition();
     }


    public void autonomousInit() {
        _rghtMain.getSensorCollection().setIntegratedSensorPosition(0.0, 0);
        
        _rghtFol1.setNeutralMode(NeutralMode.Brake);
        _leftFol1.setNeutralMode(NeutralMode.Brake);
        navX.reset();
        anglePID.reset();
        anglePID.setSetpoint(0);
    }
    public void autonomousPeriodic() {

        if(navX.getAngle() >= 2){
            rotRate = -0.35;
        }else if(navX.getAngle() <= -2){
            rotRate = 0.35;
        }else if(navX.getAngle() >= 1 ) {
            rotRate = -.2; 
        }else if(navX.getAngle() <= -1) {
            rotRate = .2;
                        
        }else {
            rotRate = 0;
        }
        SmartDashboard.putNumber("Rotation: ", rotRate);
        
        SmartDashboard.putNumber("Drive Enc: ",_rghtMain.getSelectedSensorPosition() );
        // 150000
        

    }
    public void teleopInit() {
        navX.zeroYaw();
        _rghtMain.setNeutralMode(NeutralMode.Brake);
        _leftMain.setNeutralMode(NeutralMode.Brake);
    }
    public void teleopPeriodic() {

        SmartDashboard.putNumber("Gyro Reaing", navX.getAngle());


                    if(InputMap.DeadBand(0.2d, driveJoy.getY()) ||
                    InputMap.DeadBand(0.35d, driveJoy.getRawAxis(InputMap.DRIVEJOY_Z))){
                        arcadeDrive(-driveJoy.getY() * InputMap.SPEED_Y,
                        driveJoy.getRawAxis(InputMap.DRIVEJOY_Z) * InputMap.SPEED_Z);
                        System.out.println(driveJoy.getRawAxis(InputMap.DRIVEJOY_Z) * InputMap.SPEED_Z);
        }
    }
    
    /*
     *
     * This function accepts joystick input to move the robot.
     */
    public void arcadeDrive( final double y, final double x) {
       
        mainDrive.arcadeDrive( y, x);
    }
    

    Timer tempTimer = new Timer();

    public void disabledInit(){
        _rghtMain.setNeutralMode(NeutralMode.Coast);
        _leftMain.setNeutralMode(NeutralMode.Coast);
        _rghtFol1.setNeutralMode(NeutralMode.Coast);
        _leftFol1.setNeutralMode(NeutralMode.Coast);

    }

    TalonFX [] _fxes = {_rghtMain, _rghtFol1, _leftMain, _leftFol1};
       

    public void testInit(){
        System.out.println(getDistance());

    }


    public double getAngle(){
        return navX.getAngle();
    }

    public int getDistance(){
        return _rghtMain.getSelectedSensorPosition();
    }

    public boolean driveAngle(double angle, double driveSpeed, double tolerance){
        anglePID.setSetpoint(angle);
        arcadeDrive(driveSpeed, anglePID.calculate(navX.getAngle()));
        if(navX.getAngle() < (angle + tolerance) && navX.getAngle() > (angle - tolerance)){
            return true;
        }else{
            return false;
        }
    }


    public void testing(){
 
    }

    


}