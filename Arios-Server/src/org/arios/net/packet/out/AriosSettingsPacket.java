package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.PacketHeader;
import org.arios.net.packet.context.DefaultContext;
import org.arios.net.packet.context.DisplayModelContext;

/**
 * Various settings to be sent to the client.
 * AriosSettingsPacket.java
 * @author Emperor
 */
public final class AriosSettingsPacket implements OutgoingPacket<DefaultContext> {

	@Override
	public void send(DefaultContext context) {
		IoBuffer buffer;
        buffer = new IoBuffer(6);
        boolean zoomed = (boolean) context.getObjects()[0];
        buffer.put(zoomed ? 1 : 0);
        buffer.putInt(context.getPlayer().getGlobalData().getZoom());
        //System.out.println("Sending zoom " + context.getPlayer().getGlobalData().getZoom() + " to client.");
		context.getPlayer().getSession().write(buffer);
	}

}