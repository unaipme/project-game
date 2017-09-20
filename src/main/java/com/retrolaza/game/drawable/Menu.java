package com.retrolaza.game.drawable;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Drawable {
	
	private List<MenuOption> options = new ArrayList<>();
	private int x;
	private int y;
	private int selected;
	private int separation;
	private static final int SELECTED_OPT_SIZE = 55;
	
	public Menu(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = -1;
		this.separation = 0;
	}
	
	public void addOption(String text) throws FontFormatException, IOException {
		MenuOption option = new MenuOption(this, text, x, y + options.size() * (MenuOption.DEFAULT_SIZE + this.separation));
		options.add(option);
		if (options.size() == 1) nextSelected();
	}
	
	public void nextSelected() {
		if (++selected == options.size()) {
			this.selected = 0;
		}
		updateOptions();
	}
	
	public void previousSelected() {
		if (--selected == -1) {
			this.selected = options.size() - 1;
		}
		updateOptions();
	}
	
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
