
package frc.robot;

import edu.wpi.first.wpilibj.Spark;


public class Led {
  private final Spark LEDs = new Spark(RobotMap.LEDs); 

  public void  setColor( double PWMValue ) {
    LEDs.set(PWMValue);
}

}
