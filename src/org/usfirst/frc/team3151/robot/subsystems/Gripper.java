package org.usfirst.frc.team3151.robot.subsystems;

import org.usfirst.frc.team3151.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Gripper {

	public void set(State state) {
		RobotMap.gripper.set(ControlMode.PercentOutput, state.getPower());
	}
	
	public enum State {
		
		OPEN(0.2),
		HOLD_CUBE(-0.1),
		CLOSE(-0.2),
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