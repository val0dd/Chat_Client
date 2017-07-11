package me.valodd.chatclient.gui;

import java.awt.Color;

public enum GUIChatStyle {
	USERNAME("USERNAME", "sans-serif", 12, 100.0f, Color.BLUE, null, true, true),
	MESSAGE("MESSAGE", "sans-serif", 12, 100.0f, Color.BLACK, null, false, false),
	SERVER("SERVER", "sans-serif", 12, 100.0f, Color.GREEN, null, true, true),
	INFO("INFO", "sans-serif", 12, 100.0f, Color.YELLOW, null, false, false),
	ERROR("ERROR", "sans-serif", 12, 100.0f, Color.RED, null, true, true);

	private String name;
	private String fontFamily;
	private int size;
	private float firstLineIndent;
	private Color foreground;
	private Color background;
	private boolean bold;
	private boolean underline;

	private GUIChatStyle(String name, String fontFamily, int size, float firstLineIndent, Color foreground,
			Color background, boolean bold, boolean underline) {
		this.name = name;
		this.fontFamily = fontFamily;
		this.size = size;
		this.firstLineIndent = firstLineIndent;
		this.foreground = foreground;
		this.background = background;
		this.bold = bold;
		this.underline = underline;
	}

	public String getName() {
		return name;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public int getSize() {
		return size;
	}

	public float getFirstLineIndent() {
		return firstLineIndent;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isUnderline() {
		return underline;
	}
}
