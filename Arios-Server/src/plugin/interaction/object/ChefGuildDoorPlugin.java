package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.DoorActionHandler;
import org.arios.game.content.skill.Skills;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.map.Location;
import org.arios.plugin.Plugin;

/**
 * Represents the chef guild door plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class ChefGuildDoorPlugin extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (player.getSkills().getLevel(Skills.COOKING) < 32) {
			player.getPacketDispatch().sendMessage("You need a cooking level of 32 to enter the guild.");
			return true;
		}
		final GameObject object = (GameObject) node;
		if (player.getSkills().getStaticLevel(Skills.COOKING) == 99 && (player.getEquipment().contains(9801, 1) || player.getEquipment().contains(9802, 1)) && player.getLocation().getY() <= 3443) {
			DoorActionHandler.handleAutowalkDoor(player, object);
			return true;
		}
		if (!player.getEquipment().contains(1949, 1) && player.getLocation().getY() <= 3443) {
			player.getDialogueInterpreter().open(847, null, true);
		} else {
			DoorActionHandler.handleAutowalkDoor(player, object);
		}
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(2712).getConfigurations().put("option:open", this);
		return this;
	}

	@Override
	public Location getDestination(Node node, Node n) {
		return DoorActionHandler.getDestination(((Player) node), ((GameObject) n));
	}
}
