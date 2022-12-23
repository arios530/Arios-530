package plugin.skill.cooking;

import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.free.cooking.fermenting.WineFermentingPulse;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to ferment wine.
 * @author 'Vexia
 * @version 1.0
 */
public final class WineFermentPlugin extends UseWithHandler {

	/**
	 * Represents the grapes item.
	 */
	private static final Item GRAPES = new Item(1987, 1);

	/**
	 * Represents the jug of water item.
	 */
	private static final Item JUG_OF_WATER = new Item(1937, 1);

	/**
	 * Represents the unfermented wine item.
	 */
	private static final Item UNFERMENTED_WINE = new Item(1995, 1);

	/**
	 * Constructs a new {@code WineFermentPlugin} {@code Object}.
	 */
	public WineFermentPlugin() {
		super(1937);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(1987, ITEM_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		final Player player = event.getPlayer();
		if (player.getSkills().getLevel(Skills.COOKING) < 35) {
			player.getPacketDispatch().sendMessage("You need a cooking level of 35 to do this.");
			return true;
		}
		if (player.getInventory().remove(GRAPES, JUG_OF_WATER)) {
			player.getInventory().add(UNFERMENTED_WINE);
			GameWorld.submit(new WineFermentingPulse(1, player));
		}
		return true;
	}

}
