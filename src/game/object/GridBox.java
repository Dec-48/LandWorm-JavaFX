package game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridBox extends GameObject{
	private Item item;
	
	public GridBox(int row, int col) {
		this.setColor("G");
		this.setPosition(new Position(row, col));
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void draw(GraphicsContext gc) {
		int row = this.getPosition().row;
		int col = this.getPosition().col;
		if (this.color == "R") {
			gc.setFill(Color.RED);
		} else if (this.color == "B") {
			gc.setFill(Color.BLUE);
		} else {
			gc.setFill(Color.GREEN);
		}
		gc.fillRoundRect(col * 20, row * 20, 20, 20, 3, 3);
	}
}
