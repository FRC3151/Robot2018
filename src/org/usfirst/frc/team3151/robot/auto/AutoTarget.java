package org.usfirst.frc.team3151.robot.auto;

public enum AutoTarget {
	
	LINE(500),
	SWITCH(2_000),
	SCALE(4_700);
	
	private long liftRaiseTime;
		
	AutoTarget(long liftRaiseTime) {
		this.liftRaiseTime = liftRaiseTime;
	}
		
	public long getLiftRaiseTime() {
		return liftRaiseTime;
	}
	
}