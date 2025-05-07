package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuPane extends VBox{
	private static MenuPane instance;
	private Canvas gameLogo;
	private Button playButton;
	private Button settingButton;
	private Button exitButton;

	
	private MenuPane() {
		super();
		this.setPrefWidth(200);
		this.setPrefHeight(300);
		this.setAlignment(Pos.TOP_CENTER);
		this.setSpacing(50);
		this.setPadding(new Insets(45,0,0,0));
		
		// Create each node by calling their method
		initializeGameLogoImage();
		initializePlayButton();
		initializeSettingButton();
		initializeExitButton();
		
		
		this.getChildren().addAll(gameLogo,playButton,settingButton,exitButton);
	}
	
	private void initializeGameLogoImage() {
		Canvas canvas = new Canvas(426 , 130);
		Image gameLogoImage = new Image(ClassLoader.getSystemResource("Image/GameLogo.PNG").toString());
		canvas.getGraphicsContext2D().drawImage(gameLogoImage,0,0,426,130);
		this.gameLogo = canvas;
	}
	
	private void initializePlayButton() {
		Button btn = new Button();
		Canvas buttonDisplayImage = new Canvas(135 , 75);
		Image gameLogoImage = new Image(ClassLoader.getSystemResource("Image/PLAYbutton.png").toString());
		buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
		btn.setGraphic(buttonDisplayImage);
		btn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

		btn.setOnMouseEntered(e -> {
			btn.setCursor(Cursor.HAND);
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,0,0,135,75);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseExited(e -> {
			btn.setCursor(Cursor.DEFAULT);
			buttonDisplayImage.getGraphicsContext2D().clearRect(0, 0, buttonDisplayImage.getWidth(), buttonDisplayImage.getHeight());
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseClicked(e -> {
			AudioClip clickEffect = new AudioClip(ClassLoader.getSystemResource("Audio/ClickEffect.mp3").toString());
			clickEffect.play();
		});

		this.playButton = btn; //c49152
		
	}
	
	private void initializeSettingButton() {
		Button btn = new Button();
		Canvas buttonDisplayImage = new Canvas(135 , 75);
		Image gameLogoImage = new Image(ClassLoader.getSystemResource("Image/SETTINGbutton.png").toString());
		buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
		btn.setGraphic(buttonDisplayImage);
		btn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

		btn.setOnMouseEntered(e -> {
			btn.setCursor(Cursor.HAND);
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,0,0,135,75);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseExited(e -> {
			btn.setCursor(Cursor.DEFAULT);
			buttonDisplayImage.getGraphicsContext2D().clearRect(0, 0, buttonDisplayImage.getWidth(), buttonDisplayImage.getHeight());
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseClicked(e -> {
//			this.clickEffect.play();
		});
		
		this.settingButton = btn;
	}
	
	private void initializeExitButton() {
		Button btn = new Button();
		Canvas buttonDisplayImage = new Canvas(135 , 75);
		Image gameLogoImage = new Image(ClassLoader.getSystemResource("Image/EXITbutton.png").toString());
		buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
		btn.setGraphic(buttonDisplayImage);
		btn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

		btn.setOnMouseEntered(e -> {
			btn.setCursor(Cursor.HAND);
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,0,0,135,75);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseExited(e -> {
			btn.setCursor(Cursor.DEFAULT);
			buttonDisplayImage.getGraphicsContext2D().clearRect(0, 0, buttonDisplayImage.getWidth(), buttonDisplayImage.getHeight());
			buttonDisplayImage.getGraphicsContext2D().drawImage(gameLogoImage,5,5,125,65);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseClicked(e -> {
//			this.clickEffect.play();
		});
		
		this.exitButton = btn;
	}
	
	
	public static MenuPane getInstance() {
		if (instance == null) {
			instance = new MenuPane();
		}
		return instance;
	}
}
