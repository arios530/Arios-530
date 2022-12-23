package plugin.interaction.city;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.TeleportManager.TeleportType;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * The plugin used to handle the interactions on Lunar Isle (Moonclan island).
 * @author Splinter
 * @version 1.0
 */
public final class LunarIslePlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(16777).getConfigurations().put("option:close", this);
		ObjectDefinition.forId(16774).getConfigurations().put("option:open", this);
		NPCDefinition.forId(4512).getConfigurations().put("option:go-inside", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final int id = node.getId();
		switch (id) {
		case 16777:
		case 16774:
			player.getTeleporter().send(Location.create(2101, 3926, 0), TeleportType.FAIRY_RING);
			break;
		case 4512:
			player.getTeleporter().send(Location.create(2451, 4645, 0), TeleportType.FAIRY_RING);
			break;
		}
		return true;
	}
}
