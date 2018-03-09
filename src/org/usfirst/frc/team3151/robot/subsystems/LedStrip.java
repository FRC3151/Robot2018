package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;
import org.usfirst.frc.team3151.robot.auto.AutoMode;
import org.usfirst.frc.team3151.robot.auto.Autonomous;

import edu.wpi.first.wpilibj.DriverStation;

public class LedStrip {
	
	private FieldConfig fieldConfig;
	private Autonomous autonomous;
	private Driver driver;
	private Operator operator;
	
	public LedStrip(FieldConfig fieldConfig, Autonomous autonomous, Driver driver, Operator operator) {
		this.fieldConfig = fieldConfig;
		this.autonomous = autonomous;
		this.driver = driver;
		this.operator = operator;
	}

	public void setColor(Color color) {
		RobotMap.revBlinkin.set(color.getOutput());
	}
	
	public void updateLedOutput() {		
		if (DriverStation.getInstance().isAutonomous()) {
			setColor(determineAutoColor());
		} else {
			setColor(determineTeleopColor());
		}
	}
	
	private Color determineAutoColor() {
		// auto lights are just based on what our target is
		AutoMode running = autonomous.getRunningMode();
		
		if (running != null) {
			if (running.getTarget() == Autonomous.Target.SCALE) {
				return Color.GREEN;
			} else if (running.getTarget() == Autonomous.Target.SWITCH) {
				return Color.YELLOW;
			}
		}
		
		// white for line or unknown
		return Color.WHITE;
	}
	
	private Color determineTeleopColor() {
		// teleop lights are either display patterns, to signal intent, to show little time remaining, or based on driver mode
		long timeLeft = Math.round(DriverStation.getInstance().getMatchTime());
		
		if (operator.specialLed1()) {
			return Color.COLOR_WAVE;
		} else if (operator.specialLed2()) {
			return Color.SINELON;
		} else if (driver.portalIntent()) {
			return Color.GREEN;
		} else if (timeLeft <= 40 && timeLeft % 5 == 0) {
			return fieldConfig.getAlliance().getWarningColor();
		} else if (operator.desiredLift() != Lift.State.IDLE) {
			return fieldConfig.getAlliance().getBeatingColor();
		} else {
			return fieldConfig.getAlliance().getMainColor();
		}
	}
	
	public enum Color {
		
		// Colors + variants
		RED(0.59),
		RED_BEATING(0.07),
		BLUE(0.85),
		BLUE_BEATING(0.27),
		ORANGE(0.65),
		LIGHT_GREEN(0.73),
		GREEN(0.77),
		YELLOW(0.69),
		WHITE(0.93),
		
		// Special
		COLOR_WAVE(-0.43),
		SINELON(-0.77);

		private double output;
		
	    Color(double output) {
	    		this.output = output;
		}

		public double getOutput() {
		    return output;
		}
		
	}
	
}