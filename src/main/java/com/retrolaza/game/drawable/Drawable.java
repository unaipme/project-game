package com.retrolaza.game.drawable;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Representación de todos los elementos que pueden dibujarse en la pantalla. Las pantallas también se consideran elementos dibujables.
 * Todos los elementos dibujables tienen, a su vez, una lista de elementos dibujables de los cuales se compone, y un método {@see #draw(Graphics2D)}.
 * El método {@see #draw(Graphics2D)} puede implementarse manualmente para definir un comportamiento diferente al predefinido, mediante el cual se dibujan todos los dibujables de la lista de dibujables.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public abstract class Drawable {
	
	/**
	 * Lista de elementos dibujables que forman el dibujable actual
	 */
	protected Map<Integer, Drawable> drawables = new HashMap<>();
	
	protected boolean hiding;
	
	protected void addDrawable(Integer id, Drawable drawable) {
		drawables.put(id, drawable);
	}
	
	public Drawable getDrawable(Integer id) {
		return drawables.get(id);
	}
	
	/**
	 * Esconde el elemento actual
	 */
	public void hide() {
		this.hiding = true;
	}
	
	/**
	 * Muestra el elemento actual
	 */
	public void show() {
		this.hiding = false;
	}
	
	public boolean isHiding() {
		return this.hiding;
	}
	
	/**
	 * A menos que se reimplemente, por defecto muestra todos los dibujables de la lista en el orden en el que se han introducido, si el elemento no está escondido
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
		if (!isHiding()) {
			drawables.forEach((id, d) -> d.draw(g2d));
		}
	}
}
