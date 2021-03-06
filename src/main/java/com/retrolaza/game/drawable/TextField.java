package com.retrolaza.game.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.retrolaza.game.Game;
import com.retrolaza.game.controls.WritingControls;

/**
 * Clase que representa un campo de texto que se puede rellenar usando el teclado.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class TextField extends Drawable {
	
	private Text text;
	
	private int x;
	private int y;
	private int charLimit;
	private boolean showLine;
	
	private WritingControls controls;
	
	public static final int DR_TEXT = Game.ID.getAndIncrement();
	
	/**
	 * @param x Posición X en el cual dibujar el campo de texto.
	 * @param y Posición Y en el cual dibujar el campo de texto.
	 * @param charLimit Límite de caracteres que se puede escribir en el campo.
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public TextField(int x, int y, int charLimit) throws FontFormatException, IOException {
		this.x = x;
		this.y = y;
		this.charLimit = charLimit;
		this.showLine = true;
		
		this.text = new Text("", this.x + 10, this.y + 40, Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Pixeled.ttf")));
		text.setColor(Color.BLACK);
		text.setSize(30);
		
		controls = new WritingControls(t -> text.setText(t), this.charLimit);
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				showLine = !showLine;
			}
		};
		new Timer().schedule(task, 0, 500);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!isHiding()) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(this.x, this.y, 40 * this.charLimit + 30, text.getSize() + 20);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(this.x - 1, this.y - 1, 40 * this.charLimit + 31, text.getSize() + 21);
			if (showLine) g2d.drawLine(this.x + 10 + 30 * text.getText().length(), this.y + 10, this.x + 10 + 30 * text.getText().length(), this.y + 10 + text.getSize());
			text.draw(g2d);
		}
	}
	
	public WritingControls getControls() {
		return controls;
	}
	
	public String getText() {
		return controls.getText();
	}
	
}
