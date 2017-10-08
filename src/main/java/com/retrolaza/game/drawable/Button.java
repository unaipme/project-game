package com.retrolaza.game.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.retrolaza.game.Game;

/**
 * Clase que representa un botón rectangular de bordes negros y fondo blanco con un texto y, a ser posible, una imagen a su izquierda.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Button extends Drawable {
	
	private Text text;
	private int x;
	private int y;
	
	private int width;
	
	private Game game;
	private Image image;
	
	/**
	 * 
	 * @param text Texto que contendrá el botón
	 * @param game Referencia al juego
	 * @param x Posición X en el que se mostrará el botón
	 * @param y Posición Y en el que se mostrará el botón
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public Button(String text, Game game, int x, int y) throws FontFormatException, IOException {
		this.game = game;
		setPosition(x, y);
		this.text = new Text(text, x + 10, y + 43, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Pixeled.ttf")).deriveFont(25f));
		this.text.setColor(Color.BLACK);
		this.image = null;
		this.width = text.length() * 25;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!hiding) {
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y, width, 55);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(x, y, width, 55);
			text.draw(g2d);
			if (image != null) {
				image.draw(g2d);
			}
		}
	}
	
	/**
	 * Método para definir la imagen que se usará
	 * @param s Ruta a la imagen
	 * @throws IOException
	 */
	public void setImage(String s) throws IOException {
		this.image = new Image(s, game, x + 10, y + 5);
		text.setX(x + text.getText().length() + 55);
		width = text.getText().length() * 25 + 55;
		image.scale(-1, 45, Image.SCALE_SMOOTH);
	}
	
	public int getWidth() {
		return width;
	}
	
	/**
	 * Método para añadir (o restar) anchura al botón cuando la anchura automática no es correcta
	 * @param diff Diferencia de anchura a sumar (si es menor que cero, se resta anchura)
	 */
	public void addToWidth(int diff) {
		this.width += diff;
	}
	
	/**
	 * Método para redefinir la posición en el que se mostrará el botón.
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		if (image != null) {
			text.setPosition(x + 55 + text.getText().length(), y + 43);
			image.setPosition(x + 10, y + 5);
		} else {
			Optional.ofNullable(text).ifPresent(t -> t.setPosition(x + 10, y + 43));
		}
	}
	
}
