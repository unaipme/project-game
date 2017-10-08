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
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.exception.PlayerNotFoundException;

/**
 * Pantalla de juego completado, es decir, que se muestra cuando no quedan más escenarios que superar.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class GameCompleteScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private EndgameRankingScreen rankingScreen;
	
	private Text titleText;
	private Button homeButton;
	private Button rankingButton;
	private Text loadingText;
	
	private Integer score;
	
	public final static int DR_TITLE_TEXT = Game.ID.getAndIncrement();
	public final static int DR_HOME_BTN = Game.ID.getAndIncrement();
	public final static int DR_RANKING_BTN = Game.ID.getAndIncrement();
	public final static int DR_LOADING_TEXT = Game.ID.getAndIncrement();

	public GameCompleteScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		setBackground("res/img/background.png");
		
		final GameCompleteScreen self = this;
		
		titleText = new Text("ZORIONAK!", 420, 120);
		addDrawable(DR_TITLE_TEXT, titleText);
		
		rankingScreen = new EndgameRankingScreen(game(), this);
		Game.addScreen(rankingScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ENTER).then(s -> {
			s.getParent().getParent().getParent().show();
			s.hide();
		});
		controls.when(KeyEvent.VK_R).then(s -> {
			if (!rankingButton.isHiding()) {
				s.hide();
				self.getRankingScreen().show(score);
			}
		});
		
		homeButton = new Button("Menura itzuli", game(), 400, 440);
		homeButton.setImage("res/img/teclado_enter.png");
		addDrawable(DR_HOME_BTN, homeButton);
		
		rankingButton = new Button("Rankinga", game(), 700, 440);
		rankingButton.setImage("res/img/teclado_r.png");
		rankingButton.hide();
		addDrawable(DR_RANKING_BTN, rankingButton);
		
		loadingText = new Text("Ranking-a kargatzen...", 380, 195);
		loadingText.setSize(45);
		addDrawable(DR_LOADING_TEXT, loadingText);
		
		hide();
	}
	
	public void show(Integer score) {
		super.show();
		setLoading(true);
		this.score = score;
		new Thread(() -> {
			try {
				List<Record> ranking = RankingUtil.loadRanking();
				Integer worstScore = ranking.get(ranking.size() - 1).getScore();
				getRankingScreen().setTableContent(ranking);
				if (score > worstScore) {
					isInRanking();
					loadingText.hide();
					rankingButton.show();
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

	@Override
	public void setUp() {
		game().addKeyListener(controls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
	}
	
	public void setLoading(boolean loading) {
		if (loading) {
			loadingText.show();
			loadingText.setText("Ranking-a kargatzen...");
			rankingButton.hide();
			homeButton.setPosition(400, 440);
		} else {
			loadingText.hide();
		}
	}
	
	public EndgameRankingScreen getRankingScreen() {
		return rankingScreen;
	}
	
	public void isInRanking() {
		homeButton.setPosition(200, 440);
	}
	
}
