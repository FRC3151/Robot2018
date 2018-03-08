package org.usfirst.frc.team3151.robot.auto;

import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class Autonomous {
	
	private DriveTrain driveTrain;
	private Lift lift;
	private Gripper gripper;
	
	private AutoMode runningMode = AutoMode.POS_5_SCALE;
	private TankModifier tank;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper) {
		this.driveTrain = driveTrain;
		this.lift = lift;
		this.gripper = gripper;
		
		SmartDashboard.putStringArray("Auto Choices", new String[] {
			"Idle",
			"Line",
			"1 Switch",
			"1 Scale",
			"2",
			"3",
			"4",
			"5 Switch",
			"5 Scale"
		});
		
		Waypoint[] flippedPath = new Waypoint[runningMode.getPath().length];
		
		for (int i = 0; i < flippedPath.length; i++) {
			Waypoint copyFrom = runningMode.getPath()[i];
			flippedPath[i] = new Waypoint(copyFrom.x, -copyFrom.y, -copyFrom.angle);
		}
		
		Trajectory testPath = Pathfinder.generate(flippedPath, new Trajectory.Config(
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
	}
	
	public void autonomousPeriodic() {
		driveTrain.driveEncoderPath();
		long startedAgo = driveTrain.getStartedEncoderPathAgo();
		long finishedAgo = driveTrain.getFinishedEncoderPathAgo();
		
		Target target = runningMode.getTarget();
		
		// at the start of auto  e raise based on what our target is and then hold
		if (startedAgo != -1 && startedAgo < target.getLiftRaiseTime()) {
			lift.set(Lift.State.UP);
		} else {
			lift.set(Lift.State.HOLD_CUBE);
		}
		
		if (finishedAgo != -1 && finishedAgo < 1_500) {
			gripper.eject();
		} else {
			gripper.intake(0, 0);
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