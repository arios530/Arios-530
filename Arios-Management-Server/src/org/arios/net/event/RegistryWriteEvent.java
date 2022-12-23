package org.arios.net.event;

import java.nio.ByteBuffer;

import org.arios.net.EventProducer;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.producer.PacketEventProducer;

/**
 * Handles game world registry writing events.
 * @author Emperor
 *
 */
public final class RegistryWriteEvent extends IoWriteEvent {

	/**
	 * The event producer.
	 */
	public static final EventProducer PRODUCER = new PacketEventProducer();
	
	/**
	 * Constructs a new {@code RegistryWriteEvent} {@code Object}.
	 * @param session The I/O session.
	 * @param context The writing context.
	 */
	public RegistryWriteEvent(IoSession session, Object context) {
		super(session, context);
	}

	@Override
	public void write(IoSession session, Object context) {
		ByteBuffer buffer = ByteBuffer.allocate(1);
		int opcode = (int) context;
		buffer.put((byte) opcode);
		buffer.flip();
		if (opcode == 1) {
			session.setProducer(PRODUCER);
		}
		session.queue(buffer);
	}

}