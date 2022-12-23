package plugin.consumable.food;

import org.arios.game.content.global.consumable.Consumables;
import org.arios.game.content.global.consumable.Food;
import org.arios.plugin.Plugin;

/**
 * Represents the draynor cabbage food type.
 * @author 'Vexia
 * @version 1.0
 */
public final class DraynorCabbagePlugin extends Food {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		Consumables.add(this);
		return this;
	}

	/**
	 * Constructs a new {@code DraynorCabbagePlugin} {@code Object}.
	 */
	public DraynorCabbagePlugin() {
		super(1967, 2);
		this.messages = new String[] { "You eat the cabbage.", "It seems to taste nicer than normal." };
	}

}
