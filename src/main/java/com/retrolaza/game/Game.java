package com.retrolaza.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.retrolaza.game.audio.Music;
import com.retrolaza.game.screens.MainScreen;

/**
 * Clase que "envuelve" la lógica y los elementos del juego, y lo ejecuta.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
@SuppressWarnings("serial")
public class Game extends JPanel {
	
	/**
	 * Pantalla del menú principal que se mostrará nada más abrir el juego
	 */
	private MainScreen menu;
	
	/**
	 * Variable que guarda la música que se reproduce durante la ejecución del juego
	 */
	private Music music;
	
	/**
	 * Constante que almacena el ancho de la ventana
	 */
	public final static int SCREEN_WIDTH = 1200;
	/**
	 * Constante que almacena el alto de la ventana
	 */
	public final static int SCREEN_HEIGHT = 750;
	
	/**
	 * Objeto que se emplea para la asignación automática de números de identificación para cada {@see com.retrolaza.game.drawable.Drawable}.
	 * La clase es parte de la API de Java, más información en {@link java.util.concurrent.atomic.AtomicInteger}.
	 */
	public final static AtomicInteger ID = new AtomicInteger();
	
	private static List<GameScreen> screens = new ArrayList<>();
	
	public Game() {
		setup();
	}
	
	private void setup() {
		try {
			menu = new MainScreen(this);
			addScreen(menu);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		screens.forEach(s -> s.draw(g2d));
		repaint();
	}
	
	public static void main(String[] args) throws FontFormatException, IOException {
		Game game = new Game();
		game.setFocusable(true);
		
		JFrame frame = new JFrame("project-game");
		frame.add(game);
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("res/img/logo.png").getImage());
	}
	
	/**
	 * Añade una nueva pantalla al juego (Más detalles en {@see com.retrolaza.game.GameScreen})
	 * @param screen Pantalla
	 */
	public static void addScreen(GameScreen screen) {
		screens.add(screen);
	}
	
	/**
	 * 
	 * @param file
	 */
	public void setMusic(String file) {
		try {
			this.music = new Music(file).unlimitedLoop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reproduce la canción que ha sido seleccionada.
	 */
	public void playMusic() {
		if (this.music != null) music.start();
	}
	
	/**
	 * Detiene la reproducción de la canción seleccionada.
	 */
	public void stopMusic() {
		if (this.music != null) music.stop();
	}
	
	/**
	 * Inicia la reproducción de la canción desde el principio.
	 */
	public void resetMusic() {
		if (this.music != null) music.reset();
	}

}
