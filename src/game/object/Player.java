package game.object;

import java.util.ArrayList;
import java.util.List;

import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player extends GameObject{
	private int speed;
	private int paintSize;
	private int frameCount = 0;
	private String direction; //TODO : could be Enum
	private Position prevInPosition;
	private List<GridBox> currentTrail;
	private ArrayList<KeyCode> movingKey;

	public Player(KeyCode[] movingKey) {
		this.setSpeed(18);
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
					if (this.position.row == 0 || this.direction == "DOWN") break;
					direction = "UP";
					break;
				case 1:
					if (this.position.col == 0 || this.direction == "RIGHT") break;
					direction = "LEFT";
					break;
				case 2:
					if (this.position.row == Position.maxHeight - 1 || this.direction == "UP") break;
					direction = "DOWN";
					break;
				case 3:
					if (this.position.col == Position.maxWidth - 1 || this.direction == "LEFT") break;
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
	public Position getPrevInPosition() {
		return prevInPosition;
	}
	public void setPrevInPosition(Position prevInPosition) {
		this.prevInPosition = prevInPosition;
	}
	
	public List<GridBox> getCurrentTrail() {
		return currentTrail;
	}

	public void setCurrentTrail(List<GridBox> currentTrail) {
		this.currentTrail = currentTrail;
	}
	
	public void addCurrentTrail(GridBox gb) {
		this.currentTrail.add(gb);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		int row = this.position.row;
		int col = this.position.col;
		gc.setFill(this.color);
		gc.fillOval(col * 20, row * 20, 10, 10);
	}
	
	public void setMovingKey(KeyCode[] movingKey) {
		this.movingKey = new ArrayList<KeyCode>(List.of(movingKey));
	}

}
