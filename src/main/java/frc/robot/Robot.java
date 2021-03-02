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
    }

    @Override
    public void autonomousPeriodic(){ 
        limeLight.lightOn();
        limeLight.limePeriodic();
        if(autonTimer.get() <= 4){
            limeLight.realClose();
            shooter.autonomousPeriodic();
            ballHandler.autonomousPeriodic();
        }else{
            limeLight.frontPanel();
            _drive.autonomousPeriodic();
            ballHandler.autonomousPeriodic();
            shooter.autonomousPeriodic();
        }
      LEDS.setColor(0.41);
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
      _drive.testInit();
    }

    public void testPeriodic(){
        _drive.testing();
    }

}