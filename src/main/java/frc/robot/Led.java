
package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;


public class Led {
  private final Spark LEDs = new Spark(0);
  private final Joystick joy1 = new Joystick(0);
  private final DigitalInput limitSwitch1 = new DigitalInput(0);
 
  
  public void robotInit() {
  }

  
  public void autonomousInit() {
  }

  public void autonomousPeriodic() {
  }

  
  public void teleopInit() {
  }

  
  public void teleopPeriodic() {
    
    if(joy1.getRawButton(3)){
      LEDs.set(ColorMap.Rainbow);
    }else if (joy1.getRawButton(4))  {
      LEDs.set(ColorMap.Red);
     }else if (joy1.getRawButton(5))  {
        LEDs.set(ColorMap.SkyBlue);
    }else if (joy1.getRawButton(6))  {
      LEDs.set(ColorMap.White);
    }else if (!limitSwitch1.get()){
      LEDs.set(ColorMap.SkyBlue);
    }else{
      LEDs.set(ColorMap.ColorGradient);
    }

    if(Math.abs(joy1.getRawAxis(2)) >= 0.3){
      
    }

  }

  
  public void testInit() {
  }

  
  public void testPeriodic() {
  }

}
