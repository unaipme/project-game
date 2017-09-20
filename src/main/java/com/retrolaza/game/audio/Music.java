package com.retrolaza.game.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	
	private Clip clip;
	
	public Music(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
		this.clip = AudioSystem.getClip();
		clip.open(ais);
	}
	
	public void unlimitedLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void playOnce() {
		clip.loop(0);
	}
	
	public void stop() {
		if (clip.isRunning()) clip.stop();
	}
	
	public void start() {
		if (!clip.isRunning()) clip.start();
	}
	
}
