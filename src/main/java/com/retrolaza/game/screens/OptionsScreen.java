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

public class OptionsScreen extends GameScreen {
	
	private KeyboardControls controls;
	private Menu options;
	private Text titleText;
	private Text escInstructions;
	private Image escButton;
	private AnimatedImage gameLogo;

	public static final int DR_ESC_TEXT = Game.ID.getAndIncrement();
	public static final int DR_ESC_BUTTON = Game.ID.getAndIncrement();
	public static final int DR_TITLE = Game.ID.getAndIncrement();
	public static final int DR_MENU = Game.ID.getAndIncrement();
	public static final int DR_LOGO = Game.ID.getAndIncrement();
	
	private static final String SONG_1 = "res/music/8_bit_Dungeon_Boss.wav";
	private static final String SONG_2 = "res/music/Pinball_Spring.wav";
	private static final String SONG_3 = "res/music/Pixel_Peeker_Polka_faster.wav";
	private static final String SONG_4 = "res/music/Show_Your_oves.wav";

	public OptionsScreen(Game g, GameScreen parent) throws FontFormatException, IOException {
		super(g, parent);
		
		options = new Menu(100, 230);
		options.setSeparation(30);
		options.addOption("1. abestia");
		options.addOption("2. abestia");
		options.addOption("3. abestia");
		options.addOption("4. abestia");
		options.addOption("Abestirik ez");
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
			Game game = ((OptionsScreen) m).game();
			game.getMusic().stop();
			switch (((Menu) m.getDrawable(DR_MENU)).getSelectedOption()) {
			case 0:
				game.setMusic(SONG_1);
				game.getMusic().start();
				break;
			case 1:
				game.setMusic(SONG_2);
				game.getMusic().start();
				break;
			case 2:
				game.setMusic(SONG_3);
				game.getMusic().start();
				break;
			case 3:
				game.setMusic(SONG_4);
				game.getMusic().start();
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
