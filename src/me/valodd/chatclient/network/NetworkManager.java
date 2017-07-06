package me.valodd.chatclient.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import me.valodd.chatclient.network.packet.boths.PacketConnection;

public class NetworkManager {
	private InetAddress inetAddress;
	private int port;
	private Socket socket;
	private Thread threadPacketListener;
	private boolean end = false;

	public NetworkManager(InetAddress ia, int port) {
		this.inetAddress = ia;
		this.port = port;
		connect();
	}

	public void connect() {
		try {
			socket = new Socket(inetAddress.getHostAddress(), port);
			startInputListening();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void joinServer() {

	}

	public void disconnect() {
		try {
			end = true;
			threadPacketListener.interrupt();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(Packet packet) {
		_sendPacket(packet.write().getBufferConnection());
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
		if (!"-VAL0DD-".equalsIgnoreCase(bc.readString())) {
			int packetID = bc.readInt();
			switch (packetID) {
			case 1: // PacketConnection
				PacketConnection packet = new PacketConnection();
				packet.read();

				break;
			default:
				break;
			}
		}
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
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadPacketListener.start();
	}
}
