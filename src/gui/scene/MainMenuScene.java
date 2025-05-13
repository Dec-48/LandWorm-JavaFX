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
	private ButtonPane buttonPane;
	private Canvas background;
	private Thread backgroundThread;
	private boolean running = true;

	private MainMenuScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);

		background = new Canvas(1000, 600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/MainMenuBackground1.png").toString());
		Image secondBackgroundImage = new Image(
				ClassLoader.getSystemResource("Image/MainMenuBackground2.png").toString());
		background.getGraphicsContext2D().drawImage(backgroundImage, 0, 0, 1000, 600);

		buttonPane = ButtonPane.getInstance();

		this.getChildren().addAll(background, buttonPane);

		backgroundThread = new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(800);
					Platform.runLater(() -> {
						background.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						background.getGraphicsContext2D().drawImage(secondBackgroundImage, 0, 0, 1000, 600);
					});
					Thread.sleep(800);
					Platform.runLater(() -> {
						background.getGraphicsContext2D().clearRect(0, 0, 1000, 600);
						background.getGraphicsContext2D().drawImage(backgroundImage, 0, 0, 1000, 600);
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
		AudioManager.playBGM("Audio/MainMenuBGM.mp3");
		Scene scene = new Scene(this, 1000, 600);
		sceneManager.getStage().setScene(scene);
		sceneManager.getStage().show();
	}

	public void stop(SceneManager sceneManager) {

		running = false;
		AudioManager.stopBGM();
		instance = null;
	}

	public static MainMenuScene getInstance() {
		if (instance == null) {
			instance = new MainMenuScene();
		}
		return instance;
	}

}
