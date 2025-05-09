package game.controller;

import game.object.GridBox;
import game.object.Player;
import game.object.Position;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameController {
	private KeyCode[] movingKeyA = {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
	private KeyCode[] movingKeyB = {KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT};
	private Player playerA = new Player(movingKeyA);
	private Player playerB = new Player(movingKeyB);
	private GridBox[][] grid = new GridBox[29][50];
	private Color A_color = Color.DARKRED;
	private Color A_TrailColor = Color.RED;
	private Color B_color = Color.DARKBLUE;
	private Color B_TrailColor = Color.BLUE;
	
	public void initialGame() { //TODO : this need to rename to initialGame ???
		playerA.setColor(A_color);
		playerB.setColor(B_color);
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 50; j++) {
				grid[i][j] = new GridBox(i, j);
			}
		}
		playerA.setPosition(new Position(14, 12));
		playerB.setPosition(new Position(14, 37));
	}
	
	public void update() {
		playerA.move(); // move playerA along the direction from inputUtility
		grid[playerA.getPosition().row][playerA.getPosition().col].setColor(A_TrailColor);
		playerB.move(); // move playerB along the direction from inputUtility
		grid[playerB.getPosition().row][playerB.getPosition().col].setColor(B_TrailColor);
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
	
	public void setPlayerAcolors(Color playerColor, Color trailColor) {
		this.A_color = playerColor;
		this.A_TrailColor = trailColor;
	}
	
	public void setPlayerBcolors(Color playerColor, Color trailColor) {
		this.B_color = playerColor;
		this.B_TrailColor = trailColor;
	}
}
