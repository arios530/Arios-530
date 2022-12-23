package org.arios.net.producer;

import java.nio.ByteBuffer;

import org.arios.net.EventProducer;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.event.MSHSReadEvent;
import org.arios.net.event.MSHSWriteEvent;

/**
 * Handles the Management server handshake event producing.
 * @author Emperor
 */
public final class MSHSEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new MSHSReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new MSHSWriteEvent(session, context);
	}

}