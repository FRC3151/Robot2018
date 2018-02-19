package org.usfirst.frc.team3151.robot.auto;

import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.Lift;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;

public class Autonomous {
	
	private DriveTrain driveTrain;
	private Lift lift;
	private Gripper gripper;

	private AutoMode runningMode = AutoMode.IDLE;
	private TankModifier tank;
	
	private long startedPath = 0;
	private long finishedPath = 0;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper) {
		this.driveTrain = driveTrain;
		this.lift = lift;
		this.gripper = gripper;
		
		Trajectory testPath = Pathfinder.generate(runningMode.getPath(), new Trajectory.Config(
			Trajectory.FitMethod.HERMITE_CUBIC,
			Trajectory.Config.SAMPLES_HIGH,
			0.02, // loop every 20ms aka 50hz (which is what our robot runs at)
			5.0, // max desired velocity (ft/s)
			2.0, // max desired acceleration (ft/s/s)
			10.0 // max desired jerk (ft/s/s/s)
		));
			
		tank = new TankModifier(testPath).modify(AutoConstants.WHEEL_BASE_WIDTH);
	}
	
	public void autonomousInit() {
		driveTrain.initEncoderPath(tank.getLeftTrajectory(), tank.getRightTrajectory());
		startedPath = System.currentTimeMillis();
	}
	
	public void autonomousPeriodic() {
		// driveEncoderPath returns true when it's done, so we can use it to set our finished time
		if (driveTrain.driveEncoderPath() && finishedPath == 0) {
			finishedPath = System.currentTimeMillis();
		}
		
		long startedAgo = System.currentTimeMillis() - startedPath;
		long finishedAgo = System.currentTimeMillis() - finishedPath;
		Target target = runningMode.getTarget();
		
		// at the start of auto we raise based on what our target is and then hold
		if (startedAgo < target.getLiftRaiseTime()) {
			lift.set(Lift.State.UP);
		} else {
			lift.set(Lift.State.HOLD_CUBE);
		}
		
		// if we're just going to the line or still running the path hold,
		// otherwise open for the first Xms of being done and then no power
		if (target == Target.LINE || finishedAgo == 0) {
			gripper.set(Gripper.State.HOLD_CUBE);
		} else if (finishedAgo < AutoConstants.RELEASE_TIME) {
			gripper.set(Gripper.State.OPEN);
		} else {
			gripper.set(Gripper.State.IDLE);
		}
	}

	public AutoMode getRunningMode() {
		return runningMode;
	}
	
	public enum Target {
		
		LINE(500),
		SWITCH(1500),
		SCALE(3500);
		
		private long liftRaiseTime;
		
		Target(long liftRaiseTime) {
			this.liftRaiseTime = liftRaiseTime;
		}
		
		public long getLiftRaiseTime() {
			return liftRaiseTime;
		}
		
	}
	
}