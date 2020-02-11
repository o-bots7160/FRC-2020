
package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;


public class Robot extends TimedRobot {
  private final Spark LEDs = new Spark(0);
  private final Joystick joy1 = new Joystick(0);
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
      LEDs.set(-0.97);
    }else if (joy1.getRawButton(4))  {
      LEDs.set(0.61);
     }else if (joy1.getRawButton(5))  {
        LEDs.set(0.93);
    }else if (joy1.getRawButton(6))  {
      LEDs.set(0.83);
    }else if (!limitSwitch1.get()){
      LEDs.set(0.83);
    }else{
      LEDs.set(0.41);
    }

    if(Math.abs(joy1.getRawAxis(2)) >= 0.3){
      
    }

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
