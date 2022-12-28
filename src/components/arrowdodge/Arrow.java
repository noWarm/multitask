package components.arrowdodge;

import java.util.Random;

public class Arrow {
	
	private int level;
	private int direction; // TO LEFT=-1 TO RIGHT=1
	private double velocity;
	private double tipX;
	
	private final double ARROW_LENGTH = 20;
	private final double ARROW_WIDTH = 10;
	
	public Arrow(boolean spawnLeft) {
		this.level = new Random().nextInt(5);
		this.velocity = new Random().nextInt(2) + 0.5;
		this.direction = spawnLeft ? -1 : 1;
		this.tipX = spawnLeft ? 400 : 100;
	}
	
	public void updateStatus() {
		this.tipX += this.velocity * this.direction;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public double getTipX() {
		return this.tipX;
	}
	
	public int getDirection() {
		return this.direction;
	}

	public double getARROW_LENGTH() {
		return ARROW_LENGTH;
	}

	public double getARROW_WIDTH() {
		return ARROW_WIDTH;
	}
	
}
