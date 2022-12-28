package components.scoring;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameController;

public class ScorePane extends HBox {
	private int score;
	private Text scoreText;
	private Text gameOverText;
	private Button startButton;
	
	public ScorePane() {
		super();
		this.setPrefHeight(30);
		this.setPrefWidth(1000);
		
		scoreText = new Text();
		gameOverText = new Text();
		startButton = new Button("Start");
		
		this.getChildren().addAll(startButton, scoreText, gameOverText);
		
		startButton.setOnAction((e) -> {
			GameController.init();
		});
		
		this.setSpacing(440);
		this.setAlignment(Pos.CENTER_LEFT);
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
	}

	public void startScoreCounting() {
		/*
		 * TODO 3.2) The following codes will freeze the program. 
		 * Turn this section into a separate thread.
		 * Make sure the thread is started when this method is called.
		 */
		Thread t = new Thread(() -> {
			while(!GameController.isGameEnded()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(score);
				score++;
			}
		});
		t.start();
		/*
		 * END OF 3.2)
		 */
	}
	
	public void setGameOverText(String text) {
		gameOverText.setText(text);
	}

	public void setScoreText(int score) {
		scoreText.setText(String.valueOf(score));
	}
	
	public int getScore() {
		return score;
	}

	public Button getStartButton() {
		return startButton;
	}

	public void reset() {
		this.score = 0;
		this.setScoreText(score);		
		this.setGameOverText("         ");
		this.startScoreCounting();
		this.getStartButton().setDisable(true);
	}
}
