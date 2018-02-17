package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

public class DriveTrain {

	public void curvature(double speed, double rotation, boolean quickTurn) {
		RobotMap.robotDrive.curvatureDrive(speed, rotation, quickTurn);
	}
	
	public void tank(double left, double right) {
		RobotMap.robotDrive.tankDrive(left, right, false);
	}
	
}