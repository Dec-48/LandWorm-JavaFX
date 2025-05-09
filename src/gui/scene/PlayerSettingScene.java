package gui.scene;


import Manager.AudioManager;
import Manager.SceneManager;
import game.controller.GameController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	private HBox wormPane;
	private HBox colorSelectPane;
	private boolean running = true;
	
	private PlayerSettingScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		background = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/PlayerSettingBackground.png").toString());
		background.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		
		initializeLabel();
		initializeColorSelectingPane();
		initializeShow2WormColorPane();
		initializeSettingPane();
		
		this.getChildren().addAll(background,settingPane);
	}
	
	private void initializeSettingPane() {
		settingPane = new VBox();
		settingPane.setPrefHeight(500);
		settingPane.setPrefWidth(800);
		settingPane.setAlignment(Pos.TOP_CENTER);
		settingPane.setPadding(new Insets(57,0,0,0));
		settingPane.getChildren().addAll(chooseYourColor,colorSelectPane,wormPane);
	}
	
	private void initializeLabel() {
		chooseYourColor = new Label("Choose Your Color!");
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/Font/PressStart2P-Regular.ttf"), 30);
        if (customFont != null) {
            chooseYourColor.setFont(customFont);
        }
        chooseYourColor.setTextFill(Color.YELLOW);
	}
	
	
	//if worm has it's color already
	private void initializeShow2WormColorPane() { //worm using Thread
		wormPane = new HBox();
		wormPane.setPrefHeight(200);
		wormPane.setPrefWidth(800);
		wormPane.setSpacing(100);
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
		colorSelectPane.setSpacing(200);
		colorSelectPane.setAlignment(Pos.CENTER);
		
		Image redButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/RedButton.PNG").toString());
		Image yellowButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/YellowButton.PNG").toString());
		Image greenButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/GreenButton.PNG").toString());
		Image blueButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/BlueButton.PNG").toString());
		Image pinkButtonImage = new Image(ClassLoader.getSystemResource("Image/ColorButton/PinkButton.PNG").toString());
		
		Canvas redButtonA = new Canvas(60,60);
		redButtonA.getGraphicsContext2D().drawImage(redButtonImage,5,5,50,50);
		redButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.RED) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.RED, Color.RED);
			}
		});
		Canvas yellowButtonA = new Canvas(60,60);
		yellowButtonA.getGraphicsContext2D().drawImage(yellowButtonImage,5,5,50,50);
		yellowButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.YELLOW) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.YELLOW, Color.YELLOW);
			}
		});
		Canvas greenButtonA = new Canvas(60,60);
		greenButtonA.getGraphicsContext2D().drawImage(greenButtonImage,5,5,50,50);
		greenButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.GREEN) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.GREEN, Color.GREEN);
			}
		});
		Canvas blueButtonA = new Canvas(60,60);
		blueButtonA.getGraphicsContext2D().drawImage(blueButtonImage,5,5,50,50);
		blueButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.BLUE) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.BLUE, Color.BLUE);
			}
		});
		Canvas pinkButtonA = new Canvas(60,60);
		pinkButtonA.getGraphicsContext2D().drawImage(pinkButtonImage,5,5,50,50);
		pinkButtonA.setOnMouseClicked(e -> {
			if (GameController.getInstance().getB_color() != Color.PINK) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerAcolors(Color.PINK, Color.PINK);
			}
		});
		
		Canvas redButtonB = new Canvas(60,60);
		redButtonB.getGraphicsContext2D().drawImage(redButtonImage,5,5,50,50);
		redButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.RED) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.RED, Color.RED);
			}
		});
		Canvas yellowButtonB = new Canvas(60,60);
		yellowButtonB.getGraphicsContext2D().drawImage(yellowButtonImage,5,5,50,50);
		yellowButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.YELLOW) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.YELLOW, Color.YELLOW);
			}
		});
		Canvas greenButtonB = new Canvas(60,60);
		greenButtonB.getGraphicsContext2D().drawImage(greenButtonImage,5,5,50,50);
		greenButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.GREEN) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.GREEN, Color.GREEN);
			}
		});
		Canvas blueButtonB = new Canvas(60,60);
		blueButtonB.getGraphicsContext2D().drawImage(blueButtonImage,5,5,50,50);
		blueButtonB.setOnMouseClicked(e -> {
			if (GameController.getInstance().getA_color() != Color.BLUE) {
				AudioManager.playEffect("Audio/ClickEffect.mp3");
				GameController.getInstance().setPlayerBcolors(Color.BLUE, Color.BLUE);
			}
		});
		Canvas pinkButtonB = new Canvas(60,60);
		pinkButtonB.getGraphicsContext2D().drawImage(pinkButtonImage,5,5,50,50);
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
	
    public void start(SceneManager sceneManager) {
        Scene scene = new Scene(this, 1000, 600);
        sceneManager.getStage().setScene(scene);
        sceneManager.getStage().show();
    }
	
	public void stop(SceneManager sceneManager) {

	}
	
	public static PlayerSettingScene getInstance() {
		if (instance == null) {
			instance = new PlayerSettingScene();
		}
		return instance;
	}
}
