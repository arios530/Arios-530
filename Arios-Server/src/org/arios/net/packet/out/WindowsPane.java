package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.WindowsPaneContext;

/**
 * Handles the windows pane outgoing packet.
 * @author Emperor
 */
public final class WindowsPane implements OutgoingPacket<WindowsPaneContext> {

	@Override
	public void send(WindowsPaneContext context) {
		IoBuffer buffer = new IoBuffer(145);
		buffer.putLEShortA(context.getWindowId());
		//Why is it putS?! It should almost definitely be putByteA...
		//buffer.putS(context.getType());
		buffer.putByteA(context.getType());
		buffer.putLEShortA(context.getPlayer().getInterfaceManager().getPacketCount(1));
		context.getPlayer().getDetails().getSession().write(buffer);
	}

}