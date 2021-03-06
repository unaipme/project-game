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

/**
 * Clase que representa la bola y calcula sus respuestas físicas (colisiones) respecto a la pantalla y los otros elementos relevantes.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Ball extends Movable {
	
	private static final int DIAMETER = 20;
	
	private double totalSpeed;
	
	private Runnable lifeLostListener = null;
	private Runnable noMoreBricksListener = null;

	public Ball(int x, int y) {
		super(x, y, DIAMETER, DIAMETER);
		initialSpeed();
	}
	
	public double getTotalSpeed() {
		return totalSpeed;
	}

	/**
	 * La velocidad total es el módulo de la velocidad en X y la velocidad en Y, es decir vx^2 + vy^2 = vt^2. A partir de esta velocidad total se calcula la velocidad en cada eje.
	 * @param totalSpeed Velocidad total
	 */
	public void setTotalSpeed(double totalSpeed) {
		this.totalSpeed = Math.sqrt(2 * Math.pow(totalSpeed, 2));
	}
	
	/**
	 * Método que calcula la respuesta física
	 */
	private void calculatePhysics() {
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
						double newSpeedX = Math.sin(Math.toRadians(angle)) * totalSpeed;
						if (angle < 45) newSpeedX *= -1;
						setSpeedX(newSpeedX);
						setSpeedY(-Math.cos(Math.toRadians(angle)) * totalSpeed);
						System.out.println(it.nextIndex() - 1);
					} else {
						setSpeedY(getSpeedY() * -1);
						if (((Brick) m).collision()) toRemove.add(m);
						System.out.println(it.nextIndex() - 1);
					}
				}
			} else if (getSpeedY() < 0) {
				if (getY() + getSpeedY() < m.getY() + m.getHeight() && getY() + getSpeedY() > m.getY() &&
						getX() + getWidth() > m.getX() && getX() < m.getX() + m.getWidth()) {
					setSpeedY(getSpeedY() * -1);
					if (m instanceof Brick && ((Brick) m).collision()) toRemove.add(m);
					System.out.println(it.nextIndex() - 1);
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
					System.out.println(it.nextIndex() - 1);
				} else if (m instanceof Stick && m.getSpeedX() != 0) {
					setSpeedX(m.getSpeedX());
					System.out.println(it.nextIndex() - 1);
				}
			}
		}
		toRemove.forEach(e -> getCollisionables().remove(e));
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		calculatePhysics();
		if (getCollisionables().size() == 1) toggleNoMoreBricks();
		g2d.setColor(Color.WHITE);
		g2d.fillOval((int) getX(), (int) getY(), getWidth(), getHeight());
		g2d.setColor(Color.BLACK);
		g2d.drawOval((int) getX(), (int) getY(), getWidth(), getHeight());
	}
	
	/**
	 * Define la respuesta que se tendrá cuando la bola detecte que se ha perdido una vida.
	 * @param runnable Reacción a tomar
	 */
	public void setLifeLostListener(Runnable runnable) {
		this.lifeLostListener = runnable;
	}
	
	public void toggleLifeLost() {
		if (lifeLostListener != null) {
			lifeLostListener.run();
		}
	}
	
	/**
	 * Define la respuesta que se tendrá cuando la bola no tenga ya ladrillos que romper
	 * @param runnable Reacción a tomar.
	 */
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
		setSpeedY(totalSpeed);
	}
	
	/**
	 * Hace posible los 3 segundos de espera que tiene la bola al empezar una partida
	 * @return
	 */
	public Timer makeWait() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				initialSpeed();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 3000);
		return timer;
	}

	/**
	 * Hace que la bola se detenga.
	 */
	public void stop() {
		setSpeedX(0);
		setSpeedY(0);
	}

}
