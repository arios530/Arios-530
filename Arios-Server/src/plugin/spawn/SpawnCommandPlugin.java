package plugin.spawn;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.dialogue.DialogueAction;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.Rights;
import org.arios.game.node.entity.player.link.TeleportManager.TeleportType;
import org.arios.game.node.item.Item;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.world.GameWorld;
import org.arios.parser.item.ItemConfiguration;
import org.arios.plugin.Plugin;

/**
 * Handles the spawn mode commands.
 * @author Vexia
 */
public class SpawnCommandPlugin extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		if (GameWorld.isEconomyWorld()) {
			return this;
		}
		link(CommandSet.PLAYER);
		return this;
	}

	@Override
	public boolean parse(Player player, String name, String[] args) {
		switch (name) {
		case "setkills":
			if (player.isAdmin()) {
				player.getSavedData().getSpawnData().setKills(toInteger(args[1]));
			}
			return true;
		case "home":
			if (player.inCombat() || player.getLocks().isInteractionLocked() || player.getSkullManager().isWilderness() || player.getAttribute("activity", null) != null) {
				player.getPacketDispatch().sendMessage("You can't do that right now.");
				return true;
			}
			player.getTeleporter().send(ServerConstants.getHomeLocation(), TeleportType.NORMAL);
			return true;
		case "item":
		case "itemn":
			if (args.length <= 1) {
				player.sendMessage("Invalid syntax! ::item id amount");
				return true;
			}
			if (player.getDetails().getRights() == Rights.ADMINISTRATOR) {
				return false;
			}
			if (!player.canSpawn()) {
				return true;
			}
			int id = -1;
			if (name.equals("item")) {
				id = toInteger(args[1]);
			} else {
				String params = "";
				for (int i = 1; i < args.length; i++) {
					params += i == args.length - 1 ? args[i] : args[i] + " ";
				}
				for (int i = 0; i < ItemDefinition.getDefinitions().size(); i++) {
					ItemDefinition def1 = ItemDefinition.forId(i);
					if (def1 != null && def1.getName().equalsIgnoreCase(params.toLowerCase())) {
						id = i;
						player.sendMessage("Item id=" + id + ".");
						break;
					}
				}
			}
			if (id > Cache.getItemDefinitionsSize()) {
				id = Cache.getItemDefinitionsSize();
			} else if (id < 0) {
				id = 0;
			}
			int amount = 1;
			if (args.length > 2) {
				amount = toInteger(args[2]);
			}
			final Item item = new Item(id, amount);
			boolean tradeable = item.getDefinition().getConfiguration(ItemConfiguration.TRADEABLE, false);
			if (tradeable && !ItemDefinition.forId(item.getNoteChange()).getConfiguration("tradeable", false)) {
				tradeable = false;
			}
			if (!tradeable && item.getDefinition().getConfiguration("trade-override", false)) {
				tradeable = true;
			}
			boolean spawnable = item.getDefinition().getConfiguration(ItemConfiguration.SPAWNABLE, true);
			if (spawnable && !tradeable) {
				spawnable = false;
			}
			if (spawnable && !ItemDefinition.forId(item.getNoteChange()).getConfiguration(ItemConfiguration.SPAWNABLE, true)) {
				spawnable = false;
			}
			if (!spawnable) {
				player.sendMessage("That item is unspawnable. You must earn it yourself.");
				return true;
			}
			if (item.getName().equals("Null")) {
				return true;
			}
			player.getInventory().add(item);
			return true;
		case "empty":
			player.getDialogueInterpreter().sendOptions("Are you sure?", "Yes.", "No.");
			player.getDialogueInterpreter().addAction(new DialogueAction() {
				@Override
				public void handle(Player player, int buttonId) {
					if (buttonId == 1) {
						player.getInventory().clear();
						player.sendMessage("Inventory cleared!");
					}
				}
			});
			return true;
		}
		return false;
	}

}
