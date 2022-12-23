package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.global.action.PickupHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItem;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Represents the option handler used for ground items.
 * @author Vexia
 * @author Emperor
 */
public final class PickupPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.setOptionHandler("take", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		return PickupHandler.take(player, (GroundItem) node);
	}

	@Override
	public Location getDestination(Node node, Node item) {
		return null;
	}

}