package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for the lidio npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class LidioDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code LidioDialogue} {@code Object}.
	 */
	public LidioDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code LidioDialogue} {@code Object}.
	 * @param player the player.
	 */
	public LidioDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new LidioDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Greetings, warrior, how can I fill your stomach today?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "With food preferrable.");
			stage = 1;
			break;
		case 1:
			end();
			npc.openShop(player);
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 4293 };
	}
}