/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {

    Timer ref_Timer = new Timer();

    WestCoastDrive _drive = new WestCoastDrive(ref_Timer);
  
    Joystick _joy1 = new Joystick(0);

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
    public void teleopInit(){
      _drive.stop();


    }
  @Override
  public void teleopPeriodic(){

    
      _drive.arcadeDrive(_joy1.getY(), _joy1.getZ());
  }
}
