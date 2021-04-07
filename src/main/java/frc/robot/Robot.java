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
    private PhotonVision photonVision;
    private WestCoastDrive _drive;
    private Led LEDS = new Led();
    private Lift_Leveler liftLeveler;
    //----------------------//    

    public static enum AutoModes{
       SHOOT, FORWARD, INDEX, BACKWARD
    }

    public static AutoModes mode;
    private Timer pathTimer = new Timer();

    public static AutoModes getAutoModes(){
      return mode;
    }

    private enum galaticSearch{
     
      DETERMINECOLOR, // Used to determine path color

       DETERMINEPATH, // Used to determine path letter

        REDPATH_A_BALL2, // Turn and go to ball 2
         REDPATH_A_BALL3, // Turn and go to ball 3
          REDPATH_A_END, // Drive to finish
        // All others do same for respective values
        BLUEPATH_A_BALL2,
          BLUEPATH_A_BALL3,
           BLUEPATH_A_END,

        REDPATH_B_BALL2,
          REDPATH_B_BALL3,
            REDPATH_B_END,

        BLUEPATH_B_BALL2,
          BLUEPATH_B_BALL3,
            BLUEPATH_B_END,

        END

    }

    private galaticSearch galaticMode = galaticSearch.DETERMINECOLOR;

    @Override
    public void robotInit() {
      shooter = new BallShooter(autonTimer , MINIPJOY_1, MINIPJOY_2, DRIVEJOY);
      spinner = new Spinner( LEDS , MINIPJOY_1, MINIPJOY_2 );
      limeLight = new Limelight( DRIVEJOY, MINIPJOY_1, MINIPJOY_2, shooter );
      ballHandler = new BallHandler(autonTimer, MINIPJOY_1, MINIPJOY_2, DRIVEJOY);
      _drive = new WestCoastDrive( autonTimer, DRIVEJOY );
      liftLeveler = new Lift_Leveler(driverStation, MINIPJOY_2, DRIVEJOY);
      photonVision = new PhotonVision(_drive);
      
    }

    @Override
    public void robotPeriodic() {
      ballHandler.robotPeriodic();
    }

    @Override
    public void autonomousInit() {
      galaticMode = galaticSearch.DETERMINECOLOR;
      _drive.autonomousInit();
      red = false;
      blue = false;
      ballHandler.galaticReset();
      pathTimer.start();
      pathTimer.reset();
      timerReset = false;
      hasReset = false;
      secondTimerStart = false;
      secondTimer.reset();
      
    }


    // Distance Error 9192
    boolean red = false;
    boolean blue = false;
    boolean timerReset = false;

    boolean driveDone = false;
    boolean driveForward = false;
    boolean hasReset = false;
    boolean secondTimerStart = false;

    Timer secondTimer = new Timer();

    private boolean driveToDistance(double driveDistance){
      if(!hasReset){
        _drive.resetRightMotor();
        
        _drive.navX.reset();
        System.out.println("I have reset!!!!!!!!!!!!!!!");
        secondTimer.reset();
        secondTimer.start();
        hasReset = true;
      }else{
          if(secondTimer.get() > .5){
            _drive.anglePID.setSetpoint(0);
            if(_drive.getDistance() > driveDistance){
              //System.out.println("I'm driving already!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
              _drive.arcadeDrive(.45, determinePathAngle);
            }else{
              _drive.arcadeDrive(0, 0);
              return true;
            }
        }
      }
      return false;
    }

    // -74491
    double determinePathAngle = .092;

    @Override
    public void autonomousPeriodic(){ 
      if(galaticMode != galaticSearch.END){
        ballHandler.getball();
      }
      //-6436
 
      switch(galaticMode){
        case DETERMINECOLOR:   
        photonVision.updatePhoton();
              if(!blue){
                if(_drive.getDistance() <= -61192){
                    if(!timerReset){
                      timerReset = true;
                      pathTimer.reset();
                    }
                      if(ballHandler.firstBall && pathTimer.get() > 1.25){
                        red = true;
                        System.out.println("RED");
                        galaticMode = galaticSearch.DETERMINEPATH;
                    }else if( pathTimer.get() > 1.25 && !ballHandler.firstBall){
                      System.out.println("BLUE");
                      blue = true;
                      driveForward = false;
                    }
                  }else{
                    double steerCommand = _drive.anglePID.calculate(_drive.navX.getAngle());
                    if(steerCommand > .40){
                        steerCommand = 0.40;
                    }else if(steerCommand < -0.40){
                        steerCommand = -0.40;
                    }
                    _drive.arcadeDrive(0.45, determinePathAngle);
                  }
              
            }else{
                if(_drive.getDistance() > -154067 && !ballHandler.firstBall){
                  
                  _drive.arcadeDrive(0.45, 0);
                }else{
                  galaticMode = galaticSearch.DETERMINEPATH;
                }
              }
            

                break;
        case DETERMINEPATH:
                _drive.arcadeDrive(0, 0);
              if(red){
                if(photonVision.updatePhoton()){
                  galaticMode = galaticSearch.REDPATH_B_BALL2;
                }else{
                   _drive.anglePID.reset();
                  _drive.resetRightMotor();
                  _drive.navX.reset();
                  galaticMode = galaticSearch.REDPATH_A_BALL2;
                  }
              }else if (blue){
                if(photonVision.updatePhoton()){
                  System.out.println("Blue Path B");
                  galaticMode = galaticSearch.BLUEPATH_B_BALL2;
                }else{
                  System.out.println("Blue Path A");
                  galaticMode = galaticSearch.BLUEPATH_A_BALL2;
                }
              } break;
        case REDPATH_A_BALL2:
              
                if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-63940)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.REDPATH_A_BALL3;
                  hasReset = false;
                }

              }else{
                if(_drive.driveAngle(24.5, 0, 5)){
                  driveForward = true;
                }
              }
              System.out.println(driveForward);
              System.out.println("REDPATH_A_BALL2 Angle: " + _drive.navX.getAngle());
              break;
        case REDPATH_A_BALL3:
              System.out.println("REDPATH_A_BALL3");
              if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-75603)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.REDPATH_A_END;
                }

              }else{
                if(_drive.driveAngle(-76, 0, 5)){
                  driveForward = true;
                  hasReset = false;
                  System.out.println("Going to move forward: " + driveForward);

                }
              }
              System.out.println("Angle: " + _drive.navX.getAngle());
              break;
        case REDPATH_A_END:
        //-127305
              if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-127305)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.END;
                }

              }else{
                if(_drive.driveAngle(58, 0, 5)){
                  driveForward = true;
                  hasReset = false;
                  System.out.println("Going to move forward: " + driveForward);

                }
              }
                break;
        case BLUEPATH_A_BALL2:
              //System.out.println("BLUE PATH A BALL 2");
              if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-83460)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.BLUEPATH_A_BALL3;
                }

              }else{
                if(_drive.driveAngle(-58, 0, 5)){
                  driveForward = true;
                  hasReset = false;
                  secondTimer.reset();
                  _drive.resetRightMotor();
                  System.out.println("Going to move forward: " + driveForward);

                }
              }
              System.out.println("BLUEPATH_A_BALL2 Angle: " + _drive.getAngle());
                break;
        case BLUEPATH_A_BALL3:
                System.out.println("BLUE PATH A BALL 3");
                if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-124635)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.BLUEPATH_A_END;
                }

              }else{
                if(_drive.driveAngle(67, 0, 5)){
                  driveForward = true;
                  hasReset = false;
                  secondTimer.reset();
                  _drive.resetRightMotor();
                  System.out.println("Going to move forward: " + driveForward);

                }
              }
                break;
        case BLUEPATH_A_END:
        //Nothing needed here we just drive straight thru in ball three
        galaticMode = galaticSearch.END;
          break;
        case REDPATH_B_BALL2:
        //System.out.println("RED PATH B BALL 2");
        //-83357
               if(driveForward){
                ballHandler.getball();
                if(driveToDistance(-70000)){
                  System.out.println(_drive.getAngle());
                  driveForward = false;
                  galaticMode = galaticSearch.REDPATH_B_BALL3;
                }

              }else{
                System.out.println("REDPATH_B_BALL2: " + _drive.getAngle());
                if(_drive.driveAngle(36, 0, 2)){
                  driveForward = true;
                  hasReset = false;
                  secondTimer.reset();
                  _drive.resetRightMotor();
                  System.out.println("Going to move forward: " + driveForward);

                }
              }
          break;
        case REDPATH_B_BALL3:
        System.out.println("RED PATH B BALL 3");
        //-60270
        if(driveForward){
          ballHandler.getball();
          if(driveToDistance(-64270)){
            System.out.println(_drive.getAngle());
            driveForward = false;
            galaticMode = galaticSearch.REDPATH_B_END;
          }

        }else{
          if(_drive.driveAngle(-60, 0, 5)){
            driveForward = true;
            hasReset = false;
            secondTimer.reset();
            _drive.resetRightMotor();
            System.out.println("Going to move forward: " + driveForward);

          }
        }

         break;
        case REDPATH_B_END:
        //112062
        if(driveForward){
          ballHandler.getball();
          if(driveToDistance(-111000)){
            System.out.println(_drive.getAngle());
            driveForward = false;
            galaticMode = galaticSearch.END;
          }

        }else{
          if(_drive.driveAngle(35, 0, 2)){
            driveForward = true;
            hasReset = false;
            secondTimer.reset();
            _drive.resetRightMotor();
            System.out.println("REDPATH_B_END Going to move forward: " + driveForward);

          }
        }
          break;
        case BLUEPATH_B_BALL2:
        System.out.println("BLUE PATH B BALL 2");
          break;
        case BLUEPATH_B_BALL3:
          break;
        case BLUEPATH_B_END:
          break;

        case END:
        _drive.arcadeDrive(0, 0);
        ballHandler._intake.set(0);
        break;
        
      }

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
      photonVision.updatePhoton();
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
      _drive.resetRightMotor();
       
    }

    private Timer shotTimer = new Timer();

    public void testPeriodic(){

     System.out.println(_drive.getDistance());

    }

}