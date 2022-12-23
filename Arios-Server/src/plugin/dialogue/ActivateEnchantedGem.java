package plugin.dialogue;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the enchanted gem related to slayer.
 * @author 'Vexia
 * @version 1.0
 */
public final class ActivateEnchantedGem extends OptionHandler {

	/**
	 * Constructs a new {@code ActivateEnchantedGem} {@Code Object}.
	 */
	public ActivateEnchantedGem() {
		/**
		 * empty.
		 */
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (!player.getSlayer().hasStarted()) {
			player.getPacketDispatch().sendMessage("You try to activate the gem...");
			return true;
		}
		player.getDialogueInterpreter().open(77777);
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(4155).getConfigurations().put("option:activate", this);
		return this;
	}
}
