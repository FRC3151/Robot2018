package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.DeadzoneUtils;
import org.usfirst.frc.team3151.robot.RobotMap;

public class Driver {
	
	private DriveMode mode = DriveMode.FIELD_RUN;
	
	public double speed() {
		double raw = RobotMap.driver.getY();
		double deadzoned = DeadzoneUtils.deadzone(raw, 0.04);
		double limited = deadzoned * mode.getSpeedMult();
		
		// negate the entire thing because joysticks have -1 at the top and +1 at the bottom
		return -limited;
	}
	
	public double rotation() {
		// what we limit this by depends on if we're in quick turn or not
		double limitBy = mode.getNormalRotationMult();
		
		double raw = RobotMap.driver.getX();
		double deadzoned = DeadzoneUtils.deadzone(raw, 0.04);
		double limited = deadzoned * limitBy;
		
		return limited; 
	}
	
	public boolean quickTurn() {
		return RobotMap.driver.getTrigger();
	}
	
	public boolean portalIntent() {
		return RobotMap.driver.getRawButton(2);
	}
	
	public void updateDriveMode() {
		if (RobotMap.driver.getRawButton(11)) {
			mode = DriveMode.FIELD_RUN;
		} else if (RobotMap.driver.getRawButton(10)) {
			mode = DriveMode.MANIPULATE_CUBE;
		} else if (RobotMap.driver.getRawButton(6)) {
			mode = DriveMode.MAX_OUTPUT;
		}
	}
	
	public DriveMode getDriveMode() {
		return mode;
	}
	
	public enum DriveMode {
		
		MAX_OUTPUT(1, 1),
		FIELD_RUN(1, 0.8),
		MANIPULATE_CUBE(0.5, 0.8);
		
		private double speedMult;
		private double normalRotationMult;
		
		DriveMode(double speedMult, double normalRotationMult) {
			this.speedMult = speedMult;
			this.normalRotationMult = normalRotationMult;
		}
		
		public double getSpeedMult() {
			return speedMult;
		}
		
		public double getNormalRotationMult() {
			return normalRotationMult;
		}
		
	}
	
}