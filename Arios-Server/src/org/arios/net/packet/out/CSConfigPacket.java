package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.CSConfigContext;
import org.arios.net.packet.context.InterfaceConfigContext;

/**
 * The outgoing interface configuration packet.
 * @author Emperor
 */
public class CSConfigPacket implements OutgoingPacket<CSConfigContext> {

	@Override
	public void send(CSConfigContext context) {
		IoBuffer buffer = new IoBuffer(65);
		buffer.putLEShort(context.getPlayer().getInterfaceManager().getPacketCount(1));
        buffer.putByteC((byte) context.getValue());
        buffer.putLEShortA(context.getId());
		context.getPlayer().getSession().write(buffer);
	}
}
