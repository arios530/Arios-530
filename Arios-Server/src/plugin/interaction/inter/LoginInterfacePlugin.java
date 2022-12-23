package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.login.LoginConfiguration;
import org.arios.game.system.task.Pulse;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used for the login interface.
 * @author 'Vexia
 * @version 1.0
 */
public final class LoginInterfacePlugin extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(378, this);
		return null;
	}

	@Override
	public boolean handle(final Player player, Component component, int opcode, int button, int slot, int itemId) {
		if (player.getLocks().isLocked("login")) {
			return true;
		}
		player.getLocks().lock("login", 2);
		player.getPulseManager().run(new Pulse(1) {
			@Override
			public boolean pulse() {
				LoginConfiguration.configureGameWorld(player);
				return true;
			}
		});
		return true;
	}

}
