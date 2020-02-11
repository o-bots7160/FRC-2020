
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
    switch ( newColor ) {
      case Rainbow:
        LEDs.set( -0.97 );
        break;
      case Red :
        LEDs.set( 0.61 );
        break;
      case SkyBlue:
        LEDs.set( 0.83 );
        break;
      case White:
        LEDs.set( 0.93 );
        break; 
      case Green:
        LEDs.set( 0.77 );
        break; 
      case Yellow:
        LEDs.set( 0.69 );
        break;
      case ColorGradient:
        LEDs.set( 0.41 );
        break;
      default:
        LEDs.set( 0.41 );
      break;
  }
}

}
