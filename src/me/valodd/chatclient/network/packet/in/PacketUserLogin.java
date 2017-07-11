package me.valodd.chatclient.network.packet.in;

import me.valodd.chatclient.gui.GUIChatStyle;
import me.valodd.chatclient.gui.GUIManager;
import me.valodd.chatclient.network.BufferConnection;
import me.valodd.chatclient.network.Packet;
import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.server.Server;

public class PacketUserLogin extends Packet {
	private String pseudo;

	public PacketUserLogin(Server owner) {
		super(owner);
	}

	@Override
	public void readPacket(BufferConnection bc) {
		pseudo = bc.readString();
	}

	@Override
	public void writePacket(BufferConnection bc) {
		// NOTHING TO DO HERE BECAUSE IN PACKET
	}

	@Override
	public void executePacket() {
		GUIManager.getGUIClient().addText("(+) " + getPseudo(), GUIChatStyle.LOGIN, true);
	}

	@Override
	public PACKETS getPacketID() {
		return PACKETS.PACKETUSERLOGIN;
	}

	public String getPseudo() {
		return pseudo;
	}
}
