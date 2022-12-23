package plugin.activity.pestcontrol.reward;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManager;

/**
 * Represents the option plugin used to handle the pc island related nodes.
 * @author 'Vexia
 */
public final class PCIslandOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (int id : new int[] { 3786, 3788, 3789, 5956 }) {
			NPCDefinition.forId(id).getConfigurations().put("option:exchange", this);
		}
		PluginManager.definePlugin(new PCRewardInterface());
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (option) {
		case "exchange":
			if (node.getLocation().getX() == 3097 && !GameWorld.isEconomyWorld()) {
				node.asNpc().openShop(player);
				return true;
			}
			PCRewardInterface.open(player);
			break;
		}
		return true;
	}

}