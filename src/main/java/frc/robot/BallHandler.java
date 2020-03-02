package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.playingwithfusion.TimeOfFlight;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;


 class BallHandler {
    
    //------MotorControllers------//
    private final CANSparkMax   _lowFeed = new CANSparkMax(RobotMap._lowFeed, MotorType.kBrushless);
    private final CANSparkMax   _upFeed  = new CANSparkMax(RobotMap._upFeed,  MotorType.kBrushless);
    private final WPI_VictorSPX _intake  = new WPI_VictorSPX(RobotMap._intake);
    //----------------------------//

    private Joystick MINIPJOY_1;
    private Joystick MINIPJOY_2;
    private Timer autonTimer;
    private Double fastHop = 0.0d;


    private enum autoMode{
        IDLE, INDEXUP
    }

    private autoMode mode;

    public BallHandler(Timer autonTimer, Joystick MINIPJOY_1, Joystick MINIPJOY_2) {
        this.autonTimer = autonTimer;
        this.MINIPJOY_1 = MINIPJOY_1;
        this.MINIPJOY_2 = MINIPJOY_2;
        _lowFeed.setInverted(true);
        _upFeed.setInverted(true);
        mode = autoMode.IDLE;

    }

    public void autoInit(){
        mode = autoMode.IDLE;
    }

    public void autonomousPeriodic(){
        if(autonTimer.get() >= 1 && autonTimer.get() <= 10){
            _lowFeed.set(0.35d);
            _upFeed.set(0.45d);
            
        }else if(autonTimer.get() >= 10 && autonTimer.get() <= 15){
            _intake.set(0.35d);
            _lowFeed.set(0.35d);
        }else{
            _intake.set(0.0d);
            _lowFeed.set(0.0d);
            _upFeed.set(0.0d);
        }
    }


    public void telopPeriodic(){

        if(MINIPJOY_1.getRawButton(InputMap.SHOOTBUTTON)){
            fastHop = 0.45d;
        }else{
            fastHop = 0.0d;
        }
        // INTAKE
        if(MINIPJOY_2.getRawButton(InputMap.INTAKE_IN)){
            _intake.set(0.35d);
        }else{
            _intake.set(0.0d);
        }

        // LOWER HOPPER
        if(MINIPJOY_1.getRawButton(InputMap.LOWER_HOPPER_UP)){
            _lowFeed.set(0.45d + fastHop);
        }else{
            _lowFeed.set(0.0d);
        }

        
        // UPPER HOPPER
        if(MINIPJOY_1.getRawButton(InputMap.UPPER_HOPPER_UP)){
            _upFeed.set(0.5d + fastHop);
        }else{
            _upFeed.set(0.0d);
        }

        // BACKFEED
        if(!(MINIPJOY_1.getRawButton(InputMap.UPPER_HOPPER_UP) && MINIPJOY_1.getRawButton(InputMap.LOWER_HOPPER_UP))){
            if(MINIPJOY_1.getRawButton(InputMap.BACKFEED)){
                _upFeed.set(-0.35);
                _lowFeed.set(-0.45);
                _intake.set(-0.45d);
            }
        }
        //

    }
 
}