package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.Constants;
import org.usfirst.frc.team3151.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Operator {

	public double getLift() {
		if (RobotMap.operator.getYButton()) {
			return Constants.LIFT_UP_POWER;
		} else if (RobotMap.operator.getAButton()) {
			return Constants.LIFT_DOWN_POWER;
		} else {
			return 0.0;
		}
	}
	
	public double getGripper() {
		return deadzone(RobotMap.operator.getY(Hand.kRight), 0.1) / 3;
	}
	
	public double getClimber() {
		return deadzone(RobotMap.operator.getY(Hand.kLeft), 0.1);
	}
	
	public double deadzone(double value, double deadband) {
		if (Math.abs(value) > deadband) {
			if (value > 0.0) {
				return (value - deadband) / (1.0 - deadband);
			} else {	
				return (value + deadband) / (1.0 - deadband);
			}
	    } else {
			return 0.0;
		}
	}
	
	public boolean getSpecialLed1() {
		return RobotMap.operator.getBackButton();
	}
	
	public boolean getSpecialLed2() {
		return RobotMap.operator.getStartButton();
	}
	
}