package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.DeadzoneUtils;
import org.usfirst.frc.team3151.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Operator {

	public Lift.State desiredLift() {
		if (RobotMap.operator.getYButton()) {
			return Lift.State.UP;
		} else if (RobotMap.operator.getXButton()) {
			return Lift.State.HOLD_EMPTY;
		} else if (RobotMap.operator.getBButton()) {
			return Lift.State.HOLD_CUBE;
		} else if (RobotMap.operator.getAButton()) {
			return Lift.State.DOWN;
		} else {
			return Lift.State.IDLE;
		}
	}
	
	public double intakeLeft() {
		return DeadzoneUtils.deadzone(RobotMap.operator.getTriggerAxis(Hand.kLeft), 0.1);
	}
	
	public double intakeRight() {
		return DeadzoneUtils.deadzone(RobotMap.operator.getTriggerAxis(Hand.kRight), 0.1);
	}
	
	public boolean intakeEject() {
		return RobotMap.operator.getBumper(Hand.kLeft) || RobotMap.operator.getBumper(Hand.kRight);
	}
	
	public double climberPower() {
		return DeadzoneUtils.deadzone(RobotMap.operator.getY(Hand.kLeft), 0.1);
	}
	
	public boolean specialLed1() {
		return RobotMap.operator.getBackButton();
	}
	
	public boolean specialLed2() {
		return RobotMap.operator.getStartButton();
	}
	
}