package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.PacketHeader;
import org.arios.net.packet.context.StringContext;

/**
 * The outgoing set component string packet.
 * @author Emperor
 */
public class StringPacket implements OutgoingPacket<StringContext> {

	@Override
	public void send(StringContext context) {
		IoBuffer buffer = new IoBuffer(171, PacketHeader.SHORT);
		buffer.putIntB((context.getInterfaceId() << 16) | context.getLineId());
		buffer.putString(context.getString());
		buffer.putShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
		context.getPlayer().getDetails().getSession().write(buffer);
	}
}
