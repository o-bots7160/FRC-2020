/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Team 7160, Ludington Obots                                                 */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  
    //------WPISYSTEMS------// 
    private final Timer autonTimer = new Timer();
    private final Joystick DRIVEJOY = new Joystick(InputMap.DRIVEJOY);
    private final Joystick MINIPJOY_1 = new Joystick(InputMap.MINIPJOY_1);
    private final Joystick MINIPJOY_2 = new Joystick(InputMap.MINIPJOY_2);
    private DriverStation driverStation = DriverStation.getInstance();
    //----------------------//

    //------SUBSYSTEMS------//
    private BallShooter shooter;
    private BallHandler ballHandler;
    private Spinner spinner;
    private Limelight limeLight;
    private WestCoastDrive _drive;
    private Led LEDS = new Led();
    private Lift_Leveler liftLeveler;
    //----------------------//    

    public static enum AutoModes{
       SHOOT, FORWARD, INDEX, BACKWARD
    }

    public static AutoModes mode;

    public static AutoModes getAutoModes(){
      return mode;
    }

    @Override
    public void robotInit() {
      shooter = new BallShooter(autonTimer , MINIPJOY_1, MINIPJOY_2, DRIVEJOY);
      spinner = new Spinner( LEDS , MINIPJOY_1, MINIPJOY_2 );
      limeLight = new Limelight( DRIVEJOY, MINIPJOY_1, MINIPJOY_2, shooter );
      ballHandler = new BallHandler(autonTimer, MINIPJOY_1, MINIPJOY_2, DRIVEJOY);
      _drive = new WestCoastDrive( autonTimer, DRIVEJOY );
      liftLeveler = new Lift_Leveler(driverStation, MINIPJOY_2, DRIVEJOY);
    }

    @Override
    public void robotPeriodic() {
      ballHandler.robotPeriodic();
    }

    @Override
    public void autonomousInit() {
      autonTimer.reset();
      autonTimer.start();
      ballHandler.setBrakeMode();
      shooter.autonomousInit();
      _drive.autonomousInit();
      limeLight.lightOn();
      mode = AutoModes.FORWARD;
    }

    @Override
    public void autonomousPeriodic(){ 
        limeLight.lightOn();
        limeLight.limePeriodic();
        _drive.autonomousPeriodic();
        
    }

    @Override
    public void teleopInit() {
      ballHandler.setBrakeMode();
      _drive.teleopInit();
      spinner.teleopInit();
      shooter.setRPM(3100);
      limeLight.lightOn();
    }

    @Override
    public void teleopPeriodic() {

      if(ballHandler.readyforball()){
        //LEDS.setColor(0.77);
      }else if(driverStation.getMatchTime() <= 45.0d){
        LEDS.setColor(0.61);
      } else {
        LEDS.setColor(0.41);
      }
      
      _drive.teleopPeriodic();
      shooter.teleopPeriodic();
      ballHandler.telopPeriodic();
      liftLeveler.telopPeriodic();
      spinner.teleopPeriodic();
      limeLight.limePeriodic();
      
      
      SmartDashboard.putNumber("RPM", shooter.getCurrentRPM());
      

    }

    public void disabledInit(){
      ballHandler.setCoastMode();
      _drive.disabledInit();
      shooter.disabledInit();
      limeLight.lightOff();

    }

    public void testInit(){

        mode = AutoModes.SHOOT;
        limeLight.lightOn();
        limeLight.realClose();
        _drive.shootingChallengeInit();
        shooter.shootingChallengeInit();
        shotTimer.reset();
    }

    private Timer shotTimer = new Timer();

    public void testPeriodic(){

      //System.out.println(mode);

      _drive.printRightEncoder();

      _drive.shootingChallenge();
      shooter.shootingChallenge(shotTimer);
      ballHandler.shootingChallenge(shooter.firstShoot, shotTimer);

      switch(Robot.getAutoModes()){

        case INDEX:
        LEDS.setColor(0.71);
        if(MINIPJOY_1.getRawButton(1) && MINIPJOY_2.getRawButton(1) ){
          Robot.mode = Robot.AutoModes.BACKWARD;
        }
        break;

        case SHOOT:
        LEDS.setColor(0.61);
        limeLight.limePeriodic();
        break;

        case FORWARD:
        LEDS.setColor(0.91);
        //limeLight.limePeriodic();
        
        break;

        case BACKWARD:
        LEDS.setColor(0.91);
        limeLight.limePeriodic();
        break;

      }

    }

}