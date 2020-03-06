package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;


 class BallHandler {
    
	//------MotorControllers------//
	private final CANSparkMax   _lowFeed = new CANSparkMax(   RobotMap._lowFeed, MotorType.kBrushless);
	private final CANSparkMax   _upFeed  = new CANSparkMax(   RobotMap._upFeed,  MotorType.kBrushless);
	private final WPI_VictorSPX _intake  = new WPI_VictorSPX( RobotMap._intake );
	//----------------------------//

	//--------Ball Sensors--------//
	private final TimeOfFlight ballCollected = new TimeOfFlight( 0 );
	private final TimeOfFlight intakeFull    = new TimeOfFlight( 1 );
	//----------------------------//

	private final OnOffDelay ballDelay      = new OnOffDelay( 0.5d, 1.5d );
	private       double     collectorRange = 0.0d;
	private       double     intakeRange    = 0.0d;
	private       boolean    newBall        = false;
	private       boolean    full           = false;

	private Joystick MINIPJOY_1;
	private Joystick MINIPJOY_2;
	private Timer autonTimer;
	private Double fastHop = 0.0d;

	private enum telopMode{
		IDLE, ON
	}
	private telopMode mode;

	public BallHandler(Timer autonTimer, Joystick MINIPJOY_1, Joystick MINIPJOY_2) {
		this.autonTimer = autonTimer;
		this.MINIPJOY_1 = MINIPJOY_1;
		this.MINIPJOY_2 = MINIPJOY_2;
		_lowFeed.setInverted(true);
		_upFeed.setInverted(true);
		mode  = telopMode.IDLE;
		ballCollected.setRangingMode( RangingMode.Short, 24.0d );
		intakeFull.setRangingMode   ( RangingMode.Short, 24.0d );
	}
	public void robotPeriodic() {
		collectorRange  = ballCollected.getRange();
		intakeRange     = intakeFull.getRange();

		newBall = ballDelay.isOn( collectorRange < 25.0d ); // We have a ball if distance is less than Xmm
        full    = intakeRange < 25.0d;
	}

	public void disabledInit(){
		_lowFeed.setIdleMode(IdleMode.kCoast);
		_upFeed.setIdleMode(IdleMode.kCoast);
	}

	public void setBrakeMode(){
		_lowFeed.setIdleMode(IdleMode.kBrake);
		_upFeed.setIdleMode(IdleMode.kBrake);
	}

	public void autonomousPeriodic(){
		if ( ( autonTimer.get() >= 1 ) && ( autonTimer.get() <= 10 ) ) {
			_lowFeed.set( 0.35d );
			_upFeed.set ( 0.45d );
        	} else if ( ( autonTimer.get() >= 10 ) && ( autonTimer.get() <= 15 ) ) {
			_intake.set ( 0.35d );
			_lowFeed.set( 0.35d );
		} else {
			_intake.set ( 0.0d );
			_lowFeed.set( 0.0d );
			_upFeed.set ( 0.0d );
		}
	}

	private void telopManualControl() {
		if ( MINIPJOY_1.getRawButton( InputMap.SHOOTBUTTON ) ) {
			fastHop = 0.45d;
		} else {
			fastHop = 0.0d;
		}
		//
		// INTAKE
		//
		//
		if ( MINIPJOY_2.getRawButton( InputMap.INTAKE_IN ) ) {
			_intake.set( 0.35d );
		} else {
			_intake.set( 0.0d  );
		}
		//
		// LOWER HOPPER
		//
		//
		if ( MINIPJOY_1.getRawButton( InputMap.LOWER_HOPPER_UP ) ) {
			_lowFeed.set( 0.45d + fastHop );
		} else {
			_lowFeed.set( 0.0d );
		}
		//
		// UPPER HOPPER
		//
		//
		if ( MINIPJOY_1.getRawButton( InputMap.UPPER_HOPPER_UP ) ) {
			_upFeed.set( 0.5d + fastHop );
		} else {
			_upFeed.set( 0.0d );
		}
		//
		// BACKFEED
		//
		//
		if ( ! ( MINIPJOY_1.getRawButton( InputMap.UPPER_HOPPER_UP ) &&
		         MINIPJOY_1.getRawButton( InputMap.LOWER_HOPPER_UP ) ) ) {
			if ( MINIPJOY_1.getRawButton( InputMap.BACKFEED ) ) {
				_upFeed.set ( -0.35  );
				_lowFeed.set( -0.45  );
				_intake.set ( -0.45d );
			}
		}
	}
	private void telopOn() {
		//
		// BACKFEED
		//
		//
		/* if ( ! ( MINIPJOY_1.getRawButton( InputMap.UPPER_HOPPER_UP ) &&
		         MINIPJOY_1.getRawButton( InputMap.LOWER_HOPPER_UP ) ) ) {
			if ( MINIPJOY_1.getRawButton( InputMap.BACKFEED ) ) {
				_upFeed.set(-0.35);
				_lowFeed.set(-0.45);
				_intake.set(-0.45d);
			}
		} else */if ( full ) {
			_upFeed.set ( 0.0d  );
			_lowFeed.set( 0.0d  );
			_intake.set ( 0.0d  );
		} else if ( newBall ) {
			_upFeed.set ( 0.95d );
			_lowFeed.set( 0.9d  );
			_intake.set ( 0.0d  );
		} else {
			_upFeed.set ( 0.0d  );
			_lowFeed.set( 0.0d  );
			_intake.set ( 0.35d );
		}
	}
	private void telopShoot() {
		_upFeed.set ( 0.95d );
		_lowFeed.set( 0.9d  );
		_intake.set ( 0.0d  );
	}
	public void telopInit(){
		mode = telopMode.IDLE;
	}
	public void telopPeriodic(){
		//
		//  Need to determine if we are switching modes
		//
		//
		switch ( mode ) {
			case IDLE:
				telopManualControl();
				break;
			case ON:
				if ( MINIPJOY_1.getRawButton( InputMap.SHOOTBUTTON ) ) {
					telopShoot();
				} else {
					telopOn();
				}
				break;
		}
	}
}