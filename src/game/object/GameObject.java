package game.object;

import javafx.scene.paint.Paint;
import sharedObject.IRenderable;

public abstract class GameObject implements IRenderable{
	protected Position position;
	protected Paint color;
	protected gridState state = gridState.Blank;
	protected int z;
	
	public Paint getColor() {
		return color;
	}
	public void setColor(Paint color) {
		this.color = color;
	}

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public gridState getState() {
		return state;
	}
	public void setState(gridState state) {
		this.state = state;
	}
	
	public int getZ() {
		return this.z;
	}
	public void setZ(int z) {
		this.z = z;
	}
}
