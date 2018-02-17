package org.usfirst.frc.team3151.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RobotMap {
	
	// Masters
	public static WPI_TalonSRX leftMaster = new WPI_TalonSRX(11);
	public static WPI_TalonSRX rightMaster = new WPI_TalonSRX(10);
	public static WPI_TalonSRX lift = new WPI_TalonSRX(5);
	public static WPI_TalonSRX gripper = new WPI_TalonSRX(2);
	public static WPI_TalonSRX climber = new WPI_TalonSRX(7);
	
	// Followers
	public static WPI_TalonSRX leftFollower = new WPI_TalonSRX(4);
	public static WPI_TalonSRX rightFollower = new WPI_TalonSRX(9);
	public static WPI_TalonSRX liftFollower = new WPI_TalonSRX(6);
	public static WPI_TalonSRX climberFollower = new WPI_TalonSRX(8);
	
	// Misc
	public static Spark revBlinkin = new Spark(0);
	public static DifferentialDrive robotDrive = new DifferentialDrive(leftMaster, rightMaster);
	
	// Inputs
	public static Joystick driver = new Joystick(0);
	public static XboxController operator = new XboxController(1);
	
	static {
		// Followers follow masters
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);
		liftFollower.follow(lift);
		climberFollower.follow(climber);
		
		// Encoder setup
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1000 / 50, 1000);
		leftMaster.setSensorPhase(true);
		
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1000 / 50, 1000);
	}
	
}