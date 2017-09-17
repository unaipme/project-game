package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Text;

public class RankingScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Text titleText;
	
	public static final int DR_TITLE = Game.ID.getAndIncrement();

	public RankingScreen(Game game, MainScreen mainScreen) throws FontFormatException, IOException {
		super(game, mainScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			gs.setHiding(true);
			gs.getParent().setHiding(false);
		});
		
		titleText = new Text("RANKING", 30, 30);
		addDrawable(DR_TITLE, titleText);
		
		this.hiding = true;
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}

}
