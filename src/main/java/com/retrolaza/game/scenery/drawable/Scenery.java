package com.retrolaza.game.scenery.drawable;

import java.util.Map;

import com.retrolaza.game.Game;
import com.retrolaza.game.drawable.Drawable;

/**
 * Escenario del juego. Para pasar de un escenario se deben romper todos los ladrillos que la conforman. Toda la información almacenada en esta clase se lee e interpreta directamente desde los archivos JSON.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Scenery extends Drawable {
	
	/**
	 * La lista de todas las filas de ladrillos del escenario
	 */
	private Map<Integer, BrickRow> rows;
	/**
	 * Imagen de fondo personalizada que tendrá el escenario
	 */
	private String background;
	/**
	 * Define cual es el archivo JSON que se debe leer al terminar el escenario actual para pasar al siguiente
	 */
	private String nextLevel;
	/**
	 * Velocidad total de la bola en el nivel actual
	 */
	private double speed;
	
	public Scenery(Map<Integer, BrickRow> rows, String background, String nextLevel, double speed) {
		this.rows = rows;
		this.background = background;
		this.nextLevel = nextLevel;
		this.speed = speed;
		rows.values().forEach(r -> addDrawable(Game.ID.getAndIncrement(), r));
	}

	public double getSpeed() {
		return speed;
	}

	public String getNextLevel() {
		return nextLevel;
	}

	public Map<Integer, BrickRow> getRows() {
		return rows;
	}
	
	public String getBackground() {
		return background;
	}
	
}
