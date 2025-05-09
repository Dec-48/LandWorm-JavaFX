package game.object;

import javafx.scene.paint.Paint;
import sharedObject.IRenderable;

public abstract class GameObject implements IRenderable{
	protected Position position;
	protected Paint color;
	public Paint getColor() {
		return color;
	}
	public void setColor(Paint color) {
		this.color = color;
	}

	protected String state;
	protected int z;
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
}
