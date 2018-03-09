package org.usfirst.frc.team3151.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RobotMap {
	
	// Inputs
	public static Joystick driver = new Joystick(0);
	public static XboxController operator = new XboxController(1);
	
	// Outputs
	public static WPI_TalonSRX leftMaster = setupEncoder(createTalon(11, NeutralMode.Brake));
	public static WPI_TalonSRX leftFollower = createTalon(4, NeutralMode.Brake);
	
	public static WPI_TalonSRX rightMaster = setupEncoder(createTalon(10, NeutralMode.Brake));
	public static WPI_TalonSRX rightFollower = createTalon(5, NeutralMode.Brake);
	
	public static WPI_TalonSRX lift = createTalon(6, NeutralMode.Brake);
	public static WPI_TalonSRX liftFollower = createTalon(7, NeutralMode.Brake);

	public static WPI_TalonSRX climber = createTalon(8, NeutralMode.Brake);
	public static WPI_TalonSRX climberFollower = createTalon(9, NeutralMode.Brake);
	
	public static WPI_TalonSRX intakeLeft = createTalon(2, NeutralMode.Coast);
	public static WPI_TalonSRX intakeRight = createTalon(3, NeutralMode.Coast);
	
	// Misc
	public static Spark revBlinkin = new Spark(0);
	public static DifferentialDrive robotDrive = new DifferentialDrive(leftMaster, rightMaster);
	
	static {
		// because each side is inverted in tank drive robots we invert the left side
		// (we could invert the right and then negate everything but negating left makes it slightly easier)
		leftMaster.setSensorPhase(true);
		
		leftFollower.follow(leftMaster);
		rightFollower.follow(rightMaster);
		liftFollower.follow(lift);
		climberFollower.follow(climber);

		// because of how the motors are placed we need to invert one side of the robot
		intakeRight.setInverted(true);
		
		// we do deadband on our own in our Driver class
		robotDrive.setDeadband(0);
	}
	
	private static WPI_TalonSRX setupEncoder(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000); // sensor id 0, 1s timeout
		talon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 1000); // send encoder values every 5ms, 1s timeout
		
		return talon;
	}
	
	private static WPI_TalonSRX createTalon(int id, NeutralMode mode) {
		WPI_TalonSRX talon = new WPI_TalonSRX(id);
		
		talon.setNeutralMode(mode); // coast = normal, break = apply reverse power to stop motor
		talon.configVoltageCompSaturation(12, 1000); // compensates for partially drained batteries with a target of 12v, 1s timeout
		talon.enableVoltageCompensation(true); // actually turn it on
		
		return talon;
	}
	
}