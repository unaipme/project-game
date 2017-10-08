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

/**
 * Pantalla que se muestra cuando el jugador selecciona "Aukerak" en el menú principal. En esta pantalla se puede elegir entre 4 canciones diferentes o el silencio.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class OptionsScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Menu options;
	private Text titleText;
	private Text escInstructions;
	private Image escButton;
	private AnimatedImage gameLogo;
	private Text arrowInstructions;
	private Image arrowButton;
	private Text enterInstructions;
	private Image enterButton;

	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	public static final int DR_MENU = Game.ID.getAndIncrement();
	public static final int DR_LOGO = Game.ID.getAndIncrement();
	public static final int DR_ARROW_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ARROW_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_ENTER_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ENTER_BUTTON = Game.ID.getAndIncrement();
	
	private static final String SONG_1 = "res/music/Pixel_Peeker_Polka_faster.wav";
	private static final String SONG_2 = "res/music/Pinball_Spring.wav";
	private static final String SONG_3 = "res/music/8_bit_Dungeon_Boss.wav";
	private static final String SONG_4 = "res/music/Show_Your_oves.wav";

	public OptionsScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		final OptionsScreen self = this;
		
		options = new Menu(100, 230);
		options.setSeparation(30);
		options.addOption("1. abestia");
		options.addOption("2. abestia");
		options.addOption("3. abestia");
		options.addOption("4. abestia");
		options.addOption("Isilik");
		addDrawable(DR_MENU, options);
		
		gameLogo = new AnimatedImage("res/img/retro_game.gif", game(), 480, 240);
		addDrawable(DR_LOGO, gameLogo);
		
		this.controls = new KeyboardControls(this);
		controls.when(KeyEvent.VK_ESCAPE).then(s -> {
			s.hide();
			s.getParent().show();
		});
		controls.when(KeyEvent.VK_DOWN).then(m -> ((Menu) m.getDrawable(DR_MENU)).nextSelected());
		controls.when(KeyEvent.VK_UP).then(m -> ((Menu) m.getDrawable(DR_MENU)).previousSelected());
		controls.when(KeyEvent.VK_ENTER).then(m -> {
			Game game = self.game();
			game.stopMusic();
			switch (((Menu) m.getDrawable(DR_MENU)).getSelectedOption()) {
			case 0:
				game.setMusic(SONG_1);
				game.playMusic();
				break;
			case 1:
				game.setMusic(SONG_2);
				game.playMusic();
				break;
			case 2:
				game.setMusic(SONG_3);
				game.playMusic();
				break;
			case 3:
				game.setMusic(SONG_4);
				game.playMusic();
				break;
			}
		});
		
		titleText = new Text("AUKERAK", 100, 70 + Text.DEFAULT_SIZE);
		addDrawable(DR_TITLE, titleText);
		
		escButton = new Image("res/img/teclado_esc.png", game(), 10, 650);
		escButton.scale(-1, 50, Image.SCALE_SMOOTH);
		addDrawable(DR_ESC_BUTTON, escButton);
		
		escInstructions = new Text("Bueltatzeko", 70, 690);
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
		
		this.setBackground("res/img/background.png");
		
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

}
