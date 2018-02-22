package org.usfirst.frc.team3151.robot.auto;

import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Pathfinder;

public enum AutoMode {
	
	// nothing
	IDLE(new Waypoint[] {
	    new Waypoint(0, 0, 0),
	    new Waypoint(0, 0, 0)
	}, Autonomous.Target.LINE),
	
	// drive from touching back wall to middle wheel at x=10
	LINE(new Waypoint[] {
	    new Waypoint(AutoConstants.STARTING_X, 0, 0),
	    new Waypoint(10, 0, 0)
	}, Autonomous.Target.LINE),
	
	// drive from pos2 to touching left side of switch
	POS_2_SWITCH(new Waypoint[] {
	    AutoConstants.POS_2_START,
	    AutoConstants.SWITCH_LEFT
	}, Autonomous.Target.SWITCH),
	// drive from pos3 to touching left side of switch
	POS_3_SWITCH_LEFT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_LEFT
	}, Autonomous.Target.SWITCH),
	// drive from pos3 to touching right side of switch
	POS_3_SWITCH_RIGHT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_RIGHT
	}, Autonomous.Target.SWITCH),
	// drive from pos4 to touching right side of switch
	POS_4_SWITCH(new Waypoint[] {
	    AutoConstants.POS_4_START,
	    AutoConstants.SWITCH_RIGHT
	}, Autonomous.Target.SWITCH),
	
	// drive from pos1 to angled left side of switch
	POS_1_SCALE_NEAR(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(18.00, AutoConstants.POS_1_START_Y, 0),
	    AutoConstants.SCALE_LEFT
	}, Autonomous.Target.SCALE),
	// drive from pos1 to angled right side of switch
	POS_1_SCALE_FAR(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(16.00, AutoConstants.POS_1_START_Y, 0),
	    new Waypoint(20.00, 17.00, Pathfinder.d2r(-90)),
	    new Waypoint(20.00, 10.00, Pathfinder.d2r(-90)),
	    new Waypoint(23.00, 5.00, 0),
	    AutoConstants.SCALE_RIGHT
	}, Autonomous.Target.SCALE),
	
	// drive from pos5 to angled right side of switch
	POS_5_SCALE_NEAR(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    new Waypoint(18.00, AutoConstants.POS_5_START_Y, 0),
	    AutoConstants.SCALE_RIGHT
	}, Autonomous.Target.SCALE),
	// drive from pos5 to angled left side of switch
	POS_5_SCALE_FAR(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    new Waypoint(16.00, AutoConstants.POS_5_START_Y, 0),
	    new Waypoint(20.00, 10.00, Pathfinder.d2r(90)),
	    new Waypoint(20.00, 17.00, Pathfinder.d2r(90)),
	    new Waypoint(23.00, 22.00, 0),
	    AutoConstants.SCALE_LEFT
	}, Autonomous.Target.SCALE);
	
	private Waypoint[] path;
	private Autonomous.Target target;

	AutoMode(Waypoint[] path, Autonomous.Target target) {
		this.path = path;
		this.target = target;
	}
	
	public Waypoint[] getPath() {
		return path;
	}
	
	public Autonomous.Target getTarget() {
		return target;
	}

}