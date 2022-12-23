package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Represents the interface used to logout of the game.
 * @author 'Vexia
 * @version 1.0
 */
public final class LogoutInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(182, this);
		return this;
	}

	@Override
	public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
		if (!player.getZoneMonitor().canLogout()) {
			return true;
		}
		if (player.inCombat()) {
			player.getPacketDispatch().sendMessage("You can't log out until 10 seconds after the end of combat.");
			return true;
		}
		if (player.getAttribute("logoutDelay", 0) < GameWorld.getTicks()) {
			player.getPacketDispatch().sendLogout();
			player.setAttribute("logoutDelay", GameWorld.getTicks() + 3);
		}
		return true;
	}
}
