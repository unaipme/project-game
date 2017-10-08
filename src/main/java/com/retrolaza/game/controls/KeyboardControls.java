package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.retrolaza.game.GameScreen;

/**
 * Implementación de la clase {@see java.awt.event.KeyListener} para facilitar la configuración de controles simples. Se debe definir qué tecla realizará que acción al ser pulsada, y añadir el objeto de controles al juego.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class KeyboardControls implements KeyListener {
	
	private Map<Integer, Consumer<GameScreen>> responses = new HashMap<>();
	private GameScreen game;
	
	public KeyboardControls(GameScreen game) {
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	/**
	 * Método que se ejecuta cuando una tecla es pulsada. Comprueba todas las respuestas configuradas y ejecuta la que corresponda a la tecla pulsada.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		Optional.ofNullable(responses.get(e.getKeyCode())).ifPresent(o -> o.accept(game));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	/**
	 * Crea un objeto de la clase {@see com.retrolaza.game.controls.KeyboardControls.Adder}, que se emplea para crear una respuesta a la tecla definida.
	 * @param event Identificador de la tecla, sacado de la clase {@see java.awt.event.KeyEvent}.
	 * @return Nuevo objeto {@see com.retrolaza.game.controls.KeyboardControls.Adder}
	 */
	public KeyboardControls.Adder when(int event) {
		return new KeyboardControls.Adder(event);
	}
	
	/**
	 * Clase que facilita la creación de una nueva reacción a una tecla mediante el método {@see com.retrolaza.game.controls.KeyboardControls.Adder#then(Consumer)}.
	 * El objeto es creado mediante el método {@see com.retrolaza.game.controls.KeyboardControls#when(int)}. Cuando se llama al método {@see com.retrolaza.game.controls.KeyboardControls.Adder#then(Consumer))}, se recibe un {@see java.util.function.Consumer} que se añadirá a la lista de reacciones, y por lo tanto se ejecutará cuando la tecla definida sea pulsada.
	 * @author Unai P. Mendizabal (@unaipme)
	 *
	 */
	public class Adder {
		
		private Integer event;
		
		Adder(int event) {
			this.event = event;
		}
		
		public void then(Consumer<GameScreen> function) {
			responses.put(event, function);
		}
		
	}
	
}
