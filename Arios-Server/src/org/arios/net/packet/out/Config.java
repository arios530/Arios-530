package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.ConfigContext;

/**
 * The config outgoing packet.
 * @author Emperor
 */
public class Config implements OutgoingPacket<ConfigContext> {

	@Override
	public void send(ConfigContext context) {
		IoBuffer buffer;
		if (context.getValue() < Byte.MIN_VALUE || context.getValue() > Byte.MAX_VALUE) {
			buffer = new IoBuffer(226);
			buffer.putInt(context.getValue());
			buffer.putShortA(context.getId());
		} else {
			buffer = new IoBuffer(60);
			buffer.putShortA(context.getId());
			buffer.putC(context.getValue());
		}
		context.getPlayer().getDetails().getSession().write(buffer);
	}
}