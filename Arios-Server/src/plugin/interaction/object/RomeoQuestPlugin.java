package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the plguin used to handle cadava berries.
 * @author 'Vexia
 */
public class RomeoQuestPlugin extends OptionHandler {

	/**
	 * Represents the cadava berries.
	 */
	private final Item CADAVA_BERRIES = new Item(753);

	/**
	 * Represents the picking berries animation.
	 */
	private final Animation ANIMATION = new Animation(2282);

	/**
	 * Represents the counter.
	 */
	private int counter = 0;

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(23625).getConfigurations().put("option:pick-from", this);
		ObjectDefinition.forId(23626).getConfigurations().put("option:pick-from", this);
		ObjectDefinition.forId(23627).getConfigurations().put("option:pick-from", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (((GameObject) node).getId() == 23627) {
			player.getPacketDispatch().sendMessage("There are no berries left on this bush.");
			player.getPacketDispatch().sendMessage("More berries will grow soon.");
			return true;
		}
		if (!player.getInventory().add(CADAVA_BERRIES)) {
			player.getPacketDispatch().sendMessage("Your inventory is too full to pick the berries from the bush.");
			return true;
		}
		player.animate(ANIMATION);
		if (counter == 2) {
			ObjectBuilder.replace(((GameObject) node), new GameObject(23627, node.getLocation()), 30);
			counter = 0;
			return true;
		}
		counter++;
		return true;
	}

}
