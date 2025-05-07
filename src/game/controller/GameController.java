package game.controller;

import game.object.GridBox;
import game.object.Player;
import game.object.Position;

public class GameController {
	private Player playerA = new Player();
	private Player playerB = new Player();
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
		playerB.setPosition(new Position(14, 25));
		grid[14][12].setColor(playerA.getColor());
		grid[14][12 + 25].setColor(playerB.getColor());
	}
	
	public void movePlayer() {
		playerA.move();
		playerB.move();
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
