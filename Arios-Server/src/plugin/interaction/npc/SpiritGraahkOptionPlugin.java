package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the spirit graahk familiar
 * @author Splinter
 */
public final class SpiritGraahkOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(7363).getConfigurations().put("option:interact", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		player.getDialogueInterpreter().open(7353, node.asNpc());
		return true;
	}

}
