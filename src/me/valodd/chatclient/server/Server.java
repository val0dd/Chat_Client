package me.valodd.chatclient.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import me.valodd.chatclient.network.NetworkServer;
import me.valodd.chatclient.network.packet.boths.PacketConnection;

public class Server {
	private InetAddress ia;
	private int port;
	private NetworkServer ns;
	private String name;
	private boolean connected;

	public Server(InetAddress ia, int port) {
		this.ia = ia;
		this.port = port;
	}

	public boolean connect(String username, String password) {
		try {
			this.ns = new NetworkServer(new Socket(ia, port));
			ns.setServer(this);
			ns.sendPacket(new PacketConnection(this).setUsername(username).setPassword(password));
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public void disconnect() {
		ns.stop();
	}

	public NetworkServer getNetworkServer() {
		return ns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
