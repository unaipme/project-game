package com.retrolaza.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameScreen extends GameChild {
	
	private GameScreen parent;
	private Image backgroundImage;
	private boolean showBackground;
	
	public GameScreen(Game g, GameScreen parent) {
		super(g);
		this.parent = parent;
		this.backgroundImage = null;
		this.showBackground = true;
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
		if (showBackground) g2d.drawImage(backgroundImage, 0, 0, game());
		super.draw(g2d);
	}
	
	public GameScreen getParent() {
		return parent;
	}
	
	public void setBackgroundImage(String file) {
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
