package gui;

import game.controller.GameController;
import game.object.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ResultPane extends VBox {
	private static ResultPane instance;
	private Player winner;
	private Paint winnerColor;
	private String stringColor;
	private Image[] wormImages = new Image[3];
	private Canvas winnerWorm;
	private Label resultText;
	private boolean running = true;

	private ResultPane() {
		super();
		this.setPrefHeight(600);
		this.setPrefWidth(1000);
		this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75);" // Black with 50% transparency
		);
		this.setAlignment(Pos.TOP_CENTER);
		this.setSpacing(36);
		this.setPadding(new Insets(100, 0, 0, 0));
		winner = GameController.getInstance().getWinnerPlayer();
		winnerColor = winner.getColor();

		initailizeWinnerImage();
		initializeWinnerWormPane();
		initializeResultText();

		this.getChildren().addAll(winnerWorm, resultText);
	}

	private void initailizeWinnerImage() {
		if ((winnerColor == Color.RED) || (winnerColor == Color.DARKRED)) {
			stringColor = "Red";
		} else if (winnerColor == Color.YELLOW) {
			stringColor = "Yellow";
		} else if (winnerColor == Color.GREEN) {
			stringColor = "Green";
		} else if ((winnerColor == Color.BLUE) || (winnerColor == Color.DARKBLUE)) {
			stringColor = "Blue";
		} else {
			stringColor = "Pink";
		}
		String imagePath = "Image/" + stringColor + "worm/" + stringColor + "wormH";
		for (int i = 1; i <= 3; i++) {
			wormImages[i - 1] = new Image(ClassLoader.getSystemResource(imagePath + i + ".PNG").toString());
		}
	}

	private void initializeWinnerWormPane() {
		winnerWorm = new Canvas(300, 300);
		winnerWorm.getGraphicsContext2D().drawImage(wormImages[0], 0, 0, 300, 300);
		Thread wormThread = new Thread(() -> {
			while (running) {
				try {
					Thread.sleep(200);
					Platform.runLater(() -> {
						winnerWorm.getGraphicsContext2D().clearRect(0, 0, 300, 300);
						winnerWorm.getGraphicsContext2D().drawImage(wormImages[1], 0, 0, 300, 300);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						winnerWorm.getGraphicsContext2D().clearRect(0, 0, 300, 300);
						winnerWorm.getGraphicsContext2D().drawImage(wormImages[2], 0, 0, 300, 300);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						winnerWorm.getGraphicsContext2D().clearRect(0, 0, 300, 300);
						winnerWorm.getGraphicsContext2D().drawImage(wormImages[1], 0, 0, 300, 300);
					});
					Thread.sleep(200);
					Platform.runLater(() -> {
						winnerWorm.getGraphicsContext2D().clearRect(0, 0, 300, 300);
						winnerWorm.getGraphicsContext2D().drawImage(wormImages[0], 0, 0, 300, 300);
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		wormThread.start()

		;
	}

	private void initializeResultText() {
		resultText = new Label("The Winner is the " + stringColor + " Player!!");
		Font font = Font.loadFont(getClass().getResourceAsStream("/Font/PressStart2P-Regular.ttf"), 20);
		resultText.setFont(font);
		resultText.setTextFill(Color.WHITE);
	}

	public static ResultPane getInstance() {
		if (instance == null) {
			instance = new ResultPane();
		}
		return instance;
	}

	public static void resetResultPane() {
		instance = null;
	}
}
