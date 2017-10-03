package com.retrolaza.game.scenery.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Optional;

import com.retrolaza.game.drawable.movable.Movable;

public class Brick extends Movable {
	
	private int lives;
	private Runnable whenCollision;
	
	public Brick(int x, int y, int lives) {
		super(x, y, 40, 120);
		this.lives = lives;
	}
	
	public int getLives() {
		return lives;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!hiding) {
			if (lives == 2) g2d.setColor(Color.DARK_GRAY);
			else if (lives == 1) g2d.setColor(Color.WHITE);
			else if (lives == -1) g2d.setColor(Color.BLACK);
			g2d.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
			if (lives == -1) g2d.setColor(Color.WHITE);
			else g2d.setColor(Color.BLACK);
			g2d.drawRect((int) getX(), (int) getY(), getWidth(), getHeight());
		}
	}
	
	public boolean collision() {
		if (lives >= 0) {
			lives--;
			if (lives == 0) hide();
		}
		Optional.ofNullable(whenCollision).ifPresent(Runnable::run);
		return lives == 0;
	}
	
	public void setCollisionEvent(Runnable r) {
		this.whenCollision = r;
	}
	
}
