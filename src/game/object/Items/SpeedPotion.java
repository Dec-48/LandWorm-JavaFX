package game.object.Items;

import game.object.Item;
import game.object.Player;
import game.object.Position;
import game.object.interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;

public class SpeedPotion extends Item implements Interactable{
	
	public SpeedPotion(Position pos) {
		super();
		setPosition(pos);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
			
		}
		
	}

	@Override
	public void useEffect(Player p) {
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
			
		}
	}

	@Override
	public void pick(Player p) {
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
			
		}
	}

}
