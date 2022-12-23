package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.component.Component;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Handles the new comper map.
 * @author 'Vexia
 */
public class NewComerMapPlugin extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		player.getInterfaceManager().open(new Component(270));
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(550).getConfigurations().put("option:read", this);
		return this;
	}

}
