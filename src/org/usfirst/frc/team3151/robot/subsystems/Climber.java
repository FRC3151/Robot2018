package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climber {

	public void setPower(double power) {
		RobotMap.climber.set(ControlMode.PercentOutput, power);
	}
	
}