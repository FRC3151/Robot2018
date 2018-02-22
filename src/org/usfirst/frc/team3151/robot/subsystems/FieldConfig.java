package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.subsystems.LedStrip.Color;

import edu.wpi.first.wpilibj.DriverStation;

public class FieldConfig {

	public Alliance getAlliance() {
		DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
		
		if (alliance.equals(edu.wpi.first.wpilibj.DriverStation.Alliance.Blue)) {
			return Alliance.BLUE;
		} else {
			return Alliance.RED;
		}
	}
	
	public Side getSwitchSide() {
		return sideFromChar(0);
	}
	
	public Side getScaleSide() {
		return sideFromChar(1);
	}
	
	private Side sideFromChar(int position) {
		String data = DriverStation.getInstance().getGameSpecificMessage();
		
		if (data.length() > 0 && data.charAt(position) == 'L') {
			return Side.LEFT;
		} else {
			return Side.RIGHT;
		}
	}
	
	public enum Alliance {

		RED(Color.RED, Color.RED_BEATING, Color.ORANGE),
		BLUE(Color.BLUE, Color.BLUE_BEATING, Color.LIGHT_GREEN);

		private Color mainColor;
		private Color beatingColor;
		private Color warningColor;
		
	    Alliance(Color mainColor, Color beatingColor, Color warningColor) {
	    	this.mainColor = mainColor;
	    	this.beatingColor = beatingColor;
	    	this.warningColor = warningColor;
		}

		public Color getMainColor() {
		    return mainColor;
		}
		
		public Color getBeatingColor() {
		    return beatingColor;
		}
		
		public Color getWarningColor() {
			return warningColor;
		}
		
	}
	
	public enum Side {
	
		LEFT, RIGHT
	
	}
	
}