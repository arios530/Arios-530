package plugin.interaction.item.withobject;

import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the poison fountain plugin.
 * @author 'Vexia
 * @date 24/12/2013
 */
public class PoisonFountainPlugin extends UseWithHandler {

	/**
	 * Represents the searching animation.
	 */
	private static final Animation SEARCH_ANIM = new Animation(881);

	/**
	 * Represents the poisoned fish food.
	 */
	private static final Item POISONED_FOOD = new Item(274);

	/**
	 * Constructs a new {@code PoisonFountainPlugin} {@code Object}.
	 */
	public PoisonFountainPlugin() {
		super(274);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(153, OBJECT_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		final Player player = event.getPlayer();
		if (player.getAttribute("piranhas-killed", false)) {
			player.getPacketDispatch().sendMessage("The piranhas are dead already.");
			return true;
		}
		if (player.getInventory().remove(POISONED_FOOD)) {
			player.lock();
			player.setAttribute("/save:piranhas-killed", true);
			player.animate(SEARCH_ANIM);
			player.getPacketDispatch().sendMessage("You pour the poisoned fish food into the fountain.");
			GameWorld.submit(new Pulse(1) {
				int counter = 0;

				@Override
				public boolean pulse() {
					switch (counter++) {
					case 1:
						player.getPacketDispatch().sendMessage("The piranhas start eating the food...");
						break;
					case 2:
						player.getPacketDispatch().sendMessage("... then die and float to the surface.");
						player.unlock();
						return true;
					}
					return false;
				}
			});
		}
		return true;
	}

}
