package org.usfirst.frc.team3151.robot.auto;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public enum AutoMode {
	
	// nothing
	IDLE(new Waypoint[] {
		// need 2 points so Pathfinder doesn't complain
	    new Waypoint(0, 0, 0),
	    new Waypoint(0, 0, 0)
	}, Autonomous.Target.LINE, 1),
	
	// drive from touching back wall to middle wheel at x=10
	LINE(new Waypoint[] {
	    new Waypoint(AutoConstants.STARTING_X, 0, 0),
	    new Waypoint(10, 0, 0)
	}, Autonomous.Target.LINE, 1),
	
	// drive from pos1 to angled left side of scale
	POS_1_SCALE(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(18.00, AutoConstants.POS_1_START_Y, 0),
	    AutoConstants.SCALE_LEFT
	}, Autonomous.Target.SCALE, 1),
	// drive from pos1 to touching left side of switch
	POS_1_SWITCH(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    AutoConstants.SWITCH_LEFT
	}, Autonomous.Target.SWITCH, 1),
	
	// drive from pos2 to touching left side of switch
	POS_2_SWITCH(new Waypoint[] {
	    AutoConstants.POS_2_START,
	    AutoConstants.SWITCH_LEFT
	}, Autonomous.Target.SWITCH, 1),
	
	// drive from pos3 to touching left side of switch
	POS_3_SWITCH_LEFT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_LEFT
	}, Autonomous.Target.SWITCH, 1),
	// drive from pos3 to touching right side of switch
	POS_3_SWITCH_RIGHT(new Waypoint[] {
	    AutoConstants.POS_3_START,
	    AutoConstants.SWITCH_RIGHT
	}, Autonomous.Target.SWITCH, 1),
	
	// drive from pos4 to touching right side of switch
	POS_4_SWITCH(new Waypoint[] {
	    AutoConstants.POS_4_START,
	    AutoConstants.SWITCH_RIGHT
	}, Autonomous.Target.SWITCH, 1),
	
	// drive from pos5 to angled right side of scale
	POS_5_SCALE(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    new Waypoint(18.00, AutoConstants.POS_5_START_Y, 0),
	    AutoConstants.SCALE_RIGHT
	}, Autonomous.Target.SCALE, 1),
	// drive from pos5 to touching right side of switch
	POS_5_SWITCH(new Waypoint[] {
	    AutoConstants.POS_5_START,
	    AutoConstants.SWITCH_RIGHT
	}, Autonomous.Target.SWITCH, 1);

	/* Experimental
	// drive from pos1 to angled right side of switch
	POS_1_SCALE_FAR(new Waypoint[] {
	    AutoConstants.POS_1_START,
	    new Waypoint(16.00, AutoConstants.POS_1_START_Y, 0),
	    new Waypoint(19.00, 22.00, Pathfinder.d2r(-65)),
	    new Waypoint(19.00, 21.00, Pathfinder.d2r(-90)),
	    new Waypoint(19.00, 7.00,  Pathfinder.d2r(-90)),
	    new Waypoint(19.00, 5.00,  Pathfinder.d2r(-70)),
	    new Waypoint(23.00, 3.00,  Pathfinder.d2r(0.0)),
	    new Waypoint(25.00, 5.00,  Pathfinder.d2r(60)),
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
	*/
	
	private Waypoint[] path;
	private Autonomous.Target target;
	private int version;
	private Trajectory left;
	private Trajectory right;
	
	AutoMode(Waypoint[] path, Autonomous.Target target, int version) {
		this.path = path;
		this.target = target;
		this.version = version;
	}
	
	public Autonomous.Target getTarget() {
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
			System.out.println("Path for " + name() + " v" + version + " exists, loading it...");
			left = Pathfinder.readFromFile(leftSave);
			right = Pathfinder.readFromFile(rightSave);
			System.out.println("Done!");
		} else {
			System.out.println("Generating path for " + name() + " v" + version + "...");
			Trajectory original = Pathfinder.generate(AutoConstants.flipPath(path), AutoConstants.PATHFINDER_CONFIG);
			TankModifier tankMod = new TankModifier(original).modify(AutoConstants.WHEEL_BASE_WIDTH);
			
			left = tankMod.getLeftTrajectory();
			right = tankMod.getRightTrajectory();
			
			Pathfinder.writeToFile(leftSave, left);
			Pathfinder.writeToFile(rightSave, right);
			System.out.println("Done!");
		}
	}
	
	private File getSaveFile(String side) {
		return new File("/home/lvuser/", name() + "_" + side + "_" + version + ".path");
	}

}