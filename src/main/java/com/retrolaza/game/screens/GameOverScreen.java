package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Button;
import com.retrolaza.game.drawable.Image;

public class GameOverScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private Image gameOverImage;
	private Button retryButton;
	private Button exitButton;
	private Button rankingButton;
	
	private String chosenGame;
	
	public String getChosenGame() {
		return chosenGame;
	}

	public void setChosenGame(String chosenGame) {
		this.chosenGame = chosenGame;
	}

	public static final int DR_GOVER_IMG = Game.ID.getAndIncrement();
	public static final int DR_RETRY_BTN = Game.ID.getAndIncrement();
	public static final int DR_EXIT_BTN = Game.ID.getAndIncrement();
	public static final int DR_RANKING_BTN = Game.ID.getAndIncrement();

	public GameOverScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		final GameOverScreen self = this;
		
		setBackground("res/img/background.png");
		
		gameOverImage = new Image("res/img/game_over.png", g, 450, 100);
		gameOverImage.scale(300, -1, Image.SCALE_SMOOTH);
		addDrawable(DR_GOVER_IMG, gameOverImage);
		
		retryButton = new Button("Berriz saiatu", game(), 150, 550);
		retryButton.setImage("res/img/teclado_enter.png");
		addDrawable(DR_RETRY_BTN, retryButton);
		
		exitButton = new Button("Irten", game(), 200 + retryButton.getWidth(), 550);
		exitButton.setImage("res/img/teclado_esc.png");
		addDrawable(DR_EXIT_BTN, exitButton);
		
		rankingButton = new Button("Ranking", game(), 250 + retryButton.getWidth() + exitButton.getWidth(), 550);
		rankingButton.setImage("res/img/teclado_r.png");
		addDrawable(DR_RANKING_BTN, rankingButton);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(s -> {
			s.hide();
			s.getParent().getParent().getParent().show();
		});
		controls.when(KeyEvent.VK_ENTER).then(s -> {
			s.hide();
			((GameplayScreen) s.getParent()).newGame(self.getChosenGame());
			s.getParent().show();
		});
		controls.when(KeyEvent.VK_R).then(s -> {
			s.hide();
			//MOSTRAR PANTALLA RANKING
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
