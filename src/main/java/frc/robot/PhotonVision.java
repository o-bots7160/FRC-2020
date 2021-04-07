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
    private double kP = 0.015d;
    private double kI = 0.00005d;
    private double kD = 0.0d;
    private PIDController photonAngle = new PIDController(kP, kI, kD);
    public boolean hasTarget = false;

    public PhotonVision(WestCoastDrive drive){

        camera = new PhotonCamera("Camera");

        this.drive = drive;
        
    }

    public boolean updatePhoton(){
        currentResult = camera.getLatestResult();
        //camera.setDriverMode(true);
        camera.setPipelineIndex(0);
        hasTarget = camera.hasTargets();
        if(hasTarget){
            PhotonTrackedTarget currentTarg = currentResult.getBestTarget();
            x = currentTarg.getYaw();
        }

        if(Math.abs(x) > 5){
            return false;
        }else if(Math.abs(x) < 5){
            return true;
        }else{

            return false;
        }
        
    }


    public void teleopPeriodic(){
        updatePhoton();
        
        


    }

}

    

