
package frc.robot;

import edu.wpi.first.wpilibj.Spark;


public class Led {
  private final Spark LEDs = new Spark(0); 

  public void  setColor( double PWMValue ) {
    LEDs.set(PWMValue);
}

}
