package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for ambassador fernook.
 * @author 'Vexia
 * @version 1.0
 */
public final class AmbassadorFernook extends DialoguePlugin {

	/**
	 * Constructs a new {@code AmbassadorFernook} {@code Object}.
	 */
	public AmbassadorFernook() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code AmbassadorFernook} {@code Object}.
	 * @param player the player.
	 */
	public AmbassadorFernook(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new AmbassadorFernook(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(player, FacialExpression.NORMAL, "Hello Ambassador. Are you here visiting King Roald?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Well, in theory, but he always seems to be busy.");
			stage = 1;
			break;
		case 1:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "You don't seem that upset by that, though...");
			stage = 2;
			break;
		case 2:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Oh no, I like travelling, and if you become a diplomat", "patience is a vital skill.");
			stage = 3;
			break;
		case 3:
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "I'll try to remember that.");
			stage = 4;
			break;
		case 4:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 4582 };
	}
}