package org.arios.net.event;

import java.nio.ByteBuffer;

import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.packet.IoBuffer;

/**
 * Handles management server write events.
 * @author Emperor
 */
public final class MSWriteEvent extends IoWriteEvent {

	/**
	 * Constructs a new {@code MSWriteEvent} {@code Object}
	 * @param session The I/O session.
	 * @param context The packet context.
	 */
	public MSWriteEvent(IoSession session, Object context) {
		super(session, context);
	}

	@Override
	public void write(IoSession session, Object context) {
		IoBuffer b = (IoBuffer) context;
		int size = b.toByteBuffer().position();
		ByteBuffer buffer = ByteBuffer.allocate(1 + size + b.getHeader().ordinal());
		buffer.put((byte) b.opcode());
		switch (b.getHeader()) {
		case NORMAL:
			break;
		case BYTE:
			buffer.put((byte) size);
			break;
		case SHORT:
			buffer.putShort((short) size);
			break;
		}
		buffer.put((ByteBuffer) b.toByteBuffer().flip());
		session.queue((ByteBuffer) buffer.flip());
	}

}