package com.retrolaza.game.screens;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.retrolaza.game.Game;
import com.retrolaza.game.GameScreen;
import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.drawable.AnimatedImage;
import com.retrolaza.game.drawable.Image;
import com.retrolaza.game.drawable.Menu;
import com.retrolaza.game.drawable.Text;

/**
 * Pantalla del menú principal que se muestra nada más abrir el juego.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class MainScreen extends GameScreen {
	
	private Menu menu;
	private KeyboardControls controls;
	private AnimatedImage gameLogo;
	
	private Text escInstructions;
	private Image escButton;
	private Text arrowInstructions;
	private Image arrowButton;
	private Text enterInstructions;
	private Image enterButton;
	
	private RankingScreen rankingScreen;
	private OptionsScreen optionsScreen;
	private DifficultySelectorScreen difficultyScreen;
	
	public static final int DR_MENU = Game.ID.getAndIncrement();
	public static final int DR_LOGO = Game.ID.getAndIncrement();
	
	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ARROW_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ARROW_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ENTER_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ENTER_BUTTON = Game.ID.getAndIncrement();
	
	public MainScreen(Game game) throws FontFormatException, IOException {
		super(game, null);
		final MainScreen self = this;
		
		menu = new Menu(100, 230);
		menu.setSeparation(60);
		menu.addOption("Jokatu");
		menu.addOption("Rankinga");
		menu.addOption("Aukerak");
		menu.addOption("Irten");
		addDrawable(DR_MENU, menu);
		
		rankingScreen = new RankingScreen(game, this);
		Game.addScreen(rankingScreen);
		
		optionsScreen = new OptionsScreen(game, this);
		Game.addScreen(optionsScreen);
		
		difficultyScreen = new DifficultySelectorScreen(game, this);
		Game.addScreen(difficultyScreen);
		
		gameLogo = new AnimatedImage("res/img/retro_game.gif", game(), 480, 240);
		addDrawable(DR_LOGO, gameLogo);
		
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

		game().setMusic("res/music/Pixel_Peeker_Polka_faster.wav");
		game().playMusic();
		
		setBackground("res/img/background.png");
		
		controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_DOWN).then(m -> ((Menu) m.getDrawable(DR_MENU)).nextSelected());
		controls.when(KeyEvent.VK_UP).then(m -> ((Menu) m.getDrawable(DR_MENU)).previousSelected());
		controls.when(KeyEvent.VK_ESCAPE).then(m -> System.exit(0));
		controls.when(KeyEvent.VK_ENTER).then(m -> {
			switch (((Menu) m.getDrawable(DR_MENU)).getSelectedOption()) {
			case 0:
				m.hide();
				self.getDifficultyScreen().show();
				break;
			case 1:
				m.hide();
				self.getRankingScreen().show();
				break;
			case 2:
				m.hide();
				self.getOptionsScreen().show();
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
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
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
	
	public OptionsScreen getOptionsScreen() {
		return optionsScreen;
	}
	
	public DifficultySelectorScreen getDifficultyScreen() {
		return difficultyScreen;
	}
	
}
