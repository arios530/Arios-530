package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.component.Component;
import org.arios.game.content.global.travel.glider.Gliders;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for gliders.
 * @author 'Vexia
 * @version 1.0
 */
public final class GliderPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(3809).getConfigurations().put("option:glider", this);
		NPCDefinition.forId(3810).getConfigurations().put("option:glider", this);
		NPCDefinition.forId(3811).getConfigurations().put("option:glider", this);
		NPCDefinition.forId(3812).getConfigurations().put("option:glider", this);
		NPCDefinition.forId(3813).getConfigurations().put("option:glider", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		player.getInterfaceManager().open(new Component(138));
		Gliders.sendConfig(node.asNpc(), player);
		return true;
	}

}
