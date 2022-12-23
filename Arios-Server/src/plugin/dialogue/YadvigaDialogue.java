package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the yadviga dialogue plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class YadvigaDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code YadvigaDialogue} {@code Object}.
	 */
	public YadvigaDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code YadvigaDialogue} {@code Object}.
	 * @param player the player.
	 */
	public YadvigaDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new YadvigaDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Get lost!");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 6041 };
	}
}