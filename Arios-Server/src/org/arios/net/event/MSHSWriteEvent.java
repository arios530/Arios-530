package org.arios.net.event;

import java.nio.ByteBuffer;

import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;

/**
 * Handles the management server handshake write event.
 * @author Emperor
 */
public final class MSHSWriteEvent extends IoWriteEvent {

	/**
	 * The password used to verify
	 */
	private static final String PASSWORD = "0x14ari0SSbh9898";

	/**
	 * Constructs a new {@code MSHSWriteEvent} {@code Object}
	 * @param session The session.
	 * @param context The context.
	 */
	public MSHSWriteEvent(IoSession session, Object context) {
		super(session, context);
	}

	@Override
	public void write(IoSession session, Object context) {
		ByteBuffer buffer = ByteBuffer.allocate(2 + PASSWORD.length());
		buffer.put((byte) 88);
		ByteBufferUtils.putString(PASSWORD, buffer);
		session.queue((ByteBuffer) buffer.flip());
	}

}