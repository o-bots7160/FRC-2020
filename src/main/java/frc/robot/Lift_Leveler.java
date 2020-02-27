package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class Lift_Leveler{

    private Joystick MINIPJOY_1;
    private TalonFX Lift = new TalonFX(RobotMap._endLift);
    private Servo endLiftServo = new Servo(RobotMap._endLiftServo);

    //private final 

    public Lift_Leveler(Joystick MINIPJOY_1){
        this.MINIPJOY_1 = MINIPJOY_1;
    }


    public void telopPeriodic(){
        /*if(MINIPJOY_1.getRawButton(InputMap.LIFT_UP)){
            Lift.set(TalonFXControlMode.PercentOutput, 0.25d);
        }else if(MINIPJOY_1.getRawButton(InputMap.LIFT_DOWN)){
            Lift.set(TalonFXControlMode.PercentOutput, -0.25d);
        }else{
            Lift.set(TalonFXControlMode.PercentOutput, 0.0d);
        }*/

        if(MINIPJOY_1.getRawButton(1)){
            endLiftServo.set(0.0);
        }else if(MINIPJOY_1.getRawButton(2)){
            endLiftServo.set(0.03d);
        }/*else if(MINIPJOY_1.getRawButton(InputMap.INGAGE_RATCHET)){
            endLiftServo.set(1.0d);
        }*/
    }

}