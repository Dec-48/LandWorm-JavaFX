package application;
import game.controller.GameCanvas;
import game.controller.GameController;
import game.object.GridBox;
import game.object.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sharedObject.RenderableHolder;

public class Dec48Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("LandWorm");

		GameCanvas gameCanvas = new GameCanvas(1000, 600); 
		gameCanvas.addListerner();
	
		root.getChildren().add(gameCanvas);
		gameCanvas.requestFocus(); 
		
		GameController gameController = new GameController();
		gameController.initialGame();
		GridBox[][] grid = gameController.getGrid();
		Player playerA = gameController.getPlayerA();
		Player playerB = gameController.getPlayerB();
		
		playerA.setZ(2);
		playerB.setZ(2); // I have to do this cause the grid will draw over playB blue dot :(
		
//		*** TEMPORARY USAGE NAKUB 
//		** We must not add to RenderableHolder here but in our GameController instead!
		for (GridBox[] i : grid) {
			for (GridBox j : i) {
				RenderableHolder.getInstance().add(j);
			}
		}
		
		RenderableHolder.getInstance().add(playerA);
		RenderableHolder.getInstance().add(playerB);
		
		AnimationTimer animation = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameController.update();
				gameCanvas.paintComponent(); 				
			}
		};
		animation.start();
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
