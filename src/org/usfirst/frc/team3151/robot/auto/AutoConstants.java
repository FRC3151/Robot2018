package org.usfirst.frc.team3151.robot.auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoConstants {
	
	public static double STARTING_X = 1.64;
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
	
	public static Waypoint SCALE_LEFT = new Waypoint(25.00, 21.00, Pathfinder.d2r(-45));
	public static Waypoint SCALE_RIGHT = new Waypoint(25.00, 6.00, Pathfinder.d2r(45));
	
	
	public static double MAX_VELOCITY = 8.58; // max speed of robot (in ft/s)
	public static double WHEEL_DIAMETER = 0.482; // Effective wheel diameter (in ft)
	public static double WHEEL_BASE_WIDTH = 2; // Effective distance between wheels (in ft)
	
	public static double HEADING_CORRECTION_P = -0.04;
	
	public static double DRIVE_P = 0;
	
	public static long RELEASE_TIME = 500;
	
}