package gui.scene;


import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameController;
import gui.ButtonPane;
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

public class PlayerSettingScene extends StackPane implements ChangeableScene {
	private static PlayerSettingScene instance;
	private Canvas background;
	private VBox settingPane;
	private Label chooseYourColor;
	private Label playerKeypress;
	private HBox wormPane;
	private HBox colorSelectPane;
	private Canvas backButton;
	private Canvas startButton;
	private boolean running = true;
	
	private PlayerSettingScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		background = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/PlayerSettingBackground.png").toString());
		background.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		
		initializeLabel();
		initializeShow2WormColorPane();
		initializeColorSelectingPane();
		initializeBackPane();
		initializeStartPlayingPane();
		initializeSettingPane();
		
		this.getChildren().addAll(background,settingPane);
	}
	
	private void initializeSettingPane() {
		settingPane = new VBox();
		settingPane.setPrefHeight(500);
		settingPane.setPrefWidth(800);
		settingPane.setAlignment(Pos.TOP_CENTER);
		settingPane.setSpacing(30);
		settingPane.setPadding(new Insets(70,0,0,0));
		HBox hbox = new HBox();
		hbox.getChildren().addAll(backButton,startButton);
		hbox.setSpacing(700);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(-40,0,0,0));
		settingPane.getChildren().addAll(chooseYourColor,playerKeypress,wormPane,colorSelectPane,hbox);
	}
	
	private void initializeLabel() {
		chooseYourColor = new Label("Choose Your Color!");
		playerKeypress = new Label("   Left Player [ W A S D ]             Right Player [ Arrow Keys ]");
        Font firstFont = Font.loadFont(getClass().getResourceAsStream("/Font/PressStart2P-Regular.ttf"), 30);
        Font secondFont = Font.loadFont(getClass().getResourceAsStream("/Font/PressStart2P-Regular.ttf"), 13);
        chooseYourColor.setFont(firstFont);
        chooseYourColor.setTextFill(Color.YELLOW);
        playerKeypress.setFont(secondFont);
        playerKeypress.setTextFill(Color.GRAY);
        playerKeypress.setPadding(new Insets(25,0,0,0));
	}
	
	
	private void initializeShow2WormColorPane() {
		wormPane = new HBox();
		wormPane.setPrefHeight(200);
		wormPane.setPrefWidth(800);
		wormPane.setSpacing(270);
		wormPane.setPadding(new Insets(70,0,0,0));
		wormPane.setAlignment(Pos.CENTER);
		
		Canvas wormA = new Canvas(200,200);
		Image startwormAImage = new Image(ClassLoader.getSystemResource("Image/Redworm/RedwormH1.png").toString());
		wormA.getGraphicsContext2D().drawImage(startwormAImage,0,0,200,200);
		
		Canvas wormB = new Canvas(200,200);
		Image startwormBImage = new Image(ClassLoader.getSystemResource("Image/Blueworm/BluewormH1.png").toString());
		wormB.getGraphicsContext2D().drawImage(startwormBImage,0,0,200,200);
		
		Thread backgroundThread = new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(200);
					Platform.runLater(() -> {
						wormA.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormB.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormA.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getA_stringColor()
												+ "worm/" + GameController.getInstance().getA_stringColor() + "wormH2.png")
										.toString()), 0, 0, 200, 200);
						wormB.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getB_stringColor()
												+ "worm/" + GameController.getInstance().getB_stringColor() + "wormH2.png")
										.toString()), 0, 0, 200, 200);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						wormA.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormB.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormA.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getA_stringColor()
												+ "worm/" + GameController.getInstance().getA_stringColor() + "wormH3.png")
										.toString()), 0, 0, 200, 200);
						wormB.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getB_stringColor()
												+ "worm/" + GameController.getInstance().getB_stringColor() + "wormH3.png")
										.toString()), 0, 0, 200, 200);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						wormA.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormB.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormA.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getA_stringColor()
												+ "worm/" + GameController.getInstance().getA_stringColor() + "wormH2.png")
										.toString()), 0, 0, 200, 200);
						wormB.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getB_stringColor()
												+ "worm/" + GameController.getInstance().getB_stringColor() + "wormH2.png")
										.toString()), 0, 0, 200, 200);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						wormA.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormB.getGraphicsContext2D().clearRect(0, 0, 200, 200);
						wormA.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getA_stringColor()
												+ "worm/" + GameController.getInstance().getA_stringColor() + "wormH1.png")
										.toString()), 0, 0, 200, 200);
						wormB.getGraphicsContext2D()
								.drawImage(new Image(ClassLoader
										.getSystemResource("Image/" + GameController.getInstance().getB_stringColor()
												+ "worm/" + GameController.getInstance().getB_stringColor() + "wormH1.png")
										.toString()), 0, 0, 200, 200);

					});	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		backgroundThread.start();
		
		wormPane.getChildren().addAll(wormA,wormB);
	}
	
	private void initializeColorSelectingPane() { // pick 2 player color **default is red and blue**
		colorSelectPane = new HBox();
		colorSelectPane.setPrefWidth(800);
		colorSelectPane.setPrefHeight(60);
		colorSelectPane.setSpacing(150);
		colorSelectPane.setPadding(new Insets(-60,0,0,0));
		colorSelectPane.setAlignment(Pos.CENTER);
		
		Image redButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/RedButton.PNG").toString());
		Image yellowButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/YellowButton.PNG").toString());
		Image greenButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/GreenButton.PNG").toString());
		Image blueButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/BlueButton.PNG").toString());
		Image pinkButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/PinkButton.PNG").toString());
		
		Canvas redButtonA = new Canvas(60,60);
		redButtonA.getGraphicsContext2D().drawImage(redButtonImage,0,0,60,60);
		redButtonA.setOnMouseClicked(e -> {
			if ((GameController.getInstance().getB_color() != Color.RED) && (GameController.getInstance().getB_color() != Color.DARKRED)) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.RED, Color.RED);
			}
		});
		Canvas yellowButtonA = new Canvas(60,60);
		yellowButtonA.getGraphicsContext2D().drawImage(yellowButtonImage,0,0,60,60);
		yellowButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.YELLOW) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.YELLOW, Color.YELLOW);
			}
		});
		Canvas greenButtonA = new Canvas(60,60);
		greenButtonA.getGraphicsContext2D().drawImage(greenButtonImage,0,0,60,60);
		greenButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.GREEN) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.GREEN, Color.GREEN);
			}
		});
		Canvas blueButtonA = new Canvas(60,60);
		blueButtonA.getGraphicsContext2D().drawImage(blueButtonImage,0,0,60,60);
		blueButtonA.setOnMouseClicked(e -> {
			if ((GameController.getInstance().getB_color() != Color.BLUE)&& (GameController.getInstance().getB_color() != Color.DARKBLUE)) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.BLUE, Color.BLUE);
			}
		});
		Canvas pinkButtonA = new Canvas(60,60);
		pinkButtonA.getGraphicsContext2D().drawImage(pinkButtonImage,0,0,60,60);
		pinkButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.PINK) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.PINK, Color.PINK);
			}
		});
		
		Canvas redButtonB = new Canvas(60,60);
		redButtonB.getGraphicsContext2D().drawImage(redButtonImage,0,0,60,60);
		redButtonB.setOnMouseClicked(e -> {
			if ((GameController.getInstance().getA_color() != Color.RED)&& (GameController.getInstance().getA_color() != Color.DARKRED)) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.RED, Color.RED);
			}
		});
		Canvas yellowButtonB = new Canvas(60,60);
		yellowButtonB.getGraphicsContext2D().drawImage(yellowButtonImage,0,0,60,60);
		yellowButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.YELLOW) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.YELLOW, Color.YELLOW);
			}
		});
		Canvas greenButtonB = new Canvas(60,60);
		greenButtonB.getGraphicsContext2D().drawImage(greenButtonImage,0,0,60,60);
		greenButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.GREEN) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.GREEN, Color.GREEN);
			}
		});
		Canvas blueButtonB = new Canvas(60,60);
		blueButtonB.getGraphicsContext2D().drawImage(blueButtonImage,0,0,60,60);
		blueButtonB.setOnMouseClicked(e -> {
			if ((GameController.getInstance().getA_color() != Color.BLUE)&& (GameController.getInstance().getA_color() != Color.DARKBLUE)) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.BLUE, Color.BLUE);
			}
		});
		Canvas pinkButtonB = new Canvas(60,60);
		pinkButtonB.getGraphicsContext2D().drawImage(pinkButtonImage,0,0,60,60);
		pinkButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.PINK) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.PINK, Color.PINK);
			}
		});
		
		HBox playerASelectPane = new HBox();
		playerASelectPane.setPrefWidth(340);
		playerASelectPane.setPrefHeight(60);
		playerASelectPane.setSpacing(10);
		playerASelectPane.getChildren().addAll(redButtonA,yellowButtonA,greenButtonA,blueButtonA,pinkButtonA);
		
		HBox playerBSelectPane = new HBox();
		playerBSelectPane.setPrefWidth(340);
		playerBSelectPane.setPrefHeight(60);
		playerBSelectPane.setSpacing(10);
		playerBSelectPane.getChildren().addAll(redButtonB,yellowButtonB,greenButtonB,blueButtonB,pinkButtonB);
		
		colorSelectPane.getChildren().addAll(playerASelectPane,playerBSelectPane);
	}
	
	private void initializeBackPane() {
		backButton = new Canvas(100,80);
		Image backImage = new Image(ClassLoader.getSystemResource("Image/BACKbutton.png").toString());
		backButton.getGraphicsContext2D().drawImage(backImage,5,5,90,70);
		
		backButton.setOnMouseEntered(e -> {
			backButton.setCursor(Cursor.HAND);
			backButton.getGraphicsContext2D().drawImage(backImage,0,0,100,80);
		});
		
		backButton.setOnMouseExited(e -> {
			backButton.setCursor(Cursor.DEFAULT);
			backButton.getGraphicsContext2D().clearRect(0, 0, 100,80 );
			backButton.getGraphicsContext2D().drawImage(backImage,5,5,90,70);
		});
		
		backButton.setOnMouseClicked(e -> {
			AudioManager.playEffect("Audio/ClickEffect.mp3");
			SceneManager.getInstance().setScene(MainMenuScene.getInstance());
		});
		

	}
	
	private void initializeStartPlayingPane(){
		startButton = new Canvas(140,110);
		Image startImage = new Image(ClassLoader.getSystemResource("Image/STARTbutton.png").toString());
		startButton.getGraphicsContext2D().drawImage(startImage,5,5,130,100);
		
		startButton.setOnMouseEntered(e -> {
			startButton.setCursor(Cursor.HAND);
			startButton.getGraphicsContext2D().drawImage(startImage,0,0,140,110);
		});
		
		startButton.setOnMouseExited(e -> {
			startButton.setCursor(Cursor.DEFAULT);
			startButton.getGraphicsContext2D().clearRect(0, 0, 140,110 );
			startButton.getGraphicsContext2D().drawImage(startImage,5,5,130,100);
		});
		
		startButton.setOnMouseClicked(e -> {
			AudioManager.playEffect("Audio/ClickEffect.mp3");
			SceneManager.getInstance().setScene(GamePlayScene.getInstance());
		});
		
	}
	 
    public void start(SceneManager sceneManager) {
        Scene scene = new Scene(this, 1000, 600);
		
        sceneManager.getStage().setScene(scene);
        sceneManager.getStage().show();
        AudioManager.playBGM("Audio/PlayerSettingColorBGM.mp3");
    }
	
	public void stop(SceneManager sceneManager) {
		running = false; 
		AudioManager.stopBGM();
		instance = null;
	}
	
	public static PlayerSettingScene getInstance() {
		if (instance == null) {
			instance = new PlayerSettingScene();
		}
		return instance;
	}
}
