package frc.robot;

public class Toggle {
    private boolean status;
    private boolean last;
    public Toggle() {
        status = false;
    }
    public void set( boolean value ) {
        if ( last != value ) {
            if (value ) {
                status = ! status;
            }
        }
        last = value;
    }
    public boolean isOn() {
        return status;
    }
    
}