package me.valodd.chatclient.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.valodd.chatclient.network.NetworkManager;

public class Test {

	public Test() {
		testServerConnection();
	}

	private void testServerConnection() {
		try {
			NetworkManager nm = new NetworkManager(InetAddress.getByName("127.0.0.1"), 25565);
			nm.joinServer("0ddlyoko");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}
}
