package game.object;

public class Player extends GameObject{
	private int speed;
	private int paintSize;
	private String direction; //TODO : could be Enum
	private Position prevInPosition;
	private GridBox currentTrail;
	
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
	public String getdirection() {
		return direction;
	}
	public void setdirection(String direction) {
		this.direction = direction;
	}
	public Position getPrevInPosition() {
		return prevInPosition;
	}
	public void setPrevInPosition(Position prevInPosition) {
		this.prevInPosition = prevInPosition;
	}
	public GridBox getCurrentTrail() {
		return currentTrail;
	}
	public void setCurrentTrail(GridBox currentTrail) {
		this.currentTrail = currentTrail;
	}
}
