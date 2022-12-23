package plugin.interaction.player;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.SystemLogger;
import org.arios.game.system.SystemManager;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;
import org.arios.plugin.PluginManifest;
import org.arios.plugin.PluginType;

/**
 * Validates a player login.
 * @author Emperor
 * @author Vexia
 * 
 */
@PluginManifest(type = PluginType.LOGIN)
public final class LoginValidationPlugin implements Plugin<Player> {
	
	/**
	 * Represents the quest point items to remove.
	 */
	private static final Item[] QUEST_ITEMS = new Item[] { new Item(9813), new Item(9814)};

	/**
	 * Constructs a new {@Code LoginValidationPlugin} {@Code Object}
	 */
	public LoginValidationPlugin() {
		/*
		 * empty.
		 */
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	@Override
	public Plugin<Player> newInstance(Player player) throws Throwable {
		if (player.isStaff() && !SystemManager.getSystemConfig().isStaff(player.getName())) {
			// player.getPacketDispatch().sendLogout();
			SystemLogger.log("Invalid staff login account, rights = " + player.getRights() + ", info = " + player.getUidInfo().toString() + "");
		}
		if (SystemManager.getSystemConfig().isAdmin(player.getName()) && !SystemManager.getSystemConfig().checkSerial(player.getDetails().getMacAddress())) {
			// player.getPacketDispatch().sendLogout();
			SystemLogger.log("Invalid staff login account " + player.getName() + ", info = " + player.getUidInfo().toString() + ".");
		}
		if (GameWorld.getSettings().isDevMode()) {
			player.toggleDebug();
		}
		checkQuestPointsItems(player);
		return this;
	}

	/**
	 * Method used to check for the quest point cape items.
	 * @param player the player.
	 */
	private static void checkQuestPointsItems(final Player player) {
		if (!player.getQuestRepository().hasCompletedAll() && player.getEquipment().contains(9813, 1) || player.getEquipment().contains(9814, 1)) {
			for (Item i : QUEST_ITEMS) {
				if (player.getEquipment().remove(i)) {
					player.getDialogueInterpreter().sendItemMessage(i, "As you no longer have completed all the quests, your " + i.getName() + " unequips itself to your " + (player.getInventory().freeSlots() < 1 ? "bank" : "inventory") + "!");
					if (player.getInventory().freeSlots() < 1) {
						player.getBank().add(i);
					} else {
						player.getInventory().add(i);
					}
				}
			}
		}
	}
}