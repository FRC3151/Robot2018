package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;
import org.usfirst.frc.team3151.robot.subsystems.DriverStation.Alliance;

public class LedStrip {
	
	private DriverStation driverStation;
	private Operator operator;
	
	public LedStrip(DriverStation driverStation, Operator operator) {
		this.driverStation = driverStation;
		this.operator = operator;
	}

	public void setColor(Color color) {
		RobotMap.revBlinkin.set(color.getValue());
	}
	
	public void update() {
		Alliance alliance = driverStation.getAlliance();
		
		if (operator.getSpecialLed1()) {
			setColor(Color.RAINBOW);
		} else if (operator.getSpecialLed2()) {
			setColor(Color.MOVING_SPARKLE);
		} else if (RobotMap.gripper.get() != 0) {
			setColor(alliance.getBeatingColor());
		} else if (RobotMap.climber.get() != 0) {
			setColor(alliance.getMovingColor()); 
		} else {
			setColor(alliance.getMainColor());
		}
	}
	
	public enum Color {
		
		RED(0.59),
		RED_BEATING(0.07),
		RED_MOVING(-0.39),
		BLUE(0.85),
		BLUE_BEATING(0.27),
		BLUE_MOVING(-0.41),
		RAINBOW(-0.43),
		MOVING_SPARKLE(-0.77);

		public double val;
		
	    Color(double val) {
	    	this.val = val;
		}

		public double getValue() {
		    return val;
		}
	}
	
}