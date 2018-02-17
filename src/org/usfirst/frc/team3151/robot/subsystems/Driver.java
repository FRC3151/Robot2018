package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.Constants;
import org.usfirst.frc.team3151.robot.RobotMap;

public class Driver {
	
	public double getSpeed() {
		return -RobotMap.driver.getY() * getModifier();
	}
	
	public double getRotation() {
		return RobotMap.driver.getX();
	}
	
	public boolean quickTurn() {
		return RobotMap.driver.getTrigger();
	}
	
	public double getModifier() {
		// Cap speed controller input to compensate for weight of robot not allowing robot to move at lower inputs.
		double rawModifier = (-RobotMap.driver.getZ() + 1) / 2.0D;
		double modifier = Constants.MINIMUM_DRIVE_SPEED_MODIFIER + (rawModifier * (1.0D - Constants.MINIMUM_DRIVE_SPEED_MODIFIER));
		return modifier;
	}
	
}