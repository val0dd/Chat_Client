package me.valodd.chatclient.network;

/**
 * This interface is used for all Packet types.<br />
 * <br />
 * Created: 05 juil. 2017 (20:37:18);<br />
 * Last edit: 05 juil. 2017 (20:37:18);<br />
 * 
 * @author 0ddlyoko<br />
 *         <br />
 */
public interface IPacket {
	/**
	 * Read packet
	 * 
	 * @param bc
	 *            Specific packet
	 */
	void readPacket(BufferConnection bc);

	/**
	 * Write packet
	 * 
	 * @param bc
	 *            Specific packet
	 */
	void writePacket(BufferConnection bc);
}
