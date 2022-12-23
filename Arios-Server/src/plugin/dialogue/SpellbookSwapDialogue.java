package plugin.dialogue;

import org.arios.game.component.Component;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;

/**
 * Handles the SpellbookSwapDialogue dialogue.
 * @author Vexia
 */
public class SpellbookSwapDialogue extends DialoguePlugin {

	public SpellbookSwapDialogue() {

	}

	public SpellbookSwapDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new SpellbookSwapDialogue(player);
	}

	private boolean perk;

	@Override
	public boolean open(Object... args) {
		if (args.length > 1) {
			perk = true;
			interpreter.sendOptions("Select a Spellbook", "Modern", "Ancient", "Lunar");
			return true;
		}
		interpreter.sendOptions("Select a Spellbook", "Ancient", "Modern");
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			if (perk) {
				SpellBook book = SpellBook.values()[buttonId - 1];
				player.getSpellBookManager().setSpellBook(book);
				player.getInterfaceManager().openTab(new Component(book.getInterfaceId()));
				end();
				return true;
			}
			int type = 0;
			switch (buttonId) {
			case 1:
				type = 1;
				break;
			case 2:
				type = 2;
				break;
			}
			final SpellBook book = type == 1 ? SpellBook.ANCIENT : SpellBook.MODERN;
			player.getSpellBookManager().setSpellBook(book);
			player.getInterfaceManager().openTab(new Component(book.getInterfaceId()));
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 3264731 };
	}
}