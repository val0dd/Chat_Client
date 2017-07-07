package me.valodd.chatclient.network.packet.boths;

import me.valodd.chatclient.network.BufferConnection;
import me.valodd.chatclient.network.Packet;
import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.server.Server;

public class PacketConnection extends Packet {
	private String username;
	private int nbClients;
	private String clients;

	public PacketConnection(Server server) {
		super(server);
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
	public void executePacket() {
		System.out.println("RESPONSE FROM SERVER:");
		System.out.println("Username: " + getUsername());
		System.out.println("Number of Clients:" + getNbClients());
		System.out.println("Clients: " + getClients());

	}

	@Override
	public PACKETS getPacketID() {
		return PACKETS.PACKETCONNECTION;
	}

	public PacketConnection setUsername(String username) {
		this.username = username;
		return this;
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
