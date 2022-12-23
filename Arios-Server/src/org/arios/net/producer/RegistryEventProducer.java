package org.arios.net.producer;

import java.nio.ByteBuffer;

import org.arios.net.EventProducer;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.event.RegistryReadEvent;
import org.arios.net.event.RegistryWriteEvent;

/**
 * Handles world server registry.
 * @author Emperor
 */
public final class RegistryEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new RegistryReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new RegistryWriteEvent(session, context);
	}

}