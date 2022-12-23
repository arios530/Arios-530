package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for the drop party lever.
 * @author 'Vexia
 * @version 1.0
 */
public final class DropPartyLeverOptionPlugin extends OptionHandler {

	/**
	 * Represents the animation to use.
	 */
	private static final Animation ANIMATION = new Animation(6933);

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(26194).getConfigurations().put("option:pull", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		final GameObject object = (GameObject) node;
		if (player.getAttribute("delay:lever", -1) > GameWorld.getTicks())
			return true;
		player.setAttribute("delay:picking", GameWorld.getTicks() + 3);
		player.lock(2);
		player.faceLocation(object.getLocation());
		player.getDialogueInterpreter().open(1 << 16 | 2);
		GameWorld.submit(new Pulse(1, player) {
			@Override
			public boolean pulse() {
				player.animate(ANIMATION);
				return true;
			}
		});
		return true;
	}

}
