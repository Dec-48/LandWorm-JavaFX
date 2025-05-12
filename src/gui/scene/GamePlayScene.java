package gui.scene;

import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameCanvas;
import game.controller.GameController;
import game.object.GridBox;
import game.object.Player;
import gui.ResultPane;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sharedObject.RenderableHolder;
import map.GameplayBackground;

public class GamePlayScene extends StackPane implements ChangeableScene{
	private static GamePlayScene instance;
	private AnimationTimer animation;
	private HBox backButtonPane;
	private GameCanvas gameCanvas;
	private HBox hboxCanvas;
	private HBox timeLeftPane;
	private int timeLeft = 20;
	private int remainingTime; 
	private Label timeShow;
	private ResultPane resultPane;
	private boolean isCountdown = false;
	private final double MAX_FPS = 120;
	private final long TARGET_NANOSECONDS = (long) (1_000_000_000.0 / MAX_FPS);
	private long lastUpdateTime = 0;
	
	public GamePlayScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
				
		gameCanvas = new GameCanvas(1000, 600); 
		gameCanvas.addListerner();
		hboxCanvas = new HBox();
		hboxCanvas.setPadding(new Insets(40,0,0,0));
		hboxCanvas.getChildren().add(gameCanvas);
		initializeBackButton();
		initializeTimeLeftPane();
	
		StackPane.setAlignment(timeLeftPane, Pos.TOP_CENTER);
		this.getChildren().addAll(hboxCanvas,backButtonPane,timeLeftPane);
		gameCanvas.requestFocus(); 
		
		GameController.getInstance().initialGame();
		 
		long startTime = System.nanoTime();
		
		remainingTime = timeLeft; 
	    timeShow.setText(Integer.toString(remainingTime)); 
	    
		animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - lastUpdateTime >= TARGET_NANOSECONDS) {

					int elapsedSeconds = (int) ((now - startTime) / 1_000_000_000L); // แปลงนาโนเซ็กนด์เป็นวินาที
					remainingTime = timeLeft - elapsedSeconds;
					timeShow.setText(Integer.toString(remainingTime) + " s");

					GameController.getInstance().update(elapsedSeconds);
					gameCanvas.paintComponent();

					if (remainingTime <= 0) {
						this.stop();
						gameTimesUp();
					} else if (remainingTime == 4 && !isCountdown) {
						AudioManager.playEffect("Audio/lastSecondCountdown.mp3");
						isCountdown = true;
					}
					lastUpdateTime = now;
				}
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
		hbox.setPadding(new Insets(0,930,15,15));
		
		backButton.setOnMouseEntered(e -> {
			backButton.setCursor(Cursor.HAND);
			backButton.getGraphicsContext2D().clearRect(0, 0,60,60);
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
	
	private void initializeTimeLeftPane() {
		HBox hbox = new HBox();
		hbox.setMaxWidth(1000);
		hbox.setMaxHeight(22);
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle(
			    "-fx-background-color: #3B82F6;" +        // Light blue background
			    "-fx-border-color: #1E3A8A;" +            // Dark blue border
			    "-fx-border-width: 3;" +                  // Border thickness (in pixels)
			    "-fx-border-radius: 6;" +                 // Optional: rounded corners
			    "-fx-background-radius: 6;"               // Match border radius if using it
			);
	    timeShow = new Label(	Integer.toString(remainingTime)+ " s");
        Font font = Font.loadFont(getClass().getResourceAsStream("/Font/PressStart2P-Regular.ttf"), 12);
        timeShow.setFont(font);
        timeShow.setTextFill(Color.WHITE);
        hbox.getChildren().add(timeShow);
        
	    timeLeftPane = hbox;
	}
	
	private void gameTimesUp() {
		resultPane = ResultPane.getInstance();
		this.getChildren().add(resultPane);
		AudioManager.playEffect("Audio/gameFinish.wav");
		new Thread( () ->{
			try {
				AudioManager.stopBGM();
				Thread.sleep(3000);
				Platform.runLater(() -> SceneManager.getInstance().setScene(PlayerSettingScene.getInstance()) ); //go back to playerSettingScene after 3s
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
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
		animation.stop(); 
		instance = null;
		GameController.stopGameController();
		RenderableHolder.getInstance().clearHolder();
		gameCanvas = null;
		ResultPane.resetResultPane();
	}

	public static GamePlayScene getInstance() {
		if (instance == null) {
			instance = new GamePlayScene();
		}
		return instance;
	}
}
