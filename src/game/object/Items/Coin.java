package game.object.Items;

import Manager.AudioManager;
import game.object.Item;
import game.object.Player;
import game.object.Position;
import game.object.interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Coin extends Item implements Interactable{
	private boolean isVisible = false;
	private Image Image1 = new Image(ClassLoader.getSystemResource("Image/coin1.png").toString());
	private Image Image2 = new Image(ClassLoader.getSystemResource("Image/coin2.png").toString());
	private int coinCount = 0;
	private int col;
	private int row;
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Coin(Position pos) { 
		super();
		setPosition(pos);
		this.row = pos.row;
		this.col = pos.col;
	}

	@Override
	public void draw(GraphicsContext gc) {
		float offset = (35-20)/2;
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
			if (0 <= coinCount && coinCount <= 14) {
				gc.drawImage(Image1, col * 20-offset, row * 20-offset, 35, 35);
			} else if (14 < coinCount && coinCount <= 29) {
				gc.drawImage(Image2, col * 20-offset, row * 20-offset, 35, 35);
			} else if (coinCount > 29)
				coinCount = 0;
			coinCount++;
		}
	}

	@Override
	public void useEffect(Player p) {
		p.score += 10;
		AudioManager.playEffect("Audio/ItemSelectEffect.mp3");
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
