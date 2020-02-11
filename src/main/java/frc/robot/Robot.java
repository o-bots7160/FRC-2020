/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  //private final BallShooter shooter = new BallShooter();
  private final Joystick joy1 = new Joystick(0);
  private final Led      _led      =  new Led();
  private final DigitalInput limitSwitch1 = new DigitalInput(0);

  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    if(joy1.getRawButton(3)){
      _led.setColor(ColorMap.Rainbow);
    }else if (joy1.getRawButton(4))  {
      _led.setColor(ColorMap.Red);
     }else if (joy1.getRawButton(5))  {
      _led.setColor(ColorMap.SkyBlue);
    }else if (joy1.getRawButton(6))  {
      _led.setColor(ColorMap.White);
    }else if (joy1.getRawButton(7))  {
        _led.setColor(ColorMap.Green);
    }else if (joy1.getRawButton(8))  {
        _led.setColor(ColorMap.Yellow);
    }else if (!limitSwitch1.get()){
      _led.setColor(ColorMap.SkyBlue);
    }else{
      _led.setColor(ColorMap.ColorGradient);
    }

    //shooter.teleopPeriodic();

    //System.out.println("Current RPM : " + shooter.getCurrentRPM());
    
    /*if (targetInSight) {
      _diffDrive.arcadeDrive((_joystick.getY()*-1)*0.5, ( _joystick.getZ() + ( horizAngle / 27.0d) ));
    } else {
      _diffDrive.arcadeDrive((_joystick.getY()*-1)*0.5, _joystick.getZ()*0.75);
    }*/
    //if(joy1.getRawButton(1)) {
    //  shooter.setRPM( 4000 *( -1 * joy1.getY()));
    //} else {
    //  shooter.setRPM(0);
    //}
  }    
}
