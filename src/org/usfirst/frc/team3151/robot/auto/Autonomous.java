package org.usfirst.frc.team3151.robot.auto;

import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	
	private DriveTrain driveTrain;
	private Lift lift;
	private Gripper gripper;
	
	private AutoMode runningMode = AutoMode.POS_1_SCALE;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper) {
		this.driveTrain = driveTrain;
		this.lift = lift;
		this.gripper = gripper;
		
		SmartDashboard.putStringArray("Auto List", new String[] {
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
		
		for (AutoMode mode : AutoMode.values()) {
			mode.loadOrGenPath();
		}
	}
	
	public void autonomousInit() {
		String mode = SmartDashboard.getString("Auto Selector", "Line");
		System.out.println(mode);
		driveTrain.initEncoderPath(runningMode.getLeftPath(), runningMode.getRightPath());
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