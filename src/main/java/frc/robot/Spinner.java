package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

class Spinner {
    private final Timer m_timer;
 
    private final I2C.Port      i2cPort           = I2C.Port.kOnboard;
    private final WPI_TalonSRX  _colrWheel        = new WPI_TalonSRX(30);
 
    private final ColorSensorV3 m_colorSensor  = new ColorSensorV3(i2cPort);
    private final ColorMatch    m_colorMatcher = new ColorMatch();
 
    private final Color kBlueTarget   = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget  = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget    = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private boolean colorDone    = false;
    private String  colorCurrent = "Unknown";
    private String  colorStart   = "Unknown";
    private String  colorLast    = "Unknown";
    private int     colorCount   = 0;
    private int     colorMode    = 0;

    /*
     *
     * This function is called periodically during test mode.
     */
    public Spinner(final Timer ref_timer) {
        m_timer = ref_timer;
        m_timer.get();
    }

    /*
     *
     * This function is called periodically during test mode.
     */
    public void robotInit() {
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }

    /*
     *
     * This function is called periodically no matter the mode.
     */
    public void robotPeriodic() {
        final Color detectedColor = m_colorSensor.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
        final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    
        if (match.color == kBlueTarget) {
            colorCurrent = "Blue";
        } else if (match.color == kRedTarget) {
            colorCurrent = "Red";
        } else if (match.color == kGreenTarget) {
            colorCurrent = "Green";
        } else if (match.color == kYellowTarget) {
            colorCurrent = "Yellow";
        } else {
            colorCurrent = "Unknown";          
        }
        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the 
         * sensor.
         */
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorCurrent);
    }
    /*
     *
     * This function is run once each time the robot enters autonomous mode.
     */
    public void autonomousInit() {
        _colrWheel.set(0);
    }
    /*
     *
     * This function is called periodically during autonomous.
     */
    public void autonomousPeriodic() {
    }
    
    /*
     *
     * This function is called once each time the robot enters teleoperated mode.
     */
    public void teleopInit() {
    }
    /*
     *
     * This function is called periodically during teleoperated mode.
     */
    public void teleopPeriodic() {
        if ( colorMode == 1 )
        {
            //
            // Has The color changed?
            //
            //
            if ( ( colorLast != colorCurrent ) && ( colorCurrent != "Unknown" ) )
            {
                //
                // Has The color changed?
                //
                //
                if ( colorCurrent == colorStart )
                {
                    colorCount++;
                    /*
                    *
                    * Have we counted it enough times?
                    */
                    if ( colorCount >= 8 )
                    {
                        colorDone = true;
                        colorMode = 0;
                        _colrWheel.set( 0.0 ); // Done!
                    }
                }
            }
            colorLast = colorCurrent;
        }
        else if ( colorMode == 2 )
        {
            /*
            *
            * Did we get the color we wanted?
            */
            if ( colorCurrent == colorLast )
            {
                colorDone = true;
                colorMode = 0;
                _colrWheel.set( 0.0 ); // Done!
            }
        }
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void testPeriodic() {
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledInit() {
    }
    /*
     *
     * This function is called periodically during test mode.
     */
    public void disabledPeriodic() {
    }
    /*
     *
     * This function rotates the spinner 4 times.
     */
    public void rotate(){
        colorStart = colorCurrent;
        colorLast  = colorCurrent;
        colorCount = 0;
        colorDone  = false;
        colorMode  = 1;
        _colrWheel.set(0.20);
    }
    /*
     *
     * This function positions the spinner to a particular color.
     */
    public void select( final String target) {
        colorStart = target;
        colorDone  = false;
        colorMode  = 2;
        _colrWheel.set(0.20);
    }
    /*
     *
     * Returns true if done selecting color
     */
    public boolean doneSelecting()
    {
        if ( colorMode == 2 )
        {
            return colorDone;
        }
        return false;
    }
  }