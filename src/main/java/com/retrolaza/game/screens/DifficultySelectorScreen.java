package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.AnimatedImage;
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Menu;
import com.retrolaza.game.drawable.Text;

public class DifficultySelectorScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Menu menu;
	private AnimatedImage gameLogo;
	
	private Text escInstructions;
	private Image escButton;
	private Text arrowInstructions;
	private Image arrowButton;
	private Text enterInstructions;
	private Image enterButton;
	
	private GameplayScreen gameScreen;
	
	public static final int DR_MENU = Game.ID.getAndIncrement();
	public static final int DR_LOGO = Game.ID.getAndIncrement();
	
	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ARROW_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ARROW_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ENTER_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ENTER_BUTTON = Game.ID.getAndIncrement();

	public DifficultySelectorScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		final DifficultySelectorScreen self = this;
		
		setBackground("res/img/background.png");
		
		gameLogo = new AnimatedImage("res/img/retro_game.gif", game(), 480, 240);
		addDrawable(DR_LOGO, gameLogo);
		
		gameScreen = new GameplayScreen(game(), this);
		Game.addScreen(gameScreen);
		
		escButton = new Image("res/img/teclado_esc.png", game(), 10, 650);
		escButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_ESC_BUTTON, escButton);
		
		escInstructions = new Text("Ateratzeko", 70, 690);
		escInstructions.setSize(20);
		addDrawable(DR_ESC_TEXT, escInstructions);
		
		arrowButton = new Image("res/img/teclado_flechas.png", game(), 200, 650);
		arrowButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_ARROW_BUTTON, arrowButton);
		
		arrowInstructions = new Text("Aukeretan mugitzeko", 280, 690);
		arrowInstructions.setSize(20);
		addDrawable(DR_ARROW_TEXT, arrowInstructions);
		
		enterButton = new Image("res/img/teclado_enter.png", game(), 475, 650);
		enterButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_ENTER_BUTTON, enterButton);
		
		enterInstructions = new Text("Aukeratu", 540, 690);
		enterInstructions.setSize(20);
		addDrawable(DR_ENTER_TEXT, enterInstructions);
		
		menu = new Menu(100, 270);
		menu.setSeparation(60);
		menu.addOption("Erraza");
		menu.addOption("Normala");
		menu.addOption("Zaila");
		addDrawable(DR_MENU, menu);
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_DOWN).then(gs -> self.getMenu().nextSelected());
		controls.when(KeyEvent.VK_UP).then(gs -> self.getMenu().previousSelected());
		controls.when(KeyEvent.VK_ENTER).then(gs -> {
			self.hide();
			self.getGameScreen().show();
		});
		controls.when(KeyEvent.VK_ESCAPE).then(gs -> {
			self.hide();
			self.getParent().show();
		});
		
		hide();
	}

	@Override
	public void setUp() {
		game().addKeyListener(controls);
	}

	@Override
	public void turnOff() {
		game().removeKeyListener(controls);
	}
	
	public Menu getMenu() {
		return this.menu;
	}
	
	public GameplayScreen getGameScreen() {
		return gameScreen;
	}

}
