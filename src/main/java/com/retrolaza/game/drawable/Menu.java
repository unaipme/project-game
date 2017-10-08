package com.retrolaza.game.drawable;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Elemento que muestra un menú con las opciones definidas. Cuando una de las opciones está seleccionada, el tamaño de la letra se hace mayor.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Menu extends Drawable {
	
	private List<MenuOption> options = new ArrayList<>();
	private int x;
	private int y;
	private int selected;
	private int separation;
	private static final int SELECTED_OPT_SIZE = 85;
	
	public Menu(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = -1;
		this.separation = 0;
	}
	
	/**
	 * Añade una nueva opción con el texto definido al menú, calculando automáticamente la posición en la que se debe colocar.
	 * @param text El texto de la opción
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public void addOption(String text) throws FontFormatException, IOException {
		MenuOption option = new MenuOption(text, x, y + options.size() * (MenuOption.DEFAULT_SIZE + this.separation));
		options.add(option);
		if (options.size() == 1) nextSelected();
	}
	
	/**
	 * Seleccionar la siguiente opción. Si la opción seleccionada era la última, seleccionará la primera.
	 */
	public void nextSelected() {
		if (++selected == options.size()) {
			this.selected = 0;
		}
		updateOptions();
	}
	
	/**
	 * Seleccionar la opción anterior. Si la opción seleccionada era la primera, seleccionará la última.
	 */
	public void previousSelected() {
		if (--selected == -1) {
			this.selected = options.size() - 1;
		}
		updateOptions();
	}
	
	/**
	 * Actualiza las posiciones y tamaños de las opciones dependiendo de cual esté seleccionada.
	 */
	private void updateOptions() {
		for (int i=0; i<options.size(); i++) {
			MenuOption option = options.get(i);
			if (i == this.selected) option.setFontSize(SELECTED_OPT_SIZE);
			else option.setFontSize(MenuOption.DEFAULT_SIZE);
			option.setY(y + (separation + MenuOption.DEFAULT_SIZE) * i);
		}
	}
	
	public int getSelectedOption() {
		return selected;
	}

	@Override
	public void draw(Graphics2D g2d) {
		for (int i=0; i<options.size(); i++) {
			options.get(i).draw(g2d);
		}
	}
	
	/**
	 * Actualizar la separación que hay entre opción y opción
	 * @param s La cantidad de píxels de separación.
	 */
	public void setSeparation(int s) {
		this.separation = s;
	}
	
	public int getSeparation() {
		return this.separation;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

}
