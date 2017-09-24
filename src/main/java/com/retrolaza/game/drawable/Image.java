package com.retrolaza.game.drawable;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.retrolaza.game.Game;

public class Image extends Drawable {

	private java.awt.Image image;
	private Game game;
	private int x;
	private int y;
	
	public static int SCALE_SMOOTH = java.awt.Image.SCALE_SMOOTH;
	public static int SCALE_DEFAULT = java.awt.Image.SCALE_DEFAULT;
	public static int SCALE_FAST = java.awt.Image.SCALE_FAST;
	
	public Image(String path, Game game, int x, int y) throws IOException {
		this.image = ImageIO.read(new File(path));
		this.game = game;
		this.x = x;
		this.y = y;
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
	
	public void scale(int width, int height, int hints) {
		image = image.getScaledInstance(width, height, hints);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!isHiding()) g2d.drawImage(image, x, y, game);
	}
	
}
