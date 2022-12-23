package org.arios.game.content.global.action;

import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.GodType;
import org.arios.game.content.skill.free.runecrafting.RunePouch;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.node.entity.player.link.diary.DiaryType;
import org.arios.game.node.item.GroundItem;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.system.mysql.impl.GroundSpawnSQLHandler.GroundSpawn;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Animation;

/**
 * A class used to handle the picking up of ground items.
 * @author 'Vexia
 */
public final class PickupHandler {

	/**
	 * Method used to take a ground item.
	 * @param player the player.
	 * @param item the item.
	 * @return {@code True} if taken.
	 */
	public static boolean take(final Player player, final GroundItem item) {
		if (item.getLocation() == null) {
			player.getPacketDispatch().sendMessage("Invalid ground item!");
			return true;
		}
		if (!GroundItemManager.getItems().contains(item)) {
			player.getPacketDispatch().sendMessage("Too late!");
			return true;
		}
		if (!(item instanceof GroundSpawn) && item.isRemainPrivate() && !item.droppedBy(player)) {
			player.sendMessage("You can't take that item!");
			return true;
		}
		Item add = new Item(item.getId(), item.getAmount(), item.getCharge());
		if (!player.getInventory().hasSpaceFor(add)) {
			player.getPacketDispatch().sendMessage("You don't have enough inventory space to hold that item.");
			return true;
		}
		if (!canTake(player, item, 0)) {
			return true;
		}
		if (item.isActive() && player.getInventory().add(add)) {
			if (!RegionManager.isTeleportPermitted(item.getLocation())) {
				player.animate(Animation.create(535));
			}
			if (item instanceof GroundSpawn && item.getId() == 401 && player.getZoneMonitor().isInZone("karamja") && !player.getAchievementDiaryManager().hasCompletedTask(DiaryType.KARAMJA, 0, 7)) {
				int seaweed = player.getAttribute("seaweed", 0);
				seaweed++;
				player.setAttribute("seaweed", seaweed);
				player.getAchievementDiaryManager().updateTask(player, DiaryType.KARAMJA, 0, 7, seaweed == 5);
			}
			GroundItemManager.destroy(item);
			player.getAudioManager().send(new Audio(2582, 10, 1));
		}
		return true;
	}

	/**
	 * Checks if the player can take an item.
	 * @param player the player.
	 * @param item the item.
	 * @param type the type (1= ground, 2=telegrab)
	 * @return {@code True} if so.
	 */
	public static boolean canTake(Player player, GroundItem item, int type) {
		if (item.getDropper() != null && !item.droppedBy(player) && player.getIronmanManager().checkRestriction()) {
			return false;
		}
		if (item.getId() == 8858 || item.getId() == 8859) {
			player.getDialogueInterpreter().sendDialogues(4300, FacialExpression.ANGRY, "Hey! You can't take that, it's guild property. Take one", "from the pile.");
			return false;
		}
		if (GodType.forCape(item) != null) {
			if (GodType.hasAny(player)) {
				player.sendMessages("You may only possess one sacred cape at a time.", "The conflicting powers of the capes drive them apart.");
				return false;
			}
		}
		if (RunePouch.forItem(item) != null) {
			if (player.hasItem(item)) {
				player.sendMessage("A mystical force prevents you from picking up the pouch.");
				return false;
			}
		}
		if (item.hasItemPlugin()) {
			return item.getPlugin().canPickUp(player, item, type);
		}
		return true;
	}

}
