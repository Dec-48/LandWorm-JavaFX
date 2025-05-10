package game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridBox extends GameObject{
	private Item item;
	private gridState state = gridState.Blank;
	private static Color blankColor = Color.BISQUE;
	
	public GridBox(int row, int col) {
		this.color = blankColor;
		this.setPosition(new Position(row, col));
	}
	
	public int paintTrail(Color trailColor) { //XXX: debug is needed!!! 
		if (this.state != gridState.SafeZone) {
			
			if (this.color == trailColor) {
				return 1; // kill itself
			} else if (this.color != trailColor && this.color != blankColor) {
				return 2; // kill other player
			}

			this.setColor(trailColor);
			this.state = gridState.Trail;
		} else {
			if (this.color != trailColor) {
				this.state = gridState.Trail; 
			} else {
				return 3; // move in SafeZone
			}
			this.setColor(trailColor);
		}
		return 0;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	public gridState getState() {
		return state;
	}
	public void setState(gridState state) {
		this.state = state;
	}


	@Override
	public void draw(GraphicsContext gc) {
		int row = this.getPosition().row;
		int col = this.getPosition().col;
		gc.setFill(this.color);
		gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	}
}
