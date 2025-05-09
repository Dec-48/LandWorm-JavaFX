package game.controller;

import game.object.GridBox;
import game.object.Player;
import game.object.Position;
import javafx.scene.input.KeyCode;

public class GameController {
	private KeyCode[] movingKeyA = {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
	private KeyCode[] movingKeyB = {KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT};
	private Player playerA = new Player(movingKeyA);
	private Player playerB = new Player(movingKeyB);
	private GridBox[][] grid = new GridBox[29][50];
	
	public void initialGame() { //TODO : this need to rename to initialGame ???
		playerA.setColor("R");
		playerB.setColor("B");
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 50; j++) {
				grid[i][j] = new GridBox(i, j);
			}
		}
		playerA.setPosition(new Position(14, 12));
		playerB.setPosition(new Position(14, 37));
		grid[14][12].setColor(playerA.getColor());
		grid[14][12 + 25].setColor(playerB.getColor());
	}
	
	public void update() {
		playerA.move(); // move playerA along the direction from inputUtility
		playerB.move(); // move playerB along the direction from inputUtility
	}

	public Player getPlayerA() {
		return playerA;
	}
	
	public Player getPlayerB() {
		return playerB;
	}

	public GridBox[][] getGrid() {
		return grid;
	}
}
