package com.retrolaza.game;

import com.retrolaza.game.drawable.Drawable;

public abstract class GameChild extends Drawable {
	
	private Game game;
	
	public GameChild(Game game) {
		this.game = game;
	}
	
	protected Game game() {
		return game;
	}
	
}
