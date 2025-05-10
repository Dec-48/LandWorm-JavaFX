package game.controller;

import java.util.LinkedList;
import java.util.Queue;

import game.object.GridBox;
import game.object.Player;
import game.object.PlayerState;
import game.object.Position;
import game.object.gridState;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameController {
	private static GameController instance;
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
		Boolean isOk = playerA.addCurrentTrail(grid[Arow][Acol]); // false for dead
		
		
		int paintStateA = grid[Arow][Acol].paintTrail(A_TrailColor);
		if (paintStateA == 0) {
			if (playerA.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerA.setPlayerState(PlayerState.Out);
//				System.out.println("A: Out");
				playerA.setPrevInPosition(new Position(Arow, Acol));
			}
		} else if (paintStateA == 3) { // move in SafeZone
			if (playerA.getPlayerState() == PlayerState.Out) { // closed loop
				playerA.setPlayerState(PlayerState.In);
				
				playerA.getCurrentTrail().clear();
			}
		}
		int paintStateB = grid[Brow][Bcol].paintTrail(B_TrailColor);
		if (paintStateB == 0) {
			if (playerB.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerB.setPlayerState(PlayerState.Out);
//				System.out.println("B: Out");
			}
		} else if (paintStateB == 3) { // move in SafeZone
			if (playerB.getPlayerState() == PlayerState.Out) { // closed loop
				playerB.setPlayerState(PlayerState.In);
//				System.out.println("B: In");
//				bfs(playerB.getColor());
			}
		}
	}
	
	private void bfs(Paint fillColor, Position startPos) {
		System.out.println("bfs at " + startPos.row + ":" + startPos.col);
		grid[startPos.row][startPos.col].setColor(Color.BLACK);
//		Queue<Integer> q = new LinkedList<>();
//		while (!q.isEmpty()) {
//			
//		}
	}
	

	public Player getPlayerA() {
		return playerA;
	}
	
	public Player getPlayerB() {
		return playerB;
	}

	public Color getA_color() {
		return A_color;
	}
	public Color getA_TrailColor() {
		return A_TrailColor;
	}

	public Color getB_color() {
		return B_color;
	}
	public Color getB_TrailColor() {
		return B_TrailColor;
	}
	
	//Flim Fall added this for getting color name in filepath easier
	public String getA_stringColor() {
		if (A_color == Color.RED) {
			return "Red";
		} else if (A_color == Color.YELLOW) {
			return "Yellow";
		} else if (A_color == Color.GREEN) {
			return "Green";
		} else if (A_color == Color.BLUE) {
			return "Blue";
		} else {
			return "Pink";
		}
	}
	
	public String getB_stringColor() {
		if (B_color == Color.RED) {
			return "Red";
		} else if (B_color == Color.YELLOW) {
			return "Yellow";
		} else if (B_color == Color.GREEN) {
			return "Green";
		} else if (B_color == Color.BLUE) {
			return "Blue";
		} else {
			return "Pink";
		}
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
	
	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}
}
