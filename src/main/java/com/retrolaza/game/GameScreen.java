package com.retrolaza.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <p>Clase que representa una pantalla del juego. Se considera pantalla a todo lo que se ve dentro de la ventana del juego en un momento dado.
 * Cada pantalla tiene unos elementos y una interactivilidad muy concretas que se han de definir en la implementación de cada pantalla.</p>
 * <p>Cada pantalla, además, también tiene una pantalla padre (jerárquicamente superior) y el objeto {@see com.retrolaza.game.Game} al que pertenece.
 * Se llama pantalla jerárquicamente superior a la pantalla de la cual desemboca una pantalla. Es decir, si la interacción con una pantalla lleva al usuario a una segunda pantalla, la primera pantalla es padre de la segunda.</p>
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public abstract class GameScreen extends GameChild {
	
	private GameScreen parent;
	private Image backgroundImage;
	private Color backgroundColor;
	private boolean showBackground;
	
	public GameScreen(Game g, GameScreen parent) {
		super(g);
		this.parent = parent;
		this.backgroundImage = null;
		this.showBackground = true;
		this.backgroundColor = Color.BLACK;
	}
	
	/**
	 * Método que esconde los contenidos de la pantalla y la pantalla en sí
	 */
	@Override
	public void hide() {
		super.hide();
		this.showBackground = false;
		turnOff();
	}
	
	/**
	 * Método que vuelve a mostrar / muestra la pantalla y sus contenidos
	 */
	@Override
	public void show() {
		super.show();
		this.showBackground = true;
		setUp();
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if (g2d.getBackground() != this.backgroundColor) game().setBackground(this.backgroundColor);
		if (showBackground) g2d.drawImage(backgroundImage, 0, 0, game());
		super.draw(g2d);
	}
	
	/**
	 * Método que da acceso directo a la pantalla padre de la actual
	 * @return Pantalla padre
	 */
	public GameScreen getParent() {
		return parent;
	}
	
	/**
	 * Método que permite definir el color del fondo de la pantalla
	 * @param color Color del fondo
	 */
	public void setBackground(Color color) {
		this.backgroundColor = color;
	}
	
	/**
	 * Método que permite definir una imagen como fondo de la pantalla
	 * @param file Ruta de la foto, puede ser relativa.
	 */
	public void setBackground(String file) {
		try {
			this.backgroundImage = ImageIO.read(new File(file));
			this.showBackground = true;
		} catch (IOException e) {
			e.printStackTrace();
			this.backgroundImage = null;
		}
	}
	
	/**
	 * Método que se ejecuta automáticamente cuando se manda a mostrar una pantalla. Se debe utilizar para (re)inicializar los elementos o para añadir los {@see com.retrolaza.game.controls.KeyListener} o parecidos. 
	 */
	public abstract void setUp();
	
	/**
	 * Método que se ejecuta automáticamente cuando se manda a esconder una pantalla. Normalmente se emplean para revertir el trabajo hecho en {@see #setUp()}
	 */
	public abstract void turnOff();
	
}
