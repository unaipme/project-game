package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.Menu;

public class MainScreen extends GameScreen {
	
	private Menu menu;
	private KeyboardControls controls;
	
	private RankingScreen rankingScreen;
	
	public static final int DR_MENU = Game.ID.getAndIncrement();
	
	public MainScreen(Game game) throws FontFormatException, IOException {
		super(game, null);
		menu = new Menu(30, 60);
		menu.setSeparation(20);
		menu.addOption("Jokatu");
		menu.addOption("Rankinga");
		menu.addOption("Aukerak");
		menu.addOption("Irten");
		addDrawable(DR_MENU, menu);
		
		rankingScreen = new RankingScreen(game, this);
		Game.addScreen(rankingScreen);
		
		setBackgroundImage("res/img/background.png");
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_DOWN).then(m -> ((Menu) m.getDrawable(MainScreen.DR_MENU)).nextSelected());
		controls.when(KeyEvent.VK_UP).then(m -> ((Menu) m.getDrawable(MainScreen.DR_MENU)).previousSelected());
		controls.when(KeyEvent.VK_ENTER).then(m -> {
			Menu menu = (Menu) m.getDrawable(MainScreen.DR_MENU);
			switch (menu.getSelectedOption()) {
			case 1:
				m.hide();
				((MainScreen) m).getRankingScreen().show();
				break;
			case 3:
				System.exit(0);
				break;
			default:
				System.out.println("Ekintza oraindik ez da definitu.");
			}
		});
		
		show();
	}
	
	public void setUp() {
		game().addKeyListener(controls);
	}
	
	public void turnOff() {
		game().removeKeyListener(controls);
	}
	
	public RankingScreen getRankingScreen() {
		return rankingScreen;
	}
	
}
