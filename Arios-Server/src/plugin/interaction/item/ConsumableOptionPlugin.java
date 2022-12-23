package plugin.interaction.item;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.global.consumable.Consumable;
import org.arios.game.content.global.consumable.ConsumableProperties;
import org.arios.game.content.global.consumable.Consumables;
import org.arios.game.content.global.consumable.Drink;
import org.arios.game.content.global.consumable.Food;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to consume a consumable item.
 * @author 'Vexia
 * @author Emperor
 * @version 1.0
 */
public final class ConsumableOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ItemDefinition.setOptionHandler("eat", this);
		ItemDefinition.setOptionHandler("drink", this);
		return this;
	}

	@Override
	public boolean handle(final Player player, final Node node, final String option) {
		if (player.getLocks().isLocked(option)) {
			return true;
		}
		boolean food = option.equals("eat");
		player.getLocks().lock(option, 3);
		if (!food) {
			player.getLocks().lock("eat", 2);
		}
		Item item = (Item) node;
		if (player.getInventory().get(item.getSlot()) != item) {
			return false;
		}
		Consumable consumable = food ? Consumables.forFood(item) : Consumables.forDrink(item);
		if (consumable == null) {
			consumable = food ? new Food(item.getId(), new ConsumableProperties(1)) : new Drink(item.getId(), new ConsumableProperties(1));
		}
		consumable.consume(((Item) node), player);
		if (food) {
			player.getProperties().getCombatPulse().delayNextAttack(3);
		}
		return true;
	}
}