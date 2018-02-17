package org.usfirst.frc.team3151.robot.subsystems;

import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraController {

	   public CameraController() {
		   //CameraServer.getInstance().startAutomaticCapture();
	       //configCamera("Overhead", "/dev/video0", 30);
	       //configCamera("Overhead2", "/dev/video1", 15);
	    }

	    private void configCamera(String name, String path, int fps) {
	        VideoCamera camera = CameraServer.getInstance().startAutomaticCapture(name, path);

	        camera.setFPS(fps);
	        camera.setResolution(320, 240);

	        // these 3 settings don't particularly matter so long as they stay the same
	        camera.setBrightness(35);
	        camera.setExposureManual(35);
	        camera.setWhiteBalanceManual(4_500);
	    }
	   
}