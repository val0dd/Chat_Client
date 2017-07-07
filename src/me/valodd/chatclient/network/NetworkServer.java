package me.valodd.chatclient.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.network.packet.boths.PacketConnection;
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
		_sendPacket(new BufferConnection(packet.getBufferConnection().getSizeBuff() + 4)
				.writeInt(packet.getBufferConnection().getSizeBuff()).writeBytes(packet.getBufferConnection()));
	}

	private void _sendPacket(BufferConnection bc) {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.write(bc.getAllBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void onPacketReceive(BufferConnection bc) {
		if ("-VAL0DD-".equalsIgnoreCase(bc.readString())) {
			int packetID = bc.readInt();
			PACKETS packetsID = PACKETS.getByID(packetID);
			switch (packetsID) {
			case PACKETCONNECTION: // PacketConnection
				PacketConnection packet = new PacketConnection(s);
				packet.read(bc);
				packet.executePacket();
				break;
			default:
				break;
			}
		}
	}

	private void stop() {
		end = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerManager.removeServer(getServer());
	}

	private void startInputListening() {
		threadPacketListener = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!end) {
					try {
						DataInputStream dis = new DataInputStream(socket.getInputStream());
						int length = dis.readInt();
						if (length > 0) {
							BufferConnection bc = new BufferConnection(length);
							byte[] message = new byte[length];
							dis.readFully(message, 0, message.length);
							bc.writeBytes(message);
							new Thread(new Runnable() {

								@Override
								public void run() {
									onPacketReceive(bc);
								}
							}).start();
						}
					} catch (IOException e) { // DISCONNECTED
						stop();
						e.printStackTrace();
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
