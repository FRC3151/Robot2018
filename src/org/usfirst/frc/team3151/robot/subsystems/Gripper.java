package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

public class Gripper {

	public void setPower(double power) {
		RobotMap.gripper.set(power);
	}
	
}