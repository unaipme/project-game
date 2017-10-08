package com.retrolaza.game.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Clase mediante la cual manejar archivos de música (.wav)
 * @author Unai P. Mendizabal (@unaipme) y Xabier Jauregi (@jaure96)
 *
 */
public class Music {
	
	private Clip clip;
	
	public Music(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
		this.clip = AudioSystem.getClip();
		clip.open(ais);
	}
	
	/**
	 * Método que configura la canción para repetirse en un bucle indefinido.
	 * @return El mismo objeto
	 */
	public Music unlimitedLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		return this;
	}
	
	/**
	 * Método que configura la canción para reproducirse una sola vez
	 * @return El mismo objeto
	 */
	public Music playOnce() {
		clip.loop(0);
		return this;
	}
	
	/**
	 * Método para detener la reproducción de la canción
	 */
	public void stop() {
		if (clip.isRunning()) clip.stop();
	}
	
	/**
	 * Método para comenzar la reproducción de la canción
	 */
	public void start() {
		if (!clip.isRunning()) clip.start();
	}
	
	/**
	 * Método para restablecer la canción al principio de ésta.
	 */
	public void reset() {
		clip.setMicrosecondPosition(0);
	}
	
}
