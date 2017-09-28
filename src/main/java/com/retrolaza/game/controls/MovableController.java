package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.retrolaza.game.drawable.movable.Movable;

public class MovableController<T extends Movable> implements KeyListener {
	
	private Set<Integer> pressedKeys;
	private HashMap<Integer, Consumer<T>> responses;
	private T t;
	
	public MovableController(T t) {
		this.pressedKeys = new HashSet<>();
		this.responses = new HashMap<>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int event = e.getKeyCode();
		if (!pressedKeys.contains(event)) {
			pressedKeys.add(event);
			if (responses.containsKey(event)) responses.get(event).accept(t);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}
	
	public boolean isKeyPressed(int event) {
		return pressedKeys.contains(event);
	}
	
	public void addResponse(int event, Consumer<T> response) {
		responses.put(event, response);
	}

}
