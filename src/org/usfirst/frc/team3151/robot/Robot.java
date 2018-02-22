package org.usfirst.frc.team3151.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

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
	
	// Debug
	private PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	private BuiltInAccelerometer accel = new BuiltInAccelerometer(Range.k4G);
	
	@Override
	public void robotInit() {
		cameraController.printConnectedCameras();
		cameraController.setupStream();
	}
	
	@Override
	public void teleopPeriodic() {
		driver.updateDriveMode();
		driveTrain.driveCurvature(driver.speed(), driver.rotation(), driver.quickTurn());
		lift.set(operator.desiredLift());
		gripper.set(operator.desiredGripper());
		climber.setPower(operator.climberPower());
	}
	
	@Override
	public void robotPeriodic() {
		System.out.printf("L: %.2fft (%.2ffs/s)    R: %.2fft (%.2fft/s)     TCD: %.1fA %n", driveTrain.getLeftPosition(), driveTrain.getLeftVelocity(), driveTrain.getRightPosition(), driveTrain.getRightVelocity(), pdp.getTotalCurrent());
		ledStrip.updateLedOutput();
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