package game.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import game.object.GridBox;
import game.object.Player;
import game.object.PlayerState;
import game.object.Position;
import game.object.gridState;
import game.object.Items.SpeedPotion;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import map.GameplayBackground;
import sharedObject.RenderableHolder;

public class GameController {
	private static GameController instance;
	private KeyCode[] movingKeyA = { KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D };
	private KeyCode[] movingKeyB = { KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT };
	private Player playerA = new Player(movingKeyA);
	private Player playerB = new Player(movingKeyB);
	private SpeedPotion[] speedPotions = new SpeedPotion[4];
	private GridBox[][] grid = new GridBox[29][50];
	private int frameCount = 0;
	private int maxPotion = 4;
	private Color a_color = Color.DARKRED;
	private Color a_TrailColor = Color.RED;
	private Color b_color = Color.DARKBLUE;
	private Color b_TrailColor = Color.BLUE;
	private GameplayBackground background;

	public void initialGame() { // TODO : this need to rename to initialGame ???
		playerA.setColor(a_color);
		playerB.setColor(b_color);

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

		playerA.setZ(3);
		playerB.setZ(3);

		RenderableHolder.getInstance().add(playerA);
		RenderableHolder.getInstance().add(playerB);

		ArrayList<Position> poss = new ArrayList<Position>();
		for (int i = 0; i < 4;) {
			int rowRand = ThreadLocalRandom.current().nextInt(1, 29); // [1, 29)
			int colRand = ThreadLocalRandom.current().nextInt(1, 50); // [1, 50)
			Position newPos = new Position(rowRand, colRand);
			if (!poss.contains(newPos)) {
				i++;
				poss.add(newPos);
			}
		}
		int idx = 0;
		for (Position pos : poss) {
			speedPotions[idx] = new SpeedPotion(pos);
			speedPotions[idx].setVisible(false);
			idx++;
		}
		///////////////////////
	}

	public void update() {
		frameCount++;
		if (frameCount % 100000 == 0) {

		}
		playerA.move(); // move playerA along the direction from inputUtility
		playerB.move(); // move playerB along the direction from inputUtility
		int Arow = playerA.getPosition().row;
		int Acol = playerA.getPosition().col;
		playerA.addCurrentTrail(grid[Arow][Acol]);
		int Brow = playerB.getPosition().row;
		int Bcol = playerB.getPosition().col;
		playerB.addCurrentTrail(grid[Brow][Bcol]);
		// TODO: modify this method in order to handle double line trail!!!
		int paintStateA = grid[Arow][Acol].paintTrail(a_TrailColor); // XXX here!!!
		if (paintStateA == 0) {
			if (playerA.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerA.setPlayerState(PlayerState.Out);
				playerA.setprevOutPosition(new Position(Arow, Acol));
			}
		} else if (paintStateA == 3) { // move in SafeZone
			if (playerA.getPlayerState() == PlayerState.Out) { // closed loop
				playerA.setPlayerState(PlayerState.In);
				for (GridBox gb : playerA.getCurrentTrail())
					gb.setState(gridState.SafeZone);
				ArrayList<Position> spaces = this.fillSpace(playerA.getCurrentTrail(), a_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setColor(a_TrailColor);
					grid[pos.row][pos.col].setState(gridState.SafeZone);
				}
				playerA.getCurrentTrail().clear();
			}
		} else if (paintStateA == 1) {
			if (playerA.getCurrentTrail().getLast() != grid[Arow][Acol]) { // kill itself ??
				for (GridBox gb : playerA.getCurrentTrail()) {
					if (gb.getState() == gridState.Trail) {
						gb.setColor(GridBox.blankColor);
						gb.setState(gridState.Blank);
					}
				}
				int row = playerA.getprevOutPosition().row;
				int col = playerA.getprevOutPosition().col;
				playerA.setPosition(new Position(row, col));
				grid[row][col].setState(gridState.SafeZone);
				// playerA.setPosition(playerA.getprevOutPosition()); //XXX: fuck undefied
				// behavior!!!!
				playerA.getCurrentTrail().clear();
			}
		}

		int paintStateB = grid[Brow][Bcol].paintTrail(b_TrailColor);
		if (paintStateB == 0) {
			if (playerB.getPlayerState() == PlayerState.In) { // get out of SafeZone
				playerB.setPlayerState(PlayerState.Out);
				playerB.setprevOutPosition(new Position(Brow, Bcol));
			}
		} else if (paintStateB == 3) { // move in SafeZone
			if (playerB.getPlayerState() == PlayerState.Out) { // closed loop
				playerB.setPlayerState(PlayerState.In);
				for (GridBox gb : playerB.getCurrentTrail())
					gb.setState(gridState.SafeZone);
				ArrayList<Position> spaces = this.fillSpace(playerB.getCurrentTrail(), b_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setState(gridState.SafeZone);
					grid[pos.row][pos.col].setColor(b_TrailColor);
				}
				playerB.getCurrentTrail().clear();
			}
		} else if (paintStateB == 1) {
			if (playerB.getCurrentTrail().getLast() != grid[Brow][Bcol]) { // kill itself
				for (GridBox gb : playerB.getCurrentTrail()) {
					if (gb.getState() == gridState.Trail) {
						gb.setColor(GridBox.blankColor);
						gb.setState(gridState.Blank);
					}
				}
				int row = playerB.getprevOutPosition().row;
				int col = playerB.getprevOutPosition().col;
				grid[row][col].setState(gridState.SafeZone);
				playerB.setPosition(new Position(row, col));
				playerB.getCurrentTrail().clear();
			}
		}
	}

	private ArrayList<Position> fillSpace(List<GridBox> currentTrail, Paint trailColor) { // XXX: still have bug when too
																																												// zig zag path
		ArrayList<Position> ret = new ArrayList<Position>();
		Boolean vis[][] = new Boolean[29][50];
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 50; j++) {
				vis[i][j] = false;
			}
		}
		// Map<Integer, TreeSet<Integer>> row = new HashMap<Integer,
		// TreeSet<Integer>>();
		// Map<Integer, TreeSet<Integer>> col = new HashMap<Integer,
		// TreeSet<Integer>>();
		Map<Integer, ArrayList<Integer>> row = new HashMap<Integer, ArrayList<Integer>>();
		Map<Integer, ArrayList<Integer>> col = new HashMap<Integer, ArrayList<Integer>>();
		for (GridBox gb : currentTrail) {
			Integer i = gb.getPosition().row;
			Integer j = gb.getPosition().col;
			if (row.containsKey(i)) {
				row.get(i).add(j);
			} else {
				// TreeSet<Integer> s = new TreeSet<Integer>(); s.add(j);
				ArrayList<Integer> s = new ArrayList<Integer>();
				s.add(j);
				row.put(i, s);
			}
			if (col.containsKey(j)) {
				col.get(j).add(i);
			} else {
				// TreeSet<Integer> s = new TreeSet<Integer>(); s.add(i);
				ArrayList<Integer> s = new ArrayList<Integer>();
				s.add(i);
				col.put(j, s);
			}
		}
		for (Integer i : row.keySet()) {
			if (row.get(i).size() > 1) {
				// if (row.get(i).getLast() - row.get(i).getFirst() <= 1) continue;
				// for (int j = row.get(i).getFirst() + 1; j <= row.get(i).getLast() - 1; j++) {
				// if (grid[i][j].getColor() != trailColor) {
				// if (!vis[i][j]) {
				// vis[i][j] = true;
				// ret.add(new Position(i, j));
				// }
				// }
				// }
				Integer[] copy = row.get(i).toArray(new Integer[0]);
				Arrays.sort(copy);
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(copy[0]);
				for (int idx = 1; idx < copy.length - 1; idx++) {
					if (copy[idx + 1] - copy[idx] == 1) {
					} else {
						tmp.add(copy[idx]);
					}
				}
				tmp.add(copy[copy.length - 1]);
				for (int idx = 0; idx < tmp.size() - 1; idx += 2) {
					int sJ = tmp.get(idx);
					int eJ = tmp.get(idx + 1);
					for (int j = sJ; j <= eJ; j++) {
						if (grid[i][j].getColor() != trailColor && grid[i][j].getState() != gridState.SafeZone) {
							if (!vis[i][j]) {
								vis[i][j] = true;
								ret.add(new Position(i, j));
							}
						}
					}
				}
			}
		}
		for (Integer j : col.keySet()) {
			if (col.get(j).size() > 1) {
				// if (col.get(j).getLast() - col.get(j).getFirst() <= 1) continue;
				// for (int i = col.get(j).getFirst() + 1; i <= col.get(j).getLast() - 1; i++) {
				// if (grid[i][j].getColor() != trailColor) {
				// if (!vis[i][j]) {
				// vis[i][j] = true;
				// ret.add(new Position(i, j));
				// }
				// }
				// }
				Integer[] copy = col.get(j).toArray(new Integer[0]);
				Arrays.sort(copy);
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(copy[0]);
				for (int idx = 1; idx < copy.length - 1; idx++) {
					if (copy[idx + 1] - copy[idx] == 1) {
					} else {
						tmp.add(copy[idx]);
					}
				}
				tmp.add(copy[copy.length - 1]);
				for (int idx = 0; idx < tmp.size() - 1; idx += 2) {
					int sI = tmp.get(idx);
					int eI = tmp.get(idx + 1);
					for (int i = sI; i <= eI; i++) {
						if (grid[i][j].getColor() != trailColor && grid[i][j].getState() != gridState.SafeZone) {
							if (!vis[i][j]) {
								vis[i][j] = true;
								ret.add(new Position(i, j));
							}
						}
					}
				}
			}
		}

		ArrayList<Position> tmp = new ArrayList<Position>();
		Integer canditRow = null;
		Integer canditCol = null;
		int angleCount = 0;
		for (GridBox gb : currentTrail) {
			int dirFlag[][] = new int[3][3];
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					dirFlag[i][j] = 0;
			int curRow = gb.getPosition().row;
			int curCol = gb.getPosition().col;
			vis[curRow][curCol] = true;
			for (int d = -1; d <= 1; d += 2) {
				int nowRow = curRow + d;
				int nowCol = curCol + d;
				if (0 <= nowRow && nowRow < 29) {
					if (grid[nowRow][curCol].getColor() == trailColor) {
						dirFlag[1 + d][0] += 1;
						dirFlag[1 + d][1] += 1;
						dirFlag[1 + d][2] += 1;
					}
				}
				if (0 <= nowCol && nowCol < 50) {
					if (grid[curRow][nowCol].getColor() == trailColor) {
						dirFlag[0][1 + d] += 1;
						dirFlag[1][1 + d] += 1;
						dirFlag[2][1 + d] += 1;
					}
				}
			}
			for (int di = -1; di <= 1; di += 2) {
				for (int dj = -1; dj <= 1; dj += 2) {
					if (dirFlag[1 + di][1 + dj] == 2) {
						angleCount++;
						canditRow = curRow + di;
						canditCol = curCol + dj;
					}
				}
			}
		}
		if (angleCount == 1) {
			if (canditRow != null && canditCol != null) {
				Position toAdd = new Position(canditRow, canditCol);
				if (!ret.contains(toAdd)) {
					ret.add(toAdd);
				}
			}
		}
		for (Position pos : ret) {
			// if (vis[pos.row][pos.col] || grid[pos.row][pos.col].getState() ==
			// gridState.SafeZone) continue;
			if (grid[pos.row][pos.col].getState() == gridState.SafeZone
					&& grid[pos.row][pos.col].getColor() == trailColor)
				continue;
			Queue<Position> q = new ArrayDeque<Position>();
			q.add(pos);
			while (!q.isEmpty()) {
				Position cur = q.remove();
				if (grid[cur.row][cur.col].getState() == gridState.SafeZone
						&& grid[cur.row][cur.col].getColor() == trailColor)
					continue;
				for (int d = -1; d <= 1; d += 2) {
					int newRow = cur.row + d;
					int newCol = cur.col + d;
					Position newPos;
					if (0 <= newRow && newRow < 29) {
						if (!vis[newRow][cur.col] && grid[newRow][cur.col].getColor() != trailColor) {
							newPos = new Position(newRow, cur.col);
							vis[newRow][cur.col] = true;
							tmp.add(newPos);
							q.add(newPos);
						}
					}
					if (0 <= newCol && newCol < 50) {
						if (!vis[cur.row][newCol] && grid[cur.row][newCol].getColor() != trailColor) {
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

	// Flim Fall added this for getting color name in filepath easier
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
		if ((b_color == Color.RED) || (b_color == Color.DARKRED)) {
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
		this.a_TrailColor = trailColor;
	}

	public void setPlayerBcolors(Color playerColor, Color trailColor) {
		this.b_color = playerColor;
		this.b_TrailColor = trailColor;
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	public SpeedPotion[] getSpeedPotions() {
		return speedPotions;
	}

	public void setSpeedPotions(SpeedPotion[] speedPotions) {
		this.speedPotions = speedPotions;
	}
}
