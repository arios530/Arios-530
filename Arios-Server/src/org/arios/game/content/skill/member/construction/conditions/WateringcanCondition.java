package org.arios.game.content.skill.member.construction.conditions;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;

/**
 * A watering can condition used in the garden.
 * @author Vexia
 */
public class WateringcanCondition implements DecorationCondition {

	/**
	 * The instance of this condition.
	 */
	public static final WateringcanCondition INSTANCE = new WateringcanCondition();

	/**
	 * The watering can items.
	 */
	private static final Item[] WATERING_CANS = new Item[] { new Item(5340), new Item(5339), new Item(5338), new Item(5337), new Item(5336), new Item(5335), new Item(5334), new Item(5333), new Item(5331) };

	@Override
	public boolean hasRequirements(Player player, boolean message) {
		for (Item item : WATERING_CANS) {
			System.err.println(item);
			if (player.getInventory().containsItem(item)) {
				return true;
			}
		}
		if (message) {
			player.sendMessage("You need a filled watering can in order to do this.");
		}
		return false;
	}

	@Override
	public boolean remove(Player player) {
		Item can = null;
		for (Item item : WATERING_CANS) {
			if (player.getInventory().containsItem(item)) {
				can = player.getInventory().getItem(item);
				break;
			}
		}
		if (can == null) {
			return false;
		}
		if (player.getInventory().remove(can)) {
			player.getInventory().add(getNextCan(can.getId()));
			return true;
		}
		return false;
	}

	/**
	 * Gets the next can item.
	 * @return the next can.
	 */
	private Item getNextCan(int id) {
		Item can = null;
		for (int i = 0; i < WATERING_CANS.length; i++) {
			if (WATERING_CANS[i].getId() == id) {
				return WATERING_CANS[(i + 1)];
			}
		}
		return can;
	}

}
