package com.retrolaza.data;

/**
 * Clase que representa una entrada del ranking, con su respectivo nombre de usuario, posición y puntuación
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class Record {
	private String username;
	private Integer position;
	private Integer score;
	
	public Record withUsername(String username) {
		this.username = username;
		return this;
	}
	
	public Record withPosition(Integer position) {
		this.position = position;
		return this;
	}
	
	public Record withScore(Integer score) {
		this.score = score;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
