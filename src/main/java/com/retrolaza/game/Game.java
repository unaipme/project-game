package com.retrolaza.game;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.retrolaza.game.screens.MainScreen;

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private MainScreen menu;
	
	public final static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public final static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
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
		//frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setSize(1200, 750);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void addScreen(GameScreen screen) {
		screens.add(screen);
	}

}
