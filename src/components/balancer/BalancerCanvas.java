package components.balancer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class BalancerCanvas extends Canvas {
	private Ball ball;
	private Bar bar;
	
	private KeyCode lastKeyPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private int impulseDirection;
	
	public BalancerCanvas(double width, double height) {
		super(width, height);
		bar = new Bar();
		ball = new Ball();
		this.reset();
	}
	
	public void updateStatus() {
		this.updateImpulseDirection();
		ball.updateStatus(this.impulseDirection);
		bar.updateStatus(this.impulseDirection);
	}

	public void redraw() {
		GraphicsContext gc = this.getGraphicsContext2D();
		
		double barTopLeftX = (this.getWidth() - bar.getWidth()) / 2;
		double barTopLeftY = (this.getHeight() - bar.getHeight()) / 2;
		
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		gc.setFill(Color.SANDYBROWN);
		gc.fillRect(0,  0,  this.getWidth(), this.getHeight());
		
		gc.save();
		gc.translate(this.getWidth()/2, this.getHeight()/2);
		gc.rotate(bar.getAngularDisplacement());
		gc.translate(-this.getWidth()/2, -this.getHeight()/2);
		
		gc.setFill(Color.BLACK);
		gc.fillRect(barTopLeftX, barTopLeftY, bar.getWidth(), bar.getHeight());
		
		gc.setFill(Color.MAROON);
		gc.fillOval(ball.getDisplacement()-ball.getDiameter()/2, barTopLeftY-ball.getDiameter(), ball.getDiameter(), ball.getDiameter());
		gc.restore();
	}
	
	private void updateImpulseDirection() {
		if (lastKeyPressed == null) {	    			
			if (leftPressed) {
				impulseDirection = -1;
			} else if (rightPressed){
				impulseDirection = 1;
			} else {
				impulseDirection = 0;
			}
		} else if (lastKeyPressed == KeyCode.LEFT){
			if (leftPressed) {
				impulseDirection = -1;
			} else if (rightPressed){
				impulseDirection = 1;
			} else {
				impulseDirection = 0;
			}
		} else if (lastKeyPressed == KeyCode.RIGHT) {
			if (rightPressed) {
				impulseDirection = 1;
			} else if (leftPressed){
				impulseDirection = -1;
			} else {
				impulseDirection = 0;
			}
		}
	}
	
	public boolean isBallOutOfBound() {
		return 
				ball.getDisplacement() > this.getWidth() / 2 + bar.getWidth() / 2 + 15 || 
				ball.getDisplacement() < this.getWidth() / 2- bar.getWidth() / 2 - 15;
	}
	
	public void reset() {
		this.ball.reset();
		this.bar.reset();
		this.lastKeyPressed = null;
		this.leftPressed = false;
		this.rightPressed = false;
		this.impulseDirection = 0;
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public Bar getBar() {
		return bar;
	}
	
	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public KeyCode getlastKeyPressed() {
		return lastKeyPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setLastKeyPressed(KeyCode keycode) {
		this.lastKeyPressed = keycode;
	}
	
}
