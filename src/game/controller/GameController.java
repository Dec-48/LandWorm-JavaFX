package game.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import Manager.AudioManager;
import game.object.GridBox;
import game.object.Player;
import game.object.PlayerState;
import game.object.Position;
import game.object.gridState;
import game.object.Items.Coin;
import game.object.Items.SpeedPotion;
import input.InputUtility;
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
	private ArrayList<SpeedPotion> speedPotions = new ArrayList<SpeedPotion>();
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private GridBox[][] grid = new GridBox[30][50];
	private int maxPotion = 4;
	private Color a_color = Color.DARKRED;
	private Color a_TrailColor = Color.RED;
	private Color b_color = Color.DARKBLUE;
	private Color b_TrailColor = Color.BLUE;
	private GameplayBackground background;

	public void initialGame() {
		playerA.setColor(a_color);
		playerB.setColor(b_color);
		for (int i = 0; i < 30; i++) {
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
		ArrayList<Position> randPotionPos = new ArrayList<Position>();
		ArrayList<Position> randCoinPos = new ArrayList<Position>();
		for (int i = 0; i < maxPotion;) {
			Position posRand = new Position(ThreadLocalRandom.current().nextInt(2, 27),
					ThreadLocalRandom.current().nextInt(2, 47));
			if (randPotionPos.contains(posRand))
				continue;
			randPotionPos.add(posRand);
			i++;
		}
		for (int i = 0; i < 5;) {
			Position posRand = new Position(ThreadLocalRandom.current().nextInt(2, 27),
					ThreadLocalRandom.current().nextInt(2, 47));
			if (randPotionPos.contains(posRand) || randCoinPos.contains(posRand))
				continue;
			randCoinPos.add(posRand);
			i++;
		}
		for (Position pos : randPotionPos) {
			SpeedPotion sp = new SpeedPotion(pos);
			sp.setVisible(false);
			sp.setZ(3);
			speedPotions.add(sp);
			RenderableHolder.getInstance().add(sp);
		}
		for (Position pos : randCoinPos) {
			Coin c = new Coin(pos);
			c.setVisible(true);
			c.setZ(3);
			coins.add(c);
			RenderableHolder.getInstance().add(c);
		}
	}

	public void update(int currentTime) {
		if ((currentTime + 1) % 10 == 0) {
			int idx = currentTime / 10;
			if (idx < speedPotions.size()) {
				SpeedPotion sp = speedPotions.get(idx);
				if (sp.getPosition() != null)
					sp.setVisible(true);
			}
		}
		playerA.move(); // move playerA along the direction from inputUtility
		playerB.move(); // move playerB along the direction from inputUtility
		int Arow = playerA.getPosition().row;
		int Acol = playerA.getPosition().col;
		playerA.addCurrentTrail(grid[Arow][Acol]);
		int Brow = playerB.getPosition().row;
		int Bcol = playerB.getPosition().col;
		playerB.addCurrentTrail(grid[Brow][Bcol]);
		for (SpeedPotion sp : speedPotions) {
			if (!sp.isVisible() || sp.getPosition() == null) {
				// do nothing
			} else {
				if (playerA.getPosition().equals(sp.getPosition())) {
					sp.pick(playerA);
				}
			}
		}
		for (Coin c : coins) {
			if (!c.isVisible() || c.getPosition() == null) {
				// do nothing
			} else {
				if (playerA.getPosition().equals(c.getPosition())) {
					c.pick(playerA);
				}
			}
		}
		for (SpeedPotion sp : speedPotions) {
			if (!sp.isVisible() || sp.getPosition() == null) {
				// do nothing
			} else {
				if (playerB.getPosition().equals(sp.getPosition())) {
					sp.pick(playerB);
				}
			}
		}
		for (Coin c : coins) {
			if (!c.isVisible() || c.getPosition() == null) {
				// do nothing
			} else {
				if (playerB.getPosition().equals(c.getPosition())) {
					c.pick(playerB);
				}
			}
		}
		// This method handles double line trail.
		int paintStateA = grid[Arow][Acol].paintTrail(a_TrailColor);
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
				ArrayList<Position> spaces = this.fillSpace(a_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setColor(a_TrailColor);
					grid[pos.row][pos.col].setState(gridState.SafeZone);
				}
				playerA.getCurrentTrail().clear();
			}
		} else if (paintStateA == 1) {
			if (playerA.getCurrentTrail().getLast() != grid[Arow][Acol]) { // kill itself
				AudioManager.playEffect("Audio/Killeffect.wav");
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
				playerA.getCurrentTrail().clear();
			}
		} else { // kill other
			AudioManager.playEffect("Audio/Killeffect.wav");
			int row = playerB.getprevOutPosition().row;
			int col = playerB.getprevOutPosition().col;
			playerB.setPosition(new Position(row, col));
			AudioManager.playEffect("Audio/Killeffect.wav");
			for (GridBox gb : playerB.getCurrentTrail()) {
				if (gb.getState() == gridState.Trail) {
					gb.setColor(GridBox.blankColor);
					gb.setState(gridState.Blank);
				}
			}
			grid[Arow][Acol].setState(gridState.Trail);
			grid[Arow][Acol].setColor(a_TrailColor);
			playerA.addCurrentTrail(grid[Arow][Acol]);

			grid[Brow][Bcol].setState(gridState.Blank);
			grid[Brow][Bcol].setColor(GridBox.blankColor);
			playerB.getCurrentTrail().clear();
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
				ArrayList<Position> spaces = this.fillSpace(b_TrailColor);
				for (Position pos : spaces) {
					grid[pos.row][pos.col].setState(gridState.SafeZone);
					grid[pos.row][pos.col].setColor(b_TrailColor);
				}
				playerB.getCurrentTrail().clear();
			}
		} else if (paintStateB == 1) {
			if (playerB.getCurrentTrail().getLast() != grid[Brow][Bcol]) { // kill itself
				AudioManager.playEffect("Audio/Killeffect.wav");
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
		} else {
			int row = playerA.getprevOutPosition().row;
			int col = playerA.getprevOutPosition().col;
			playerA.setPosition(new Position(row, col));
			AudioManager.playEffect("Audio/Killeffect.wav");
			for (GridBox gb : playerA.getCurrentTrail()) {
				if (gb.getState() == gridState.Trail) {
					gb.setColor(GridBox.blankColor);
					gb.setState(gridState.Blank);
				}
			}
			grid[Brow][Bcol].setState(gridState.Trail);
			grid[Brow][Bcol].setColor(b_TrailColor);
			playerB.addCurrentTrail(grid[Brow][Bcol]);
			grid[Arow][Acol].setColor(GridBox.blankColor);
			grid[Arow][Acol].setState(gridState.Blank);
			playerA.getCurrentTrail().clear();
		}
	}
	
	private ArrayList<Position> fillSpace(Paint trailColor) {
		boolean can[][] = new boolean[30][50];
		boolean flag[][] = new boolean[30][50];
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 50; j++) {
				flag[i][j] = false;
				can[i][j] = true;
				if (grid[i][j].getColor() == trailColor) {
					can[i][j] = false;
				}
			}
		}
		Position startPos = new Position(0, 0);
		Queue<Position> q = new ArrayDeque<Position>();
		q.add(startPos);
		while (!q.isEmpty()) {
			Position cur = q.remove();
			if (flag[cur.row][cur.col]) continue;
			if (!can[cur.row][cur.col]) continue;
			flag[cur.row][cur.col] = true;
			for (int d = -1; d <= 1; d += 2) {
				int newRow = cur.row + d;
				int newCol = cur.col + d;
				if (0 <= newRow && newRow < 30) {
					if (!flag[newRow][cur.col])
						q.add(new Position(newRow, cur.col));
				}
				if (0 <= newCol && newCol < 50) {
					if (!flag[cur.row][newCol])
						q.add(new Position(cur.row, newCol));
				}
			}
		}
		ArrayList<Position> ret = new ArrayList<Position>();
		for (int i = 1; i < 29; i++) {
			for (int j = 1; j < 49; j++) {
				if (!flag[i][j]) {
					ret.add(new Position(i, j));
				}
			}
		}
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

	// return the winner to be shown in resultPane.
	public Player getWinnerPlayer() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 50; j++) {
				if (grid[i][j].getColor() == a_TrailColor) {
					playerA.score += 1;
				} else if (grid[i][j].getColor() == b_TrailColor) {
					playerB.score += 1;
				}
			}
		}
		if (playerA.score >= playerB.score) {
			return playerA;
		} else {
			return playerB;
		}
	}

	public static void stopGameController() {
		instance = null;
	}
}
