/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private int shots = 0;

  private final Joystick m_stick         = new Joystick      ( 0       );
  private final Timer m_timer            = new Timer         (         );
  private final WestCoastDrive m_drive   = new WestCoastDrive( m_timer );
  private final BallCollector  collector = new BallCollector ( m_timer );
  private final Crane crane              = new Crane         ( m_timer );
  private final Hopper hopper            = new Hopper        ( m_timer );
  private final Shooter shooter          = new Shooter       ( m_timer );
  private final Spinner spinner          = new Spinner       ( m_timer );
  private ColorState targetColor             = ColorState.UKNOWN;

  public ColorState color;

  enum autonomousStep {
    STEP_1,  // Move forward 90 feet
    STEP_2,  // rotate -90 degrees
    STEP_3,  // move forward 5 feet
    STEP_4,  // rotate 90 degrees to shoot balls
    STEP_5,  // align shot
    STEP_6,  // shoot 3 balls
    STEP_7
  }
    private autonomousStep step = autonomousStep.STEP_1;
  /*
   *
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    collector.robotInit();
    crane    .robotInit();
    hopper   .robotInit();
    shooter  .robotInit();
    spinner  .robotInit();
    m_drive  .robotInit();
  }
  /*
   *
   * This function is called periodically no matter the mode.
   */
  @Override
  public void robotPeriodic() {
    collector.robotPeriodic();
    crane    .robotPeriodic();
    hopper   .robotPeriodic();
    shooter  .robotPeriodic();
    spinner  .robotPeriodic();
    m_drive  .robotPeriodic();
  }
  /*
   *
   * This function is run once each time the robot enters autonomous mode.
   */
  @Override
  public void autonomousInit() {
    collector.autonomousInit();
    crane    .autonomousInit();
    hopper   .autonomousInit();
    shooter  .autonomousInit();
    spinner  .autonomousInit();
    m_drive  .autonomousInit();

    m_timer.reset();
    m_timer.start();
    step = autonomousStep.STEP_1;
  }
  /*
   *
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    collector.autonomousPeriodic();
    crane    .autonomousPeriodic();
    hopper   .autonomousPeriodic();
    shooter  .autonomousPeriodic();
    spinner  .autonomousPeriodic();
    m_drive  .autonomousPeriodic();

    switch ( step )
    {
      case STEP_1:  
        m_drive.autonomousMove( 90.0d );
        step = autonomousStep.STEP_2;
        break; 
      case STEP_2:  
        if (m_drive.atLocation())
        {
          m_drive.autonomousRotate( -90.0d );
          step = autonomousStep.STEP_3;
        }
        break;
      case STEP_3:  
        if (m_drive.atAngle())
        {
          m_drive.autonomousMove( 5.0d );
          shooter.idle();
          step = autonomousStep.STEP_4;
        }
        break;
      case STEP_4: 
      if (m_drive.atLocation())
        {
          m_drive.autonomousRotate( 90.0d );
          shooter.set(1000);
          step = autonomousStep.STEP_5;
        }
        break; 
      case STEP_5:
      if (m_drive.atAngle())
        {
          if ( shots < 2 )
          {
            hopper.eject();
            shots++;
          }
          step = autonomousStep.STEP_6;
        }
        break; 
      case STEP_6:
          shooter.idle();
          step = autonomousStep.STEP_7;
          break;
      case STEP_7:
      }
    }

  /*
   *
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
    collector.teleopInit();
    crane    .teleopInit();
    hopper   .teleopInit();
    shooter  .teleopInit();
    spinner  .teleopInit();
    m_drive  .teleopInit();
  }

    /*
    *
    * This function is called periodically during teleoperated mode.
    */
    @Override
    public void teleopPeriodic() {
        collector.teleopPeriodic();
        crane    .teleopPeriodic();
        hopper   .teleopPeriodic();
        shooter  .teleopPeriodic();
        spinner  .teleopPeriodic();
        m_drive  .teleopPeriodic();

        m_drive.arcadeDrive(m_stick.getY(), m_stick.getX());
        String gameData = DriverStation.getInstance().getGameSpecificMessage();

        if(gameData.length() > 0)
        {
          switch (gameData.charAt(0))
          {
            case 'B' :
                targetColor = ColorState.BLUE;
                break;
            case 'G' :
                targetColor = ColorState.GREEN;
                break;
            case 'R' :
                targetColor = ColorState.RED;
                break;
            case 'Y' :
                targetColor = ColorState.YELLOW;
                break;
            default :
                targetColor = ColorState.UKNOWN;
                break;
          }
        }
        if ( m_stick.getRawButton(1) )
        {
          spinner.select( targetColor );  
        }
  }
  /*
   *
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    collector.testPeriodic();
    crane    .testPeriodic();
    hopper   .testPeriodic();
    shooter  .testPeriodic();
    spinner  .testPeriodic();
    m_drive  .testPeriodic();
  }
  /*
   *
   * This function shuts off all robot movement.
   */
  @Override
  public void disabledInit() {
    collector.disabledInit();
    crane    .disabledInit();
    hopper   .disabledInit();
    shooter  .disabledInit();
    spinner  .disabledInit();
    m_drive  .disabledInit();
  }
  /*
   *
   * This function handles periodic updates in a disabled state.
   */
  @Override
  public void disabledPeriodic() {
    collector.disabledPeriodic();
    crane    .disabledPeriodic();
    hopper   .disabledPeriodic();
    shooter  .disabledPeriodic();
    spinner  .disabledPeriodic();
    m_drive  .disabledPeriodic();
  }
}