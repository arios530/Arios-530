package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.prayer.PrayerType;
import org.arios.plugin.Plugin;

/**
 * Represents the prayer interface.
 * @author 'Vexia
 */
public final class PrayerTabInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(271, this);
		return this;
	}

	@Override
	public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
		final PrayerType type = PrayerType.get(button);
		if (type == null) {
			return true;
		}
		player.getPrayer().toggle(type);
		return true;
	}
}
