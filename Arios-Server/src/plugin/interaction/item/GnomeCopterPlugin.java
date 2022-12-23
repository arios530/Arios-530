package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Handles the gnome copter plugin.
 * @author Vexia
 */
public final class GnomeCopterPlugin implements Plugin<Object> {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.forId(12842).getConfigurations().put("equipment", this);
		return this;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		final Player player = (Player) args[0];
		final Item item = (Item) args[1];
		switch (identifier) {
		case "equip":
			break;
		case "unequip":
			if (item.getId() == 12842) {
				player.getEquipment().remove(item, EquipmentContainer.SLOT_WEAPON, true);
				return false;
			}
			break;
		}
		return false;
	}

}
