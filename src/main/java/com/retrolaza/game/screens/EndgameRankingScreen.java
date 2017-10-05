package com.retrolaza.game.screens;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import com.retrolaza.data.RankingUtil;
import com.retrolaza.data.Record;
import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Button;
import com.retrolaza.game.drawable.Table;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.drawable.TextField;
import com.retrolaza.game.exception.PlayerNotFoundException;

public class EndgameRankingScreen extends GameScreen {
	
	private KeyboardControls controls;
	
	private TextField textField;
	private Table table;
	private Button button;
	private Text errorText;
	private Text scoreText;
	
	private Timer errorTimer;
	
	private Integer userScore;
	
	public static final int DR_TEXT_FIELD = Game.ID.getAndIncrement();
	public static final int DR_TABLE = Game.ID.getAndIncrement();
	public static final int DR_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ERROR_TEXT = Game.ID.getAndIncrement();
	public static final int DR_SCORE_TEXT = Game.ID.getAndIncrement();

	public EndgameRankingScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		setBackground("res/img/background.png");
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(s -> {
			s.hide();
			s.getParent().show();
		});
		controls.when(KeyEvent.VK_ENTER).then(s -> {
			List<Text> row = table.getRowWith(textField.getText(), 1);
			if (row != null ) {
				Integer existingScore = Integer.parseInt(row.get(2).getText());
				if (existingScore >= userScore) {
					createErrorText("Sartutako erabiltzaileak puntuazio hobea edo bera du jada");
					return;
				}
			}
			try {
				RankingUtil.putScore(textField.getText(), userScore);
				table.clear();
				List<Record> l = RankingUtil.loadRanking();
				for (Record r : l) table.withRow(r.getPosition(), r.getUsername(), r.getScore());
			} catch (IOException | UnsupportedOperationException | PlayerNotFoundException e) {}
		});
		
		textField = new TextField(215, 520, 8);
		addDrawable(DR_TEXT_FIELD, textField);
		
		errorText = new Text("", 10, 30, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Pixeled.ttf")));
		errorText.setSize(20);
		errorText.hide();
		addDrawable(DR_ERROR_TEXT, errorText);
		
		scoreText = new Text("", 600, 570);
		addDrawable(DR_SCORE_TEXT, scoreText);
		
		table = Table.create(215, 175, 3).withWidth(70).inColumn(0)
										.withWidth(500).inColumn(1)
										.withWidth(200).inColumn(2)
										.build();
		addDrawable(DR_TABLE, table);
		
		button = new Button("Gorde", game(), 775, 520);
		button.setImage("res/img/teclado_enter.png");
		button.addToWidth(30);
		addDrawable(DR_BUTTON, button);
		
		this.errorTimer = null;
		
		hide();
	}
	
	public void show(Integer score) {
		super.show();
		this.userScore = score;
		scoreText.setText(score.toString());
	}
	
	public void setTableContent(List<Record> list) {
		table.clear();
		list.forEach(r -> table.withRow(r.getPosition(), r.getUsername(), r.getScore()));
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
		game().addKeyListener(textField.getControls());
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(textField.getControls());
		game().removeKeyListener(controls);
	}
	
	private void createErrorText(String error) {
		try {
			errorTimer.cancel();
		} catch (NullPointerException | IllegalStateException e) {}
		errorText.setText(error);
		errorText.show();
		errorTimer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				errorText.setText("");
				errorText.hide();
			}
		};
		errorTimer.schedule(task, 5000);
	}

}
