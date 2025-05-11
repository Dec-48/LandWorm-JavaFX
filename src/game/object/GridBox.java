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
	        // วาดเฉพาะเมื่อสีหรือ state เปลี่ยน
	        if (!color.equals(prevColor) || !isDrawn) {
	            // ล้างช่องด้วยความโปร่งใส
	            gc.setFill(Color.color(0, 0, 0, 0));
	            gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	            // ระบายสีโปร่งใส 30%
	     //      gc.setGlobalAlpha(0.3); // ความโปร่งใส 30%
	            gc.setFill((Color) color);
	            gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	            gc.setGlobalAlpha(1.0); // รีเซ็ต
	            prevColor = this.color;
	            isDrawn = true;
	        }
		}
	}
}
