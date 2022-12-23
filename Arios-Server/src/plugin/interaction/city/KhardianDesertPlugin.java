package plugin.interaction.city;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.DoorActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles interactions in the khardian desert.
 * @author 'Vexia
 * @version 1.0
 */
public final class KhardianDesertPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(6481).getConfigurations().put("option:enter", this);
		ObjectDefinition.forId(6545).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(6547).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(6551).getConfigurations().put("option:use", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (node.getId()) {
		case 6481:
			player.teleport(new Location(3233, 9313, 0));
			break;
		case 6545:
		case 6547:
			// player.getPacketDispatch().sendMessage("A mystical power has sealed this door...");
			DoorActionHandler.handleAutowalkDoor(player, node.asObject());
			break;
		case 6551:
			player.teleport(new Location(3233, 2887, 0));
			break;
		}
		return true;
	}

}
