package gui.scene;


import Manager.SceneManager;
import game.controller.GameController;
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
	private HBox showWormPlayerPane;
	private HBox colorSelectPane;
	
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
		settingPane.getChildren().addAll(chooseYourColor);
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
		colorSelectPane = new HBox();
		colorSelectPane.setPrefHeight(80);
		colorSelectPane.setPrefWidth(800);
		colorSelectPane.setSpacing(20);
		
		
	}
	
	private void initializeColorSelectingPane() { // pick 2 player color **default is red and blue**
		colorSelectPane = new HBox();
		colorSelectPane.setPrefHeight(200);
		colorSelectPane.setPrefWidth(800);
		colorSelectPane.setSpacing(20);
		
		GameController.getInstance().setPlayerAcolors(null, null);
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
