package org.usfirst.frc.team3151.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.usfirst.frc.team3151.robot.auto.Autonomous;
import org.usfirst.frc.team3151.robot.subsystems.CameraController;
import org.usfirst.frc.team3151.robot.subsystems.Climber;
import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Driver;
import org.usfirst.frc.team3151.robot.subsystems.FieldConfig;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.LedStrip;
import org.usfirst.frc.team3151.robot.subsystems.Lift;
import org.usfirst.frc.team3151.robot.subsystems.Operator;

public class Robot extends TimedRobot {
	
	// Input
	private Driver driver = new Driver();
	private Operator operator = new Operator();
	
	// Sensors
	private Gyro gyro = new ADXRS450_Gyro();
	
	// Physical systems
	private Lift lift = new Lift();
	private Gripper gripper = new Gripper();
	private Climber climber = new Climber();
	private DriveTrain driveTrain = new DriveTrain(gyro);
	
	// Misc
	private CameraController cameraController = new CameraController();
	private FieldConfig fieldConfig = new FieldConfig();
	private Autonomous autonomous = new Autonomous(driveTrain, lift, gripper);
	private LedStrip ledStrip = new LedStrip(fieldConfig, autonomous, driver, operator);
	
	@Override
	public void robotInit() {
		cameraController.printConnectedCameras();
		cameraController.setupStream();
	}
	
	@Override
	public void robotPeriodic() {
		ledStrip.updateLedOutput();
	}
	
	@Override
	public void teleopPeriodic() {
		driver.updateDriveMode();
		driveTrain.driveCurvature(driver.speed(), driver.rotation(), driver.quickTurn());
		lift.set(operator.desiredLift());
		climber.setPower(operator.climberPower());
		
		if (operator.intakeEject()) {
			gripper.eject();
		} else {
			gripper.intake(operator.intakeLeft(), operator.intakeRight());
		}
	}
	
	@Override
	public void autonomousInit() {
		autonomous.autonomousInit();
	}
	
	@Override
	public void autonomousPeriodic() {
		autonomous.autonomousPeriodic();
	}
	
}