package org.arios.net.producer;

import java.nio.ByteBuffer;

import org.arios.net.EventProducer;
import org.arios.net.IoReadEvent;
import org.arios.net.IoSession;
import org.arios.net.IoWriteEvent;
import org.arios.net.event.LoginReadEvent;
import org.arios.net.event.LoginWriteEvent;

/**
 * Produces login I/O events.
 * @author Emperor
 */
public final class LoginEventProducer implements EventProducer {

	@Override
	public IoReadEvent produceReader(IoSession session, ByteBuffer buffer) {
		return new LoginReadEvent(session, buffer);
	}

	@Override
	public IoWriteEvent produceWriter(IoSession session, Object context) {
		return new LoginWriteEvent(session, context);
	}

}