package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.jayway.jsonpath.JsonPath;
import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.screens.data.Record;

import net.minidev.json.JSONArray;

public class RankingScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Text titleText;
	private Text errorText;
	private int [] titleIds = new int [5];
	
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	public static final int DR_LOAD_ERROR = Game.ID.getAndIncrement();
	
	private static final String RANKING_GENERAL_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/ranking";

	public RankingScreen(Game game, MainScreen mainScreen) throws FontFormatException, IOException {
		super(game, mainScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			gs.hide();
			gs.getParent().show();
		});
		
		titleText = new Text("RANKING", 30, 30);
		titleText.show();
		addDrawable(DR_TITLE, titleText);
		
		errorText = new Text("", 30, 75);
		errorText.show();
		addDrawable(DR_LOAD_ERROR, errorText);
		
		setBackgroundImage("res/img/background.png");
		
		for (int i=0; i<5; i++) {
			Text t = new Text("", 30, 60 + i * 30);
			t.hide();
			int id = Game.ID.getAndIncrement();
			titleIds[i] = id;
			drawables.put(id, t);
		}
		
		hide();
	}

	@Override
	public void setUp() {
		errorText.setText("Cargando");
		errorText.show();
		game().addKeyListener(controls);
		new Thread(() -> {
			loadRanking();
			errorText.setText("");
		}).start();
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
		errorText.hide();
		for (int i=0; i<5; i++) {
			getDrawable(titleIds[i]).hide();
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
	
	private void activateError(String err) {
		for (int i=0; i<5; i++) {
			getDrawable(titleIds[i]).hide();
		}
		errorText.setText(err);
		errorText.show();
	}
	
	@SuppressWarnings("unchecked")
	private void loadRanking() {
		HttpClient http = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(RANKING_GENERAL_URL);
		try {
			HttpResponse response = http.execute(request);
			Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
			String json = scanner.useDelimiter("\\A").next();
			scanner.close();
			JSONArray array = JsonPath.read(json, "$[*]");
			ListIterator<Object> objectList = array.listIterator();
			int i = 0;
			while (objectList.hasNext()) {
				Map<String, Object> info = (Map<String, Object>) objectList.next();
				Record r = new Record().withPosition((Integer) info.get("position")).withScore((Integer) info.get("score")).withUsername(info.get("username").toString());
				Text t = (Text) drawables.get(titleIds[i]);
				t.setText(String.format("%s %d", r.getUsername(), r.getScore()));
				t.show();
				i++;
			}
			System.out.println("Rankinga ondo kargatu da");
		} catch (Exception e) {
			activateError("Akats bat gertatu da");
			System.err.println(e.getLocalizedMessage());
		}
	}

}
