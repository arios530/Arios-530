package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.action.DoorActionHandler;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.repository.Repository;
import org.arios.plugin.Plugin;

public class TollGateOptionPlugin extends OptionHandler {

	@Override
	public boolean handle(Player player, Node node, String option) {
		if (option.equals("pay-toll(10gp)")) {
			if (player.getQuestRepository().getQuest("Prince Ali Rescue").getStage() > 50) {
				player.getPacketDispatch().sendMessage("The guards let you through for free.");
				DoorActionHandler.handleDoor(player, (GameObject) node);
			} else {
				if (player.getInventory().contains(995, 10)) {
					player.getInventory().remove(new Item(995, 10));
					player.getPacketDispatch().sendMessage("You quickly pay the 10 gold toll and go through the gates.");
					DoorActionHandler.handleAutowalkDoor(player, (GameObject) node);
					return true;
				} else {
					player.getPacketDispatch().sendMessage("You need 10 gold to pass through the gates.");
				}
			}
		} else {
			player.getDialogueInterpreter().open(925, Repository.findNPC(925), (GameObject) node);
			return true;
		}
		return true;
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(35551).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(35551).getConfigurations().put("option:pay-toll(10gp)", this);
		ObjectDefinition.forId(35549).getConfigurations().put("option:open", this);
		ObjectDefinition.forId(35549).getConfigurations().put("option:pay-toll(10gp)", this);
		ObjectDefinition.forId(2882).getConfigurations().put("option:pay-toll(10gp)", this);
		return this;
	}

}
