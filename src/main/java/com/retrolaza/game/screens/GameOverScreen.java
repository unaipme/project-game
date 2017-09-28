package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Text;

public class GameOverScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private Image gameOverImage;
	private Image retryButton;
	private Image exitButton;
	private Text retryText;
	private Text exitText;
	
	public static final int DR_GOVER_IMG = Game.ID.getAndIncrement();
	public static final int DR_RETRY_BTN = Game.ID.getAndIncrement();
	public static final int DR_EXIT_BTN = Game.ID.getAndIncrement();
	public static final int DR_RETRY_TEXT = Game.ID.getAndIncrement();
	public static final int DR_EXIT_TEXT = Game.ID.getAndIncrement();

	public GameOverScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		setBackground("res/img/background.png");
		
		gameOverImage = new Image("res/img/game_over.png", g, 450, 100);
		gameOverImage.scale(300, -1, Image.SCALE_SMOOTH);
		addDrawable(DR_GOVER_IMG, gameOverImage);
		
		exitButton = new Image("res/img/teclado_esc.png", game(), 478, 425);
		exitButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_EXIT_BTN, exitButton);
		
		retryButton = new Image("res/img/teclado_enter.png", game(), 338, 505);
		retryButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_RETRY_BTN, retryButton);
		
		exitText = new Text("ATERA", 548, 470);
		exitText.setSize(70);
		addDrawable(DR_EXIT_TEXT, exitText);
		
		retryText = new Text("BERRIZ SAIATU", 408, 550);
		retryText.setSize(70);
		addDrawable(DR_RETRY_TEXT, retryText);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(s -> {
			s.hide();
			s.getParent().getParent().getParent().show();
		});
		controls.when(KeyEvent.VK_ENTER).then(s -> {
			s.hide();
			((GameplayScreen) s.getParent()).newGame();
			s.getParent().show();
		});
		
		hide();
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
		game().stopMusic();
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
		game().resetMusic();
		game().playMusic();
	}

}
