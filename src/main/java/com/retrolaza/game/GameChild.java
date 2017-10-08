package com.retrolaza.game;

import com.retrolaza.game.drawable.Drawable;

/**
 * Clase que representa un elemento que tiene como padre el juego
 * (es decir, estaría debajo de {@see com.retrolaza.game.Game} jerárquicamente, y requiere de acceso directo al juego padre). También es un {@see com.retrolaza.game.drawable.Drawable}, por lo se rige también por su implementación.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public abstract class GameChild extends Drawable {
	
	private Game game;
	
	public GameChild(Game game) {
		this.game = game;
	}
	
	/**
	 * Método que da acceso al juego
	 * @return El juego
	 */
	protected Game game() {
		return game;
	}
	
}
