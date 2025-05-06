package gui.scene;

import gui.MenuPane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class MainMenuState extends StackPane {
	private static MainMenuState instance;
	private MenuPane menupane;
	private Canvas background;
	
	private MainMenuState() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		this.background = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground1.PNG").toString());
		Image secondBackgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground2.PNG").toString());
		this.background.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		this.menupane = MenuPane.getInstance();
		this.getChildren().addAll(background,menupane);
		new Thread(() ->{
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
		}).start();
	}
	
	public static MainMenuState getInstance() {
		if (instance == null) {
			instance = new MainMenuState();
		}
		return instance;
	}
}
