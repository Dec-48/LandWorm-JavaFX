package gui.scene;

import Manager.AudioManager;
import Manager.SceneManager;
import gui.ButtonPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuScene extends StackPane implements ChangeableScene {
	private static MainMenuScene instance;
	private static ButtonPane BUTTONPANE;
	private Canvas background;
	private Thread backgroundThread;
	
	private MainMenuScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		background = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground1.png").toString());
		Image secondBackgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground2.png").toString());
		background.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		
		BUTTONPANE = ButtonPane.getInstance();
	
		this.getChildren().addAll(background,BUTTONPANE);
		AudioManager.playBGM("Audio/MainMenuBGM.mp3");
		
		backgroundThread = new Thread(() ->{
			while (true) {
				try {
					Thread.sleep(800);
					Platform.runLater(() ->{
						background.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						background.getGraphicsContext2D().drawImage(secondBackgroundImage, 0,0,1000,600);
					});
					Thread.sleep(800);
					Platform.runLater(() ->{
						background.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						background.getGraphicsContext2D().drawImage(backgroundImage, 0,0,1000,600);
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		backgroundThread.start();
	}
	

	@Override
    public void start(SceneManager sceneManager) {
        // Create and set Scene with this StackPane
        Scene scene = new Scene(this, 1000, 600);
        sceneManager.getStage().setScene(scene);
        sceneManager.getStage().show();
    }
	
	public void stop(SceneManager sceneManager) {
		backgroundThread.interrupt();
		AudioManager.stopBGM();
	}

	public static MainMenuScene getInstance() {
		if (instance == null) {
			instance = new MainMenuScene();
		}
		return instance;
	}
	
}
