package com.retrolaza.game.scenery.data;

import java.util.ArrayList;
import java.util.List;

public class BrickRow {
	
	private int row;
	private List<Brick> bricks;
	
	public BrickRow(int row) {
		bricks = new ArrayList<>();
		this.row = row;
	}
	
	public void addBrick(Brick b) {
		bricks.add(b);
	}

	public int getRow() {
		return row;
	}
	
	public List<Brick> getBricks() {
		return bricks;
	}
	
}
