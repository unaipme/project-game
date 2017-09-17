package com.retrolaza.game.drawable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class MenuOption extends Drawable {
	
	private Text text;
	//private boolean selected = false;
	
	public static final int DEFAULT_SIZE = 34;
	
	private Consumer<MenuOption> drawWhenSelected;
	private Consumer<MenuOption> drawWhenUnselected;
	
	public MenuOption(String text, int x, int y) throws FontFormatException, IOException {
		this(text, x, y, DEFAULT_SIZE);
	}
	
	public MenuOption(String text, int x, int y, int size) throws FontFormatException, IOException {
		this(text, x, y, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/blocktopia.ttf")).deriveFont((float) size));
	}
	
	public MenuOption(String text, int x, int y, Font font) {
		this.text = new Text(text, x, y, font);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		text.draw(g2d);
	}
	
	public void select() {
		//this.selected = true;
		drawWhenSelected.accept(this);
	}
	
	public void deselect() {
		//this.selected = false;
		drawWhenUnselected.accept(this);
	}
	
	public void changeFontFace(int type, String name) throws FontFormatException, IOException {
		text.setFont(Font.createFont(type, new File(name)));
	}
	
	public void changeFontSize(int size) {
		text.setFont(text.getFont().deriveFont((float) size));
	}
	
	public void setSelectionAnimations(Consumer<MenuOption> selected, Consumer<MenuOption> unselected) {
		this.drawWhenSelected = selected;
		this.drawWhenUnselected = unselected;
	}
	
	public Font getFont() {
		return text.getFont();
	}
	
	public String getTextString() {
		return text.getText();
	}
	
	public Text getText() {
		return text;
	}
	
	public int getX() {
		return text.getX();
	}
	
	public int getY() {
		return text.getY();
	}
	
}
