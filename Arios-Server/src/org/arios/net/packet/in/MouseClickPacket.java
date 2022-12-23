package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;

/**
 * Handles incoming mouse click packets.
 * @author Emperor
 */
public final class MouseClickPacket implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		int data = buffer.getLEShortA();
		int positioning = buffer.getIntB();
		boolean rightClick = ((data >> 15) & 0x1) == 1;
		int delay = data & 0x7FF;
		int x = positioning >> 16;
		int y = positioning & 0xFFFF;
		if (player == null) {
			return;
		}
		player.getMonitor().handleMouseClick(x, y, delay, rightClick);
	}

}