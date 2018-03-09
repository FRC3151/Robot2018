package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Lift {

	public void set(State state) {
		RobotMap.lift.set(ControlMode.PercentOutput, state.getPower());
	}
	
	public enum State {
		
		UP(0.5),
		HOLD(0.15),
		DOWN(-0.3),
		IDLE(0);
		
		private double power;
		
		State(double power) {
			this.power = power;
		}
		
		public double getPower() {
			return power;
		}
		
	}
	
}