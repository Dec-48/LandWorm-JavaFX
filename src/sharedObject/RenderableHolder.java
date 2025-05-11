package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Manager.AudioManager;

public class RenderableHolder {
	private static RenderableHolder instance;
	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	
	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}
	
	public void add(IRenderable entity) {
		entities.add(entity);
		Collections.sort(entities, comparator);
	}
	
	public void clearHolder() {
		entities.clear();
	}
	
	public List<IRenderable> getEntities() {
		return entities;
	}
	
	public static RenderableHolder getInstance() {
		if (instance == null) {
			instance = new RenderableHolder();
		}
		return instance;
	}
}
