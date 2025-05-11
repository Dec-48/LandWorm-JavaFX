package gui.scene;

import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameCanvas;
import game.controller.GameController;
import game.object.GridBox;
import game.object.Player;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import sharedObject.RenderableHolder;
import map.GameplayBackground;

public class GamePlayScene extends StackPane implements ChangeableScene{
	private static GamePlayScene instance;
	private AnimationTimer animation;
	private HBox backButtonPane;
	GameCanvas gameCanvas;
	
	public GamePlayScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
				
		gameCanvas = new GameCanvas(1000, 580); 
		gameCanvas.addListerner();
		
		initializeBackButton();
	
		this.getChildren().addAll(gameCanvas,backButtonPane);
		gameCanvas.requestFocus(); 
		
		GameController.getInstance().initialGame();
		
		animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				GameController.getInstance().update();
				gameCanvas.paintComponent(); 				
			}
		};
		animation.start();
	}
	
	private void initializeBackButton() {
		Image redBtn = new Image(ClassLoader.getSystemResource("Image/BACKbutton2.png").toString());
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		Canvas backButton = new Canvas(60,60);
		backButton.getGraphicsContext2D().drawImage(redBtn,5,5,50,50);
		hbox.getChildren().add(backButton);
		hbox.setPadding(new Insets(0,600,15,15));
		
		hbox.setOnMouseEntered(e -> {
			backButton.setCursor(Cursor.HAND);
			backButton.getGraphicsContext2D().drawImage(redBtn,0,0,60,60);
		});
		
		backButton.setOnMouseExited(e -> {
			backButton.setCursor(Cursor.DEFAULT);
			backButton.getGraphicsContext2D().clearRect(0, 0,60,60);
			backButton.getGraphicsContext2D().drawImage(redBtn,5,5,50,50);
		});
		
		backButton.setOnMouseClicked(e -> {
			AudioManager.playEffect("Audio/ClickEffect.mp3");
			SceneManager.getInstance().setScene(PlayerSettingScene.getInstance());
		});

		this.backButtonPane = hbox;
	}
	
	public void start(SceneManager sceneManager) {
		Scene scene = new Scene(this, 1000, 600);
		sceneManager.getStage().setScene(scene);
		sceneManager.getStage().show();
		AudioManager.playBGM("Audio/GamePlayBGM.mp3");
		scene.setOnKeyPressed(e -> InputUtility.setKeyPressed(e.getCode(), true));
        scene.setOnKeyReleased(e -> InputUtility.setKeyPressed(e.getCode(), false));
	}
	
	public void stop(SceneManager sceneManager) {
		AudioManager.stopBGM();
		animation.stop(); //
		instance = null;
		GameController.stopGameController();
		RenderableHolder.getInstance().clearHolder();
		gameCanvas = null;
	}

	public static GamePlayScene getInstance() {
		if (instance == null) {
			instance = new GamePlayScene();
		}
		return instance;
	}
}
