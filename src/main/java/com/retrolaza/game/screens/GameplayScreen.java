package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Text;

public class GameplayScreen extends GameScreen {
	
	private Text testText;
	private KeyboardControls gameControls;
	
	private GameOverScreen gameOverScreen;
	
	public static final int DR_TEST_TEXT = Game.ID.getAndIncrement();
	public static final int DR_GOVER_TEXT = Game.ID.getAndIncrement();

	public GameplayScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		gameOverScreen = new GameOverScreen(game(), this);
		Game.addScreen(gameOverScreen);
		
		testText = new Text("JOKOA", 60, 120);
		addDrawable(DR_TEST_TEXT, testText);
		
		gameControls = new KeyboardControls(this);
		gameControls.when(KeyEvent.VK_P).then(s -> ((GameplayScreen) s).gameOver());
		
		hide();
	}

	@Override
	public void setUp() {
		game().addKeyListener(gameControls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(gameControls);
	}
	
	public void gameOver() {
		hide();
		gameOverScreen.show();
	}
	
	public void newGame() {
		
	}

}
