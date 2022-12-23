package plugin.interaction.npc;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle the spirit kyatt familiar and the summoning area.
 * @author Splinter
 */
public final class SpiritKyattOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(7365).getConfigurations().put("option:interact", this);
		ObjectDefinition.forId(28741).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(28743).getConfigurations().put("option:climb-up", this);
		ObjectDefinition.forId(28743).getConfigurations().put("option:climb-up", this);
		ObjectDefinition.forId(14910).getConfigurations().put("option:take", this);
		ObjectDefinition.forId(14912).getConfigurations().put("option:take", this);
		return this;
	}
	
	private final Item BRONZE_AXE = new Item(1351, 1);
	private final Item BRONZE_PICKAXE = new Item(1265, 1);

	@Override
	public boolean handle(final Player player, Node node, String option) {
		switch(node.getId()){
		case 7365:
			player.getDialogueInterpreter().open(7365, node.asNpc());
			break;
		case 28741:
			player.animate(new Animation(827));
			player.teleport(new Location(2333, 10015));
			break;
		case 28743:
			player.animate(new Animation(828));
			player.teleport(new Location(2328, 3646));
			break;
		case 14912:
		    if (!player.getInventory().add(BRONZE_AXE)) {
				player.getPacketDispatch().sendMessage("You don't have enough inventory space.");
				return true;
			    }
			    ObjectBuilder.replace(((GameObject) node), ((GameObject) node).transform(14908), 5000);
			break;
		case 14910:
		    if (!player.getInventory().add(BRONZE_PICKAXE)) {
				player.getPacketDispatch().sendMessage("You don't have enough inventory space.");
				return true;
			    }
			    ObjectBuilder.replace(((GameObject) node), ((GameObject) node).transform(14908), 5000);
			break;
		}
		return true;
	}

}
