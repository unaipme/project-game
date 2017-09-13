package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.retrolaza.game.Game;

public class KeyboardControls implements KeyListener {
	
	private Map<Integer, Consumer<Game>> responses = new HashMap<>();
	private Game game;
	
	public KeyboardControls(Game game) {
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
		private Consumer<Game> function = (g) -> {};
		
		Adder(int event) {
			and(event);
		}
		
		public Adder and(int event) {
			this.event = event;
			return this;
		}
		
		public Adder then(Consumer<Game> function) {
			this.function = function;
			return this;
		}
		
		public void save() {
			responses.put(event, function);
		}
		
	}
	
}
