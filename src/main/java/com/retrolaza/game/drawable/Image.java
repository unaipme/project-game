package com.retrolaza.game.drawable;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.retrolaza.game.Game;

/**
 * Clase que representa una imagen (no animada) reescalable.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Image extends Drawable {

	private java.awt.Image image;
	private Game game;
	private int x;
	private int y;
	
	public static int SCALE_SMOOTH = java.awt.Image.SCALE_SMOOTH;
	public static int SCALE_DEFAULT = java.awt.Image.SCALE_DEFAULT;
	public static int SCALE_FAST = java.awt.Image.SCALE_FAST;
	
	public Image(String path, Game game, int x, int y) throws IOException {
		this.image = ImageIO.read(new File(path));
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Reescala la imagen
	 * @param width Establece el nuevo ancho de la imagen. Si es -1, se mantiene en proporción dependiendo de la altura establecida.
	 * @param height Establece el nuevo alto de la imagen. Si es -1, se mantiene en proporción dependiendo de la anchura establecida.
	 * @param hints Modo en el que reescalar la imagen. Se espera uno de los valores estáticos de esta clase
	 */
	public void scale(int width, int height, int hints) {
		image = image.getScaledInstance(width, height, hints);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!isHiding()) g2d.drawImage(image, x, y, game);
	}
	
}
