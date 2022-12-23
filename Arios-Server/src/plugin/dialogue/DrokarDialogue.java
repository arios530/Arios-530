package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for drokar.
 * @author 'Vexia
 * @version 1.0
 */
public final class DrokarDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code DrokarDialogue} {@code Object}.
	 */
	public DrokarDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code DrokarDialogue} {@code Object}.
	 * @param player the player.
	 */
	public DrokarDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new DrokarDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello, how are you?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Packages, packages and more!");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Ugh.. Okay, have a good day.");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 7729 };
	}
}