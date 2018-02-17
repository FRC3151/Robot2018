package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.subsystems.LedStrip.Color;

public class DriverStation {

	public Alliance getAlliance() {
		edu.wpi.first.wpilibj.DriverStation.Alliance alliance = edu.wpi.first.wpilibj.DriverStation.getInstance().getAlliance();
		
		if (alliance.equals(edu.wpi.first.wpilibj.DriverStation.Alliance.Blue)) {
			return Alliance.BLUE;
		} else {
			return Alliance.RED;
		}
	}
	
	public enum Alliance {

		RED(Color.RED, Color.RED_MOVING, Color.RED_BEATING),
		BLUE(Color.BLUE, Color.BLUE_MOVING, Color.BLUE_BEATING);

		private Color mainColor;
		private Color movingColor;
		private Color beatingColor;
		
	    Alliance(Color mainColor, Color movingColor, Color beatingColor) {
	    	this.mainColor = mainColor;
	    	this.movingColor = movingColor;
	    	this.beatingColor = beatingColor;
		}

		public Color getMainColor() {
		    return mainColor;
		}
		
		public Color getMovingColor() {
		    return movingColor;
		}
		
		public Color getBeatingColor() {
		    return beatingColor;
		}
		
	}
	
}