package com.retrolaza.game.screens;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.drawable.movable.Ball;
import com.retrolaza.game.drawable.movable.Stick;
import com.retrolaza.game.scenery.SceneryParser;
import com.retrolaza.game.scenery.drawable.Scenery;

/**
 * Pantalla en la cual se lleva a cabo el mismo juego. El jugador controla el stick para evitar que la bola salga de la pantalla por la parte inferior y para romper los ladrillos.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class GameplayScreen extends GameScreen {
	
	private KeyboardControls gameControls;
	
	private Integer lives;
	private Text score;
	
	private Stick stick;
	private Ball ball;
	private Scenery scenery;
	
	private GameOverScreen gameOverScreen;
	private GameCompleteScreen gameCompleteScreen;
	
	private final Runnable brokenBrickScoreAdder = () -> setScore(getScore() + 1);
	
	private Timer scoreSubstracter;
	private Timer waitTimer;
	
	public static final int DR_LIVES_TEXT = Game.ID.getAndIncrement();
	public static final int DR_GOVER_TEXT = Game.ID.getAndIncrement();
	
	public static final int DR_SCENERY = Game.ID.getAndIncrement();
	
	public static final int DR_STICK = Game.ID.getAndIncrement();
	public static final int DR_BALL = Game.ID.getAndIncrement();
	public static final int DR_SCORE = Game.ID.getAndIncrement();
	
	private List<Image> hearts;

	public GameplayScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		final GameplayScreen self = this;
		
		gameOverScreen = new GameOverScreen(game(), this);
		Game.addScreen(gameOverScreen);
		
		gameCompleteScreen = new GameCompleteScreen(game(), this);
		Game.addScreen(gameCompleteScreen);
		
		stick = new Stick(540, 630);
		addDrawable(DR_STICK, stick);
		
		scoreSubstracter = new Timer();
		score = new Text("", 15, 65);
		
		hearts = new ArrayList<>();
		hearts.add(new Image("res/img/vida.png", game(), 1115, 10));
		hearts.add(new Image("res/img/vida.png", game(), 1030, 10));
		hearts.add(new Image("res/img/vida.png", game(), 945, 10));
		
		ball = new Ball(590, 500);
		ball.addCollisionable(stick);
		ball.setLifeLostListener(() -> lifeLost());
		ball.setNoMoreBricksListener(() -> gameComplete()); 
		addDrawable(DR_BALL, ball);
		
		gameControls = new KeyboardControls(this);
		gameControls.when(KeyEvent.VK_ESCAPE).then(s -> self.gameOver());
		gameControls.when(KeyEvent.VK_F1).then(s -> self.gameComplete());
		
		hide();
	}
	
	private void lifeLost() {
		if (--lives == 0) gameOver();
		ball.stop();
		ball.setPosition(590, 500);
		stick.setPosition(540, 630);
		waitTimer = ball.makeWait();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!hiding) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, 1200, 80);
			for (int i=lives; i>0; i--) hearts.get(i - 1).draw(g2d);
			score.draw(g2d);
		}
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
	
	public void gameComplete() {
		scoreSubstracter.cancel();
		if (scenery.getNextLevel() != null)
			newGame(scenery.getNextLevel());
		else {
			this.hide();
			gameCompleteScreen.show(getScore());
		}
	}
	
	public GameOverScreen getGameOverScreen() {
		return gameOverScreen;
	}

	public void setGameOverScreen(GameOverScreen gameOverScreen) {
		this.gameOverScreen = gameOverScreen;
	}

	public void gameOver() {
		try {
			waitTimer.cancel();
		} catch (NullPointerException | IllegalStateException e) {}
		scoreSubstracter.cancel();
		hide();
		stick.getControlls().clear();
		gameOverScreen.show(getScore());
	}
	
	public void newGame(String file) {
		try {
			scoreSubstracter.cancel();
		} catch (IllegalStateException | NullPointerException e) {}
		try {
			ball.getCollisionables().clear();
			ball.addCollisionable(stick);
			scenery = SceneryParser.load(file);
			addDrawable(DR_SCENERY, scenery);
			scenery.getRows().values().forEach(r -> r.getBricks().forEach(b -> ball.addCollisionable(b)));
			scenery.getRows().values().stream().flatMap(r -> r.getBricks().stream()).forEach(b -> b.setCollisionEvent(brokenBrickScoreAdder));
			ball.setTotalSpeed(scenery.getSpeed());
			setBackground(scenery.getBackground());
			this.lives = 4;
			lifeLost();
			scoreSubstracter = new Timer();
			scoreSubstracter.schedule(getSubstracterTask(), 13000, 10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private TimerTask getSubstracterTask() {
		return new TimerTask() {
			@Override
			public void run() {
				setScore(getScore() - 1);
			}
		};
	}
	
	public Integer getScore() {
		return Integer.parseInt(score.getText());
	}
	
	public void setScore(Integer s) {
		score.setText(s.toString());
	}

}
