package game.object.Items;

import game.object.Item;
import game.object.Player;
import game.object.interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpeedPotion extends Item implements Interactable{
	private static Image Image1 = new Image(ClassLoader.getSystemResource("Image/potion_1.png").toString());
	private static Image Image2 = new Image(ClassLoader.getSystemResource("Image/potion_2.png").toString());
	private int poCount=0;
	private int col = this.getPosition().col;
	private int row = this.getPosition().row;

	@Override
	public void draw(GraphicsContext gc) {
		if (0 <= poCount && poCount <= 14) {
			gc.drawImage(Image1, col * 20, row * 20, 20, 20);
		} else if (14 < poCount && poCount <= 29) {
			gc.drawImage(Image2, col * 20, row * 20, 20, 20);
		} else if (poCount > 29)
			poCount = 0;
		poCount++;

	}

	@Override
	public void useEffect(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pick(Player p) {
		// TODO Auto-generated method stub
		
	}

}
