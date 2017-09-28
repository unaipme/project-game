package com.retrolaza.game.drawable;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public abstract class Drawable {
	
	protected Map<Integer, Drawable> drawables = new HashMap<>();
	
	protected boolean hiding;
	
	protected void addDrawable(Integer id, Drawable drawable) {
		drawables.put(id, drawable);
	}
	
	public Drawable getDrawable(Integer id) {
		return drawables.get(id);
	}
	
	public void hide() {
		this.hiding = true;
	}
	
	public void show() {
		this.hiding = false;
	}
	
	public boolean isHiding() {
		return this.hiding;
	}
	
	public void draw(Graphics2D g2d) {
		if (!isHiding()) {
			drawables.forEach((id, d) -> d.draw(g2d));
		}
	}
}
