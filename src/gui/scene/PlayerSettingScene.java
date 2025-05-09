package gui.scene;


import Manager.SceneManager;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class PlayerSettingScene extends StackPane implements ChangeableScene {
	private static PlayerSettingScene instance;
	private Canvas background;
	
	private PlayerSettingScene() {
		super();
		this.setPrefWidth(1000);
		this.setPrefHeight(600);
		
		background = new Canvas(1000,600);
		Image backgroundImage = new Image(ClassLoader.getSystemResource("Image/PlayerSettingBackground.png").toString());
		background.getGraphicsContext2D().drawImage(backgroundImage,0,0,1000,600);
		
		this.getChildren().addAll(background);
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
