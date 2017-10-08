package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import com.retrolaza.data.RankingUtil;
import com.retrolaza.data.Record;
import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Button;
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.exception.PlayerNotFoundException;

/**
 * Pantalla de juego terminado (game over), que se muestra cuando al jugador no le quedan más vidas.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class GameOverScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private Image gameOverImage;
	private Button retryButton;
	private Button exitButton;
	private Button rankingButton;
	private Text loadingText;
	
	private EndgameRankingScreen rankingScreen;
	
	private String chosenGame;
	private Integer score;
	
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
	public static final int DR_LOADING_TEXT = Game.ID.getAndIncrement();

	public GameOverScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		final GameOverScreen self = this;
		
		setBackground("res/img/background.png");
		
		rankingScreen = new EndgameRankingScreen(game(), this);
		Game.addScreen(rankingScreen);
		
		gameOverImage = new Image("res/img/game_over.png", g, 450, 100);
		gameOverImage.scale(300, -1, Image.SCALE_SMOOTH);
		addDrawable(DR_GOVER_IMG, gameOverImage);
		
		retryButton = new Button("Berriz saiatu", game(), 270, 550);
		retryButton.setImage("res/img/teclado_enter.png");
		addDrawable(DR_RETRY_BTN, retryButton);
		
		exitButton = new Button("Irten", game(), 320 + retryButton.getWidth(), 550);
		exitButton.setImage("res/img/teclado_esc.png");
		addDrawable(DR_EXIT_BTN, exitButton);
		
		rankingButton = new Button("Ranking", game(), 250 + retryButton.getWidth() + exitButton.getWidth(), 550);
		rankingButton.setImage("res/img/teclado_r.png");
		rankingButton.hide();
		addDrawable(DR_RANKING_BTN, rankingButton);
		
		loadingText = new Text("Ranking-a kargatzen...", 380, 420);
		loadingText.setSize(45);
		addDrawable(DR_LOADING_TEXT, loadingText);
		
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
			if (!rankingButton.isHiding()) {
				s.hide();
				self.getRankingScreen().show(score);
			}
		});
		
		hide();
	}
	
	public void show(Integer score) {
		super.show();
		setLoading(true);
		this.score = score;
		new Thread(() ->  {
			try {
				List<Record> ranking = RankingUtil.loadRanking();
				Integer worstScore = ranking.get(ranking.size() - 1).getScore();
				getRankingScreen().setTableContent(ranking);
				if (score > worstScore) {
					isInRanking();
					loadingText.hide();
					setLoading(false);
				} else {
					loadingText.setText("Ez zaude ranking-an");
					loadingText.setX(400);
				}
			} catch (UnsupportedOperationException | IOException | PlayerNotFoundException e) {
				loadingText.setText("Ez zaude ranking-an");
				loadingText.setX(400);
			}
		}).start();
	}
	
	private void setLoading(boolean loading) {
		if (loading) {
			loadingText.show();
			loadingText.setText("Ranking-a kargatzen...");
			loadingText.setX(380);
			rankingButton.hide();
			retryButton.setPosition(270, 550);
			exitButton.setPosition(320 + retryButton.getWidth(), 550);
		} else {
			loadingText.hide();
		}
	}
	
	private void isInRanking() {
		retryButton.setPosition(150, 550);
		exitButton.setPosition(200 + retryButton.getWidth(), 550);
		rankingButton.show();
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
	
	public EndgameRankingScreen getRankingScreen() {
		return rankingScreen;
	}

}
