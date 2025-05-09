package application;

import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameController;
import gui.scene.MainMenuScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("LandWorm");
		primaryStage.getIcons().add(new Image(ClassLoader.getSystemResource("Image/GameIcon.PNG").toString()));
		primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            AudioManager.stopBGM(); 
            Platform.exit();       
            System.exit(0);         
        });
//      GameController.
        SceneManager.getInstance(primaryStage).setScene(MainMenuScene.getInstance());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

