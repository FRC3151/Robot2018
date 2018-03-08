package org.usfirst.frc.team3151.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraController {
	
   public void setupStream() {
        VideoCamera camera = CameraServer.getInstance().startAutomaticCapture("Gripper", "/dev/video0");
        camera.setResolution(640, 480);
    }
   
   public void printConnectedCameras() {
	   for (UsbCameraInfo camInfo : UsbCamera.enumerateUsbCameras()) {
		   System.out.println(camInfo.name + " is device " + camInfo.dev + " on path " + camInfo.path);
	   }
   }
	   
}