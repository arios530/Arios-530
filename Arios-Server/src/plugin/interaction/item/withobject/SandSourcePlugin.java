package plugin.interaction.item.withobject;

import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the sand source plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class SandSourcePlugin extends UseWithHandler {

	/**
	 * Represents the ids to use.
	 */
	private static final int[] IDS = new int[] { 2645, 10814 };

	/**
	 * Represents the bucket to use.
	 */
	private static final Item BUCKET = new Item(1925);

	/**
	 * Represents the sand item.
	 */
	private static final Item SAND = new Item(1783);

	/**
	 * Represents the animation to use.
	 */
	private static final Animation ANIMATION = new Animation(832);

	/**
	 * Constructs a new {@code SandSourcePlugin} {@code Object}.
	 */
	public SandSourcePlugin() {
		super(1925);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (int i : IDS) {
			addHandler(i, OBJECT_TYPE, this);
		}
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		handle(event.getPlayer());
		return true;
	}

	/**
	 * Method used to handle the interaction.
	 * @param player the player.
	 */
	public final void handle(final Player player) {
		player.getPulseManager().run(new Pulse(2, player) {
			@Override
			public boolean pulse() {
				if (player.getInventory().remove(BUCKET)) {
					player.animate(ANIMATION);
					player.getPacketDispatch().sendMessage("You fill the bucket with sand.");
					player.getInventory().add(SAND);
				}
				return !player.getInventory().containsItem(BUCKET);
			}

			@Override
			public void stop() {
				super.stop();
			}
		});
	}
}
