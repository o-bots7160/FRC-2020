package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;

class EndGame{
   
    private final WPI_TalonFX _endLift               = new WPI_TalonFX(RobotMap._endLift);
    private final WPI_TalonFX _leveler               = new WPI_TalonFX(RobotMap._leveler);
    private       Joystick    _joystick              = new Joystick(0);
    //needs PID for a set point for the endLift
public EndGame(){
   if (_joystick.getRawButton(10)){
    _endLift.set(.6);
   }else {
        _endLift.set(0);
   }
   if (_joystick.getRawButton(11)){
       _leveler.set(.25);
   } else   if 
      (_joystick.getRawButton(12)){
    _leveler.set(.25 * -1);
   } else { _leveler.set(0);
  }
}
//not final but needs code for the end game








}
