package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.ClimbActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for managing ladders.
 * @author Emperor
 * @version 2.0
 */
public final class LadderManagingPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.setOptionHandler("climb-up", this);
		ObjectDefinition.setOptionHandler("climb-down", this);
		ObjectDefinition.setOptionHandler("climb", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, final String option) {
		ClimbActionHandler.climbLadder(player, (GameObject) node, option);
		return true;
	}

	@Override
	public Location getDestination(Node n, Node object) {
		return ClimbActionHandler.getDestination((GameObject) object);
	}

}