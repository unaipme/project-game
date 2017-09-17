package com.retrolaza.game;

public abstract class GameScreen extends GameChild {
	
	private GameScreen parent;
	
	public GameScreen(Game g, GameScreen parent) {
		super(g);
		this.parent = parent;
	}
	
	@Override
	public void setHiding(boolean hiding) {
		if (hiding) turnOff();
		else setUp();
		this.hiding = hiding;
	}
	
	public GameScreen getParent() {
		return parent;
	}
	
	public abstract void setUp();
	public abstract void turnOff();
	
}
