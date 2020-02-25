package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.DriverStation;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

class Spinner {

    private enum SpinnerModes   { SPINNER_IDLE, SPINNER_LIFT, SPINNER_SETUP, SPINNER_ROTATION, SPINNER_POSITION };
    private enum ColorState     { BLUE, RED, GREEN, YELLOW, UKNOWN};
    private final I2C.Port      i2cPort        = I2C.Port.kOnboard;
    private final WPI_VictorSPX  _colrWheel     = new WPI_VictorSPX(RobotMap._colrWhel);
    private Joystick    _joystick;
 
    private final ColorSensorV3 m_colorSensor  = new ColorSensorV3(i2cPort);
    private final ColorMatch    m_colorMatcher = new ColorMatch();
 
    //-------RGBColorValues-------//
    private final Color kBlueTarget   = ColorMatch.makeColor(0.135, 0.465, 0.380);
    private final Color kGreenTarget  = ColorMatch.makeColor(0.175, 0.585, 0.232);
    private final Color kRedTarget    = ColorMatch.makeColor(0.465, 0.400, 0.160);
    private final Color kYellowTarget = ColorMatch.makeColor(0.300, 0.575, 0.128);
    //----------------------------//

    private ColorState  validColor  = ColorState.UKNOWN; //Value of color to count
    private ColorState  tempColor    = ColorState.UKNOWN;
    private ColorState  fmsColor     = ColorState.UKNOWN;
    private int         colorCount      = 0;
    private int         gameStage       = 0;
    SpinnerModes        spinnerMode    = SpinnerModes.SPINNER_IDLE;

 


    public Spinner( Joystick _joy ) {
        
        _joystick = _joy;
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);

    }

    public void teleopInit(){
        spinnerMode = SpinnerModes.SPINNER_IDLE;
        _colrWheel.setNeutralMode(NeutralMode.Brake);
        
    }

    public void teleopPeriodic() {

            //TODO Change this to correct buttons when control board is done
            if(_joystick.getRawButton(3)){//Button to tell Spinner system to run rotation control
                gameStage = 2;
                spinnerMode = SpinnerModes.SPINNER_LIFT;

            }else if(_joystick.getRawButton(4)){//Button to tell Spinner system to run position control
                gameStage = 3;
                spinnerMode = SpinnerModes.SPINNER_LIFT;
            }else if(_joystick.getRawButton(1)){//Button to tell Spinner system to go to idle mode
                gameStage = 0;
                spinnerMode = SpinnerModes.SPINNER_IDLE;
            }

/*
*    Switch statement to control the different parts of the Spinner Control panel manipulator  
*/            
            switch (spinnerMode) {
                
/*
*   Sets the spinner components back to and idle state   
*/                
                case SPINNER_IDLE: //default position
                    _colrWheel.stopMotor();
                    colorCount = 0;
                    tempColor = ColorState.UKNOWN;
                    
                break;
                 
/*
*   Rotates the arm into position   
*/                
                case SPINNER_LIFT: // move arm in to position
                    //TODO Rotate arm in to position
                    spinnerMode = SpinnerModes.SPINNER_SETUP;
                   
                break;

/*
*   Decides which mode we are in   
*/
                case SPINNER_SETUP: //Checks if valid color and which part of game based on FMS data
                    colorMatching();
                    validColor = tempColor;
                    if(validColor != ColorState.UKNOWN){ //Checking to make sure the color is good then go to next step
                        if( gameStage == 2 ){
                            spinnerMode = SpinnerModes.SPINNER_ROTATION;
                        }else if ( gameStage == 3 ) {
                            spinnerMode = SpinnerModes.SPINNER_POSITION;  
                        }
                    }
                break;

/*
*   Rotation control   
*/
                case SPINNER_ROTATION: //Rotate 4 times
                    if (colorCount <= 32){ //rotate until color has changed 32 times = 4 full rotations
                        _colrWheel.set(.75); //This may be to fast??
                        
                        colorMatching();

                        if(validColor != tempColor && tempColor != ColorState.UKNOWN){ //If the color has actually changed count it and set new valid color
                            colorCount ++; //Add one for our color change
                            validColor = tempColor;  //Set the new color as Valid  
                        }
                    }else{
                        spinnerMode = SpinnerModes.SPINNER_IDLE;  
                    }

                    break;

/*
*   Position control
*/
                case SPINNER_POSITION:
                String gameData;
        
                gameData = DriverStation.getInstance().getGameSpecificMessage();
                    
                if(gameData.length() > 0){
//These have a 2 color offset to match the FMS sensor location
                    if ( gameData.charAt(0) == 'B') fmsColor = ColorState.RED;
                    if ( gameData.charAt(0) == 'R') fmsColor = ColorState.BLUE;
                    if ( gameData.charAt(0) == 'G') fmsColor = ColorState.YELLOW;
                    if ( gameData.charAt(0) == 'Y') fmsColor = ColorState.GREEN;

                }else{
                    
                    fmsColor = ColorState.UKNOWN; 
                }
                    
                    
                    colorMatching();

                    if ( fmsColor != tempColor && tempColor != ColorState.UKNOWN ){
                        _colrWheel.set(.4); 
                    }else{
                        _colrWheel.stopMotor();
                    }
                

                }
            SmartDashboard.putNumber("color count", colorCount );
            SmartDashboard.putString("First Color", validColor.toString() );
            SmartDashboard.putString("Current Color", tempColor.toString() );
            SmartDashboard.putString("CASE", spinnerMode.toString() );
            SmartDashboard.putString("FMS Color", fmsColor.toString() );

    }
 
/*
*   This is called to get the current color if it's a valid color
*/
     void colorMatching(){ 
        Color detectedColor = m_colorSensor.getColor();
       
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
                
            if ( match.color == kBlueTarget && match.confidence > 0.93 ) {
                tempColor = ColorState.BLUE;
            } else if ( match.color == kRedTarget  && match.confidence > 0.90 ) {
                tempColor = ColorState.RED;
            } else if ( match.color == kGreenTarget  && match.confidence > 0.94) {
                tempColor = ColorState.GREEN;
            } else if ( match.color == kYellowTarget && match.confidence > 0.96 ) {
                tempColor = ColorState.YELLOW;
            } else {
                tempColor = ColorState.UKNOWN;
        }
        SmartDashboard.putNumber("Match Con", match.confidence);
    }

    
}