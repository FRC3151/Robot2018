package org.usfirst.frc.team3151.robot.auto;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public enum AutoPath {
	
	// nothing
	IDLE(new Waypoint[] {
		// need 2 points so Pathfinder doesn't complain
	    new Waypoint(0, 0, 0),
	    new Waypoint(0, 0, 0)
	}, AutoTarget.LINE, 2),
	
	// drive from touching back wall to middle wheel at x=10
	LINE(new Waypoint[] {
	    new Waypoint(AutoConstants.STARTING_X, 0, 0),
	    new Waypoint(10, 0, 0)
	}, AutoTarget.LINE, 2),
	
	// drive from pos1 to angled left side of scale
	POS_1_SCALE(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(18.00, AutoConstants.POS_1_START_Y, 0),
	    AutoConstants.SCALE_LEFT
	}, AutoTarget.SCALE, 2),
	// drive from pos1 to touching left side of switch
	POS_1_SWITCH(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(10.00, AutoConstants.POS_1_START_Y, 0),
	    AutoConstants.SWITCH_LEFT_SIDE
	}, AutoTarget.SWITCH, 2),
	
	// drive from pos2 to touching left side of switch
	POS_2_SWITCH(new Waypoint[] {
	    AutoConstants.POS_2_START,
	    AutoConstants.SWITCH_LEFT_FRONT
	}, AutoTarget.SWITCH, 2),
	
	// drive from pos3 to touching left side of switch
	POS_3_SWITCH_LEFT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_LEFT_FRONT
	}, AutoTarget.SWITCH, 2),
	// drive from pos3 to touching right side of switch
	POS_3_SWITCH_RIGHT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_RIGHT_FRONT
	}, AutoTarget.SWITCH, 2),
	
	// drive from pos4 to touching right side of switch
	POS_4_SWITCH(new Waypoint[] {
	    AutoConstants.POS_4_START,
	    AutoConstants.SWITCH_RIGHT_FRONT
	}, AutoTarget.SWITCH, 2),
	
	// drive from pos5 to angled right side of scale
	POS_5_SCALE(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    new Waypoint(18.00, AutoConstants.POS_5_START_Y, 0),
	    AutoConstants.SCALE_RIGHT
	}, AutoTarget.SCALE, 2),
	// drive from pos5 to touching right side of switch
	POS_5_SWITCH(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    new Waypoint(10.00, AutoConstants.POS_5_START_Y, 0),
	    AutoConstants.SWITCH_RIGHT_SIDE
	}, AutoTarget.SWITCH, 2);
	
	private Waypoint[] path;
	private AutoTarget target;
	private int version;
	private Trajectory left;
	private Trajectory right;
	
	AutoPath(Waypoint[] path, AutoTarget target, int version) {
		this.path = path;
		this.target = target;
		this.version = version;
	}
	
	public AutoTarget getTarget() {
		return target;
	}
	 
	public Trajectory getLeftPath() {
		return left;
	}
	
	public Trajectory getRightPath() {
		return right;
	}
	
	public void loadOrGenPath() {
		File leftSave = getSaveFile("left");
		File rightSave = getSaveFile("right");
		
		if (leftSave.exists() && rightSave.exists()) {
			System.out.println("Path for " + name() + " version " + version + " exists, loading it...");
			left = Pathfinder.readFromFile(leftSave);
			right = Pathfinder.readFromFile(rightSave);
		} else {
			System.out.println("Generating path for " + name() + " version " + version + "...");
			Trajectory original = Pathfinder.generate(AutoConstants.flipPath(path), AutoConstants.PATHFINDER_CONFIG);
			TankModifier tankMod = new TankModifier(original).modify(AutoConstants.WHEEL_BASE_WIDTH);
			
			left = tankMod.getLeftTrajectory();
			right = tankMod.getRightTrajectory();
			
			Pathfinder.writeToFile(leftSave, left);
			Pathfinder.writeToFile(rightSave, right);
		}
	}
	
	private File getSaveFile(String side) {
		return new File("/home/lvuser/", name() + "_" + side + "_" + version + ".path");
	}

}