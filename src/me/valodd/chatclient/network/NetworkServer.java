package me.valodd.chatclient.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.network.packet.boths.PacketConnection;
import me.valodd.chatclient.network.packet.in.PacketUserLogin;
import me.valodd.chatclient.server.Server;

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
		String str = bc.readString();
		if ("-VAL0DD-".equalsIgnoreCase(str)) {
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
		end = true;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startInputListening() {
		threadPacketListener = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!end) {
					try {
						int ch1 = socket.getInputStream().read();
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
							/*
							 * I DON'T KNOW WHY BUT THERE IS 2 MORE BYTES (0x0
							 * and 0x0) PLEASE SOMEONE EXPLAIN TO ME WHY DOES
							 * THIS HAPPENED ?
							 */
							socket.getInputStream().read();
							socket.getInputStream().read();
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
