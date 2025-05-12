package game.object.Items;

import game.object.Item;
import game.object.Player;
import game.object.Position;
import game.object.interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;

public class Coin extends Item implements Interactable{
	private boolean isVisible = false;
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Coin(Position pos) {
		setPosition(pos);
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
//			gc.drawImage(null, z, z);
		}
	}

	@Override
	public void useEffect(Player p) {
		p.score += 10;
	}

	@Override
	public void pick(Player p) {
		if (!isVisible) {
			// do nothing
		} else {			
			this.setPosition(null);
			this.isVisible = false;
			this.useEffect(p);
		}
	}

}
