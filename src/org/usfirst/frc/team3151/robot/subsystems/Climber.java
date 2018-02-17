package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

public class Climber {

	public void setPower(double power) {
		RobotMap.climber.set(power);
	}
	
}