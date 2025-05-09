package game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridBox extends GameObject{
	private Item item;
	
	public GridBox(int row, int col) {
		this.color = Color.BISQUE;
		this.setPosition(new Position(row, col));
	}
	
	public void paintTrail(Color trailColor) {
		if (this.color != trailColor) { // TODO : this can be used to indicate death state of player
			this.setColor(trailColor);
			this.state = gridState.Trail;
		}
		else if (this.state != gridState.SafeZone) {
			this.setColor(trailColor);
			this.state = gridState.Trail;
		}
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public void draw(GraphicsContext gc) {
		int row = this.getPosition().row;
		int col = this.getPosition().col;
		gc.setFill(this.color);
		gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	}
}
