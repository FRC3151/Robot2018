package org.usfirst.frc.team3151.robot.auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class AutoConstants {
	
	public static Trajectory.Config PATHFINDER_CONFIG = new Trajectory.Config(
		Trajectory.FitMethod.HERMITE_CUBIC,
		Trajectory.Config.SAMPLES_HIGH,
		0.02, // loop every 20ms aka 50hz (which is what our robot runs at)
		5.0, // max desired velocity (ft/s)
		2.0, // max desired acceleration (ft/s/s)
		10.0 // max desired jerk (ft/s/s/s)
	);
	
	public static double STARTING_X = 1.583;
	public static double POS_1_START_Y = 23.00;
	public static double POS_2_START_Y = 20.00;
	public static double POS_3_START_Y = 13.00;
	public static double POS_4_START_Y = 9.00;
	public static double POS_5_START_Y = 4.00;
	
	public static Waypoint POS_1_START = new Waypoint(STARTING_X, POS_1_START_Y, 0);
	public static Waypoint POS_2_START = new Waypoint(STARTING_X, POS_2_START_Y, 0);
	public static Waypoint POS_3_START = new Waypoint(STARTING_X, POS_3_START_Y, 0);
	public static Waypoint POS_4_START = new Waypoint(STARTING_X, POS_4_START_Y, 0);
	public static Waypoint POS_5_START = new Waypoint(STARTING_X, POS_5_START_Y, 0);
	
	public static Waypoint SWITCH_LEFT = new Waypoint(10.00, 18.00, 0);
	public static Waypoint SWITCH_RIGHT = new Waypoint(10.00, 9.00, 0);
	
	public static Waypoint SCALE_LEFT = new Waypoint(24, 20.0, Pathfinder.d2r(-60));
	public static Waypoint SCALE_RIGHT = new Waypoint(24, 7.00, Pathfinder.d2r(60));
	
	public static double MAX_VELOCITY = 12.3; // max speed of robot (in ft/s)
	public static double WHEEL_DIAMETER = 0.482; // Effective wheel diameter (in ft)
	public static double WHEEL_BASE_WIDTH = 3.5; // Effective distance between wheels (in ft)
	
	public static double DRIVE_TRAIN_P = 0.25;
	public static double GYRO_P = -0.05;
	
	public static Waypoint[] flipPath(Waypoint[] original) {
		Waypoint[] flipped = new Waypoint[original.length];
		
		for (int i = 0; i < flipped.length; i++) {
		 	Waypoint copyFrom = original[i];
			flipped[i] = new Waypoint(copyFrom.x, -copyFrom.y, -copyFrom.angle);
		}
		
		return flipped;
	}
	
}