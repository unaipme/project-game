package com.retrolaza.game.drawable.movable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.retrolaza.game.Game;
import com.retrolaza.game.controls.MovableController;

public class Stick extends Movable {
	
	public static final int WIDTH = 120;
	public static final int HEIGHT = 20;

	public Stick(int x, int y) {
		super(x, y, HEIGHT, WIDTH);
		
		controls = new MovableController<>(this);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (controls.isKeyPressed(KeyEvent.VK_RIGHT)) {
			setSpeedX(5.0);
			if (getX() + WIDTH + getSpeedX() > Game.SCREEN_WIDTH) setX(Game.SCREEN_WIDTH - WIDTH);
		} else if (controls.isKeyPressed(KeyEvent.VK_LEFT)) {
			setSpeedX(-5.0);
			if (getX() + getSpeedX() < 0) setX(0.0);
		} else {
			setSpeedX(0.0);
		}
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int) getX(), (int) getY(), WIDTH, HEIGHT);
		g2d.setColor(Color.BLACK);
		g2d.drawRect((int) getX(), (int) getY(), WIDTH, HEIGHT);
	}
	
}
