package plugin.interaction.object;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.impl.ForceMovement;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Represents the plugin used for handling a lumber yard crate.
 * @author 'Vexia
 * @version 1.0
 */
public final class LumberYardCratePlugin extends OptionHandler {

	/**
	 * Represents the kittem item.
	 */
	private static final Item KITTEN = new Item(1555);

	@Override
	public boolean handle(Player player, Node node, String option) {
		final Quest quest = player.getQuestRepository().getQuest("Gertrude's Cat");
		switch (option) {
		case "squeeze-under":
			Location dest = null;
			Location start = node.getLocation();
			if (player.getLocation().getX() > node.getLocation().getX()) {
				start = Location.create(3296, 3498, 0);
				dest = Location.create(3295, 3498, 0);
			} else {
				dest = Location.create(3296, 3498, 0);
			}
			ForceMovement.run(player, start, dest, Animation.create(9221));
			break;
		case "search":
			if (quest.getStage() == 50 && !player.getInventory().containsItem(KITTEN) && !player.getBank().containsItem(KITTEN)) {
				quest.setStage(40);
			}
			if (node instanceof NPC) {
				player.getPacketDispatch().sendMessage("You search the crate.");
				player.getPacketDispatch().sendMessage("You find nothing.");
			}
			if (quest.getStage() == 40) {
				if (player.getAttribute("findkitten", false) && player.getInventory().freeSlots() > 0) {
					quest.setStage(50);
					player.getDialogueInterpreter().sendDialogue("You find a kitten! You carefully place it in your backpack.");
					player.getInventory().add(KITTEN);
					return true;
				}
				player.getPacketDispatch().sendMessage("You search the crate.");
				player.getPacketDispatch().sendMessage("You find nothing.");
				if (RandomFunction.random(0, 3) == 1) {
					player.getPacketDispatch().sendMessage("You can hear kittens mewing close by...");
					player.setAttribute("findkitten", true);
				}
			} else {
				player.getPacketDispatch().sendMessage("You search the crate.");
				player.getPacketDispatch().sendMessage("You find nothing.");
			}
			break;
		}
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(767).getConfigurations().put("option:search", this);
		ObjectDefinition.forId(2620).getConfigurations().put("option:search", this);
		ObjectDefinition.forId(31149).getConfigurations().put("option:squeeze-under", this);
		return null;
	}

}
