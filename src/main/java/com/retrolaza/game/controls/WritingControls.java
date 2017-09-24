package com.retrolaza.game.controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class WritingControls implements KeyListener {
	
	private String writtenText;
	private int charLimit;
	
	private Consumer<String> consumer;
	
	public WritingControls(Consumer<String> consumer, int charLimit) {
		this.writtenText = "";
		this.charLimit = charLimit;
		this.consumer = consumer;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			add("A");
			break;
		case KeyEvent.VK_B:
			add("B");
			break;
		case KeyEvent.VK_C:
			add("C");
			break;
		case KeyEvent.VK_D:
			add("D");
			break;
		case KeyEvent.VK_E:
			add("E");
			break;
		case KeyEvent.VK_F:
			add("F");
			break;
		case KeyEvent.VK_G:
			add("G");
			break;
		case KeyEvent.VK_H:
			add("H");
			break;
		case KeyEvent.VK_I:
			add("I");
			break;
		case KeyEvent.VK_J:
			add("J");
			break;
		case KeyEvent.VK_K:
			add("K");
			break;
		case KeyEvent.VK_L:
			add("L");
			break;
		case KeyEvent.VK_M:
			add("M");
			break;
		case KeyEvent.VK_N:
			add("N");
			break;
		case KeyEvent.VK_O:
			add("O");
			break;
		case KeyEvent.VK_P:
			add("P");
			break;
		case KeyEvent.VK_Q:
			add("Q");
			break;
		case KeyEvent.VK_R:
			add("R");
			break;
		case KeyEvent.VK_S:
			add("S");
			break;
		case KeyEvent.VK_T:
			add("T");
			break;
		case KeyEvent.VK_U:
			add("U");
			break;
		case KeyEvent.VK_V:
			add("V");
			break;
		case KeyEvent.VK_W:
			add("W");
			break;
		case KeyEvent.VK_X:
			add("X");
			break;
		case KeyEvent.VK_Y:
			add("Y");
			break;
		case KeyEvent.VK_Z:
			add("Z");
			break;
		case KeyEvent.VK_SPACE:
			add(" ");
			break;
		case KeyEvent.VK_BACK_SPACE:
			writtenText = writtenText.substring(0, writtenText.length() - 1);
			break;
		}
		consumer.accept(writtenText);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}
	
	private void add(String s) {
		if (writtenText.length() == charLimit) writtenText = writtenText.substring(0, writtenText.length() - 1);
		writtenText = writtenText.concat(s.toUpperCase());
	}
	
	public String getText() {
		return writtenText;
	}

}
