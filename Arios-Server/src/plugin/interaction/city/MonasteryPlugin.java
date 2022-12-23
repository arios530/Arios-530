package plugin.interaction.city;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.ClimbActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles the edgeville monastery.
 * @author Vexia
 */
public final class MonasteryPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(2641).getConfigurations().put("option:climb-up", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (node.getId()) {
		case 2641:
			final boolean abbot = node.getLocation().equals(new Location(3057, 3483, 0));
			if (!player.getSavedData().getGlobalData().isJoinedMonastery()) {
				player.getDialogueInterpreter().open(abbot ? 801 : 7727, true);
			} else {
				ClimbActionHandler.climbLadder(player, (GameObject) node, option);
			}
			break;
		}
		return true;
	}

}
