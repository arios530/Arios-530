package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Handles the SinisterStrangerDialogue dialogue.
 * @author 'Vexia
 */
public class SinisterStrangerDialogue extends DialoguePlugin {

	public SinisterStrangerDialogue() {

	}

	public SinisterStrangerDialogue(Player player) {
		super(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 3677 };
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {

		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {

		return new SinisterStrangerDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		end();
		player.getPacketDispatch().sendMessage("He doesn't seem interested in talking to you.");
		return true;
	}
}