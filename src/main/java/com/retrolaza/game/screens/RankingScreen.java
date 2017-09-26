package com.retrolaza.game.screens;

import java.awt.FontFormatException;
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
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Table;
import com.retrolaza.game.drawable.Text;
import com.retrolaza.game.drawable.TextField;
import com.retrolaza.game.exception.PlayerNotFoundException;

import net.minidev.json.JSONArray;

public class RankingScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Text titleText;
	private Text errorText;
	private Table table;

	private Text escInstructions;
	private Image escButton;
	private Text searchText;
	private Image searchButton;
	
	private TextField textField;
	
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	public static final int DR_LOAD_ERROR = Game.ID.getAndIncrement();
	public static final int DR_TABLE = Game.ID.getAndIncrement();

	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_SEARCH_TEXT = Game.ID.getAndIncrement();
	public static final int DR_SEARCH_BUTTON = Game.ID.getAndIncrement();
	
	public static final int DR_TEXT_FIELD = Game.ID.getAndIncrement();
	
	private static final String RANKING_GENERAL_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/ranking/";

	public RankingScreen(Game game, MainScreen mainScreen) throws FontFormatException, IOException {
		super(game, mainScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			gs.hide();
			gs.getParent().show();
		});
		controls.when(KeyEvent.VK_ENTER).then(gs -> {
			RankingScreen rs = (RankingScreen) gs;
			new Thread(() -> rs.loadRanking(RANKING_GENERAL_URL + rs.getSearchTerm())).start();
		});
		
		titleText = new Text("RANKING", 470, 100);
		titleText.show();
		addDrawable(DR_TITLE, titleText);
		
		errorText = new Text("", 510, 150);
		errorText.setSize(45);
		errorText.show();
		addDrawable(DR_LOAD_ERROR, errorText);
		
		escButton = new Image("res/img/teclado_esc.png", game(), 10, 650);
		escButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_ESC_BUTTON, escButton);
		
		escInstructions = new Text("Bueltatzeko", 70, 690);
		escInstructions.setSize(20);
		addDrawable(DR_ESC_TEXT, escInstructions);
		
		searchText = new Text("BILATU", 750, 585);
		searchText.hide();
		addDrawable(DR_SEARCH_TEXT, searchText);
		
		searchButton = new Image("res/img/teclado_enter.png", game(), 680, 535);
		searchButton.scale(60, -1, Image.SCALE_SMOOTH);
		searchButton.hide();
		addDrawable(DR_SEARCH_BUTTON, searchButton);
		
		setBackground("res/img/background.png");
		
		table = Table.create(215, 175, 3).withWidth(70).inColumn(0)
										.withWidth(500).inColumn(1)
										.withWidth(200).inColumn(2)
										.build();
		table.hide();
		addDrawable(DR_TABLE, table);
		
		textField = new TextField(215, 520, 8);
		textField.hide();
		addDrawable(DR_TEXT_FIELD, textField);
		
		hide();
	}

	@Override
	public void setUp() {
		errorText.setText("KARGATZEN");
		errorText.show();
		game().addKeyListener(controls);
		game().addKeyListener(textField.getControls());
		new Thread(() -> loadRanking()).start();
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(textField.getControls());
		game().removeKeyListener(controls);
		errorText.hide();
		table.hide();
	}
	
	private void activateError(String err) {
		errorText.setText(err);
		errorText.setX((1200 - (err.length() * 20)) / 2);
		table.hide();
		searchText.hide();
		searchButton.hide();
		textField.hide();
	}
	
	public void loadRanking() {
		loadRanking(RANKING_GENERAL_URL);
	}
	
	@SuppressWarnings("unchecked")
	public void loadRanking(String url) {
		table.hide();
		errorText.setText("KARGATZEN");
		errorText.show();
		table.clear();
		HttpClient http = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = http.execute(request);
			Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
			String json = scanner.useDelimiter("\\A").next();
			scanner.close();
			JSONArray array = JsonPath.read(json, "$[*]");
			ListIterator<Object> objectList = array.listIterator();
			if (!objectList.hasNext()) throw new PlayerNotFoundException(getSearchTerm());
			while (objectList.hasNext()) {
				Map<String, Object> info = (Map<String, Object>) objectList.next();
				table.withRow(info.get("position"), info.get("username"), info.get("score"));
			}
			errorText.setText("");
			table.show();
			textField.show();
			searchText.show();
			searchButton.show();
		} catch (PlayerNotFoundException e) {
			activateError("Jokalaria ez da ageri datu basean");
			searchText.show();
			searchButton.show();
			textField.show();
		} catch (Exception e) {
			activateError("Akats bat gertatu da");
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	public String getSearchTerm() {
		return textField.getText();
	}

}
