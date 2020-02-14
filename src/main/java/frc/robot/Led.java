
package frc.robot;

import edu.wpi.first.wpilibj.Spark;


public class Led {
  private final Spark LEDs = new Spark(0); 

    
  public void robotInit() {
  }

  
  public void autonomousInit() {
  }

  public void autonomousPeriodic() {
  }

  
  public void teleopInit() {
  }

  
  public void teleopPeriodic() {
  }

  
  public void testInit() {
  }

  
  public void testPeriodic() {
  }
  public void  setColor( ColorMap newColor ) {
    LEDs.set(newColor.getColor());
}

}
