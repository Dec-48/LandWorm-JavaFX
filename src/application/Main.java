package application;

import gui.scene.MainMenuState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import utilities.AudioManager;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		StackPane root = MainMenuState.getInstance();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("LandWorm");
		primaryStage.getIcons().add(new Image(ClassLoader.getSystemResource("Image/GameIcon.PNG").toString()));
		primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            AudioManager.stopBGM(); 
            Platform.exit();       
            System.exit(0);         
        });
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

