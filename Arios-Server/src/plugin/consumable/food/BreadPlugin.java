package plugin.consumable.food;

import org.arios.game.content.global.consumable.ConsumableProperties;
import org.arios.game.content.global.consumable.CookingProperties;
import org.arios.game.content.global.consumable.Food;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.global.tutorial.TutorialStage;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;

/**
 * Represents the bread food that is based in it's own class because of the
 * conditions related to Tutorial Island.
 * @author 'Vexia
 * @version 1.0
 */
public final class BreadPlugin extends Food {

	/**
	 * Represents the cooking properties of bread.
	 */
	private static final BreadProperties PROPERTIES = new BreadProperties();

	/**
	 * Constructs a new {@code Bread} {@code Object}.
	 */
	public BreadPlugin() {
		super(2309, 2307, 2311, new ConsumableProperties(5), PROPERTIES);
	}

	@Override
	public boolean interact(final Player player, final Node node) {
		int stage = TutorialSession.getExtension(player).getStage();
		if (stage < TutorialSession.MAX_STAGE) {
			cook(player, (GameObject) node, 1);
			return false;
		}
		return true;
	}

	/**
	 * Represents the bread cooking properties.
	 * @author 'Vexia
	 * @date 22/12/2013
	 */
	public static class BreadProperties extends CookingProperties {

		/**
		 * Constructs a new {@code Bread} {@code Object}.
		 */
		public BreadProperties() {
			super(1, 40, 40, true);
		}

		@Override
		public boolean cook(final Food food, final Player player, final GameObject object) {
			if (TutorialSession.getExtension(player).getStage() == 20) {
				TutorialStage.load(player, 21, false);
				return super.cook(food, player, object, false);
			}
			return super.cook(food, player, object);
		}
	}
}
