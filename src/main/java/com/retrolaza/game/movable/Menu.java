package com.retrolaza.game.movable;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Menu implements Drawable {
	
	private List<MenuOption> options = new ArrayList<>();
	private int x;
	private int y;
	private int selected;
	
	private static final BiConsumer<Graphics2D, MenuOption> DF_OPTION_DRAW_SELECTED = (g2d, o) -> {
		g2d.setFont(o.getFont());
		g2d.drawString(o.getText(), o.getX() + 75, o.getY());
	};
	
	public Menu(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = 0;
	}
	
	public void addOption(String text) throws FontFormatException, IOException {
		addOption(text, DF_OPTION_DRAW_SELECTED);
	}
	
	public void addOption(String text, BiConsumer<Graphics2D, MenuOption> drawWhenSelected) throws FontFormatException, IOException {
		MenuOption option = new MenuOption(text, x, y + options.size() * MenuOption.DEFAULT_SIZE);
		option.setDrawWhenSelected(drawWhenSelected);
		options.add(option);
	}
	
	public void nextSelected() {
		options.get(this.selected++).deselect();
		if (this.selected == options.size()) {
			this.selected = 0;
		}
		options.get(this.selected).select();
	}
	
	public void previousSelected() {
		options.get(this.selected--).deselect();
		if (this.selected == -1) {
			this.selected = options.size() - 1;
		}
		options.get(this.selected).select();
	}

	@Override
	public void draw(Graphics2D g2d) {
		for (int i=0; i<options.size(); i++) {
			MenuOption o = options.get(i);
			if (selected == i) {
				o.select();
			} else {
				o.deselect();
			}
			o.draw(g2d);
		}
	}

}
