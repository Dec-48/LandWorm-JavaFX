package gui.scene;

import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameCanvas;
import game.controller.GameController;
import game.object.GridBox;
import game.object.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import sharedObject.RenderableHolder;

public class GamePlayScene extends StackPane implements ChangeableScene{
	private static GamePlayScene instance;
	private boolean running = true;
	
	
	public GamePlayScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		GameCanvas gameCanvas = new GameCanvas(1000, 580); 
		gameCanvas.addListerner();
	
		// TAwan Code since here v
		this.getChildren().add(gameCanvas);
		gameCanvas.requestFocus(); 
		
		GameController gameController = new GameController();
		gameController.initialGame();
		GridBox[][] grid = gameController.getGrid();
		Player playerA = gameController.getPlayerA();
		
		System.out.println(GameController.getInstance().getA_stringColor());  //File trying this and yes! playerA's color already change from that choosing page bue the gameplay still red and blue.
		
		Player playerB = gameController.getPlayerB();
		
		playerA.setZ(2);
		playerB.setZ(2); // I have to do this cause the grid will draw over playB blue dot :(
		
//		*** TEMPORARY USAGE NAKUB 
//		** We must not add to RenderableHolder here but in our GameController instead!
		for (GridBox[] i : grid) {
			for (GridBox j : i) {
				RenderableHolder.getInstance().add(j);
			}
		}
		
		RenderableHolder.getInstance().add(playerA);
		RenderableHolder.getInstance().add(playerB);
		
		AnimationTimer animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameController.update();
				gameCanvas.paintComponent(); 				
			}
		};
		animation.start();
	}
	
	public void start(SceneManager sceneManager) {
		Scene scene = new Scene(this, 1000, 600);
		sceneManager.getStage().setScene(scene);
		sceneManager.getStage().show();
		AudioManager.playBGM("Audio/MainMenuBGM.mp3");
	}
	
	public void stop(SceneManager sceneManager) {
		running = false;
		AudioManager.stopBGM();
		instance = null;
	}

	public static GamePlayScene getInstance() {
		if (instance == null) {
			instance = new GamePlayScene();
		}
		return instance;
	}
}
