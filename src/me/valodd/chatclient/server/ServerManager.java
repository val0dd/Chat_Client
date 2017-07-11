package me.valodd.chatclient.server;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {
	public static final int PORT = 42157;
	private static List<Server> servers = new ArrayList<>();
	private static Server activeServer = null;

	public static void addServer(Server s) {
		servers.add(s);
	}

	public static void removeServer(Server s) {
		servers.remove(s);
		if (activeServer == s)
			activeServer = null;
	}

	public static List<Server> getServers() {
		return servers;
	}

	public static Server getActiveServer() {
		return activeServer;
	}

	public static void setActiveServer(Server activeServer) {
		ServerManager.activeServer = activeServer;
	}
}
