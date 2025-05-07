package gui.scene;

import gui.ButtonPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utilities.AudioManager;

public class MainMenuState extends StackPane {
	private static MainMenuState instance;
	private static ButtonPane BUTTONPANE;
	private static Canvas BACKGROUND;
	
	private MainMenuState() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		BACKGROUND = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground1.PNG").toString());
		Image secondBackgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground2.PNG").toString());
		BACKGROUND.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		
		BUTTONPANE = ButtonPane.getInstance();
	
		this.getChildren().addAll(BACKGROUND,BUTTONPANE);
		AudioManager.playBGM("Audio/MainMenuBGM.mp3");
		
		new Thread(() ->{
			while (true) {
				try {
					Thread.sleep(800);
					Platform.runLater(() ->{
						BACKGROUND.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						BACKGROUND.getGraphicsContext2D().drawImage(secondBackgroundImage, 0,0,1000,600);
					});
					Thread.sleep(800);
					Platform.runLater(() ->{
						BACKGROUND.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						BACKGROUND.getGraphicsContext2D().drawImage(backgroundImage, 0,0,1000,600);
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	public static ButtonPane getButtonPane() {
		return BUTTONPANE;
	}

	public static Canvas getBackgroundCanvas() {
		return BACKGROUND;
	}

	public static MainMenuState getInstance() {
		if (instance == null) {
			instance = new MainMenuState();
		}
		return instance;
	}
}
