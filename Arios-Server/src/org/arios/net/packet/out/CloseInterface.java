package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.InterfaceContext;

/**
 * Represents the outgoing packet used for closing an interface.
 * @author Emperor
 */
public final class CloseInterface implements OutgoingPacket<InterfaceContext> {

	@Override
	public void send(InterfaceContext context) {
		IoBuffer buffer = new IoBuffer(149);
		buffer.putShort(context.getPlayer().getInterfaceManager().getPacketCount(1));
		buffer.putShort(context.getWindowId());
		buffer.putShort(context.getComponentId());
		context.getPlayer().getSession().write(buffer);
	}

}