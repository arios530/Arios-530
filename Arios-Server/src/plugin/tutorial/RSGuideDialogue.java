package plugin.tutorial;

import org.arios.game.component.Component;
import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.global.tutorial.TutorialStage;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Hanldes the runescape guide dialogue.
 * @author Vexia
 */
public class RSGuideDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code RuneScapeGuideDialogue} {@code Object}.
	 */
	public RSGuideDialogue() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code RuneScapeGuideDialogue} {@code Object}.
	 * @param player The player.
	 */
	private RSGuideDialogue(Player player) {
		super(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		int tut_stage = TutorialSession.getExtension(player).getStage();
		switch (tut_stage) {
		case 0:
			Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Greetings! I see you are a new arrival to this land. My", "job is to welcome all new visitors. So welcome!"));
			return true;
		case 1:
			Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "You will notice a flashing icon of a spanner; please click", "on this to continue the tutorial."));
			return true;
		case 2:
			Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I'm glad you're making progress!"));
			stage = 1;
			return true;
		}
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		final int tut_stage = TutorialSession.getExtension(player).getStage();
		switch (tut_stage) {
		case 0:
			switch (stage) {
			case 0:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "You have already learned the first thing needed to", "succeed in this world: talking to other people!"));
				stage = 1;
				break;
			case 1:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "You will find many inhabitants of this world have useful", "things to say to you. By clicking on them with your", "mouse you can talk to them."));
				stage = 2;
				break;
			case 2:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "I would also suggest reading through some of the", "supporting information on the website. There you can", "find the Knowledge Base, which contains all the", "additional information you're ever likely to need. It also"));
				stage = 3;
				break;
			case 3:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "contains maps and helpful tips to help you on your", "journey."));
				stage = 4;
				break;
			case 4:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "You will notice a flashing icon of a wrench, please click", "on this to continue the tutorial."));
				player.getInterfaceManager().openTab(new Component(261));
				player.getConfigManager().set(1021, 12);
				stage = 5;
				break;
			case 5:
				end();
				TutorialStage.load(player, 1, false);
				stage = 1;
				return true;
			}
			break;
		case 2:
			switch (stage) {
			case 1:
				Component.setUnclosable(player, interpreter.sendDialogues(npc, FacialExpression.NORMAL, "To continue the tutorial go through that door over", "there and speak to your first instructor!"));
				stage = 2;
				break;
			case 2:
				end();
				TutorialStage.load(player, 3, false);
				stage = 1;
				break;
			}
			break;
		case 1:
			end();
			break;
		}
		return true;
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new RSGuideDialogue(player);
	}

	@Override
	public int[] getIds() {
		return new int[] { 945 };
	}

}
