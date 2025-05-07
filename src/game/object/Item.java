package game.object;

import game.object.interfaces.Consumable;

public abstract class Item extends GameObject implements Consumable{
	protected String desc;

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
