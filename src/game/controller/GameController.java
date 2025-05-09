package game.controller;

import game.object.GridBox;
import game.object.Player;
import game.object.Position;
import game.object.gridState;
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
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {
				grid[14 + di][12 + dj].setColor(A_TrailColor);
				grid[14 + di][12 + dj].setState(gridState.SafeZone);
				grid[14 + di][37 + dj].setColor(B_TrailColor);
				grid[14 + di][37 + dj].setState(gridState.SafeZone);
			}
		}
	}
	
	public void update() {
		playerA.move(); // move playerA along the direction from inputUtility
		playerB.move(); // move playerB along the direction from inputUtility
		int Arow = playerA.getPosition().row;
		int Acol = playerA.getPosition().col;
		int Brow = playerB.getPosition().row;
		int Bcol = playerB.getPosition().col;
		
		// |0 -> chill|1 -> kill itself|2 -> kill other| 
		int AplayerState = grid[Arow][Acol].paintTrail(A_TrailColor);
		if (AplayerState == 0) { // Chill
			playerA.addCurrentTrail(grid[Arow][Acol]);
		} else if (AplayerState == 1) { // TODO: remove A's currentTrail and kill A
			
		} else { // TODO : remove B's currentTrail and kill B
			
		}
		int BplayerState = grid[Brow][Bcol].paintTrail(B_TrailColor);
		if (BplayerState == 0) {
			playerB.addCurrentTrail(grid[Brow][Bcol]);			
		} else if (BplayerState == 1) { // TODO : remove B's currentTrail and kill B
			
		} else { // TODO : remove A's currentTrail and kill A
			
		}
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
