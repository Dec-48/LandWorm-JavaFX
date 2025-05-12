package game.object.Items;


import Manager.AudioManager;
import game.object.Item;
import game.object.Player;
import game.object.Position;
import game.object.interfaces.Interactable;
import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class SpeedPotion extends Item implements Interactable {
	private static Image Image1 = new Image(ClassLoader.getSystemResource("Image/potion_1.png").toString());
	private static Image Image2 = new Image(ClassLoader.getSystemResource("Image/potion_2.png").toString());
	private int poCount = 0;
	private int col;
	private int row;

	public SpeedPotion(Position pos) {
		super();
		setPosition(pos);
		this.row = pos.row;
		this.col = pos.col;
	} 

	@Override
	public void draw(GraphicsContext gc) {
		float offset = (35-20)/2;
		if (!isVisible) {
			// do nothing
		} else {
			if (0 <= poCount && poCount <= 14) {
				gc.drawImage(Image1, col * 20-offset, row * 20-offset, 35, 35);
			} else if (14 < poCount && poCount <= 29) {
				gc.drawImage(Image2, col * 20-offset, row * 20-offset, 35, 35);
			} else if (poCount > 29)
				poCount = 0;
			poCount++; 
		}
	}

	@Override
	public void useEffect(Player p) {
		AudioManager.playEffect("Audio/ItemSelectEffect.mp3");
		new Thread(() -> { // XXX avoid this
			Platform.runLater(() -> p.setSpeed(17));
			try {
				Thread.sleep(1250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(() -> p.setSpeed(10));
		}).start();
	}

	@Override
	public void pick(Player p) {
		// TODO Auto-generated method stub
		if (!isVisible) {
			// do nothing
		} else {
			if (p.getSpeed() > 10) {
				// do nothing
			} else {
				this.setPosition(null);
				this.isVisible = false;
				this.useEffect(p);
			}
		}
	}

}
