package com.retrolaza.game.drawable.movable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import com.retrolaza.game.Game;
import com.retrolaza.game.scenery.drawable.Brick;

public class Ball extends Movable {
	
	private static final int DIAMETER = 20;
	
	private static final double TOTAL_SPEED = Math.sqrt(2.0);
	
	private Runnable lifeLostListener = null;
	private Runnable noMoreBricksListener = null;

	public Ball(int x, int y) {
		super(x, y, DIAMETER, DIAMETER);
		
		initialSpeed();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (getX() + getWidth() + getSpeedX() > Game.SCREEN_WIDTH || getX() + getSpeedX() < 0) setSpeedX(getSpeedX() * -1);
		if (getY() + getSpeedY() <= 80) setSpeedY(getSpeedY() * -1);
		if (getY() + getHeight() + getSpeedY() >= Game.SCREEN_HEIGHT) {
			toggleLifeLost();
			return;
		}
		ListIterator<Movable> it = getCollisionables().listIterator();
		List<Movable> toRemove = new ArrayList<>();
		while (it.hasNext()) {
			Movable m = it.next();
			if (getSpeedY() > 0) {
				if (getY() + getSpeedY() + getHeight() > m.getY() && getY() < m.getY() + m.getHeight() &&
						getX() + getWidth() > m.getX() && getX() < m.getX() + m.getWidth()) {
					if (m instanceof Stick) {
						double relativePosition = (((getX() + (getWidth() / 2)) - m.getX()) * 100) / 120;
						double angle = 25 + ((relativePosition * 45) / 100);
						double newSpeedX = Math.sin(Math.toRadians(angle)) * TOTAL_SPEED;
						if (angle < 45) newSpeedX *= -1;
						setSpeedX(newSpeedX);
						setSpeedY(-Math.cos(Math.toRadians(angle)) * TOTAL_SPEED);
					} else {
						setSpeedY(getSpeedY() * -1);
						if (((Brick) m).collision()) toRemove.add(m);
					}
				}
			} else if (getSpeedY() < 0) {
				if (getY() + getSpeedY() < m.getY() + m.getHeight() && getY() + getSpeedY() > m.getY() &&
						getX() + getWidth() > m.getX() && getX() < m.getX() + m.getWidth()) {
					setSpeedY(getSpeedY() * -1);
					if (m instanceof Brick && ((Brick) m).collision()) toRemove.add(m);
				}
			}
			if((getSpeedX() != 0) && 
					((getX() + getSpeedX() + getWidth() > m.getX() && getX() < m.getX() + m.getWidth() &&
					getY() + getHeight() > m.getY() && getY() < m.getY() + m.getHeight()) ||
					(getX() + getSpeedX() < m.getX() + m.getWidth() && getX() + getSpeedX() > m.getX() &&
					getY() + getHeight() > m.getY() && getY() < m.getY() + m.getHeight()))) {
				if (m instanceof Brick) {
					setSpeedX(getSpeedX() * -1);
					if (((Brick) m).collision()) toRemove.add(m);
				} else if (m instanceof Stick && m.getSpeedX() != 0) {
					setSpeedX(m.getSpeedX());
				}
			}
		}
		toRemove.forEach(e -> getCollisionables().remove(e));
		if (getCollisionables().size() == 1) toggleNoMoreBricks();
		g2d.setColor(Color.WHITE);
		g2d.fillOval((int) getX(), (int) getY(), getWidth(), getHeight());
		g2d.setColor(Color.BLACK);
		g2d.drawOval((int) getX(), (int) getY(), getWidth(), getHeight());
	}
	
	public void setLifeLostListener(Runnable runnable) {
		this.lifeLostListener = runnable;
	}
	
	public void toggleLifeLost() {
		if (lifeLostListener != null) {
			lifeLostListener.run();
		}
	}
	
	public void setNoMoreBricksListener(Runnable runnable) {
		this.noMoreBricksListener = runnable;
	}
	
	public void toggleNoMoreBricks() {
		if (noMoreBricksListener != null) {
			noMoreBricksListener.run();
		}
	}
	
	public void initialSpeed() {
		setSpeedX(0.0);
		setSpeedY(TOTAL_SPEED);
	}
	
	public void makeWait() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				initialSpeed();
			}
		};
		new Timer().schedule(task, 3000);
	}

	public void stop() {
		setSpeedX(0);
		setSpeedY(0);
	}

}
