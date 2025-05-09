package gui.scene;


import Manager.SceneManager;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class PlayerSettingPane extends StackPane implements ChangeableScene {
    public void start(SceneManager sceneManager) {
    	
        Scene scene = new Scene(this, 1000, 600);
        sceneManager.getStage().setScene(scene);
        sceneManager.getStage().show();
    }
	
	public void stop(SceneManager sceneManager) {

	}
}
