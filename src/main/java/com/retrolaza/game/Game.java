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

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private MainScreen menu;

	private Music music;
	
	public final static int SCREEN_WIDTH = 1200;
	public final static int SCREEN_HEIGHT = 750;
	
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
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("res/img/logo.png").getImage());
	}
	
	public static void addScreen(GameScreen screen) {
		screens.add(screen);
	}
	
	public void setMusic(String file) {
		try {
			this.music = new Music(file).unlimitedLoop();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playMusic() {
		if (this.music != null) music.start();
	}
	
	public void stopMusic() {
		if (this.music != null) music.stop();
	}
	
	public void resetMusic() {
		if (this.music != null) music.reset();
	}

}
