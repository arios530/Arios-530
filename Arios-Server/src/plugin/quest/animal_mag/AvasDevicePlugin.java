package plugin.quest.animal_mag;

import org.arios.game.content.global.quest.impl.member.AnimalMagnetism;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.state.EntityState;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Handles the equippage event of an ava device.
 * @author Vexia
 */
public final class AvasDevicePlugin implements Plugin<Object> {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		AnimalMagnetism.AVAS_ACCUMULATOR.getDefinition().getConfigurations().put("equipment", this);
		AnimalMagnetism.AVAS_ATTRACTOR.getDefinition().getConfigurations().put("equipment", this);
		return this;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		final Player player = (Player) args[0];
		final Item item = (Item) args[1];
		switch (identifier) {
		case "equip":
			if (GameWorld.isEconomyWorld()) {
				player.getStateManager().register(EntityState.AVA_DEVICE, true, item);
			}
			break;
		case "unequip":
			if (args.length == 3) {
				Item second = (Item) args[2];
				if (second.getId() == 10498 || second.getId() == 10499) {
					player.getStateManager().register(EntityState.AVA_DEVICE, true, second);
					break;
				}
			}
			if (GameWorld.isEconomyWorld()) {
				player.getStateManager().remove(EntityState.AVA_DEVICE);
			}
			break;
		}
		return true;
	}

}
