package game.object;

import game.controller.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GridBox extends GameObject{
	private Item item;
	private gridState state = gridState.Blank;
	private static Color blankColor = Color.BISQUE;
	private Paint prevColor = blankColor;
	private boolean isDrawn = false;
	
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
			prevColor = this.getColor();
			this.setColor(trailColor);
			this.state = gridState.Trail;
		} else {
			if (this.color != trailColor) {
				this.state = gridState.Trail; 
			} else {
				return 3; // move in SafeZone
			}
			prevColor = this.getColor();
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
		if (state == gridState.Trail || state == gridState.SafeZone) {
	        int row = this.getPosition().row;
	        int col = this.getPosition().col;
	        // ล้างช่องก่อนระบายสีใหม่
	        gc.setFill(blankColor);
	        gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	        // ระบายสีอ่อนคงที่
	        gc.setFill(color);
	        gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	        prevColor = this.color; 
	    }
	}
}
