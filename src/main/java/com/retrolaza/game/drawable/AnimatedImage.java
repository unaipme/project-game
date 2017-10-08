package com.retrolaza.game.drawable;

import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.retrolaza.game.Game;

/**
 * Clase que representa y dibuja una imagen que, al contrario que {@see com.retrolaza.game.drawable.Image}, es animada.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class AnimatedImage extends Drawable {
	
	private Icon image;
	private int x;
	private int y;
	private Game game;
	
	/**
	 * 
	 * @param path Ruta a la imagen animada
	 * @param game Referencia al juego
	 * @param x Posición X en la cual mostrar la imagen
	 * @param y Posición Y en la cual mostrar la imagen
	 */
	public AnimatedImage(String path, Game game, int x, int y) {
		this.image = new ImageIcon(path);
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		image.paintIcon(game, g2d, x, y);
	}
	
}
