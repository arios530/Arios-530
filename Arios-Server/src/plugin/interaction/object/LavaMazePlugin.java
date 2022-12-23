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
 * Handles the lava maze.
 * @author Vexia
 */
public final class LavaMazePlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(1767).getConfigurations().put("option:climb-down", this);
		ObjectDefinition.forId(1768).getConfigurations().put("option:climb-up", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (node.getId()) {
		case 1767:
			if (node.getLocation().getX() == 3069) {
				ClimbActionHandler.climb(player, null, Location.create(3017, 10248, 0));
				return true;
			}
			ClimbActionHandler.climbLadder(player, (GameObject) node, option);
			return true;
		case 1768:
			if (node.getLocation().getX() == 3017) {
				ClimbActionHandler.climb(player, null, Location.create(3069, 3857, 0));
				return true;
			}
			ClimbActionHandler.climbLadder(player, (GameObject) node, option);
			return true;
		}
		return true;
	}

}
