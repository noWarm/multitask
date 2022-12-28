package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GameController;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
        System.out.println("Hello, World!");
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
		GameController.setupScene();
		GameController.setKeyboardBindings();
		
		primaryStage.setScene(GameController.getScene());
		primaryStage.setTitle("MULTITASK CLONE");
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	       @Override
	       public void handle(WindowEvent e) {
	          Platform.exit();
	          System.exit(0);
	       }
		});
	}

}
