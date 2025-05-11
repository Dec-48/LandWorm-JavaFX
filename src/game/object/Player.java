package game.object;

import java.util.ArrayList;
import java.util.List;

import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player extends GameObject {
	private int speed;
	private int paintSize;
	private int frameCount = 0;
	private int wormCount = 0; //
	private PlayerState playerState = PlayerState.In;
	private String direction; // TODO : could be Enum
	private Position prevOutPosition;
	private List<GridBox> currentTrail;
	private ArrayList<KeyCode> movingKey;

	public Player(KeyCode[] movingKey) {
		this.setSpeed(10);
		this.setPaintSize(1);
		this.setDirection("N");
		this.setCurrentTrail(new ArrayList<GridBox>());
		this.setMovingKey(movingKey);
	}

	public void move() {
		this.setCurrentDirection();
		this.frameCount++;
		if (this.frameCount == (20 - this.speed)) {
			this.frameCount = 0;
			switch (this.direction) {
				case "UP":
					this.position.updateRow(-1);
					break;
				case "LEFT":
					this.position.updateCol(-1);
					break;
				case "DOWN":
					this.position.updateRow(+1);
					break;
				case "RIGHT":
					this.position.updateCol(+1);
					break;
				default:
					break;
			}
		}
	}

	public void setCurrentDirection() {
		for (KeyCode kc : InputUtility.getKeyPressed()) {
			if (this.movingKey.contains(kc)) {
				switch (this.movingKey.indexOf(kc)) {
					case 0:
						if (this.position.row == 0 || this.direction == "DOWN")
							break;
						direction = "UP";
						break;
					case 1:
						if (this.position.col == 0 || this.direction == "RIGHT")
							break;
						direction = "LEFT";
						break;
					case 2:
						if (this.position.row == Position.maxHeight - 1 || this.direction == "UP")
							break;
						direction = "DOWN";
						break;
					case 3:
						if (this.position.col == Position.maxWidth - 1 || this.direction == "LEFT")
							break;
						direction = "RIGHT";
						break;

					default:
						break;
				}
			}
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPaintSize() {
		return paintSize;
	}

	public void setPaintSize(int paintSize) {
		this.paintSize = paintSize;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Position getprevOutPosition() {
		return prevOutPosition;
	}

	public void setprevOutPosition(Position prevOutPosition) {
		this.prevOutPosition = prevOutPosition;
	}

	public List<GridBox> getCurrentTrail() {
		return currentTrail;
	}

	public void setCurrentTrail(List<GridBox> currentTrail) {
		this.currentTrail = currentTrail;
	}

	public void addCurrentTrail(GridBox gb) {
		if (this.currentTrail.contains(gb)) {
			// do nothing
		} else {
			if (gb.getState() != gridState.SafeZone)
				this.currentTrail.add(gb);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		wormCount++;
		String stringColor;
		if ((this.getColor() == Color.RED) || (this.getColor() == Color.DARKRED)) {
			stringColor = "Red";
		} else if (this.getColor() == Color.YELLOW) {
			stringColor = "Yellow";
		} else if (this.getColor() == Color.GREEN) {
			stringColor = "Green";
		} else if ((this.getColor() == Color.BLUE) || (this.getColor() == Color.DARKBLUE)) {
			stringColor = "Blue";
		} else {
			stringColor = "Pink";
		}

		if (this.getDirection() == "UP" || this.getDirection() == "DOWN") {
			direction = "V";
		} else {
			direction = "H";
		}

		String Imagepath = "Image/" + stringColor + "worm/" + stringColor + "worm" + direction;

		if (wormCount == 1) {
			// gc.clearRect(this.getPosition().col * 20, this.getPosition().row * 20, 20,
			// 20);
			Image Image1 = new Image(ClassLoader.getSystemResource(Imagepath + "1.png").toString());
			if (direction == "UP" || direction == "RIGHT") {
				gc.drawImage(Image1, this.getPosition().col * 20, this.getPosition().row * 20, 20, 20);
			} else if (direction == "LEFT") {
				gc.drawImage(Image1, this.getPosition().col * 20 + 20, this.getPosition().row * 20, -20, 20);
			} else {
				gc.drawImage(Image1, this.getPosition().col * 20, this.getPosition().row * 20 + 20, 20, -20);
			}
		} else if (wormCount == 11) {
			// gc.clearRect(this.getPosition().col * 20, this.getPosition().row * 20, 20,
			// 20);
			Image Image2 = new Image(ClassLoader.getSystemResource(Imagepath + "2.png").toString());
			if (direction == "UP" || direction == "RIGHT") {
				gc.drawImage(Image2, this.getPosition().col * 20, this.getPosition().row * 20, 20, 20);
			} else if (direction == "LEFT") {
				gc.drawImage(Image2, this.getPosition().col * 20 + 20, this.getPosition().row * 20, -20, 20);
			} else {
				gc.drawImage(Image2, this.getPosition().col * 20, this.getPosition().row * 20 + 20, 20, -20);
			}
		} else if (wormCount == 16) {
			// gc.clearRect(this.getPosition().col * 20, this.getPosition().row * 20, 20,
			// 20);
			Image Image3 = new Image(ClassLoader.getSystemResource(Imagepath + "3.png").toString());
			if (direction == "UP" || direction == "RIGHT") {
				gc.drawImage(Image3, this.getPosition().col * 20, this.getPosition().row * 20, 20, 20);
			} else if (direction == "LEFT") {
				gc.drawImage(Image3, this.getPosition().col * 20 + 20, this.getPosition().row * 20, -20, 20);
			} else {
				gc.drawImage(Image3, this.getPosition().col * 20, this.getPosition().row * 20 + 20, 20, -20);
			}
		} else if (wormCount == 21) {
			// gc.clearRect(this.getPosition().col * 20, this.getPosition().row * 20, 20,
			// 20);
			Image Image2 = new Image(ClassLoader.getSystemResource(Imagepath + "2.png").toString());
			if (direction == "UP" || direction == "RIGHT") {
				gc.drawImage(Image2, this.getPosition().col * 20, this.getPosition().row * 20, 20, 20);
			} else if (direction == "LEFT") {
				gc.drawImage(Image2, this.getPosition().col * 20 + 20, this.getPosition().row * 20, -20, 20);
			} else {
				gc.drawImage(Image2, this.getPosition().col * 20, this.getPosition().row * 20 + 20, 20, -20);
			}
		} else if (wormCount == 26) {
			wormCount = 0;
		}

		// int row = this.getPosition().row;
		// int col = this.getPosition().col;
		// gc.setFill(this.color);
		// gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	}

	public void setMovingKey(KeyCode[] movingKey) {
		this.movingKey = new ArrayList<KeyCode>(List.of(movingKey));
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

}
