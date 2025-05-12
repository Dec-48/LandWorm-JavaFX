package gui;

import Manager.AudioManager;
import gui.scene.MainMenuScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GameSettingPane extends StackPane{
	private static GameSettingPane instance;
	private Canvas leafBackground;
	private Canvas settingImageCanvas; 
	private HBox bgmSettingPane;
	private HBox effectSettingPane;
	private VBox elementPane;
	private Button backButton;
	
	private GameSettingPane() {
		super();
		this.setPrefWidth(800);
		this.setPrefHeight(600);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(110,0,0,0));
		
		initializeLeafBackground();
		initializeSettingImage();
		this.getStylesheets().add(ClassLoader.getSystemResource("css/SliceBar.css").toString());
		initializeBGMSettingPane();
		initializeEffectSettingPane();
		initializeBackButton();
		initializeElementPane();
		
		this.getChildren().addAll(leafBackground,elementPane);
	}
	
	private void initializeLeafBackground() {
		Canvas LeafCanvas = new Canvas(600 , 600);
		Image backgroundLeafImage = new Image(ClassLoader.getSystemResource("Image/SettingBackground.PNG").toString());
		LeafCanvas.getGraphicsContext2D().drawImage(backgroundLeafImage,50,0,500,500);
		leafBackground = LeafCanvas;
	}
	 
	private void initializeSettingImage() {
		Canvas settingCanvas = new Canvas(240,120);
		Image settingImage = new Image(ClassLoader.getSystemResourceAsStream("Image/SETTINGImage.png"));
		settingCanvas.getGraphicsContext2D().drawImage(settingImage,20,0,240,120);
		settingImageCanvas = settingCanvas;
	}
	
	private void initializeBGMSettingPane() {
		bgmSettingPane = new HBox();
		bgmSettingPane.setAlignment(Pos.CENTER);
		bgmSettingPane.setSpacing(50);
		
		Canvas bgmSign = new Canvas(149,108);
		Image bgmImage = new Image(ClassLoader.getSystemResource("Image/BGMImage.png").toString());
		bgmSign.getGraphicsContext2D().drawImage(bgmImage,0,0,149,108);
		
		Slider bgmSlider = new Slider(0, 0.6, 0.2); 
        bgmSlider.setPrefWidth(150);
        bgmSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            AudioManager.setBGMVolume(newVal.doubleValue());

        });
        
        bgmSlider.getStyleClass().add("custom-slider");
		
		bgmSettingPane.getChildren().addAll(bgmSign,bgmSlider); 
	}
	
	private void initializeEffectSettingPane() {
		effectSettingPane = new HBox();
		effectSettingPane.setAlignment(Pos.CENTER);
		effectSettingPane.setSpacing(50);
		
		Canvas effectSign = new Canvas(170,130);
		Image effectImage = new Image(ClassLoader.getSystemResource("Image/EffectImage.png").toString());
		effectSign.getGraphicsContext2D().drawImage(effectImage,0,0,170,130);
		
		Slider effectSlider = new Slider(0, 2, 0.8); 
        effectSlider.setPrefWidth(150);
        effectSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            AudioManager.setEffectVolume(newVal.doubleValue());
        });
        
        effectSlider.getStyleClass().add("custom-slider");
        
		effectSettingPane.getChildren().addAll(effectSign,effectSlider);
	}
	
	private void initializeBackButton() {
		Button btn = new Button();
		Canvas buttonDisplayImage = new Canvas(80,80);
		Image backImage = new Image(ClassLoader.getSystemResource("Image/BACKbutton.png").toString());
		buttonDisplayImage.getGraphicsContext2D().drawImage(backImage, 5,5,70,70);
		btn.setGraphic(buttonDisplayImage);
		btn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		
		btn.setOnMouseEntered(e -> {
			btn.setCursor(Cursor.HAND);
			buttonDisplayImage.getGraphicsContext2D().drawImage(backImage,0,0,80,80);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseExited(e -> {
			btn.setCursor(Cursor.DEFAULT);
			buttonDisplayImage.getGraphicsContext2D().clearRect(0, 0, buttonDisplayImage.getWidth(), buttonDisplayImage.getHeight());
			buttonDisplayImage.getGraphicsContext2D().drawImage(backImage,5,5,70,70);
			btn.setGraphic(buttonDisplayImage);
		});
		
		btn.setOnMouseClicked(e -> {
			AudioManager.playEffect("Audio/ClickEffect.mp3");
			
			MainMenuScene.getInstance().getChildren().remove(1);
			MainMenuScene.getInstance().getChildren().addAll(ButtonPane.getInstance());
		});
		
		this.backButton = btn;
	}
	
	private void initializeElementPane() {
		elementPane = new VBox();
		elementPane.setAlignment(Pos.TOP_CENTER);
		elementPane.setSpacing(1);
		
		HBox backButtonContainer = new HBox();
        backButtonContainer.setAlignment(Pos.CENTER_LEFT); 
        backButtonContainer.getChildren().add(backButton);
        backButtonContainer.setPadding(new Insets(0,0,0,300));
			
		elementPane.getChildren().addAll(settingImageCanvas,bgmSettingPane,effectSettingPane,backButtonContainer);
		
	}
	
	
	public static GameSettingPane getInstance() {
		if (instance == null) {
			instance = new GameSettingPane();
		}
		return instance;
	}
}
