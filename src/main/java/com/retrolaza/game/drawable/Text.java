package com.retrolaza.game.drawable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Text extends Drawable {
	
	private String text;
	private Font font;
	private int x;
	private int y;
	
	public static final int DEFAULT_SIZE = 34;
	
	public Text(String text, int x, int y) throws FontFormatException, IOException {
		this(text, x, y, DEFAULT_SIZE);
	}
	
	public Text(String text, int x, int y, int size) throws FontFormatException, IOException {
		this(text, x, y, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/blocktopia.ttf")).deriveFont((float) size));
	}
	
	public Text(String text, int x, int y, Font font) {
		this.text = text;
		this.font = font;
		this.x = x;
		this.y = y;
		this.hiding = false;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setFont(font);
		g2d.drawString(text, x, y);
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public Font getFont() {
		return font;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getText() {
		return text;
	}
	
}