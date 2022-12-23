package plugin.skill.magic.lunar;

import org.arios.game.content.global.consumable.Consumables;
import org.arios.game.content.global.consumable.Food;
import org.arios.game.content.skill.SkillPulse;
import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.free.magic.MagicSpell;
import org.arios.game.content.skill.free.magic.Runes;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.SpellType;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.item.Item;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.plugin.Plugin;

/**
 * Represents the pie baking lunar spell.
 * @author 'Vexia
 * @version 1.0
 */
public final class BakePieSpell extends MagicSpell {

	/**
	 * Represents the animation of the spell.
	 */
	private static final Animation ANIMATION = Animation.create(4413);

	/**
	 * Represents the graphics to use.
	 */
	private static final Graphics GRAPHIC = new Graphics(746, (150 << 16));

	/**
	 * Constructs a new {@code BakePieSpell} {@code Object}.
	 */
	public BakePieSpell() {
		super(SpellBook.LUNAR, 65, 60, null, null, null, new Item[] { new Item(Runes.ASTRAL_RUNE.getId(), 1), new Item(Runes.FIRE_RUNE.getId(), 5), new Item(Runes.WATER_RUNE.getId(), 4) });
	}

	@Override
	public Plugin<SpellType> newInstance(SpellType arg) throws Throwable {
		SpellBook.LUNAR.register(15, this);
		return this;
	}

	@Override
	public boolean cast(Entity entity, Node target) {
		final Player player = ((Player) entity);
		if (!super.meetsRequirements(player, true, false)) {
			return false;
		}
		Food food = null;
		for (Item item : player.getInventory().toArray()) {
			if (item == null) {
				continue;
			}
			if (item.getName().toLowerCase().contains("pie") && item.getName().toLowerCase().contains("uncooked") || item.getName().toLowerCase().contains("raw")) {
				food = Consumables.forRaw(item);
			}
		}
		if (food == null) {
			player.getPacketDispatch().sendMessage("You need a pie in order to cast this spell.");
			return false;
		}
		player.getPulseManager().run(new LunarPiePulse(player, food.getRaw(), food));
		return true;
	}

	/**
	 * Represents the skill pulse used to cook a pie.
	 * @author 'Vexia
	 */
	public final class LunarPiePulse extends SkillPulse<Item> {

		/**
		 * Represents the pie to make.
		 */
		private Food pie;

		/**
		 * Constructs a new {@code BakePieSpell} {@code Object}.
		 * @param player the player.
		 * @param node the node.
		 */
		public LunarPiePulse(final Player player, final Item node, final Food pie) {
			super(player, node);
			this.pie = pie;
		}

		@Override
		public boolean checkRequirements() {
			if (player.getSkills().getLevel(Skills.COOKING) < pie.getCookingProperties().getLevel()) {
				player.getDialogueInterpreter().sendDialogue("You need to have a Cooking level of t " + pie.getCookingProperties().getLevel() + " to cook this pie.");
				return false;
			}
			if (!player.getInventory().containsItem(pie.getRaw())) {
				stop();
				return false;
			}
			return true;
		}

		@Override
		public void animate() {
			player.animate(ANIMATION);
			player.graphics(GRAPHIC);
		}

		@Override
		public boolean reward() {
			if (getDelay() == 1) {
				setDelay(5);
				return false;
			}
			if (player.getInventory().remove(pie.getRaw()) && meetsRequirements(player, true, true)) {
				player.getInventory().add(pie.getItem());
			} else {
				return true;
			}
			return nextPie() == null;
		}

		@Override
		public void stop() {
			super.stop();
			player.graphics(new Graphics(-1));
		}

		@Override
		public void message(int type) {
		}

		/**
		 * Method used to get the next pie.
		 * @return the pie.
		 */
		public Food nextPie() {
			for (Item item : player.getInventory().toArray()) {
				if (item == null) {
					continue;
				}
				if (item.getName().toLowerCase().contains("pie") && item.getName().toLowerCase().contains("uncooked") || item.getName().toLowerCase().contains("raw")) {
					pie = Consumables.forRaw(item);
					node = pie.getItem();
				}
			}
			return pie;
		}
	}
}
