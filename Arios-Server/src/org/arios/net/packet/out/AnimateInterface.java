package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.AnimateInterfaceContext;

/**
 * The animate interface outgoing packet.
 * @author Emperor
 */
public class AnimateInterface implements OutgoingPacket<AnimateInterfaceContext> {

	@Override
	public void send(AnimateInterfaceContext context) {
		IoBuffer buffer = new IoBuffer(36);
		buffer.putIntB((context.getInterfaceId() << 16) + context.getChildId());
		buffer.putLEShort(context.getAnimationId());
		buffer.putShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
		context.getPlayer().getDetails().getSession().write(buffer);
	}
}