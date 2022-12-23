package plugin.skill.fletching;

import org.arios.game.content.dialogue.SkillDialogueHandler;
import org.arios.game.content.dialogue.SkillDialogueHandler.SkillDialogue;
import org.arios.game.content.skill.member.fletching.items.gem.Gem;
import org.arios.game.content.skill.member.fletching.items.gem.GemBoltCutPulse;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the gem bolt creating plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class GemBoltPlugin extends UseWithHandler {

	/**
	 * Constructs a new {@code GemBoltPlugin} {@code Object}.
	 */
	public GemBoltPlugin() {
		super(1609, 1611, 1613, 1607, 1605, 1603, 1601, 1615, 6573);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(1755, ITEM_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(final NodeUsageEvent event) {
		final Player player = event.getPlayer();
		final Gem gem = Gem.forItem(event.getUsedItem().getId() == 1755 ? event.getBaseItem() : event.getUsedItem());
		new SkillDialogueHandler(player, SkillDialogue.ONE_OPTION, gem.getGem()) {
			@Override
			public void create(final int amount, final int index) {
				player.getPulseManager().run(new GemBoltCutPulse(player, event.getUsedItem(), gem, amount));
			}

			@Override
			public int getAll(int index) {
				return player.getInventory().getAmount(gem.getGem());
			}

		}.open();
		return true;
	}

}
