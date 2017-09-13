package com.retrolaza.game.movable;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Drawable {
	
	private List<MenuOption> options = new ArrayList<>();
	private int x;
	private int y;
	private int selected;
	
	public Menu(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = 0;
	}
	
	public void addOption(String text) {
		options.add(new MenuOption(text, x, y + options.size() * MenuOption.T_SIZE));
	}
	
	public void nextSelected() {
		this.selected++;
		if (this.selected == options.size()) {
			this.selected = 0;
		}
		updateSelected();
	}
	
	public void previousSelected() {
		this.selected--;
		if (this.selected == -1) {
			this.selected = options.size() - 1;
		}
		updateSelected();
	}
	
	private void updateSelected() {
		options.forEach(o -> o.setSelected(false));
		options.get(this.selected).setSelected(true);
	}

	@Override
	public void draw(Graphics2D g2d) {
		for (int i=0; i<options.size(); i++) {
			MenuOption o = options.get(i);
			if (selected == i) {
				o.setSelected(true);
			} else {
				o.setSelected(false);
			}
			o.draw(g2d);
		}
	}

}
