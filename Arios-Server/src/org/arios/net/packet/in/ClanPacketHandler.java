package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.game.system.communication.ClanRank;
import org.arios.game.system.communication.ClanRepository;
import org.arios.game.system.communication.CommunicationInfo;
import org.arios.game.world.repository.Repository;
import org.arios.net.amsc.MSPacketRepository;
import org.arios.net.amsc.WorldCommunicator;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;
import org.arios.tools.StringUtils;

/**
 * Handles incoming clan packets.
 * @author Emperor
 */
public class ClanPacketHandler implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		switch (buffer.opcode()) {
		case 104:
			long nameLong = buffer.getLong();
			String name = StringUtils.longToString(nameLong);
			if (nameLong != 0l) {
				player.getPacketDispatch().sendMessage("Attempting to join channel...:clan:");
			}
			if (WorldCommunicator.isEnabled()) {
				MSPacketRepository.sendJoinClan(player, name);
				return;
			}
			if (player.getCommunication().getClan() != null) {
				player.getCommunication().getClan().leave(player, true);
				player.getCommunication().setClan(null);
				return;
			}
			ClanRepository clan = ClanRepository.get(name);
			if (clan == null || clan.getName().equals("Chat disabled")) {
				player.getPacketDispatch().sendMessage("The channel you tried to join does not exist.");
				break;
			}
			if (clan.enter(player)) {
				player.getCommunication().setClan(clan);
			}
			break;
		case 188:
			int rank = buffer.getA();
			name = StringUtils.longToString(buffer.getLong());
			if (WorldCommunicator.isEnabled()) {
				MSPacketRepository.sendContactUpdate(player.getName(), name, false, false, ClanRank.values()[rank + 1]);
				break;
			}
			CommunicationInfo.updateClanRank(player, name, ClanRank.values()[rank + 1]);
			break;
		case 162:
			name = StringUtils.longToString(buffer.getLong());
			if (WorldCommunicator.isEnabled()) {
				MSPacketRepository.sendClanKick(player.getName(), name);
				break;
			}
			clan = player.getCommunication().getClan();
			Player target = Repository.getPlayerByDisplay(name);
			if (clan == null || target == null) {
				break;
			}
			clan.kick(player, target);
			break;
		}
	}

}