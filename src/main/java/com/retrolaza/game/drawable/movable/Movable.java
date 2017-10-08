package com.retrolaza.game.drawable.movable;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.retrolaza.game.controls.MovableController;
import com.retrolaza.game.drawable.Drawable;

/**
 * Clase abstracta que define el comportamiento y atributos estándares de los objetos dibujables que además tengan un componente de movimiento. 
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public abstract class Movable extends Drawable {
	
	protected MovableController controls;
	
	/**
	 * Lista de todos los otros elementos movibles contra los cuales el elemento actual tenga que colisionar.
	 */
	private List<Movable> collisionables;
	
	private double speedX;
	private double speedY;
	private double x;
	private double y;
	private int height;
	private int width;
	
	public Movable(int x, int y, int height, int width) {
		setPosition(x, y);
		this.height = height;
		this.width = width;
		this.speedX = 0;
		this.speedY = 0;
		
		this.collisionables = new ArrayList<>();
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getX() {
		return x;
	}
	
	protected void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	protected void setY(double y) {
		this.y = y;
	}
	
	public MovableController getControlls() {
		return controls;
	}
	
	public void addCollisionable(Movable m) {
		collisionables.add(m);
	}
	
	public List<Movable> getCollisionables() {
		return collisionables;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public void draw(Graphics2D g2d) {
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
	}

}
