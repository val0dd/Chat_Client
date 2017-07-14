package me.valodd.chatclient.network.packet.in;

import me.valodd.chatclient.gui.GUIChatStyle;
import me.valodd.chatclient.gui.GUIManager;
import me.valodd.chatclient.network.BufferConnection;
import me.valodd.chatclient.network.Packet;
import me.valodd.chatclient.network.packet.PACKETS;
import me.valodd.chatclient.server.Server;

public class PacketUserLogout extends Packet {
	private String pseudo;

	public PacketUserLogout(Server owner) {
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
		GUIManager.getGUIClient().addText("(-) " + getPseudo(), GUIChatStyle.LOGOUT, true);
	}

	@Override
	public PACKETS getPacketID() {
		return PACKETS.PACKETUSERLOGOUT;
	}

	public String getPseudo() {
		return pseudo;
	}
}
