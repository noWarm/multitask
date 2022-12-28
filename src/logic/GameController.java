package logic;

import components.arrowdodge.ArrowDodgeCanvas;
import components.balancer.BalancerCanvas;
import components.scoring.ScorePane;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameController {
	private static VBox root;
	private static ScorePane scorePane;
	private static BalancerCanvas balancerCanvas;
	private static ArrowDodgeCanvas arrowDodgeCanvas;
	private static HBox gamePane;
	private static Scene scene;
	private static boolean isGameEnded;

	public static void setupScene() {
		root = new VBox();
		scorePane = new ScorePane();
		balancerCanvas = new BalancerCanvas(500, 500);
		arrowDodgeCanvas = new ArrowDodgeCanvas(500, 500);
		gamePane = new HBox();

		gamePane.getChildren().addAll(balancerCanvas, arrowDodgeCanvas);
		root.getChildren().addAll(scorePane, gamePane);
		scene = new Scene(root, 1000, 530);
	}
	public static void setKeyboardBindings() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				switch(key.getCode()) {	
				case UP:
				case DOWN:
					arrowDodgeCanvas.setPlayerSlotIndex(key.getCode());
					break;
				case LEFT:
					balancerCanvas.setLastKeyPressed(KeyCode.LEFT);
					balancerCanvas.setLeftPressed(true);
					break;
				case RIGHT:
					balancerCanvas.setLastKeyPressed(KeyCode.RIGHT);
					balancerCanvas.setRightPressed(true);
					break;
				
				default:
					break;
				}
			}
		});
	
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				switch (key.getCode()) {
				case LEFT:
					balancerCanvas.setLeftPressed(false);
					break;
				case RIGHT:
					balancerCanvas.setRightPressed(false);
					break;
				default:
					break;
				}
			}
		});
	}

	public static void init() {
		isGameEnded = false;
		balancerCanvas.reset();
		scorePane.reset();	
		arrowDodgeCanvas.reset(); // NOTE : IF THE PROGRAM IS NOT 'THREADIFY', THIS LINE ON WILL NEVER BE EXECUTED
		arrowDodgeCanvas.startSpawningArrows();
		startGameLoop();
	}

	private static void startGameLoop() {
		/*
		 * TODO 3.3.1) The following codes will freeze the program. 
		 * Turn this entire section into a separate thread. (See END OF 3.3.1))
		 * Make sure the thread is started when this method is called.
		 */
		Thread t = new Thread(() -> {
			while (!GameController.isGameEnded()) {
				balancerCanvas.updateStatus();
				arrowDodgeCanvas.updateStatus();
				/*
				 * TODO 3.3.2) The following codes modifies the GUI.
				 * Make sure that it is run on a JavaFX Thread.
				 */
				Platform.runLater(() -> {
					balancerCanvas.redraw();
					arrowDodgeCanvas.redraw();
					scorePane.setScoreText(scorePane.getScore());
				});
				/*
				 * END OF 3.3.2)
				 */
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateGameEndStatus();
			}
		});
		t.start();
		/*
		 * END OF 3.3.1)
		 */
	}
	
	private static void updateGameEndStatus() {
		if (balancerCanvas.isBallOutOfBound() || arrowDodgeCanvas.isPlayerHit()) {
			/*
			 * TODO 3.4) The following codes modifies the GUI.
			 * Make sure that it is run on a JavaFX Thread.
			 */
			Platform.runLater(() -> {
				balancerCanvas.redraw();
				arrowDodgeCanvas.redraw();
				scorePane.setGameOverText("GAME OVER");
				scorePane.getStartButton().setDisable(false);
			});
			/*
			 * END OF 3.4)
			 */
			isGameEnded = true;
		}
	}
	
	public static BalancerCanvas getBalancerCanvas() {
		return balancerCanvas;
	}

	public static boolean isGameEnded() {
		return isGameEnded;
	}

	public static Scene getScene() {
		return scene;
	}
}
