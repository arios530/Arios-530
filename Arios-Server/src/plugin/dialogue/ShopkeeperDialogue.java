package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the generic shop keeper dialogue.
 * @author Vexia
 */
public final class ShopkeeperDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code ShopkeeperDialogue} {@code Object}.
	 */
	public ShopkeeperDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code ShopkeeperDialogue} {@code Object}.
	 * @param player the player.
	 */
	public ShopkeeperDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new ShopkeeperDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Can I help you at all?");
		stage = 0;
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Select an Option", "Yes, please. What are you selling?", "No, thanks.");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				end();
				npc.openShop(player);
				break;
			case 2:
				end();
				break;
			}
			break;
		case 10:
			end();
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 523, 522, 520, 521, 1699 };
	}
}