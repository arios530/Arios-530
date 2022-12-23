package plugin.skill.fletching;

import org.arios.game.content.dialogue.SkillDialogueHandler;
import org.arios.game.content.dialogue.SkillDialogueHandler.SkillDialogue;
import org.arios.game.content.skill.member.fletching.FletchItem;
import org.arios.game.content.skill.member.fletching.FletchType;
import org.arios.game.content.skill.member.fletching.FletchingPulse;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to open the fletching dialogue.
 * @author 'Vexia
 * @version 1.0
 */
public class FletchingPlugin extends UseWithHandler {

	/**
	 * Constructs a new {@code FletchingPlugin} {@code Object}.
	 */
	public FletchingPlugin() {
		super(1511, 1521, 1519, 1517, 1515, 1513, 6332, 6333);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		addHandler(946, ITEM_TYPE, this);
		return this;
	}

	@Override
	public boolean handle(final NodeUsageEvent event) {
		final FletchType type = FletchType.forItem(event.getUsedItem());
		if (type == null) {
			return true;
		}
		final Player player = event.getPlayer();
		final Item log = event.getUsedItem();
		Object[] items = new Object[type.getItems().length];
		for (int i = 0; i < items.length; i++) {
			items[i] = type.getItems()[i].getProduct();
		}
		SkillDialogueHandler handler = new SkillDialogueHandler(player, SkillDialogue.forLength(type.getItems().length), items) {
			
			@Override
			public void create(final int amount, int index) {
				final FletchItem fletch = type.getItems()[index];
				fletch.setType(type);
				player.getPulseManager().run(new FletchingPulse(player, log, amount,  fletch));
			}

			@Override
			public int getAll(int index) {
				return player.getInventory().getAmount(log);
			}

		};
		handler.open();
		return true;
	}

}
