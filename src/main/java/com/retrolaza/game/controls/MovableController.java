package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementación de {@see java.awt.event.KeyListener} que guarda en todo momento las teclas que se están pulsando. Esta implementación es ideal para crear la interacción de los objetos interactuables del juego.
 * Por ejemplo, el {@see com.retrolaza.game.drawable.movable.Stick} utiliza esta implementación para comprobar en todo momento si las teclas que crean movimiento están siendo pulsadas. Mientras lo estén la velocidad del objeto será diferente a cuando no lo estén.
 * @author Unai P. Mendizabal (@unaipme)
 *
 * @param <T>
 */
public class MovableController implements KeyListener {
	
	private Set<Integer> pressedKeys;
	
	public MovableController() {
		this.pressedKeys = new HashSet<>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	/**
	 * Método que se ejecuta automáticamente cuando se pulsa una tecla. Se añade esta tecla a la lista de teclas que están siendo pulsadas.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int event = e.getKeyCode();
		if (!pressedKeys.contains(event)) {
			pressedKeys.add(event);
		}
	}

	/**
	 * Método que se ejecuta automáticamente cuando una tecla se deja de pulsar. Se borra dicha tecla de la lista de teclas pulsadas.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}
	
	/**
	 * Comprueba si la tecla definida existe en la lista, es decir, si está siendo pulsada, y devuelve el resultado.
	 * @param event Identificador de la tecla, sacado de {@see java.awt.event.KeyEvent}
	 * @return Un boolean de si la tecla está pulsada o no
	 */
	public boolean isKeyPressed(int event) {
		return pressedKeys.contains(event);
	}
	
	/**
	 * Vacía la lista de teclas pulsadas.
	 */
	public void clear() {
		pressedKeys.clear();
	}

}
