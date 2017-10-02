package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Text;

public class GameCompleteScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private Text text;
	
	public final static int DR_TEXT = Game.ID.getAndIncrement();

	public GameCompleteScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		text = new Text("Amaiera", 30, 60);
		addDrawable(DR_TEXT, text);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ENTER).then(s -> {
			s.getParent().getParent().getParent().show();
			s.hide();
		});
		
		hide();
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
	}

}
