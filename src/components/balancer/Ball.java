package components.balancer;

import logic.GameController;

public class Ball {
	
	private double diameter;
	private double mass;
	private double displacement; // ball's center measured in the bar axis respect to barTopLeftX
	private double velocity;
	private double acceleration;
	private double gravity;
	
	private final double IMPULSE = 0.03;
	
	public Ball() {
		this.diameter = 30;
		this.mass = 0.06;
		this.gravity = 0.1;
		this.reset();
	}
	
	public void updateStatus(int impulseDirection) {
		if (impulseDirection == -1 && velocity > 0)
			velocity = 0;
		if (impulseDirection == 1 && velocity < 0)
			velocity = 0;
		
		this.acceleration = impulseDirection * IMPULSE;
		
		acceleration += 0.3 * gravity * Math.sin(Math.toRadians(GameController.getBalancerCanvas().getBar().getAngularDisplacement()));
		velocity += acceleration;
		displacement += velocity;
	}

	public double getTorque() {
		return (displacement-250) * gravity * mass;
	}
	
	public double getDisplacement() {
		return displacement;
	}
	
	public double getDiameter() {
		return diameter;
	}

	public void reset() {
		this.displacement = 250; 
		this.velocity = 0;
	}
}
