package game.object;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Player extends GameObject{
	private int speed;
	private int paintSize;
	private String direction; //TODO : could be Enum
	private Position prevInPosition;
	private List<GridBox> currentTrail;

	public Player() {
		this.setSpeed(10);
		this.setPaintSize(1);
		this.setDirection("N");
		this.setCurrentTrail(new ArrayList<GridBox>());
	}
	
	public void move() {
		int row = this.getPosition().row;
		int col = this.getPosition().col;
		if (direction == "D") {
			this.setPosition(new Position(row + 1, col));
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
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}
}
