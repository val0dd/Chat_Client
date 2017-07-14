package me.valodd.chatclient.network;

import java.io.IOException;
import java.net.Socket;

import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.network.packet.boths.PacketConnection;
import me.valodd.chatclient.network.packet.boths.PacketMessage;
import me.valodd.chatclient.network.packet.in.PacketUserLogin;
import me.valodd.chatclient.network.packet.in.PacketUserLogout;
import me.valodd.chatclient.server.Server;
import me.valodd.chatclient.server.ServerManager;

public class NetworkServer {
	private Server s;
	private Socket socket;
	private Thread threadPacketListener;
	private boolean end = false;

	public NetworkServer(Socket socket) {
		this.socket = socket;
		startInputListening();
	}

	public void sendPacket(Packet packet) {
		packet.write();
		_sendPacket(packet.getBufferConnection());
	}

	private void _sendPacket(BufferConnection bc) {
		try {
			int size = bc.getSizeBuff();
			socket.getOutputStream().write(size >> 24);
			socket.getOutputStream().write(size >> 16);
			socket.getOutputStream().write(size >> 8);
			socket.getOutputStream().write(size >> 0);
			socket.getOutputStream().write(bc.getAllBytes());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void onPacketReceive(BufferConnection bc) {
		if ("-VAL0DD-".equalsIgnoreCase(bc.readString())) {
			int packetID = bc.readInt();
			PACKETS packetsID = PACKETS.getByID(packetID);
			Packet packet = null;
			switch (packetsID) {
			case PACKETCONNECTION: // PacketConnection
				packet = new PacketConnection(s);
				break;
			case PACKETUSERLOGIN: // PacketUserLogin
				packet = new PacketUserLogin(s);
				break;
			case PACKETMESSAGE: // PacketMessage
				packet = new PacketMessage(s);
				break;
			case PACKETUSERLOGOUT: // PacketUserLogout
				packet = new PacketUserLogout(s);
				break;
			default:
				break;
			}
			if (packet != null) {
				packet.read(bc);
				packet.executePacket();
			}
		}
	}

	public void stop() {
		if (!end) {
			end = true;
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			ServerManager.setActiveServer(null);
		}
	}

	private void startInputListening() {
		threadPacketListener = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!end) {
					try {
						int ch1 = socket.getInputStream().read();
						if (ch1 == -1) {
							stop();
						} else {
							int ch2 = socket.getInputStream().read();
							int ch3 = socket.getInputStream().read();
							int ch4 = socket.getInputStream().read();
							int length = (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
							if (length > 0) {
								BufferConnection bc = new BufferConnection(length);
								byte[] message = new byte[length];
								socket.getInputStream().read(message, 0, length);
								bc.writeBytes(message);
								new Thread(new Runnable() {

									@Override
									public void run() {
										onPacketReceive(bc);
									}
								}).start();
							}
						}
					} catch (IOException ex) { // DISCONNECTED
						stop();
						ex.printStackTrace();
					}
				}
			}
		});
		threadPacketListener.start();
	}

	public void setServer(Server s) {
		this.s = s;
	}

	public Server getServer() {
		return s;
	}
}
