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
		double limitBy = quickTurn() ? mode.getQuickTurnRotationMult() : mode.getNormalRotationMult();
		
		double raw = RobotMap.driver.getX();
		double deadzoned = DeadzoneUtils.deadzone(raw, 0.04);
		double limited = deadzoned * limitBy;
		
		return limited; 
	}
	
	public boolean quickTurn() {
		return RobotMap.driver.getTrigger();
	}
	
	public boolean portalIntent() {
		return RobotMap.driver.getTop();
	}
	
	public void updateDriveMode() {
		if (RobotMap.driver.getRawButton(4)) {
			mode = DriveMode.FIELD_RUN;
		} else if (RobotMap.driver.getRawButton(5)) {
			mode = DriveMode.MANIPULATE_CUBE;
		} else if (RobotMap.driver.getRawButton(6)) {
			mode = DriveMode.MAX_OUTPUT;
		}
	}
	
	public DriveMode getDriveMode() {
		return mode;
	}
	
	public enum DriveMode {
		
		MAX_OUTPUT(1, 1, 1),
		FIELD_RUN(1, 1, 0.3),
		MANIPULATE_CUBE(0.5, 1, 0.3);
		
		private double speedMult;
		private double normalRotationMult;
		private double quickTurnRotationMult;
		
		DriveMode(double speedMult, double normalRotationMult, double quickTurnRotationMult) {
			this.speedMult = speedMult;
			this.normalRotationMult = normalRotationMult;
			this.quickTurnRotationMult = quickTurnRotationMult;
		}
		
		public double getSpeedMult() {
			return speedMult;
		}
		
		public double getNormalRotationMult() {
			return normalRotationMult;
		}
		
		public double getQuickTurnRotationMult() {
			return quickTurnRotationMult;
		}
		
	}
	
}