package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.global.travel.ship.ShipCharter;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the "charter" option.
 * @author 'Vexia
 * @version 1.0
 */
public final class ShipCharterNPCPlugin extends OptionHandler {

	/**
	 * Represents the ship charter npcs.
	 */
	private static final int[] IDS = new int[] { 4650, 4651, 4652, 4653, 4654, 4655, 4656 };

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (int id : IDS) {
			NPCDefinition.forId(id).getConfigurations().put("option:charter", this);
		}
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		ShipCharter.open(player);
		return true;
	}

}
