package com.retrolaza.game.drawable;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa una tabla que muestra información. Para crearla deberá usarse el método estático {@see #create(int, int, int)}. Al momento de crear la tabla se deben definir cuántas columnas tendrá, y cuánta anchura tendrán cada una de ellas.
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
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
	
	/**
	 * Devuelve una instancia de la clase {@see com.retrolaza.game.drawable.Table.Builder}, la cual facilita la creación de la tabla.
	 * @param x Posición X en la cual se colocará la tabla
	 * @param y Posición Y en la cual se colocará la tabla
	 * @param columns Cantidad de columnas
	 * @return
	 */
	public static Table.Builder create(int x, int y, int columns) {
		return new Builder(x, y, columns);
	}
	
	/**
	 * Permite definir o sobreescribir la anchura de una columna concreta
	 * @param column El número de la columna, siendo 0 la primera.
	 * @param width La nueva anchura.
	 */
	public void setWidthForColumn(int column, int width) {
		columnWidths.put(column, width);
	}
	
	/**
	 * Método que permite la creación de una nueva fila de información
	 * @param rowData
	 * @return La misma tabla, para permitir la concatenación de funciones y, así, hacer más fácil la creación de estas.
	 */
	public Table withRow(Object... rowData) {
		try {
			List<Text> row = new ArrayList<>();
			int xOffset = 0;
			for (int i=0; i<columns; i++) {
				Text t = new Text(rowData[i].toString(), x + xOffset + 20, y + (ROW_HEIGHT_DEFAULT * (data.size() + 1) - 20));
				t.setSize(45);
				if (data.size() % 2 == 1) t.setColor(Color.BLACK);
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
	
	/**
	 * Vacía el contenido de la tabla
	 */
	public void clear() {
		data.clear();
	}
	
	/**
	 * Comprueba si el existe, en alguna fila, el valor dado en la columna señalada
	 * @param col Columna en la que buscar
	 * @param value Valor buscado
	 * @return Booleano que indica si se ha encontrado el valor
	 */
	public boolean contains(Integer col, String value) {
		return data.get(col).stream().anyMatch(t -> value.equals(t.getText()));
	}
	
	/**
	 * Devuelve la fila cuya columna tenga el valor señalado
	 * @param col Columna en la que buscar
	 * @param value Valor que buscar
	 * @return La fila que coincida con el criterio de búsqueda
	 */
	public List<Text> getRowWith(Integer col, String value) {
		return data.stream().filter(t -> {
			return value.toLowerCase().equals(t.get(col).getText().toLowerCase());
		}).findFirst().orElse(null);
	}
	
	/**
	 * Clase que ayuda a construir la tabla, recibiendo la anchura que van a tener las columnas antes de que se cree la table como tal. Hay un ejemplo en la clase {@see com.retrolaza.game.screens.RankingScreen#RankingScreen(com.retrolaza.game.Game, com.retrolaza.game.screens.MainScreen)}
	 * @author Unai P. Mendizabal (@unaipme)
	 *
	 */
	public static class Builder {
		
		private Table table;
		private int tempWidth = -1;
		
		private Builder(int x, int y, int columns) {
			this.table = new Table(x, y, columns);
		}
		
		/**
		 * Establecer anchura de la columna que se especificará a continuación con el método {@see #inColumn(int)}. Debe llamarse a este método antes que a {@see #inColumn(int)}.
		 * @param width
		 * @return
		 */
		public Builder withWidth(int width) {
			this.tempWidth = width;
			return this;
		}
		
		/**
		 * Define a qué columna asignar la anchura recién establecida.
		 * @param column
		 * @return
		 */
		public Builder inColumn(int column) {
			if (tempWidth == -1) throw new RuntimeException("Ez da zabalera definitu");
			table.setWidthForColumn(column, tempWidth);
			tempWidth = -1;
			return this;
		}
		
		public Table build() {
			return table;
		}
		
	}
	
	public List<List<Text>> getRows() {
		return data;
	}

}
