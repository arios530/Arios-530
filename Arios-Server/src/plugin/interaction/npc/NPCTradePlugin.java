package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.skill.free.crafting.TanningProduct;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for an npc with the trade option.
 * @author 'Vexia
 * @version 1.0
 */
public final class NPCTradePlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.setOptionHandler("trade", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final NPC npc = (NPC) node;
		if (npc.getId() == 2824) {
			TanningProduct.open(player, 2824);
			return true;
		}
		return node.asNpc().openShop(player);
	}

}
