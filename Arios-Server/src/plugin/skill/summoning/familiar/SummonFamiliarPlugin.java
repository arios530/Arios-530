package plugin.skill.summoning.familiar;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Handles summoning a familiar.
 * @author Emperor
 */
public final class SummonFamiliarPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.setOptionHandler("summon", this);
		return null;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		Item item = (Item) node;
		if (!player.getQuestRepository().isComplete("Wolf Whistle") && player.getAttribute("in-cutscene", null) == null) {
			player.getPacketDispatch().sendMessage("You have to complete Wolf Whistle before you can summon a familiar.");
			return true;
		}
		player.getFamiliarManager().summon(item, false);
		return true;
	}

}
