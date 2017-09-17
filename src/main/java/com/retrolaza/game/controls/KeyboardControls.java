package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.retrolaza.game.GameScreen;

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

	@Override
	public void keyPressed(KeyEvent e) {
		Optional.ofNullable(responses.get(e.getKeyCode())).ifPresent(o -> o.accept(game));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public KeyboardControls.Adder when(int event) {
		return new KeyboardControls.Adder(event);
	}
	
	public class Adder {
		
		private Integer event;
		//private Consumer<Drawable> function = (g) -> {};
		
		Adder(int event) {
			this.event = event;
		}
		
		public void then(Consumer<GameScreen> function) {
			responses.put(event, function);
		}
		
	}
	
}
