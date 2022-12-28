package components.arrowdodge;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import logic.GameController;

public class ArrowDodgeCanvas extends Canvas {

	private int playerSlotIndex;
	private final double PLAYER_FRAME_X;
	private final double PLAYER_FRAME_Y;
	private final double PLAYER_SLOT_WIDTH = 20;
	private final double PLAYER_SLOT_HEIGHT = 40;
	private final int SLOTS = 5;
	
	private boolean spawnLeft;
	private ArrayList<Arrow> activeArrows;

	public ArrowDodgeCanvas(double width, double height) {
		super(width, height);
		this.PLAYER_FRAME_X = (width - this.PLAYER_SLOT_WIDTH) / 2;
		this.PLAYER_FRAME_Y = (height - this.PLAYER_SLOT_HEIGHT * this.SLOTS) / 2;
		this.reset();
	}

	public void startSpawningArrows() { 
		/*
		 * TODO 3.1) The following codes will freeze the program. 
		 * Turn this section into a separate thread.
		 * Make sure the thread is started when this method is called.
		 */
		Thread t = new Thread(() -> {
			while (!GameController.isGameEnded()) {
				Arrow a = new Arrow(this.spawnLeft);
				activeArrows.add(a);
				this.spawnLeft = !this.spawnLeft;
				try {
					Thread.sleep(new Random().nextInt(800) + 2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		/*
		 * END OF 3.1)
		 */
	}

	public void updateStatus() {
		for (int i=this.activeArrows.size()-1;i>=0;i--) {
			Arrow a = this.activeArrows.get(i);
			a.updateStatus();
			
			if (
					a.getDirection()==-1 && a.getTipX() < this.getWidth()/2 - 50 || 
					a.getDirection()==1 && a.getTipX() > this.getWidth()/2 + 50
				) {
				this.activeArrows.remove(i);
			}
		}
	}

	public void redraw() {
		GraphicsContext gc = this.getGraphicsContext2D();

		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		gc.setFill(Color.LIGHTBLUE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		  		
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < 5; i++) {
			gc.strokeRect(PLAYER_FRAME_X, PLAYER_FRAME_Y + i * PLAYER_SLOT_HEIGHT, PLAYER_SLOT_WIDTH, PLAYER_SLOT_HEIGHT); // draw the five slots
		}

		gc.setFill(Color.BLUE);
		gc.fillRect(PLAYER_FRAME_X, PLAYER_FRAME_Y + playerSlotIndex * PLAYER_SLOT_HEIGHT, PLAYER_SLOT_WIDTH, PLAYER_SLOT_HEIGHT); // draw player
		
		for (int i=0;i<this.activeArrows.size();i++) {
			Arrow a = this.activeArrows.get(i);
			double upperX = a.getTipX() - a.getDirection() * a.getARROW_LENGTH();
			double lowerX = a.getTipX() - a.getDirection() * a.getARROW_LENGTH();
			double tipY = this.PLAYER_FRAME_Y + this.PLAYER_SLOT_HEIGHT/2 + a.getLevel()*this.PLAYER_SLOT_HEIGHT;
			double upperY = tipY - a.getARROW_WIDTH();
			double lowerY = tipY + a.getARROW_WIDTH();
			
			double xpoints[] = {a.getTipX(), upperX, lowerX};
			double ypoints[] = {tipY, upperY, lowerY};
			
			gc.setFill(Color.BLACK);
			gc.fillPolygon(xpoints, ypoints, 3);
		}
	}
	
	public void setPlayerSlotIndex(KeyCode key) {
		switch (key) {
		case UP:
			playerSlotIndex = playerSlotIndex == 0 ? 0 : playerSlotIndex - 1;
			break;

		case DOWN:
			playerSlotIndex = playerSlotIndex == 4 ? 4 : playerSlotIndex + 1;
			break;
			
		default:
			break;
		}
	}

	public boolean isPlayerHit() {
		for (int i=0;i<this.activeArrows.size();i++) {
			Arrow a = this.activeArrows.get(i);
			if (playerSlotIndex == a.getLevel()) {
				if (a.getDirection() == -1) {
					if (a.getTipX() < (this.getWidth() + this.PLAYER_SLOT_WIDTH) / 2 &&
						a.getTipX() > (this.getWidth() - this.PLAYER_SLOT_WIDTH) / 2) {
						return true;
					}
				} else {
					if (a.getTipX() > (this.getWidth() - this.PLAYER_SLOT_WIDTH) / 2 &&
						a.getTipX() < (this.getWidth() + this.PLAYER_SLOT_WIDTH) / 2) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void reset() {
		this.spawnLeft = false;
		this.playerSlotIndex = 2;
		this.activeArrows = new ArrayList<Arrow>();
	}
}
