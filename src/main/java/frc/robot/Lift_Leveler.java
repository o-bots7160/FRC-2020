package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class Lift_Leveler{

    private Joystick MINIPJOY_2;
    private Joystick DRIVEJOY;
    private TalonFX Lift = new TalonFX(RobotMap._endLift);
    private TalonSRX Levler = new TalonSRX(RobotMap._leveler);
    private Servo endLiftServo = new Servo(RobotMap._endLiftServo);

    //private final 

    public Lift_Leveler(Joystick MINIPJOY_2, Joystick DRIVEJOY){
        this.MINIPJOY_2 = MINIPJOY_2;
        this.DRIVEJOY = DRIVEJOY;
    }


    // SERVO INGAGE VALUE 0.03 || SERVO DISINGAGE VALUE 0.0

    public void telopPeriodic(){

        // LIFT
        if(MINIPJOY_2.getRawButton(InputMap.LIFT_UP)){
            Lift.set(TalonFXControlMode.PercentOutput, 0.25d);
        }else if(MINIPJOY_2.getRawButton(InputMap.LIFT_DOWN)){
            Lift.set(TalonFXControlMode.PercentOutput, -0.25d);
        }else{
            Lift.set(TalonFXControlMode.PercentOutput, 0.0d);
        }

        // LEVLER
        if(MINIPJOY_2.getRawButton(InputMap.LEVELER_GREEN)){
            Levler.set(TalonSRXControlMode.PercentOutput, 0.25);
        }else if(MINIPJOY_2.getRawButton(InputMap.LEVELER_RED)){
            Levler.set(TalonSRXControlMode.PercentOutput, -0.25);
        }else{
            Levler.set(TalonSRXControlMode.PercentOutput, 0.0);
        }

        // END LIFT SERVO
        if(DRIVEJOY.getRawButton(InputMap.INGAGE_RATCHET)){
            endLiftServo.set(0.03);
        }else if(DRIVEJOY.getRawButton(InputMap.DISINGAGE_RATCHET)){
            endLiftServo.set(0.0);
        }

    }

}