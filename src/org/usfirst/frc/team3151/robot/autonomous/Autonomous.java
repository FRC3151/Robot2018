package org.usfirst.frc.team3151.robot.autonomous;

import org.usfirst.frc.team3151.robot.Constants;
import org.usfirst.frc.team3151.robot.RobotMap;
import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Autonomous {
	
	private DriveTrain driveTrain;
	private Lift lift;
	private Gripper gripper;
	private Gyro gyro;
	private EncoderFollower leftFollower;
	private EncoderFollower rightFollower;
	private long autoStarted = 0;
	private long autoFinished = 0;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper, Gyro gyro) {
		this.driveTrain = driveTrain;
		this.lift = lift;
		this.gripper = gripper;
		this.gyro = gyro;
	}

	public void generatePaths() {
		Trajectory testPath = Pathfinder.generate(new Waypoint[] {
			    new Waypoint(0, 0, 0),
			    new Waypoint(7, 0, 0)
			}, new Trajectory.Config(
				Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH,
				0.02, // loop at 20ms per second (what the RIO runs at)
				3.0, // max desired velocity (ft/s)
				2, // max desired acceleration (ft/s/s)
				10.0 // max desired jerk (ft/s/s/s)
			));
			
			TankModifier tank = new TankModifier(testPath).modify(Constants.WHEEL_BASE_WIDTH);
			leftFollower = new EncoderFollower(tank.getLeftTrajectory());
			rightFollower = new EncoderFollower(tank.getRightTrajectory());
			
			// P = kP, I = unused, D = kD, V = 1 / kV, A = kA
			// V = 1 / max velocity (m/s)
			leftFollower.configurePIDVA(0, 0, 0, 1 / Constants.MAX_VELOCITY, 0);
			rightFollower.configurePIDVA(0, 0, 0, 1 / Constants.MAX_VELOCITY, 0);
	}
	
	public void autonomousInit() {
		leftFollower.reset();
		rightFollower.reset();
		gyro.reset();
		
		leftFollower.configureEncoder(RobotMap.leftMaster.getSelectedSensorPosition(0), 4096, Constants.LEFT_WHEEL_DIAMETER);
		rightFollower.configureEncoder(RobotMap.rightMaster.getSelectedSensorPosition(0), 4096, Constants.RIGHT_WHEEL_DIAMETER);
		
		autoStarted = System.currentTimeMillis();
	}
	
	public void autonomousPeriodic() {	
		updateLift();
		updateGripper();
		updateDrive();
	}
	
	private void updateLift() {
		long autoFinishedAgo = System.currentTimeMillis() - autoFinished;
		long autoStartedAgo = System.currentTimeMillis() - autoStarted;
		
		if ((autoStartedAgo > 500 && autoStartedAgo < 1000) || autoFinishedAgo < 5000) {
			lift.setPower(Constants.LIFT_UP_POWER);
		} else {
			lift.setPower(0.1);
		}
	}
	
	private void updateGripper() {
		long autoFinishedAgo = System.currentTimeMillis() - autoFinished;
		long autoStartedAgo = System.currentTimeMillis() - autoStarted;
		
		if (autoStartedAgo < 500) {
			gripper.setPower(-0.1);
		} else if (autoFinishedAgo > 2500 && autoFinishedAgo < 3000) {
			gripper.setPower(0.1);
		} else {
			gripper.setPower(0);
		}
	}
	
	private void updateDrive() {
		long autoStartedAgo = System.currentTimeMillis() - autoStarted;
		if (autoStartedAgo < 2000) return;
		
		double left = leftFollower.calculate(RobotMap.leftMaster.getSelectedSensorPosition(0));
		double right = rightFollower.calculate(RobotMap.rightMaster.getSelectedSensorPosition(0));
	
		// update auto finished here
		if (leftFollower.isFinished() && autoFinished == 0) {
			autoFinished = System.currentTimeMillis();
		}
		
		double gyro_heading = gyro.getAngle();
		double desired_heading = Pathfinder.r2d(leftFollower.getHeading());  // Should also be in degrees
		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		System.out.println("offset: " + angleDifference);
		double turn = -0.04 * angleDifference;

		driveTrain.tank(left - turn, right + turn);
	}
	
}