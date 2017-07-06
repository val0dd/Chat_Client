package me.valodd.chatclient.network;

/**
 * This class will be extended by all Class Packet to send packet over the
 * Internet.<br />
 * <br />
 * Created: 06 juil. 2017 (16:22:31);<br />
 * Last edit: 06 juil. 2017 (16:22:31);<br />
 * 
 * @author 0ddlyoko<br />
 *         <br />
 */
public abstract class Packet implements IPacket {
	private BufferConnection bc;

	public Packet() {
		bc = new BufferConnection(32);
	}

	public final Packet read() {
		readPacket(bc);
		return this;
	}

	public final Packet write() {
		writePacket(bc);
		return this;
	}

	public BufferConnection getBufferConnection() {
		return bc;
	}
}
