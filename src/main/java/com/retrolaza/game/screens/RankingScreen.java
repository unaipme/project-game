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

import net.minidev.json.JSONArray;

public class RankingScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Text titleText;
	private Text errorText;
	private Table table;

	private Text escInstructions;
	private Image escButton;
	
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	public static final int DR_LOAD_ERROR = Game.ID.getAndIncrement();
	public static final int DR_TABLE = Game.ID.getAndIncrement();

	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	
	private static final String RANKING_GENERAL_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/ranking";

	public RankingScreen(Game game, MainScreen mainScreen) throws FontFormatException, IOException {
		super(game, mainScreen);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			gs.hide();
			gs.getParent().show();
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
		
		setBackground("res/img/background.png");
		
		table = Table.create(215, 200, 3).withWidth(70).inColumn(0)
										.withWidth(500).inColumn(1)
										.withWidth(200).inColumn(2)
										.build();
		drawables.put(DR_TABLE, table);
		table.hide();
		
		hide();
	}

	@Override
	public void setUp() {
		errorText.setText("KARGATZEN");
		errorText.show();
		game().addKeyListener(controls);
		new Thread(() -> {
			loadRanking();
		}).start();
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
		errorText.hide();
		table.hide();
	}
	
	private void activateError(String err) {
		Text text = ((Text) drawables.get(DR_LOAD_ERROR));
		text.setText(err);
		text.setX(385);
		table.hide();
	}
	
	@SuppressWarnings("unchecked")
	private void loadRanking() {
		table.clear();
		HttpClient http = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(RANKING_GENERAL_URL);
		try {
			HttpResponse response = http.execute(request);
			Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
			String json = scanner.useDelimiter("\\A").next();
			scanner.close();
			JSONArray array = JsonPath.read(json, "$[*]");
			ListIterator<Object> objectList = array.listIterator();
			while (objectList.hasNext()) {
				Map<String, Object> info = (Map<String, Object>) objectList.next();
				table.withRow(info.get("position"), info.get("username"), info.get("score"));
			}
			errorText.setText("");
			table.show();
			System.out.println("Rankinga ondo kargatu da");
		} catch (Exception e) {
			activateError("Akats bat gertatu da");
			System.err.println(e.getLocalizedMessage());
		}
	}

}
