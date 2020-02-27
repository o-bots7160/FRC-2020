package frc.robot;

public class InputMap{

    // Dead band
    public static boolean DeadBand(double lowerLimit, double input){

        if(Math.abs(input) >= lowerLimit)
            return true;
        else
            return false;
    }

    // Input systemI
    public static final int DRIVEJOY            =   0;
    public static final int MINIPJOY_1           =   1;
    public static final int MINIPJOY_2           =   2;
    //public static final int 

    // Speeds

    public static final double SPEED_Y          = 0.5d;
    public static final double SPEED_Z          = 0.5d;

    // Drive joy axises
    public static final int DRIVEJOY_X          =    0;
    public static final int DRIVEJOY_Y          =    1;
    public static final int DRIVEJOY_Z          =    2;
    //

    // MINPJOY_1 joy buttons
    public static final int LIMELIGHT_ON        =    7;
    public static final int SHOOTBUTTON         =    6;
    public static final int INTAKE_IN           =    5;
    public static final int INTAKE_OUT          =    4;
    public static final int UPPER_HOPPER_UP     =    3;
    public static final int LOWER_HOPPER_UP     =    2;
    public static final int BACKFEED            =    1;

    // MINPJOY_2 Joy buttons
    public static final int LIFT_UP             =    1;
    public static final int LIFT_DOWN           =    2;
    public static final int INGAGE_RATCHET      =    3;
    public static final int DISINGAGE_RATCHET   =    4;
    public static final int COLOR_4_TIMES       =    5;
    public static final int COLOR_POSITION      =    6;
    public static final int COLOR_AUTO          =    7;

    

}