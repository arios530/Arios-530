package org.arios.net.producer;

import java.nio.ByteBuffer;

import org.arios.net.EventProducer;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.event.HSReadEvent;
import org.arios.net.event.HSWriteEvent;

/**
 * Produces I/O events for the handshake protocol.
 * @author Emperor
 */
public final class HSEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new HSReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new HSWriteEvent(session, context);
	}

}