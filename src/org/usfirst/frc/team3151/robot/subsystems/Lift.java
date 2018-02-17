package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

public class Lift {

	public void setPower(double power) {
		RobotMap.lift.set(power);
	}
	
}