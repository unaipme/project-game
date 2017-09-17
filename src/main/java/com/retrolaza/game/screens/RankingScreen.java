package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
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
	private Map<Integer, Text> ranking;
	private String loadingError;
	
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	
	private static final String RANKING_GENERAL_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/ranking";

	public RankingScreen(Game game, MainScreen mainScreen) throws FontFormatException, IOException {
		super(game, mainScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			gs.setHiding(true);
			gs.getParent().setHiding(false);
		});
		
		titleText = new Text("RANKING", 30, 30);
		addDrawable(DR_TITLE, titleText);
		
		loadRanking();
		
		this.hiding = true;
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
	
	@SuppressWarnings("unchecked")
	private void loadRanking() throws FontFormatException {
		ranking = new HashMap<>();
		
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
				Text t = new Text(String.format("%s %d", r.getUsername(), r.getScore()), 30, 60 + i * 30);
				int id = Game.ID.getAndIncrement();
				ranking.put(id, t);
				addDrawable(id, t);
				i++;
			}
		} catch (IOException e) {
			loadingError = "Akats bat gertatu da";
			e.printStackTrace();
		}
	}

}
