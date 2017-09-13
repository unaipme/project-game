package com.retrolaza.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.retrolaza.game.controls.KeyboardControls;
import com.retrolaza.game.movable.Menu;

@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private Menu menu = new Menu(30, 30);

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		menu.draw(g2d);
		repaint();
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
		game.getMenu().addOption("NAHI DOT JOAKTU");
		game.getMenu().addOption("OPZIÑUK");
		game.getMenu().addOption("ATERA EHMENDIQ OSTIA");
		
		KeyboardControls controls = new KeyboardControls(game);
		controls.when(KeyEvent.VK_DOWN).then(g -> g.getMenu().nextSelected()).save();
		controls.when(KeyEvent.VK_UP).then(g -> g.getMenu().previousSelected()).save();
		
		game.addKeyListener(controls);
		game.setFocusable(true);
		
		JFrame frame = new JFrame("Prueba");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.add(game);
		frame.setSize(screenSize.width, screenSize.height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
