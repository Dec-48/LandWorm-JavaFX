package Manager;

import gui.scene.ChangeableScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
	private static SceneManager instance;
	private Stage stage;
	private ChangeableScene currentScene;

	private SceneManager(Stage stage) {
		this.stage = stage;
	}

	public static SceneManager getInstance(Stage stage) {
		if (instance == null) {
			instance = new SceneManager(stage);
		}
		return instance;
	}

	public static SceneManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException("SceneManager has not been initilized with a Stage yet.");
		}
		return instance;
	}

	public void setScene(ChangeableScene newScene) {
		if (currentScene != null) {
			currentScene.stop(this);
		}

		currentScene = newScene;
		currentScene.start(this);
	}

	public Stage getStage() {
		return stage;
	}
}
