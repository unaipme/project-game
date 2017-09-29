package com.retrolaza.game.scenery.drawable;

import java.util.Map;

import com.retrolaza.game.Game;
import com.retrolaza.game.drawable.Drawable;

public class Scenery extends Drawable {
	
	private Map<Integer, BrickRow> rows;
	private String background;
	private String nextLevel;
	private double speed;
	
	public Scenery(Map<Integer, BrickRow> rows, String background, String nextLevel, double speed) {
		this.rows = rows;
		this.background = background;
		this.nextLevel = nextLevel;
		this.speed = speed;
		rows.values().forEach(r -> addDrawable(Game.ID.getAndIncrement(), r));
	}

	public double getSpeed() {
		return speed;
	}

	public String getNextLevel() {
		return nextLevel;
	}

	public Map<Integer, BrickRow> getRows() {
		return rows;
	}
	
	public String getBackground() {
		return background;
	}
	
}
