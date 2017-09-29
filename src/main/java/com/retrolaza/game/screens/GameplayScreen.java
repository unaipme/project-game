package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.movable.Ball;
import com.retrolaza.game.drawable.movable.Stick;
import com.retrolaza.game.scenery.SceneryParser;
import com.retrolaza.game.scenery.drawable.Scenery;

public class GameplayScreen extends GameScreen {
	
	private KeyboardControls gameControls;
	
	private Integer lives;
	
	private Stick stick;
	private Ball ball;
	private Scenery scenery;
	
	private GameOverScreen gameOverScreen;
	
	public static final int DR_LIVES_TEXT = Game.ID.getAndIncrement();
	public static final int DR_GOVER_TEXT = Game.ID.getAndIncrement();
	
	public static final int DR_SCENERY = Game.ID.getAndIncrement();
	
	public static final int DR_STICK = Game.ID.getAndIncrement();
	public static final int DR_BALL = Game.ID.getAndIncrement();

	public GameplayScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		gameOverScreen = new GameOverScreen(game(), this);
		Game.addScreen(gameOverScreen);
		
		stick = new Stick(540, 630);
		addDrawable(DR_STICK, stick);
		
		ball = new Ball(590, 500);
		ball.addCollisionable(stick);
		ball.setLifeLostListener(() -> lifeLost());
		addDrawable(DR_BALL, ball);
		
		gameControls = new KeyboardControls(this);
		gameControls.when(KeyEvent.VK_P).then(s -> ((GameplayScreen) s).gameOver());
		
		hide();
	}
	
	private void lifeLost() {
		if (--lives == 0) gameOver();
		ball.stop();
		ball.setPosition(590, 500);
		stick.setPosition(540, 630);
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
	
	public void newGame(String file) {
		try {
			ball.getCollisionables().clear();
			ball.addCollisionable(stick);
			scenery = SceneryParser.load(file);
			addDrawable(DR_SCENERY, scenery);
			scenery.getRows().values().forEach(r -> r.getBricks().forEach(b -> ball.addCollisionable(b)));
			this.lives = 4;
			lifeLost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
