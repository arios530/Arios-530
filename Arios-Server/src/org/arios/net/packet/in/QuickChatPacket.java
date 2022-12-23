package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;

/**
 * Handles the quick chat packet.
 * @author Vexia
 *
 */
public class QuickChatPacket implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		player.sendMessage("" + buffer.readableBytes());
	}

}
