package com.retrolaza.game;

public abstract class GameScreen extends GameChild {
	
	private GameScreen parent;
	
	public GameScreen(Game g, GameScreen parent) {
		super(g);
		this.parent = parent;
	}
	
	@Override
	public void hide() {
		super.hide();
		turnOff();
	}
	
	@Override
	public void show() {
		super.show();
		setUp();
	}
	
	public GameScreen getParent() {
		return parent;
	}
	
	public abstract void setUp();
	public abstract void turnOff();
	
}
