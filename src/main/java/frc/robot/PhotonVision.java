package frc.robot;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;
import org.photonvision.PhotonTrackedTarget;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.WestCoastDrive;

class PhotonVision {

    private PhotonCamera camera;
    private PhotonPipelineResult currentResult;
    private double x;
    private double area;
    private WestCoastDrive drive;
    private double kP = 0.02d;
    private double kI = 0.000005d;
    private double kD = 0.0d;
    private PIDController photonAngle = new PIDController(kP, kI, kD);
    private boolean hasTarget = false;

    public PhotonVision(WestCoastDrive drive){

        camera = new PhotonCamera("Camera");

        this.drive = drive;
        photonAngle.setSetpoint(0);
    }

    public void updatePhoton(){
        currentResult = camera.getLatestResult();
        //camera.setDriverMode(true);
        camera.setPipelineIndex(0);
        hasTarget = camera.hasTargets();
        if(hasTarget){
            PhotonTrackedTarget currentTarg = currentResult.getBestTarget();
            x = currentTarg.getYaw();
        }
        
    }


    public void teleopPeriodic(){
        updatePhoton();

        if(hasTarget){
            drive.arcadeDrive(0, -photonAngle.calculate(x));
        }
    }

}

    

