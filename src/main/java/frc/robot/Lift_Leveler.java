package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class Lift_Leveler{

    private Joystick MINIPJOY_2;
    private Joystick DRIVEJOY;
    private WPI_TalonFX Lift = new WPI_TalonFX(RobotMap._endLift);
    private WPI_VictorSPX Levler = new WPI_VictorSPX(RobotMap._leveler);
    private Servo endLiftServo = new Servo(RobotMap._endLiftServo);
    private boolean liftAble = true;
    private DriverStation station;

    //private final 

    public Lift_Leveler(DriverStation station, Joystick MINIPJOY_2, Joystick DRIVEJOY){
        this.station = station;
        this.MINIPJOY_2 = MINIPJOY_2;
        this.DRIVEJOY = DRIVEJOY;
    }


    // SERVO INGAGE VALUE 0.03 || SERVO DISINGAGE VALUE 0.0

    public void telopPeriodic(){

        Levler.setNeutralMode(NeutralMode.Brake);
        // LIFT
        if(station.getMatchTime() <= 30.0d){
        if(MINIPJOY_2.getRawButton(InputMap.LIFT_UP) && liftAble){
            Lift.set( 0.45d );
        }else if(MINIPJOY_2.getRawButton(InputMap.LIFT_DOWN)){
            Lift.set( -0.25d );
        }else{
            Lift.set( 0.0d );
        }
        }

        // LEVLER
        if(MINIPJOY_2.getRawButton(InputMap.LEVELER_GREEN)){
            Levler.set( 0.60d );
        }else if(MINIPJOY_2.getRawButton(InputMap.LEVELER_RED)){
            Levler.set( -0.60d );
        }else{
            Levler.set( 0.0d );
        }

        // END LIFT SERVO
        if(DRIVEJOY.getRawButton(InputMap.INGAGE_RATCHET)){
            liftAble = false;
            endLiftServo.set(0.03);
        }else if(DRIVEJOY.getRawButton(InputMap.DISINGAGE_RATCHET)){
            liftAble = true;
            endLiftServo.set(0.0);
        }

    }

}