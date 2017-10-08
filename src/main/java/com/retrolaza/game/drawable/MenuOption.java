package com.retrolaza.game.drawable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import com.retrolaza.game.Game;

/**
 * Clase que representa una opción única de un menú. No debe usarse si no es mediante un objeto de la clase {@see com.retrolaza.game.drawable.Menu}.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class MenuOption extends Drawable {
	
	private Text text;
	
	private final static int DR_TEXT = Game.ID.getAndIncrement();
	
	public static final int DEFAULT_SIZE = 35;
	
	public MenuOption(String text, int x, int y) throws FontFormatException, IOException {
		this(text, x, y, DEFAULT_SIZE);
	}
	
	public MenuOption(String text, int x, int y, int size) throws FontFormatException, IOException {
		this(text, x, y, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/blocktopia.ttf")).deriveFont((float) size));
	}
	
	public MenuOption(String text, int x, int y, Font font) {
		this.text = new Text(text, x, y, font);
		addDrawable(DR_TEXT, this.text);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
	
	public void setFontFace(int type, String name) throws FontFormatException, IOException {
		text.setFont(Font.createFont(type, new File(name)));
	}
	
	public void setFontSize(int size) {
		text.setFont(text.getFont().deriveFont((float) size));
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
	
	public void setX(int x) {
		text.setX(x);
	}
	
	public int getY() {
		return text.getY();
	}
	
	public void setY(int y) {
		text.setY(y);
	}
	
}
