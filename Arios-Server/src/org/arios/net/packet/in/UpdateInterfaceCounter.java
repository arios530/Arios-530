package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;

/**
 * Handles the update interface packet counter packet.
 * @author Emperor
 *
 */
public final class UpdateInterfaceCounter implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		int count = buffer.getShort() - player.getInterfaceManager().getPacketCount(0);
		player.getInterfaceManager().getPacketCount(count);
	}

}