package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.skill.Skills;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Represents the khardian node interaction plugin.
 * @author 'Vexia
 * @date 06/11/2013
 */
public final class KhardianInteractionPlugin extends OptionHandler {

	/**
	 * Represents the hydrateable waterskin items.
	 */
	private static final Item[] WATER_SKINS = new Item[] { new Item(1825), new Item(1827), new Item(1829), new Item(1831) };

	/**
	 * Represents the animation of cutting a cactus.
	 */
	private static final Animation ANIMATION = new Animation(911);

	/**
	 * Represents the dry cactus object id.
	 */
	private static final int DRY_CACTUS = 2671;

	/**
	 * Represents the item used to cut a cactus.
	 */
	private static final Item KNIFE = new Item(946);

	/**
	 * Represents the delay a cactus spawns at.
	 */
	private static final int SPAWN_DELAY = 45;

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(2670).getConfigurations().put("option:cut", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (option) {
		case "cut":
			if (!player.getInventory().containsItem(KNIFE)) {
				player.getPacketDispatch().sendMessage("You need a knife to cut this Cactus...");
				return true;
			}
			boolean failed = false;
			if (RandomFunction.random(3) == 1) {
				failed = true;
				player.getPacketDispatch().sendMessage("You fail to cut the cactus correctly and it gives you no water this time.");
			}
			if (!failed) {
				final Item remove = getWaterSkin(player);
				if (remove != null && player.getInventory().remove(remove)) {
					player.getInventory().add(new Item(remove.getId() - 2));
					player.getPacketDispatch().sendMessage("You top up your skin with water from the cactus.");
				} else {
					player.getPacketDispatch().sendMessage("You have no empty waterskins to put the water in.");
				}
			}
			player.lock(3);
			player.animate(ANIMATION);
			if (!failed) {
				player.getSkills().addExperience(Skills.WOODCUTTING, 10, true);
			}
			ObjectBuilder.replace(((GameObject) node), new GameObject(DRY_CACTUS, node.getLocation()), SPAWN_DELAY + RandomFunction.random(RegionManager.getLocalPlayers(player).size() / 2));
			break;
		}
		return true;
	}

	/**
	 * Method used to get a waterskin <code>Item</code>.
	 * @param player the <code>Player</code>.
	 * @return the <code>Item</code> or <code>Null</code>.
	 */
	public final Item getWaterSkin(final Player player) {
		for (Item item : WATER_SKINS) {
			if (player.getInventory().containsItem(item)) {
				return item;
			}
		}
		return null;
	}

}
