package me.valodd.chatclient.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.valodd.chatclient.server.Server;
import me.valodd.chatclient.server.ServerManager;

public class Test {

	public Test() {
		testServerConnection();
	}

	private void testServerConnection() {
		try {
			Server s = new Server(InetAddress.getByName("127.0.0.1"), 25565);
			ServerManager.addServer(s);
			s.connect("0ddlyoko");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}
}
