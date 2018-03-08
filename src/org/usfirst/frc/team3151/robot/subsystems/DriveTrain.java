package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;
import org.usfirst.frc.team3151.robot.auto.AutoConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class DriveTrain {

	private Gyro gyro;
	private long startedEncoderPath;
	private long finishedEncoderPath;
	
	private EncoderFollower leftFollower;
	private EncoderFollower rightFollower;
	
	public DriveTrain(Gyro gyro) {
		this.gyro = gyro;
	}
	
	public void initEncoderPath(Trajectory left, Trajectory right) {
		gyro.reset();
		startedEncoderPath = 0;
		finishedEncoderPath = 0;
		
		leftFollower = new EncoderFollower(left);
		leftFollower.configurePIDVA(AutoConstants.DRIVE_TRAIN_P, 0, 0, 1 / AutoConstants.MAX_VELOCITY, 0);
		leftFollower.configureEncoder(RobotMap.leftMaster.getSelectedSensorPosition(0), 4096, AutoConstants.WHEEL_DIAMETER);
		
		rightFollower = new EncoderFollower(right);
		rightFollower.configurePIDVA(AutoConstants.DRIVE_TRAIN_P, 0, 0, 1 / AutoConstants.MAX_VELOCITY, 0);
		rightFollower.configureEncoder(RobotMap.rightMaster.getSelectedSensorPosition(0), 4096, AutoConstants.WHEEL_DIAMETER);
	}
	
	public void driveCurvature(double speed, double rotation, boolean quickTurn) {
		RobotMap.robotDrive.curvatureDrive(speed, rotation, quickTurn);
	}
	
	public void driveTank(double left, double right) {
		RobotMap.robotDrive.tankDrive(left, right, false);
	}
	
	public void driveEncoderPath() {
		if (startedEncoderPath == 0) {
			startedEncoderPath = System.currentTimeMillis();
		}
		
		double leftOutput = leftFollower.calculate(RobotMap.leftMaster.getSelectedSensorPosition(0));
		double rightOutput = rightFollower.calculate(RobotMap.rightMaster.getSelectedSensorPosition(0));
		
		double actualHeading = gyro.getAngle();
		double desiredHeading = Pathfinder.r2d(leftFollower.getHeading());
		double errorHeading = Pathfinder.boundHalfDegrees(desiredHeading - actualHeading);
		double turn = AutoConstants.GYRO_P * errorHeading;

		driveTank(leftOutput - turn, rightOutput + turn);
		
		if (leftFollower.isFinished() && finishedEncoderPath == 0) {
			finishedEncoderPath = System.currentTimeMillis();
		}
	}
	
	// returns how long ago the drivetrain started following the path (in ms),
	// or -1 if it hasn't started
	public long getStartedEncoderPathAgo() {
		if (startedEncoderPath == 0) {
			 return -1;
		} else {
			return System.currentTimeMillis() - startedEncoderPath;
		}
	}
	
	// returns how long ago the drivetrain finished following the path (in ms),
	// or -1 if it hasn't finished
	public long getFinishedEncoderPathAgo() {
		if (finishedEncoderPath == 0) {
			 return -1;
		} else {
			return System.currentTimeMillis() - finishedEncoderPath;
		}
	}
	
	public double getLeftPosition() {
		return calcPosition(RobotMap.leftMaster);
	}
	
	public double getLeftVelocity() {
		return calcVelocity(RobotMap.leftMaster);
	}
	
	public double getRightPosition() {
		return calcPosition(RobotMap.rightMaster);
	}
	
	public double getRightVelocity() {
		return calcVelocity(RobotMap.rightMaster);
	}
	
	private double calcPosition(WPI_TalonSRX talon) {
		double positionInTicks = talon.getSelectedSensorPosition(0);
		
		// divide by 4096 to convert from ticks to wheel rotations
		double totalRotations = positionInTicks / 4096; 
		
		// circumference of a wheel is pi * diameter so we multiply that by number of turns
		// this converts total rotations to total distance (in feet)
		return totalRotations * Math.PI * AutoConstants.WHEEL_DIAMETER;
	}
	
	private double calcVelocity(WPI_TalonSRX talon) {
		double velocityInTicksPer100ms = talon.getSelectedSensorVelocity(0);
		
		// divide by 4096 to convert from ticks to wheel rotations,
		// multiply by 10 because these readings are per 100ms (1000ms per second)
		double rotationsPerSecond = (velocityInTicksPer100ms / 4096) * 10; 
		
		// circumference of a wheel is pi * diameter so we multiply that by number of turns
		// this converts rotations per second to feet per second (which is our output unit)
		return rotationsPerSecond * Math.PI * AutoConstants.WHEEL_DIAMETER;
	}
	
}