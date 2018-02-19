package org.usfirst.frc.team3151.robot;

public class DeadzoneUtils {

	public static double deadzone(double value, double deadband) {
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
	
}