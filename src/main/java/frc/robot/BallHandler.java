package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;


 class BallHandler {
    
	//------MotorControllers------//
	private final CANSparkMax   _lowFeed = new CANSparkMax(   RobotMap._lowFeed, MotorType.kBrushless);
	private final CANSparkMax   _upFeed  = new CANSparkMax(   RobotMap._upFeed,  MotorType.kBrushless);
	private final WPI_VictorSPX _intake  = new WPI_VictorSPX( RobotMap._intake );
	//----------------------------//

	//--------Ball Sensors--------//
	private final TimeOfFlight _ballCollected = new TimeOfFlight( RobotMap._ballCollected );
	private final TimeOfFlight _intakeFull    = new TimeOfFlight( RobotMap._intakeFull );
	//----------------------------//

	private final OnOffDelay ballDelay      = new OnOffDelay( 0.0d, 0.15d );
	private final OnOffDelay fullDelay      = new OnOffDelay( 0.25d, 0.0d );
	private final OnOffDelay shotDelay      = new OnOffDelay( 1.15d, 0.0d );
  //private final OnOffDelay fullDelay      = new OnOffDelay( 0.25d, 0.0d );
	private       double     collectorRange = 0.0d;
	private       double     intakeRange    = 0.0d;
	private       boolean    newBall        = false;
	private       boolean    full           = false;
	private 	  boolean 	 shot 			= false;
//  private final Timer timerdelay = new Timer();
	private Joystick MINIPJOY_1;
	private Joystick MINIPJOY_2;
	private Joystick DRIVEJOY;
	private Timer autonTimer;
	private Double fastHop = 0.0d;
	private double ballAmount = 0;

	private enum telopMode{
		IDLE, ON
	}

	private enum IntakeMode{
		INTAKE, UPFEED
	}

	private IntakeMode intakeMode;
	private telopMode mode;

	public BallHandler(Timer autonTimer, Joystick MINIPJOY_1, Joystick MINIPJOY_2, Joystick DRIVEJOY) {
		this.autonTimer = autonTimer;
		this.MINIPJOY_1 = MINIPJOY_1;
		this.MINIPJOY_2 = MINIPJOY_2;
		this.DRIVEJOY   = DRIVEJOY;
		_lowFeed.setInverted(true);
		_upFeed.setInverted(true);
		_ballCollected.setRangingMode( RangingMode.Short, 24.0d );
		_intakeFull.setRangingMode   ( RangingMode.Short, 24.0d );
		mode = telopMode.ON;
	}
	public void robotPeriodic() {
		collectorRange  = _ballCollected.getRange();
		intakeRange     = _intakeFull.getRange();

        SmartDashboard.putNumber("Intake", collectorRange );
        SmartDashboard.putNumber("Full", intakeRange);

		newBall = ballDelay.isOn( collectorRange < 100.0d ); // We have a ball if distance is less than Xmm
		full    = fullDelay.isOn( intakeRange < 150.0d );
		shot = shotDelay.isOn( MINIPJOY_1.getRawButton( InputMap.SHOOTBUTTON ) );
	}


	public void setBrakeMode(){
		_lowFeed.setIdleMode(IdleMode.kBrake);
		_upFeed.setIdleMode(IdleMode.kBrake);
	}
	
	public void setCoastMode(){
		_lowFeed.setIdleMode(IdleMode.kCoast);
		_upFeed.setIdleMode(IdleMode.kCoast);
	}

	public void autonomousInit(){
		
	}

	public void autonomousPeriodic(){
		/*if ( ( autonTimer.get() >= 1 ) && ( autonTimer.get() <= 5 ) ) {
			_lowFeed.set( 1.0d);
			_upFeed.set ( 0.95d);
        } else if ( ( autonTimer.get() >= 5 ) && ( autonTimer.get() <= 15 ) ) {
			_intake.set ( 0.35d );
			_lowFeed.set( 0.95d);
			_upFeed.set ( 0.90d);
		} else {
			_intake.set ( 0.0d );
			_lowFeed.set( 0.0d );
			_upFeed.set ( 0.0d );
		}*/

		switch(Robot.getAutoModes()){

			case INDEX:
				if(ballAmount != 3){
					if(_intakeFull.getRange() < 100.0d){
						_lowFeed.set(0.35d);
						_upFeed.set(0.3d);
						_intake.set(0.0d);
					}
				}else{
					_intake.set(0.0d);
					_lowFeed.set(0.0d);
					_upFeed.set(0.0d);
					Robot.mode = Robot.AutoModes.FORWARD;
				}
				break;

		}


	}

	private void autoIntake(){
		
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
		//	if(timerdelay.get() <= 1.5){
			
			//MINIPJOY_1.getRawButton( InputMap.UPPER_HOPPER_UP); 
			//MINIPJOY_1.getRawButton( InputMap.LOWER_HOPPER_UP); 
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
		if ( MINIPJOY_1.getRawButton( InputMap.BACKFEED ) ) {
			_upFeed.set(-0.35);
			_lowFeed.set(-0.45);
			_intake.set(-0.45d);
		} else if ( full ) {
			_upFeed.set ( 0.0d  );
			_lowFeed.set( 0.0d  );
			_intake.set ( 0.0d  );
		} else if ( newBall ) {
			_upFeed.set ( 0.3d  );
			_lowFeed.set( 0.35d );
			_intake.set ( 0.0d  );
		} else {
			_upFeed.set ( 0.0d  );
			_lowFeed.set( 0.0d  );
			_intake.set ( 0.35d );
		}
	}
	private void telopShoot() {
		_upFeed.set ( 0.20d );
		_lowFeed.set( 0.15d  );
		_intake.set ( 0.0d  );
	}
	public void telopInit(){
		mode = telopMode.ON;
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
					if(shot){			
						telopShoot();
					}			
				} else if (DRIVEJOY.getRawButton(InputMap.ENGAGE_INTAKE) ) { 
					telopOn();
				} else {
					telopManualControl();
				}
				break;
		}
	}	
		public boolean readyforball (){
			return(!full && !newBall);

		}


		public void shootingChallenge(boolean firstShoot, Timer shotTimer){
			switch(Robot.getAutoModes()){
				case INDEX: 
				if (DRIVEJOY.getRawButton( InputMap.FEED_VIA_SHOOTER )) {
					_upFeed.set ( -0.75d  );
					_lowFeed.set( -0.85d  );
					_intake.set ( -0.90d  );
				
				}else{
					_upFeed.set ( 0.0d  );
					_lowFeed.set( 0.0d  );
					_intake.set(  0.0d  );
				}
				break;

				case SHOOT:

				if(firstShoot){
					if(shotTimer.get() <= 1){
						_upFeed.set ( 0.0d );
						_lowFeed.set( 0.0d  );
					}else{
						_upFeed.set ( 0.95d );
						_lowFeed.set( 0.9d  );
					}
				}else{
					_upFeed.set ( 0.95d );
					_lowFeed.set( 0.9d  );
				}
				break;

				case FORWARD:
				_upFeed.set ( 0.0 );
				_lowFeed.set( 0.0  );
				break;
				case BACKWARD:
				_upFeed.set ( 0.0 );
				_lowFeed.set( 0.0  );
				break;
			}
		}



		//private boolean grabbedBall = false;
		

		public boolean hasBall = false;
		public boolean timerReset = false;
		public Timer intakeTimer = new Timer();
		public boolean firstBall = false;

		public void galaticReset(){
			hasBall = false;
			newBall = false;
			timerReset = false;
			firstBall = false;
			//intakeTimer.start();
			intakeTimer.reset();
			
		}

		public void getball(){

			//System.out.println(timerReset);

			if ( collectorRange > 100 && !hasBall) {
				//System.out.println("collect");
				_upFeed.set ( 0.0d  );
				_lowFeed.set( 0.0d  );
				_intake.set ( 0.35d );
				
			} else {
				//System.out.println("HasBall0");			
				hasBall = true;
				_intake.set ( 0.0d  );
				firstBall = true;
				intakeTimer.start();
				
				
			}

			if(hasBall){
				//System.out.println("HasBall1");
				if (timerReset==true){
					//System.out.println("HasBall2");
					if (intakeTimer.get() < .15 ){
						_upFeed.set ( 0.3d  );
						_lowFeed.set( 0.35d );
						_intake.set ( 0.0d  );
					}else{
						
						//System.out.println("HasBall3");
						hasBall = false;
						timerReset = false;
					}
				}else{
					timerReset = true;
					
					intakeTimer.reset();
					//System.out.println("reset");
				}

			}

		}





}