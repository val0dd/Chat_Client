package me.valodd.chatclient.gui;

public class GUIManager {
	private static GUIAbout guiAbout;
	private static GUIClient guiClient;
	private static GUIConnection guiConnect;

	public static void showGUIAbout() {
		if (guiAbout != null && guiAbout.isShowing()) {
			guiAbout.requestFocus();
		} else {
			guiAbout = new GUIAbout();
			guiAbout.setVisible(true);
		}
	}

	public static GUIAbout getGUIAbout() {
		return guiAbout;
	}

	public static void showGUIClient() {
		if (guiClient != null && guiClient.isShowing()) {
			guiClient.requestFocus();
		} else {
			guiClient = new GUIClient();
			guiClient.setVisible(true);
		}
	}

	public static GUIClient getGUIClient() {
		return guiClient;
	}

	public static void showGUIConnect() {
		if (guiConnect != null && guiConnect.isShowing()) {
			guiConnect.requestFocus();
		} else {
			guiConnect = new GUIConnection();
			guiConnect.setVisible(true);
		}
	}

	public GUIConnection getGUIConnect() {
		return guiConnect;
	}
}
