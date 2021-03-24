package frc.robot;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;

public class PhotonVision {

    private PhotonCamera camera;
    private PhotonPipelineResult currentResult;
   // private Led LEDS = new Led();

    public PhotonVision(){
        camera = new PhotonCamera("Camera");
    }

    public void updatePhoton(){
        currentResult = camera.getLatestResult();
        camera.setDriverMode(true);
        camera.setPipelineIndex(0);
        if ( currentResult.hasTargets()){
           System.out.println("x"); 
        }else{
            System.out.println("Y"); 
        }
        }
    }

    

