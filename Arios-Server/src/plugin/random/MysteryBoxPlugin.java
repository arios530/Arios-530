package plugin.random;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.drop.DropFrequency;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;
import org.arios.tools.StringUtils;

/**
 * Handles the mystery box item.
 * @author Vexia
 */
public final class MysteryBoxPlugin extends OptionHandler {

	/**
	 * The rewards recieved from a mystery box.
	 */
	private static final ChanceItem[] REWARDS = new ChanceItem[] { new ChanceItem(995, 1, 5000, DropFrequency.COMMON), new ChanceItem(685, 1, 1, DropFrequency.COMMON), new ChanceItem(1623, 1, 1, DropFrequency.COMMON), new ChanceItem(559, 1, 1, DropFrequency.COMMON), new ChanceItem(1925, 1, 1, DropFrequency.COMMON), new ChanceItem(1965, 1, 1, DropFrequency.COMMON), new ChanceItem(956, 1, 1, DropFrequency.COMMON), new ChanceItem(1061, 1, 1, DropFrequency.COMMON), new ChanceItem(1957, 1, 1, DropFrequency.COMMON), new ChanceItem(592, 1, 1, DropFrequency.COMMON), new ChanceItem(359, 1, 1, DropFrequency.COMMON), new ChanceItem(405, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(1621, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(7937, 30, 30, DropFrequency.UNCOMMON), new ChanceItem(1119, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(1442, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(1444, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(1601, 1, 1, DropFrequency.RARE), new ChanceItem(563, 10, 10, DropFrequency.RARE), new ChanceItem(561, 20, 20, DropFrequency.RARE), new ChanceItem(1329, 1, 1, DropFrequency.RARE), new ChanceItem(1315, 1, 1, DropFrequency.RARE), new ChanceItem(1123, 1, 1, DropFrequency.RARE), new ChanceItem(1454, 1, 1, DropFrequency.RARE), new ChanceItem(1127, 1, 1, DropFrequency.VERY_RARE), new ChanceItem(985, 1, 1, DropFrequency.VERY_RARE), new ChanceItem(987, 1, 1, DropFrequency.VERY_RARE) };

	/**
	 * The spawn rewards.
	 */
	private static final ChanceItem[] SPAWN_REWARDS = new ChanceItem[] { new ChanceItem(10420, 1, 1, DropFrequency.UNCOMMON),// white
			// ele
			new ChanceItem(10422, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10400, 1, 1, DropFrequency.RARE),// black
			// ele
			new ChanceItem(10402, 1, 1, DropFrequency.RARE), new ChanceItem(10416, 1, 1, DropFrequency.UNCOMMON),// purple
			// ele
			new ChanceItem(10418, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10436, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10438, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10404, 1, 1, DropFrequency.UNCOMMON),// red
			// ele
			// shirt
			new ChanceItem(10406, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10424, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10426, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10408, 1, 1, DropFrequency.UNCOMMON),// blue
			// ele
			// shirt
			new ChanceItem(10410, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10428, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10430, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10412, 1, 1, DropFrequency.UNCOMMON),// green
			// ele
			// shirt
			new ChanceItem(10414, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10432, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10434, 1, 1, DropFrequency.UNCOMMON),

			new ChanceItem(7319, 1, 1, DropFrequency.UNCOMMON),// Boaters
			new ChanceItem(7321, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(7323, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(7325, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(7327, 1, 1, DropFrequency.UNCOMMON),

			// Riu's items

			new ChanceItem(10939, 1, 1, DropFrequency.RARE), // lumberjack
			new ChanceItem(10940, 1, 1, DropFrequency.RARE), new ChanceItem(10941, 1, 1, DropFrequency.RARE),

			new ChanceItem(6199, 1, 1, DropFrequency.UNCOMMON), // Mbox

			new ChanceItem(6335, 1, 1, DropFrequency.UNCOMMON), // tribal masks
			new ChanceItem(6337, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(13109, 1, 1, DropFrequency.RARE), // animal
			// masks
			new ChanceItem(13111, 1, 1, DropFrequency.COMMON), new ChanceItem(13113, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(13115, 1, 1, DropFrequency.RARE),

			new ChanceItem(9925, 1, 1, DropFrequency.RARE), // skele set
			new ChanceItem(9924, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(9923, 1, 1, DropFrequency.UNCOMMON),

			new ChanceItem(11021, 1, 1, DropFrequency.RARE), new ChanceItem(11020, 1, 1, DropFrequency.RARE), new ChanceItem(11022, 1, 1, DropFrequency.RARE), new ChanceItem(11019, 1, 1, DropFrequency.RARE), // chicken

			new ChanceItem(6188, 1, 1, DropFrequency.COMMON),

			new ChanceItem(6858, 1, 1, DropFrequency.COMMON), // woolywooly
			new ChanceItem(6862, 1, 1, DropFrequency.COMMON),

			new ChanceItem(8928, 1, 1, DropFrequency.RARE), // Misc
			new ChanceItem(12595, 1, 1, DropFrequency.RARE), new ChanceItem(13101, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(5607, 1, 1, DropFrequency.VERY_RARE),

			new ChanceItem(6859, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10836, 1, 1, DropFrequency.RARE), new ChanceItem(10837, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10838, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10839, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(10840, 1, 1, DropFrequency.RARE), new ChanceItem(6857, 1, 1, DropFrequency.UNCOMMON), new ChanceItem(12645, 1, 1, DropFrequency.COMMON),

			new ChanceItem(7806, 1, 1, DropFrequency.VERY_RARE), new ChanceItem(7807, 1, 1, DropFrequency.VERY_RARE), new ChanceItem(7808, 1, 1, DropFrequency.VERY_RARE), new ChanceItem(7809, 1, 1, DropFrequency.VERY_RARE) };

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(6199).getConfigurations().put("option:open", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final ChanceItem item = RandomFunction.getChanceItem(GameWorld.isEconomyWorld() ? REWARDS : SPAWN_REWARDS);
		final String name = item.getName().toLowerCase();
		final Item box = (Item) node;
		if (player.getInventory().remove(box, box.getSlot(), true)) {
			player.lock(1);
			player.getInventory().add(new Item(item.getId(), RandomFunction.random(item.getMinimumAmount(), item.getMaximumAmount())));
			player.sendMessage("Inside the box you find " + (item.getId() == 995 ? "some" : (StringUtils.isPlusN(name) ? "an" : "a")) + " " + name + "!");
		}
		return true;
	}

	@Override
	public boolean isWalk() {
		return false;
	}

}
