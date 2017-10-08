package com.retrolaza.game.exception;

/**
 * Excepción personalizada llamada cuando el jugador que se busca en el ranking no se ha encontrado en éste
 * @author Unai P. Mendizabal (@unaipme)
 *
 */
public class PlayerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlayerNotFoundException(String s) {
		super(s);
	}

}
