package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the ScoolgirlMuseumDialogue dialogue.
 * @author 'Vexia
 */
public class ScoolgirlMuseumDialogue extends DialoguePlugin {

	public ScoolgirlMuseumDialogue() {

	}

	public ScoolgirlMuseumDialogue(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 5984 };
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
	public DialoguePlugin newInstance(Player player) {

		return new ScoolgirlMuseumDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I wanna be an archaeologist when I grow up!");
		stage = 0;
		return true;
	}
}