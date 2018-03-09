package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.DeadzoneUtils;
import org.usfirst.frc.team3151.robot.RobotMap;

public class Driver {
	
	public double speed() {
		double raw = RobotMap.driver.getY();
		double deadzoned = DeadzoneUtils.deadzone(raw, 0.04);
		
		// negate the entire thing because joysticks have -1 at the top and +1 at the bottom
		return -deadzoned;
	}
	
	public double rotation() {
		double raw = RobotMap.driver.getX();
		double deadzoned = DeadzoneUtils.deadzone(raw, 0.04);
		
		return deadzoned; 
	}
	
	public boolean quickTurn() {
		return RobotMap.driver.getTrigger();
	}
	
	public boolean portalIntent() {
		return RobotMap.driver.getRawButton(2);
	}
	
}