package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.mysql.impl.ShopSQLHandler;
import org.arios.plugin.Plugin;

/**
 * Represents the buy crate option plugin for the seers village city.
 * @author 'Vexia
 * @version 1.0
 */
public final class BuyCrateOptionPlugin extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		ShopSQLHandler.openUid(player, 93);
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(6839).getConfigurations().put("option:buy", this);
		return this;
	}

}
