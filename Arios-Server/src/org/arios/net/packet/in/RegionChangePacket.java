package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;

/**
 * Packet received when a player's region has changed.
 * @author Emperor
 * @author 'Vexia
 */
public class RegionChangePacket implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		//TODO: no data is sen't so not sure what to do.
	}

}
