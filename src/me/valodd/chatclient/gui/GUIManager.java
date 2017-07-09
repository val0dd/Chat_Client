package me.valodd.chatclient.gui;

public class GUIManager {
	private static GUIAbout guiAbout;
	private static GUIClient guiClient;

	public static void showGUIAbout() {
		if (guiAbout != null && guiAbout.isShowing()) {
			guiAbout.requestFocus();
		} else {
			guiAbout = new GUIAbout();
			guiAbout.setVisible(true);
		}
	}

	public static void showGUIClient() {
		if (guiClient != null && guiClient.isShowing()) {
			guiClient.requestFocus();
		} else {
			guiClient = new GUIClient();
			guiClient.setVisible(true);
		}
	}
}
