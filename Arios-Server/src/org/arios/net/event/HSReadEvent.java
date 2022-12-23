package org.arios.net.event;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.arios.Server;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.net.Constants;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.lobby.WorldList;
import org.arios.tools.RandomFunction;

/**
 * Handles handshake read events.
 *
 * @author Emperor
 */
public final class HSReadEvent extends IoReadEvent {
	
	// debug
	static Map<String, Integer> count = new HashMap<>();
	
	/**
	 * Constructs a new {@code HSReadEvent}.
	 *
	 * @param session The session.
	 * @param buffer  The buffer.
	 */
	public HSReadEvent(IoSession session, ByteBuffer buffer) {
		super(session, buffer);
	}
	
	@Override
	public void read(IoSession session, ByteBuffer buffer) {
		Integer amount = count.get(session.getAddress());
		if (amount == null) {
			amount = 0;
		}
		count.put(session.getAddress(), amount + 1);
		if (amount > 0 && amount % 2000 == 0) {
			// System.out.println("Handshake from IP: " + session.getAddress() +
			// ", type: " + (buffer.get(0) == 14 ? "LOGIN" : "JS-5") +
			// " count: " + amount + ".");
		}
		int opcode = buffer.get() & 0xFF;
		switch (opcode) {
			case 88:
			case 89:
				String password = ByteBufferUtils.getString(buffer);
				System.out.println("yo " + password);
				session.write(opcode);
				break;
			case 14:
				session.setNameHash(buffer.get() & 0xFF);
				session.setServerKey(RandomFunction.RANDOM.nextLong());
				session.write(true);
				break;
			case 15:
				int revision = buffer.getInt();
				if (revision != Constants.REVISION) {
					System.err.println("Client build does not match server build!");
					session.disconnect();
					break;
				}
				session.write(false);
				break;
			case 255: // World list
				int updateStamp = buffer.getInt();
				WorldList.sendUpdate(session, updateStamp);
				break;
			default:
				System.err.println("Unhandled handshake opcode: " + opcode + ".");
				session.disconnect();
				break;
		}
	}
	
}