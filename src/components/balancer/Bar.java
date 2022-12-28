package components.balancer;

import logic.GameController;

public class Bar {
	
	private double angularDisplacement;
	private double angularVelocity;
	private double angularAcceleration;
	private double dampingConstant;
	private double dampingSign;
	
	private final double ANGULAR_IMPULSE = 0.3;
	
	private double width;
	private double height;
	
	public Bar() {
		this.dampingConstant = 1.1;
		this.width = 250;
		this.height = 10;
		this.reset();
	}
	
	public void updateStatus(int impulseDirection) {
		angularAcceleration = impulseDirection * ANGULAR_IMPULSE;
		
		if (angularVelocity < 0) {
			dampingSign = 1;
		} else {
			dampingSign = -1;
		}
		
		angularAcceleration += GameController.getBalancerCanvas().getBall().getTorque();
		angularAcceleration += dampingSign * dampingConstant * angularVelocity * angularVelocity;
		angularVelocity += angularAcceleration;
		angularDisplacement += angularVelocity;
	}

	public double getAngularDisplacement() {
		return angularDisplacement;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void reset() {
		this.angularDisplacement = 0.01;
		this.angularVelocity = 0;
		this.angularAcceleration = 0;
		this.dampingSign = -1;
	}
}
