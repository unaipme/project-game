package com.retrolaza.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameScreen extends GameChild {
	
	private GameScreen parent;
	private Image backgroundImage;
	private Color backgroundColor;
	private boolean showBackground;
	
	public GameScreen(Game g, GameScreen parent) {
		super(g);
		this.parent = parent;
		this.backgroundImage = null;
		this.showBackground = true;
		this.backgroundColor = Color.BLACK;
	}
	
	@Override
	public void hide() {
		super.hide();
		this.showBackground = false;
		turnOff();
	}
	
	@Override
	public void show() {
		super.show();
		this.showBackground = true;
		setUp();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (g2d.getBackground() != this.backgroundColor) game().setBackground(this.backgroundColor);
		if (showBackground) g2d.drawImage(backgroundImage, 0, 0, game());
		super.draw(g2d);
	}
	
	public GameScreen getParent() {
		return parent;
	}
	
	public void setBackground(Color color) {
		this.backgroundColor = color;
	}
	
	public void setBackground(String file) {
		try {
			this.backgroundImage = ImageIO.read(new File(file));
			this.showBackground = true;
		} catch (IOException e) {
			e.printStackTrace();
			this.backgroundImage = null;
		}
	}
	
	public abstract void setUp();
	public abstract void turnOff();
	
}
