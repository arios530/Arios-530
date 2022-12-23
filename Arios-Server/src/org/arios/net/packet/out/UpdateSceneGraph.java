package org.arios.net.packet.out;

import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.PacketHeader;
import org.arios.net.packet.context.SceneGraphContext;
import org.arios.parser.misc.XTEAKeyParser;

/**
 * The update scene graph outgoing packet.
 * @author Emperor
 */
public final class UpdateSceneGraph implements OutgoingPacket<SceneGraphContext> {

	@Override
	public void send(SceneGraphContext context) {
		IoBuffer buffer = new IoBuffer(162, PacketHeader.SHORT);
		Player player = context.getPlayer();
		player.getPlayerFlags().setLastSceneGraph(player.getLocation());
		buffer.putShortA(player.getLocation().getSceneX());
		for (int regionX = (player.getLocation().getRegionX() - 6) / 8; regionX <= ((player.getLocation().getRegionX() + 6) / 8); regionX++) {
			for (int regionY = (player.getLocation().getRegionY() - 6) / 8; regionY <= ((player.getLocation().getRegionY() + 6) / 8); regionY++) {
				int[] keys = XTEAKeyParser.getRegionXTEA(regionX << 8 | regionY);
				for (int i = 0; i < keys.length; i++) {
					buffer.putIntB(keys[i]);
				}
			}
		}

		buffer.putS(player.getLocation().getZ());
		buffer.putShort(player.getLocation().getRegionX());
		buffer.putShortA(player.getLocation().getRegionY());
		buffer.putShortA(player.getLocation().getSceneY());
		player.getDetails().getSession().write(buffer);
	}

}