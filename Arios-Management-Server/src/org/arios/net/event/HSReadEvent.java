package org.arios.net.event;

import java.nio.ByteBuffer;

import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.system.util.ByteBufferUtils;
import org.arios.world.WorldDatabase;

/**
 * Handles handshake read events.
 * @author Emperor
 */
public final class HSReadEvent extends IoReadEvent {

	/**
	 * The password used to verify
	 */
	private static final String PASSWORD = "0x14ari0SSbh9898";
	
	/**
	 * Constructs a new {@code HSReadEvent}.
	 * @param session The session.
	 * @param buffer The buffer.
	 */
	public HSReadEvent(IoSession session, ByteBuffer buffer) {
		super(session, buffer);
	}

	@Override
	public void read(IoSession session, ByteBuffer buffer) {
		int opcode = buffer.get() & 0xFF;
		switch (opcode) {
		case 88:
			String password = ByteBufferUtils.getString(buffer);
			if (!password.equals(PASSWORD)) {
				System.out.println("Password mismatch (attempt=" + password + ")!");
				session.disconnect();
				break;
			}
			session.write(opcode);
			break;
		case 255: // World list
			int updateStamp = buffer.getInt();
			WorldDatabase.sendUpdate(session, updateStamp);
			break;
		default:
			System.err.println("Unhandled handshake opcode: " + opcode + ".");
			session.disconnect();
			break;
		}
	}
	
}