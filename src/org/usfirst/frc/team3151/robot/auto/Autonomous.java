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
	//private AutoMode runningMode = AutoMode.POS_3_SWITCH_LEFT;
	private AutoMode runningMode = AutoMode.POS_5_SCALE_NEAR;
	private TankModifier tank;
	
	private long startedPath = 0;
	private long finishedPath = 0;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper) {
		this.driveTrain = driveTrain;
		this.lift = lift;
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
		
		if (finishedPath == 0) {
			//gripper.set(Gripper.State.CLOSE);
		} else if (finishedAgo < AutoConstants.RELEASE_TIME) {
			//gripper.set(Gripper.State.OPEN);
		} else {
			//gripper.set(Gripper.State.IDLE);
		}
	}

	public AutoMode getRunningMode() {
		return runningMode;
	}
	
	public enum Target {
		
		LINE(500),
		SWITCH(2_000),
		SCALE(5_000);
		
		private long liftRaiseTime;
		
		Target(long liftRaiseTime) {
			this.liftRaiseTime = liftRaiseTime;
		}
		
		public long getLiftRaiseTime() {
			return liftRaiseTime;
		}
		
	}
	
}