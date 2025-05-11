package game.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

import game.object.GameplayBackground;
import game.object.GridBox;
import game.object.Player;
import game.object.PlayerState;
import game.object.Position;
import game.object.gridState;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sharedObject.RenderableHolder;

public class GameController {
	private static GameController instance;
	private KeyCode[] movingKeyA = {KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};
	private KeyCode[] movingKeyB = {KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT};
	private Player playerA = new Player(movingKeyA);
	private Player playerB = new Player(movingKeyB);
	private GridBox[][] grid = new GridBox[29][50];
	private Color a_color = Color.DARKRED;
	private Color a_TrailColor = Color.RED;
	private Color b_color = Color.DARKBLUE;
	private Color b_TrailColor = Color.BLUE;
	private GameplayBackground background;
	
	public void initialGame() { //TODO : this need to rename to initialGame ???
		playerA.setColor(a_color);
		a_TrailColor = a_TrailColor.deriveColor(0, 1, 1, 0.3);
		playerB.setColor(b_color);
		b_TrailColor = b_TrailColor.deriveColor(0, 1, 1, 0.3);
		
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 50; j++) {
				grid[i][j] = new GridBox(i, j);
			}
		}
		playerA.setPosition(new Position(14, 12));
		playerB.setPosition(new Position(14, 37));
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {
				grid[14 + di][12 + dj].setColor(a_TrailColor);
				grid[14 + di][12 + dj].setState(gridState.SafeZone);
				grid[14 + di][37 + dj].setColor(b_TrailColor);
				grid[14 + di][37 + dj].setState(gridState.SafeZone);
			}
		}
		
		background = new GameplayBackground();
		background.setZ(0);
		RenderableHolder.getInstance().add(background);
		
		for (GridBox[] i : grid) {
			for (GridBox j : i) {
				j.setZ(1);
				RenderableHolder.getInstance().add(j);
			}
		}
		
		playerA.setZ(2);
		playerB.setZ(2); 
		
		RenderableHolder.getInstance().add(playerA);
		RenderableHolder.getInstance().add(playerB);
///////////////////////
	}
	
	public void update() {
		playerA.move(); // move playerA along the direction from inputUtility
		playerB.move(); // move playerB along the direction from inputUtility
		int Arow = playerA.getPosition().row;
		int Acol = playerA.getPosition().col;
		playerA.addCurrentTrail(grid[Arow][Acol]); 
		int Brow = playerB.getPosition().row;
		int Bcol = playerB.getPosition().col;
		playerB.addCurrentTrail(grid[Brow][Bcol]);
		//TODO: modify this method in order to handle double line trail!!!
		int paintStateA = grid[Arow][Acol].paintTrail(a_TrailColor); //XXX here!!!
		if (paintStateA == 0) {
			if (playerA.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerA.setPlayerState(PlayerState.Out);
			}
		} else if (paintStateA == 3) { // move in SafeZone
			if (playerA.getPlayerState() == PlayerState.Out) { // closed loop
				playerA.setPlayerState(PlayerState.In);
				for (GridBox gb : playerA.getCurrentTrail()) gb.setState(gridState.SafeZone);
				ArrayList<Position> spaces = this.fillSpace(playerA.getCurrentTrail(), a_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setColor(a_TrailColor);
					grid[pos.row][pos.col].setState(gridState.SafeZone);
				}
				playerA.getCurrentTrail().clear();
			}
		}
		
		
		int paintStateB = grid[Brow][Bcol].paintTrail(b_TrailColor);
		if (paintStateB == 0) {
			if (playerB.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerB.setPlayerState(PlayerState.Out);
			}
		} else if (paintStateB == 3) { // move in SafeZone
			if (playerB.getPlayerState() == PlayerState.Out) { // closed loop
				playerB.setPlayerState(PlayerState.In);
				for (GridBox gb : playerB.getCurrentTrail()) gb.setState(gridState.SafeZone);
				ArrayList<Position> spaces = this.fillSpace(playerB.getCurrentTrail(), b_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setState(gridState.SafeZone);
					grid[pos.row][pos.col].setColor(b_TrailColor);
				}
				playerB.getCurrentTrail().clear();
			}
		}
	}

	private ArrayList<Position> fillSpace(List<GridBox> currentTrail, Paint trailColor) { //XXX: still have bug when too zig zag path
		ArrayList<Position> ret = new ArrayList<Position>();
		Boolean vis[][] = new Boolean[29][50];
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 50; j++) {
				vis[i][j] = false;
			}
		}
		Map<Integer, TreeSet<Integer>> row = new HashMap<Integer, TreeSet<Integer>>();
		Map<Integer, TreeSet<Integer>> col = new HashMap<Integer, TreeSet<Integer>>();
		for (GridBox gb : currentTrail) {
			Integer i = gb.getPosition().row;
			Integer j = gb.getPosition().col;
			if (row.containsKey(i)) {
				row.get(i).add(j);
			} else {
				TreeSet<Integer> s = new TreeSet<Integer>(); s.add(j);
				row.put(i, s);
			}
			if (col.containsKey(j)) {
				col.get(j).add(i);
			} else {
				TreeSet<Integer> s = new TreeSet<Integer>(); s.add(i);
				col.put(j, s);
			}
		}
		for (Integer i : row.keySet()) {
			if (row.get(i).size() > 1) {
				if (row.get(i).getLast() - row.get(i).getFirst() <= 1) continue;
				for (int j = row.get(i).getFirst() + 1; j <= row.get(i).getLast() - 1; j++) {
					if (grid[i][j].getColor() != trailColor) {
						if (!vis[i][j]) {
							vis[i][j] = true;
							ret.add(new Position(i, j));
						}
					}
				}
			}
		}
		for (Integer j : col.keySet()) {
			if (col.get(j).size() > 1) {
				if (col.get(j).getLast() - col.get(j).getFirst() <= 1) continue;
				for (int i = col.get(j).getFirst() + 1; i <= col.get(j).getLast() - 1; i++) {
					if (grid[i][j].getColor() != trailColor) {
						if (!vis[i][j]) {
							vis[i][j] 	= true;
							ret.add(new Position(i, j));
						}
					}
				}
			}	
		}
		
		ArrayList<Position> tmp = new ArrayList<Position>();
		for (Position pos : ret) {
			if (vis[pos.row][pos.col] || grid[pos.row][pos.col].getState() == gridState.SafeZone) continue;
			Queue<Position> q = new ArrayDeque<Position>();
			q.add(pos);
			while (!q.isEmpty()) {
				Position cur = q.remove();
				if (grid[cur.row][cur.col].getState() == gridState.SafeZone) continue;
				for (int d = -1; d <= 1; d += 2) {
					int newRow = cur.row + d; int newCol = cur.col + d;
					Position newPos;
					if (0 <= newRow && newRow < 29) {
						if (!vis[newRow][cur.col] && grid[newRow][cur.col].getColor() != a_TrailColor) {
							newPos = new Position(newRow, cur.col); 
							vis[newRow][cur.col] = true;
							tmp.add(newPos);
							q.add(newPos);
						}
					}
					if (0 <= newCol && newCol < 50) {
						if (!vis[cur.row][newCol] && grid[cur.row][newCol].getColor() != a_TrailColor) {
							newPos = new Position(cur.row, newCol);
							vis[cur.row][newCol] = true;
							tmp.add(newPos);
							q.add(newPos);
						}
					}
				}
			}
		}
		ret.addAll(tmp);
		return ret;
	}
	
	
	public Player getPlayerA() {
		return playerA;
	}
	
	public Player getPlayerB() {
		return playerB;
	}

	public Color getA_color() {
		return a_color;
	}
	public Color getA_TrailColor() {
		return a_TrailColor;
	}

	public Color getB_color() {
		return b_color;
	}
	public Color getB_TrailColor() {
		return b_TrailColor;
	}
	
	//Flim Fall added this for getting color name in filepath easier
	public String getA_stringColor() {
		if ((a_color == Color.RED) || (a_color == Color.DARKRED)) {
			return "Red";
		} else if (a_color == Color.YELLOW) {
			return "Yellow";
		} else if (a_color == Color.GREEN) {
			return "Green";
		} else if ((a_color == Color.BLUE) || (a_color == Color.DARKBLUE)) {
			return "Blue";
		} else {
			return "Pink";
		}
	}
	
	public String getB_stringColor() {
		if ((b_color == Color.RED ) || (b_color == Color.DARKRED)) {
			return "Red";
		} else if (b_color == Color.YELLOW) {
			return "Yellow";
		} else if (b_color == Color.GREEN) {
			return "Green";
		} else if ((b_color == Color.BLUE) || (b_color == Color.DARKBLUE)) {
			return "Blue";
		} else {
			return "Pink";
		}
	}


	public GridBox[][] getGrid() {
		return grid;
	}
	
	public void setPlayerAcolors(Color playerColor, Color trailColor) {
		this.a_color = playerColor;
		this.a_TrailColor = trailColor.deriveColor(0, 1, 1, 0.3);;
	}
	
	public void setPlayerBcolors(Color playerColor, Color trailColor) {
		this.b_color = playerColor;
		this.b_TrailColor = trailColor.deriveColor(0, 1, 1, 0.3);;
	}
	
	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}
}
