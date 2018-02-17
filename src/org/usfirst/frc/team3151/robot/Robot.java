package org.usfirst.frc.team3151.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.usfirst.frc.team3151.robot.autonomous.Autonomous;
import org.usfirst.frc.team3151.robot.subsystems.CameraController;
import org.usfirst.frc.team3151.robot.subsystems.Climber;
import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Driver;
import org.usfirst.frc.team3151.robot.subsystems.DriverStation;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.LedStrip;
import org.usfirst.frc.team3151.robot.subsystems.Lift;
import org.usfirst.frc.team3151.robot.subsystems.Operator;

public class Robot extends TimedRobot {
	
	// Input
	private Driver driver = new Driver();
	private Operator operator = new Operator();
	
	// Physical systems
	private DriveTrain driveTrain = new DriveTrain();
	private Lift lift = new Lift();
	private Gripper gripper = new Gripper();
	private Climber climber = new Climber();
	private Gyro gyro = new ADXRS450_Gyro();
	
	// Misc
	private DriverStation driverStation = new DriverStation();
	private LedStrip ledStrip = new LedStrip(driverStation, operator);
	private Autonomous autonomous = new Autonomous(driveTrain, lift, gripper, gyro);
	
	@Override
	public void robotInit() {
		new CameraController();
		autonomous.generatePaths();
	}
	
	@Override
	public void teleopPeriodic() {
		driveTrain.curvature(driver.getSpeed(), driver.getRotation(), driver.quickTurn());
		lift.setPower(operator.getLift());
		gripper.setPower(operator.getGripper());
		climber.setPower(operator.getClimber());
		ledStrip.update();
		
		double leftPos = RobotMap.leftMaster.getSelectedSensorPosition(0);
		double leftVel = RobotMap.leftMaster.getSelectedSensorVelocity(0);
		double rightPos = RobotMap.rightMaster.getSelectedSensorPosition(0);
		double rightVel = RobotMap.rightMaster.getSelectedSensorVelocity(0);
		
		leftPos = (leftPos / 4096.0) * Math.PI * Constants.LEFT_WHEEL_DIAMETER;
		leftVel = (leftVel / 4096.0) * Math.PI * Constants.LEFT_WHEEL_DIAMETER;
		rightPos = (rightPos / 4096.0) * Math.PI * Constants.RIGHT_WHEEL_DIAMETER;
		rightVel = (rightVel / 4096.0) * Math.PI * Constants.RIGHT_WHEEL_DIAMETER;
		
		System.out.printf("L: %.2fft (%.2ffs/s)    R: %.2fft (%.2fft/s)%n", leftPos, leftVel, rightPos, rightVel);
	}
	
	@Override
	public void autonomousInit() {
		autonomous.autonomousInit();
	}
	
	@Override
	public void autonomousPeriodic() {
		autonomous.autonomousPeriodic();
		ledStrip.update();
	}
	
}