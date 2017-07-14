package me.valodd.chatclient.network.packet.boths;

import me.valodd.chatclient.gui.GUIChatStyle;
import me.valodd.chatclient.gui.GUIManager;
import me.valodd.chatclient.network.BufferConnection;
import me.valodd.chatclient.network.Packet;
import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.server.Server;

public class PacketMessage extends Packet {
	private String pseudo;
	private String message;

	public PacketMessage(Server server) {
		super(server);
	}

	@Override
	public void readPacket(BufferConnection bc) {
		this.pseudo = bc.readString();
		this.message = bc.readString();
	}

	@Override
	public void writePacket(BufferConnection bc) {
		bc.writeString(message);
	}

	@Override
	public void executePacket() {
		GUIManager.getGUIClient().addText(getPseudo() + ": ", GUIChatStyle.USERNAME, true);
		GUIManager.getGUIClient().addText(getMessage(), GUIChatStyle.MESSAGE, false);
	}

	@Override
	public PACKETS getPacketID() {
		return PACKETS.PACKETMESSAGE;
	}

	public String getMessage() {
		return message;
	}

	public PacketMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getPseudo() {
		return pseudo;
	}
}
