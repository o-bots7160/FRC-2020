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

    private enum SpinnerModes { SPINNER_IDLE, SPINNER_ROTATE, SPINNER_SELECT };

    private final I2C.Port      i2cPort        = I2C.Port.kOnboard;
    private final WPI_TalonSRX  _colrWheel     = new WPI_TalonSRX(RobotMap._colrWhelID);
 
    private final ColorSensorV3 m_colorSensor  = new ColorSensorV3(i2cPort);
    private final ColorMatch    m_colorMatcher = new ColorMatch();
 
    private final Color kBlueTarget   = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget  = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget    = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private ColorState  colorCurrent = ColorState.UKNOWN;
    private ColorState  colorStart   = ColorState.UKNOWN;
    private ColorState  colorLast    = ColorState.UKNOWN;
    private int     colorCount   = 0;
    SpinnerModes     spinnerMode    = SpinnerModes.SPINNER_IDLE;
    private int     changeCount  = 0;


    /*
     *
     * This function is called periodically during test mode.
     */
    public Spinner( ) {
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
        ColorState tempColor = ColorState.UKNOWN;

        /**
         * Run the color match algorithm on our detected color
         */
        final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
    
        if ( match.color == kBlueTarget ) {
            tempColor = ColorState.BLUE;
        } else if ( match.color == kRedTarget ) {
            tempColor = ColorState.RED;
        } else if ( match.color == kGreenTarget ) {
            tempColor = ColorState.GREEN;
        } else if ( match.color == kYellowTarget ) {
            tempColor = ColorState.YELLOW;
        } else {
            tempColor = ColorState.UKNOWN;          
        }
        //
        //  See if we want to say the color changed
        //
        //
        if ( tempColor != ColorState.UKNOWN )
        {
            if ( tempColor != colorCurrent ) {
                changeCount++;
                if ( changeCount > 8 ) {
                    colorCurrent = tempColor;
                    changeCount  = 0;
                }
            } else {
                changeCount = 0;
            }
        }
        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the 
         * sensor.
         */
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorCurrent.toString());
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

        switch (spinnerMode) {
            case SPINNER_IDLE:
                // Nothing to see here
                break;
        
            case SPINNER_ROTATE:
                //
                // Has The color changed?
                //
                //
                if ( colorLast != colorCurrent )
                {
                    colorLast = colorCurrent;
                    //
                    // Have we reached the starting color again?
                    //
                    //
                    if ( colorCurrent == colorStart )
                    {
                        colorCount++;
                        //
                        //  Have we counted the starting color enough times?
                        //
                        //
                        if ( colorCount >= 8 )
                        {
                            spinnerMode = SpinnerModes.SPINNER_IDLE;
                            _colrWheel.set( 0.0 ); // Done!
                        }
                    }
                }
                else
                {
                    changeCount = 0;
                }
                break;

            case SPINNER_SELECT:
                /*
                *
                * Did we get the color we wanted?
                */
                if ( colorCurrent == colorStart )
                {
                    spinnerMode = SpinnerModes.SPINNER_IDLE;
                    _colrWheel.set( 0.0 ); // Done!
                }
                break;
        }
        SmartDashboard.putNumber("color count", colorCount );
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
        colorStart  = colorCurrent; // TODO: should we check if it is currently "Unknown"?
        colorLast   = colorCurrent;
        colorCount  = 0;
        spinnerMode   = SpinnerModes.SPINNER_ROTATE;
        changeCount = 0;
        _colrWheel.set(0.35);
    }
    /*
     *
     * This function positions the spinner to a particular color.
     */
    public void select( final ColorState target) {
        colorStart  = target;
        spinnerMode   = SpinnerModes.SPINNER_SELECT;
        changeCount = 0;
        _colrWheel.set(0.35);
        SmartDashboard.putString("Selected Color", colorStart.toString());
    }
    /*
     *
     * Returns true if done selecting color
     */
    public boolean done()
    {
        if ( spinnerMode == SpinnerModes.SPINNER_IDLE )
        {
            return false;
        }
        return true;
    }
    public ColorState current( ){
        return colorCurrent;
    }
}