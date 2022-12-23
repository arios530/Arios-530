package org.arios.net.packet.out;

import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.PacketHeader;
import org.arios.net.packet.context.AreaPositionContext;

/**
 * Handles the update area position packet.
 * @author Emperor
 */
public final class UpdateAreaPosition implements OutgoingPacket<AreaPositionContext> {

	/**
	 * Gets the region chunk update buffer.
	 * @param player The player.
	 * @param base The base location of the chunk.
	 * @return The buffer.
	 */
	public static IoBuffer getChunkUpdateBuffer(Player player, Location base) {
		int x = base.getSceneX(player.getPlayerFlags().getLastSceneGraph());
		int y = base.getSceneY(player.getPlayerFlags().getLastSceneGraph());
		return new IoBuffer(230, PacketHeader.SHORT).putA(y).putS(x);
	}

	/**
	 * Gets the region chunk update buffer.
	 * @param player The player.
	 * @param base The base location of the chunk.
	 * @return The buffer.
	 */
	public static IoBuffer getBuffer(Player player, Location base) {
		int x = base.getSceneX(player.getPlayerFlags().getLastSceneGraph());
		int y = base.getSceneY(player.getPlayerFlags().getLastSceneGraph());
		return new IoBuffer(26).putC(x).put(y);
	}

	@Override
	public void send(AreaPositionContext context) {
		context.getPlayer().getSession().write(getBuffer(context.getPlayer(), context.getLocation()));
	}

}