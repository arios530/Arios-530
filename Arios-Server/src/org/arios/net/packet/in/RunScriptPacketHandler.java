package org.arios.net.packet.in;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.RunScript;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;
import org.arios.tools.StringUtils;

/**
 * Represents the incoming packet to handle a run script.
 * @author 'Vexia
 */
public class RunScriptPacketHandler implements IncomingPacket {

	@Override
	public void decode(Player player, int opcode, IoBuffer buffer) {
		final RunScript script = player.getAttribute("runscript", null);
		if (script == null || player.getLocks().isInteractionLocked()) {
			return;
		}
		Object value = "";
		if (opcode == 244) {
			value = StringUtils.longToString(buffer.getLong());
		} else if (opcode == 23) {
			value = buffer.getInt();
		} else {
			value = buffer.getInt();
		}
		script.setValue(value);
		script.setPlayer(player);
		player.removeAttribute("runscript");
		script.handle();
	}
}
