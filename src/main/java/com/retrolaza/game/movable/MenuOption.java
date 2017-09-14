package com.retrolaza.game.movable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

public class MenuOption implements Drawable {
	
	private String text;
	private Font font;
	private int x;
	private int y;
	private boolean selected = false;
	
	public static final int DEFAULT_SIZE = 34;
	
	private BiConsumer<Graphics2D, MenuOption> drawWhenSelected;
	
	public MenuOption(String text, int x, int y) throws FontFormatException, IOException {
		this(text, x, y, DEFAULT_SIZE);
	}
	
	public MenuOption(String text, int x, int y, int size) throws FontFormatException, IOException {
		this(text, x, y, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/blocktopia.ttf")).deriveFont((float) size));
	}
	
	public MenuOption(String text, int x, int y, Font font) {
		this.text = text;
		this.font = font;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (selected) drawWhenSelected.accept(g2d, this);
		else {
			g2d.setFont(font);
			g2d.drawString(text, x, y);
		}
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public void changeFontFace(int type, String name) throws FontFormatException, IOException {
		font = Font.createFont(type, new File(name));
	}
	
	public void changeFontSize(int size) {
		font = font.deriveFont((float) size);
	}
	
	public void setDrawWhenSelected(BiConsumer<Graphics2D, MenuOption> response) {
		this.drawWhenSelected = response;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
