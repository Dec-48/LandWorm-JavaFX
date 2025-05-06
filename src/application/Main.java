package application;
import game.controller.GameController;
import game.object.GridBox;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tank game");

		Canvas canvas = new Canvas(1000, 580);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		GameController gameController = new GameController();
		gameController.initialGame();
		GridBox[][] grid = gameController.getGrid();
		AnimationTimer animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameController.movePlayer();
				for (int i = 0; i < 29; i++) {
					for (int j = 0; j < 50; j++) {
						grid[i][j].draw(gc);
					}
				}
			}
		};
		animation.start();
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
