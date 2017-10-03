package com.retrolaza.game.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import com.retrolaza.game.Game;

public class Button extends Drawable {
	
	private Text text;
	private int x;
	private int y;
	
	private int width;
	
	private Game game;
	private Image image;
	
	public Button(String text, Game game, int x, int y) throws FontFormatException, IOException {
		this.game = game;
		this.x = x;
		this.y = y;
		this.text = new Text(text, x + 10, y + 43, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Pixeled.ttf")).deriveFont(25f));
		this.text.setColor(Color.BLACK);
		this.image = null;
		this.width = text.length() * 25;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, width, 55);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x, y, width, 55);
		text.draw(g2d);
		if (image != null) {
			image.draw(g2d);
		}
	}
	
	public void setImage(String s) throws IOException {
		this.image = new Image(s, game, x + 10, y + 5);
		text.setX(x + text.getText().length() + 55);
		width = text.getText().length() * 25 + 55;
		image.scale(-1, 45, Image.SCALE_SMOOTH);
	}
	
	public int getWidth() {
		return width;
	}
	
}
