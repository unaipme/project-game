package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.drawable.movable.Ball;
import com.retrolaza.game.drawable.movable.Stick;

public class GameplayScreen extends GameScreen {
	
	private Text livesText;
	private KeyboardControls gameControls;
	
	private Integer lives;
	
	private Stick stick;
	private Ball ball;
	
	private GameOverScreen gameOverScreen;
	
	public static final int DR_LIVES_TEXT = Game.ID.getAndIncrement();
	public static final int DR_GOVER_TEXT = Game.ID.getAndIncrement();
	
	public static final int DR_STICK = Game.ID.getAndIncrement();
	public static final int DR_BALL = Game.ID.getAndIncrement();

	public GameplayScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		this.lives = 3;
		
		gameOverScreen = new GameOverScreen(game(), this);
		Game.addScreen(gameOverScreen);
		
		livesText = new Text(lives.toString(), 60, 120);
		addDrawable(DR_LIVES_TEXT, livesText);
		
		stick = new Stick(200, 630);
		addDrawable(DR_STICK, stick);
		
		ball = new Ball(150, 100);
		ball.addCollisionable(stick);
		ball.setLifeLostListener(() -> lifeLost());
		addDrawable(DR_BALL, ball);
		
		gameControls = new KeyboardControls(this);
		gameControls.when(KeyEvent.VK_P).then(s -> ((GameplayScreen) s).gameOver());
		
		hide();
	}
	
	private void lifeLost() {
		if (--lives == 0) gameOver();
		livesText.setText(lives.toString());
		ball.stop();
		ball.setPosition(150, 100);
		ball.makeWait();
	}

	@Override
	public void setUp() {
		game().addKeyListener(gameControls);
		game().addKeyListener(stick.getControlls());
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(gameControls);
		game().removeKeyListener(stick.getControlls());
	}
	
	public void gameOver() {
		hide();
		gameOverScreen.show();
	}
	
	public void newGame() {
		this.lives = 4;
		lifeLost();
	}

}
