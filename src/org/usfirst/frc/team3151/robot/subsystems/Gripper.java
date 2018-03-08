package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Gripper {

	public void intake(double left, double right) {
		RobotMap.intakeLeft.set(ControlMode.PercentOutput, left);
		RobotMap.intakeRight.set(ControlMode.PercentOutput, right);
	}
	
	public void eject() {
		intake(-1, -1);
	}
	
}