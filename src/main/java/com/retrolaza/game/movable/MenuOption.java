package com.retrolaza.game.movable;

import java.awt.Font;
import java.awt.Graphics2D;

public class MenuOption implements Drawable {
	
	private String text;
	private Font font;
	private int x;
	private int y;
	private boolean selected;
	
	private int selectedOffset = 75;
	
	protected final static int T_SIZE = 34;
	
	public MenuOption(String text, int x, int y) {
		this.text = text;
		font = new Font("Blocktopia", Font.PLAIN, T_SIZE);
		this.x = x;
		this.y = y;
		this.selected = false;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setFont(font);
		g2d.drawString(text, (selected) ? x + selectedOffset : x, y);
	}
	
	public int getSelectedOffset() {
		return selectedOffset;
	}
	
	public void setSelectedOffset(int offset) {
		selectedOffset = offset;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
