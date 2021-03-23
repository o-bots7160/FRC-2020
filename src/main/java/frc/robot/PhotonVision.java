package frc.robot;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;

public class PhotonVision {

    private PhotonCamera camera;
    private PhotonPipelineResult currentResult;

    public PhotonVision(){
        camera = new PhotonCamera("Camera");
    }

    private void updatePhoton(){
        currentResult = camera.getLatestResult();
    }
    
}
