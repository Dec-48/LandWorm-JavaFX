package game.controller;

import game.object.GameplayBackground;
import game.object.GridBox;
import game.object.Player;
import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class GameCanvas extends Canvas{
	private boolean isBackgroundDrawn = false;
	
	public GameCanvas(int width, int height) {
		super(width, height);
		this.setVisible(true);
		this.setFocusTraversable(true);
		//addListerner();
	}
	
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		//draw background at first and done
	    if (!isBackgroundDrawn) {
	        for (IRenderable entity : RenderableHolder.getInstance().getEntities()) {
	            if (entity instanceof GameplayBackground) {
	                entity.draw(gc);
	            }
	        }
	        isBackgroundDrawn = true;
	    }

	    for (IRenderable entity : RenderableHolder.getInstance().getEntities()) {
	        if (entity instanceof GridBox || entity instanceof Player) {
	            entity.draw(gc);
	        }
	    }
        
	}
	
	public void addListerner() {
		this.setOnKeyPressed((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		this.setOnKeyReleased((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}
	
}
