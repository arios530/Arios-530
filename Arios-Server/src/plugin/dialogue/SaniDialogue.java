package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the SaniDialogue dialogue.
 * @author 'Vexia
 */
public class SaniDialogue extends DialoguePlugin {

	public SaniDialogue() {

	}

	public SaniDialogue(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 4905 };
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {

		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Welcome to my workshop. Feel free", "to use any of my anvils, I can't use them all at once!");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Thanks!");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}

		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new SaniDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello.");
		stage = 0;
		return true;
	}
}