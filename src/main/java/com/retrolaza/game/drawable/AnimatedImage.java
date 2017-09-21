package com.retrolaza.game.drawable;

import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.retrolaza.game.Game;

public class AnimatedImage extends Drawable {
	
	private Icon image;
	private int x;
	private int y;
	private Game game;
	
	public AnimatedImage(String path, Game game, int x, int y) {
		this.image = new ImageIcon(path);
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		image.paintIcon(game, g2d, x, y);
	}
	
}
