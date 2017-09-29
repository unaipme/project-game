package com.retrolaza.game.scenery.drawable;

import java.util.Map;

import com.retrolaza.game.Game;
import com.retrolaza.game.drawable.Drawable;

public class Scenery extends Drawable {
	
	private Map<Integer, BrickRow> rows;
	private String background;
	
	public Scenery(Map<Integer, BrickRow> rows, String background) {
		this.rows = rows;
		this.background = background;
		rows.values().forEach(r -> addDrawable(Game.ID.getAndIncrement(), r));
	}

	public Map<Integer, BrickRow> getRows() {
		return rows;
	}
	
	public String getBackground() {
		return background;
	}
	
}
