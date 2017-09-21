package com.retrolaza.game.drawable;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table extends Drawable {
	
	private List<List<Text>> data;
	private Map<Integer, Integer> columnWidths = new HashMap<>();
	private int columns;
	private int x;
	private int y;
	
	public final static int ROW_HEIGHT_DEFAULT = 65;
	public final static int TEXT_SIZE = 45;
	
	private Table(int x, int y, int columns) {
		data = new ArrayList<>();
		this.x = x;
		this.y = y;
		this.columns = columns;
	}
	
	public static Table.Builder create(int x, int y, int columns) {
		return new Builder(x, y, columns);
	}
	
	public void setWidthForColumn(int column, int width) {
		columnWidths.put(column, width);
	}
	
	public Table withRow(Object... rowData) {
		try {
			List<Text> row = new ArrayList<>();
			int currentRows = this.data.size();
			int xOffset = 0;
			for (int i=0; i<columns; i++) {
				Text t = new Text(rowData[i].toString(), x + xOffset + 20, y + (ROW_HEIGHT_DEFAULT * (currentRows + 1) - 20));
				t.setSize(45);
				if (currentRows % 2 == 1) t.setColor(Color.BLACK);
				xOffset += columnWidths.get(i);
				row.add(t);
			}
			data.add(row);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public void show() {
		super.show();
		data.forEach(r -> r.forEach(c -> c.show()));
	}
	
	@Override
	public void hide() {
		super.hide();
		data.forEach(r -> r.forEach(c -> c.hide()));
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		if (!isHiding()) {
			int totalWidth = columnWidths.values().stream().mapToInt(Integer::intValue).sum();
			for (int i=0; i<data.size(); i++) {
				if (i % 2 == 0) g2d.setColor(Color.LIGHT_GRAY);
				else g2d.setColor(Color.WHITE);
				g2d.fillRect(x, y + i * ROW_HEIGHT_DEFAULT, totalWidth, ROW_HEIGHT_DEFAULT);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x - 1, y + i * ROW_HEIGHT_DEFAULT - 1, totalWidth + 2, ROW_HEIGHT_DEFAULT + 2);
			}
			data.forEach(r -> r.forEach(c -> c.draw(g2d)));
		}
	}
	
	public static class Builder {
		
		private Table table;
		private int tempWidth = -1;
		
		private Builder(int x, int y, int columns) {
			this.table = new Table(x, y, columns);
		}
		
		public Builder withWidth(int width) {
			this.tempWidth = width;
			return this;
		}
		
		public Builder inColumn(int column) {
			if (tempWidth == -1) throw new RuntimeException("Ez da zabalera definitu");
			table.setWidthForColumn(column, tempWidth);
			return this;
		}
		
		public Table build() {
			return table;
		}
		
	}

}
