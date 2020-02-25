package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.playingwithfusion.TimeOfFlight;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;


 class BallHandler {
    
    //------MotorControllers------//
    private final CANSparkMax   _lowFeed = new CANSparkMax(RobotMap._lowFeed, MotorType.kBrushless);
    private final CANSparkMax   _upFeed  = new CANSparkMax(RobotMap._upFeed,  MotorType.kBrushless);
    private final WPI_VictorSPX _intake  = new WPI_VictorSPX(RobotMap._intake);
    //----------------------------//

    //--------IndexSensors--------//
    private final TimeOfFlight _intakeSensor = new TimeOfFlight(100);
    private final TimeOfFlight _lowerToUpperSensor = new TimeOfFlight(101);
    private final TimeOfFlight _UpperToShooterSensor = new TimeOfFlight(102);
    //----------------------------//

    private Joystick _joy;

    private int ballCount = 0;

    private enum CollectionMode{
        IDLE, INTAKE, INDEXUP
    }

    private CollectionMode mode;

    public BallHandler(Joystick _joy) {
        this._joy = _joy;
        _lowFeed.setInverted(true);
        _upFeed.setInverted(true);
        mode = CollectionMode.IDLE;


        // TIME OF FLIGHT SENSOR RANGING MODES //
        _intakeSensor.setRangingMode(TimeOfFlight.RangingMode.Short, 24);
        _lowerToUpperSensor.setRangingMode(TimeOfFlight.RangingMode.Short, 24);
        _UpperToShooterSensor.setRangingMode(TimeOfFlight.RangingMode.Short, 24);

    }


    public void handler(){


        if(_joy.getRawButton(0)){
            intakeOn();
        }

    }


    public boolean ballReady(){

        //if()


        return false;
    }


    private void ballCounter(){
        if(_intakeSensor.getRange() <= 3)
            ballCount++;
    }

    private void intakeOn() {
        _intake.set( 0.25d );
    }
    private void intakeOff() {
        _intake.stopMotor();
    }
    private void hopperOn() {
        _lowFeed.set( 0.25d );
        _upFeed.set( 0.25d );
    }
    private void hopperOff() {
        _lowFeed.stopMotor();
        _upFeed.stopMotor();
    }
 
}