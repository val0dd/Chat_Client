package me.valodd.chatclient.server;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {
	private static List<Server> servers = new ArrayList<>();

	public static void addServer(Server s) {
		servers.add(s);
	}

	public static void removeServer(Server s) {
		servers.remove(s);
	}

	public static List<Server> getServers() {
		return servers;
	}
}
