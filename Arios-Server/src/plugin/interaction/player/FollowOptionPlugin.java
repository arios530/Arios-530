package plugin.interaction.player;

import org.arios.game.interaction.DestinationFlag;
import org.arios.game.interaction.MovementPulse;
import org.arios.game.interaction.Option;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to start following a node.
 * @author 'Vexia
 * @version 1.0
 */
public final class FollowOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		Option._P_FOLLOW.setHandler(this);
		return this;
	}

	@Override
	public boolean handle(final Player player, Node node, String option) {
		final Player target = ((Player) node);
		player.getPulseManager().run(new MovementPulse(player, target, DestinationFlag.FOLLOW_ENTITY) {
			@Override
			public boolean pulse() {
				player.face(target);
				return false;
			}

			@Override
			public void stop() {
				super.stop();
				mover.face(null);
			}
		}, "movement");
		return true;
	}
}
