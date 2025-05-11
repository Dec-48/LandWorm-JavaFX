package gui.scene;

import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameCanvas;
import game.controller.GameController;
import game.object.GridBox;
import game.object.Player;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import sharedObject.RenderableHolder;
import map.GameplayBackground;

public class GamePlayScene extends StackPane implements ChangeableScene{
	private static GamePlayScene instance;
	private AnimationTimer animation;
	
	public GamePlayScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
				
		GameCanvas gameCanvas = new GameCanvas(1000, 580); 
		gameCanvas.addListerner();
	
		this.getChildren().addAll(gameCanvas);
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
	}

	public static GamePlayScene getInstance() {
		if (instance == null) {
			instance = new GamePlayScene();
		}
		return instance;
	}
}
