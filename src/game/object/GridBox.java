package game.object;

public class GridBox extends GameObject{
	private Item item;
	private Player player;
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public void movePlayer(GridBox nextGridBox) {
		if (this.player != null) {
			nextGridBox.setPlayer(this.player);
			this.player = null;
		}
	}
}
