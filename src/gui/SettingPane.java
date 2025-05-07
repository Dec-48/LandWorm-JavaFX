package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingPane extends StackPane{
	private static SettingPane instance;
	private Canvas leafBackground;
	private Canvas settingImageCanvas;
	private VBox elementPane;
	private HBox bgmSettingPane;
	
	private SettingPane() {
		super();
		this.setPrefWidth(800);
		this.setPrefHeight(600);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(120,0,0,0));
		
		initializeLeafBackground();
		initializeSettingImage();
		initializeBGMSettingPane();
		
		initializeElementPane();
		
		this.getChildren().addAll(leafBackground,elementPane);
	}
	
	private void initializeLeafBackground() {
		Canvas firstCanvas = new Canvas(600 , 600);
		Image backgroundLeafImage = new Image(ClassLoader.getSystemResource("Image/SettingBackground.PNG").toString());
		firstCanvas.getGraphicsContext2D().drawImage(backgroundLeafImage,50,0,500,500);
		leafBackground = firstCanvas;
	}
	 
	private void initializeSettingImage() {
		Canvas secondCanvas = new Canvas(200,100);
		Image settingImage = new Image(ClassLoader.getSystemResourceAsStream("Image/SETTINGImage.PNG"));
		secondCanvas.getGraphicsContext2D().drawImage(settingImage,20,0,200,110);
		settingImageCanvas = secondCanvas;
	}
	
	private void initializeBGMSettingPane() {
		bgmSettingPane = new HBox();
		bgmSettingPane.setAlignment(Pos.CENTER);
		Label bgmLabel = new Label("BGM");
//		Font pixelFont = Font.loadFont(ClassLoader.getSystemResourceAsStream("Font/PressStart2P-Regular.ttf").toString(), 24);
//		bgmLabel.setFont(pixelFont);
		bgmLabel.setStyle("-fx-text-fill: #8b4513;");
		
		
		
		bgmSettingPane.getChildren().addAll(bgmLabel);
	}
	
	private void initializeElementPane() {
		elementPane = new VBox();
		elementPane.setAlignment(Pos.TOP_CENTER);
		elementPane.getChildren().addAll(settingImageCanvas,bgmSettingPane);
		
	}
	
	public static SettingPane getInstance() {
		if (instance == null) {
			instance = new SettingPane();
		}
		return instance;
	}
}
