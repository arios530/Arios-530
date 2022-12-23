package plugin.skill.summoning.familiar;

import org.arios.game.content.dialogue.DialogueInterpreter;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.RemoteViewer;
import org.arios.game.content.skill.member.summoning.familiar.RemoteViewer.ViewType;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the remote viewing dialogue.
 * @author Vexia
 */
public final class RemoteViewDialogue extends DialoguePlugin {

	/**
	 * The familiar instance.
	 */
	private Familiar familiar;

	/**
	 * Constructs a new {@code RemoteViewDialogue} {@code Object}.
	 */
	public RemoteViewDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code RemoteViewDialogue} {@code Object}.
	 * @param player the player.
	 */
	public RemoteViewDialogue(final Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new RemoteViewDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		familiar = (Familiar) args[0];
		options("North", "East", "Sotuh", "West", "Straight up");
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		end();
		RemoteViewer.create(player, familiar, familiar.getViewAnimation(), ViewType.values()[-1 + buttonId]).startViewing();
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { DialogueInterpreter.getDialogueKey(RemoteViewer.DIALOGUE_NAME) };
	}

}
