package org.usfirst.frc.team3151.robot.auto;

import org.usfirst.frc.team3151.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3151.robot.subsystems.FieldConfig;
import org.usfirst.frc.team3151.robot.subsystems.FieldConfig.Side;
import org.usfirst.frc.team3151.robot.subsystems.Gripper;
import org.usfirst.frc.team3151.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	
	private DriveTrain driveTrain;
	private Lift lift;
	private Gripper gripper;
	private FieldConfig fieldConfig;
	
	private AutoPath runningPath;
	
	public Autonomous(DriveTrain driveTrain, Lift lift, Gripper gripper, FieldConfig fieldConfig) {
		this.driveTrain = driveTrain;
		this.lift = lift;
		this.gripper = gripper;
		this.fieldConfig = fieldConfig;
		
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
		
		// either generate + save or load all of our possible paths
		for (AutoPath mode : AutoPath.values()) {
			mode.loadOrGenPath();
		}
	}
	
	public void autonomousInit() {
		String mode = SmartDashboard.getString("Auto Selector", "Line");
		Side switchSide = fieldConfig.getSwitchSide();
		Side scaleSide = fieldConfig.getScaleSide();
		
		System.out.println("Running auto mode '" + mode + "'");
		System.out.println("Switch is on the " + switchSide + ", scale is on the " + scaleSide);
		
		switch (mode) {
			case "Idle":
				runningPath = AutoPath.IDLE;
				break;
			case "Line":
				runningPath = AutoPath.LINE;
				break;
			case "1 Switch":
				if (switchSide == Side.LEFT) {
					runningPath = AutoPath.POS_1_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			case "1 Scale":
				if (scaleSide == Side.LEFT) {
					runningPath = AutoPath.POS_1_SCALE;
				} else if (switchSide == Side.LEFT) {
					runningPath = AutoPath.POS_1_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			case "2":
				if (switchSide == Side.LEFT) {
					runningPath = AutoPath.POS_2_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			case "3":
				if (switchSide == Side.LEFT) {
					runningPath = AutoPath.POS_3_SWITCH_LEFT;
				} else {
					runningPath = AutoPath.POS_3_SWITCH_RIGHT;
				}
				
				break;
			case "4":
				if (switchSide == Side.RIGHT) {
					runningPath = AutoPath.POS_4_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			case "5 Switch":
				if (switchSide == Side.RIGHT) {
					runningPath = AutoPath.POS_5_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			case "5 Scale":
				if (scaleSide == Side.RIGHT) {
					runningPath = AutoPath.POS_5_SCALE;
				} else if (switchSide == Side.RIGHT) {
					runningPath = AutoPath.POS_5_SWITCH;
				} else {
					runningPath = AutoPath.LINE;
				}
				
				break;
			default:
				runningPath = AutoPath.LINE;
				break;
		}
		
		driveTrain.initEncoderPath(runningPath.getLeftPath(), runningPath.getRightPath());
	}
	
	public void autonomousPeriodic() {
		driveTrain.driveEncoderPath();
		
		long startedAgo = driveTrain.getStartedEncoderPathAgo();
		long finishedAgo = driveTrain.getFinishedEncoderPathAgo();
		AutoTarget target = runningPath.getTarget();
		
		// at the start of auto  e raise based on what our target is and then hold
		if (startedAgo != -1 && startedAgo < target.getLiftRaiseTime()) {
			lift.set(Lift.State.UP);
		} else {
			lift.set(Lift.State.HOLD);
		}
		
		if (finishedAgo != -1 && finishedAgo < 1_500) {
			gripper.eject();
		} else {
			gripper.intake(0, 0);
		}
	}

	public AutoPath getRunningPath() {
		return runningPath;
	}
	
}