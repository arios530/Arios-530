package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.component.Component;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Handles the spining interface open.
 * @author Adam
 */
public class SpinSpinningWheelPlugin extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		player.getInterfaceManager().open(new Component(459));
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(2644).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(4309).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(8748).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(20365).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(21304).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(25824).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(26143).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(34497).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(36970).getConfigurations().put("option:spin", this);
		ObjectDefinition.forId(37476).getConfigurations().put("option:spin", this);
		return this;
	}

	@Override
	public Location getDestination(Node node, Node n) {
		return null;
	}

}
