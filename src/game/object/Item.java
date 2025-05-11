package game.object;

import game.object.interfaces.Consumable;

public abstract class Item extends GameObject implements Consumable{
	protected String desc;
	protected boolean isVisible;

	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
