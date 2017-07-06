package me.valodd.chatclient.network.packet.boths;

import me.valodd.chatclient.network.BufferConnection;
import me.valodd.chatclient.network.Packet;
import me.valodd.chatclient.network.packet.PACKETS;

public class PacketConnection extends Packet {
	private String username;
	private int nbClients;
	private String clients;

	public PacketConnection() {
		super();
	}

	@Override
	public void readPacket(BufferConnection bc) {
		username = bc.readString();
		nbClients = bc.readInt();
		clients = bc.readString();
	}

	@Override
	public void writePacket(BufferConnection bc) {
		bc.writeString(username);
	}

	@Override
	public PACKETS getPacketID() {
		return PACKETS.PACKETCONNECTION;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public int getNbClients() {
		return nbClients;
	}

	public String getClients() {
		return clients;
	}
}
