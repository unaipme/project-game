package com.retrolaza.game.drawable;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Menu extends Drawable {
	
	private List<MenuOption> options = new ArrayList<>();
	private int x;
	private int y;
	private int selected;
	
	public static final Integer DF_SELECTED_OFFSET = 75;
	
	private static final Consumer<MenuOption> DF_OPTION_DRAW_SELECTED = (o) -> o.getText().setX(o.getText().getX() + DF_SELECTED_OFFSET);
	
	private static final Consumer<MenuOption> DF_OPTION_DRAW_UNSELECTED = (o) -> o.getText().setX(o.getText().getX() - DF_SELECTED_OFFSET);
	
	public Menu(int x, int y) {
		this.x = x;
		this.y = y;
		this.selected = 0;
	}
	
	public void addOption(String text) throws FontFormatException, IOException {
		addOption(text, DF_OPTION_DRAW_SELECTED, DF_OPTION_DRAW_UNSELECTED);
	}
	
	public void addOption(String text, Consumer<MenuOption> drawWhenSelected, Consumer<MenuOption> drawWhenUnselected) throws FontFormatException, IOException {
		MenuOption option = new MenuOption(text, x, y + options.size() * MenuOption.DEFAULT_SIZE);
		option.setSelectionAnimations(drawWhenSelected, drawWhenUnselected);
		options.add(option);
		if (options.size() == 1) options.get(0).select();
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
	
	public int getSelectedOption() {
		return selected;
	}

	@Override
	public void draw(Graphics2D g2d) {
		for (int i=0; i<options.size(); i++) {
			options.get(i).draw(g2d);
		}
	}

}
