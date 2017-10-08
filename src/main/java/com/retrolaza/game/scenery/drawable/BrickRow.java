package com.retrolaza.game.scenery.drawable;

import java.util.ArrayList;
import java.util.List;

import com.retrolaza.game.Game;
import com.retrolaza.game.drawable.Drawable;

/**
 * Clase que representa una fila de ladrillos.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class BrickRow extends Drawable {
	
	private int row;
	private List<Brick> bricks;
	
	public BrickRow(int row) {
		bricks = new ArrayList<>();
		this.row = row;
	}
	
	public void addBrick(Brick b) {
		bricks.add(b);
		addDrawable(Game.ID.getAndIncrement(), b);
	}

	public int getRow() {
		return row;
	}
	
	public List<Brick> getBricks() {
		return bricks;
	}
	
}
