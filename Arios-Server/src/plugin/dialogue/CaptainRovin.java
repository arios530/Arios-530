package plugin.dialogue;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.free.DemonSlayer;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the dialogue plugin used for the captain rovin npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class CaptainRovin extends DialoguePlugin {

	/**
	 * Represents the quest instance.
	 */
	private Quest quest;

	/**
	 * Constructs a new {@code CaptainRovin} {@code Object}.
	 */
	public CaptainRovin() {
		/**
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code CaptainRovin} {@code Object}.
	 * @param player the player.
	 */
	public CaptainRovin(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new CaptainRovin(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		quest = player.getQuestRepository().getQuest("Demon Slayer");
		switch (quest.getStage()) {
		default:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "What are you doing up here? Only the palace guards", "are allowed up here.");
			stage = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (quest.getStage()) {
		default:
			defaultDialogue(buttonId);
			break;
		}
		return true;
	}

	/**
	 * Method used to handle the default dialogue.
	 * @param buttonId the button id.
	 */
	private final void defaultDialogue(int buttonId) {
		switch (stage) {
		case 0:
			interpreter.sendOptions("Select an Option", "I am one of the palace guards.", "What about the King?", "Yes I know, but this is important.");
			stage = 1;
			break;
		case 1:
			switch (buttonId) {
			case 1:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "I am one of the palace guards.");
				stage = 10;
				break;
			case 2:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "What about the King? Surely you'd let him up here.");
				stage = 20;
				break;
			case 3:
				interpreter.sendDialogues(player, FacialExpression.NORMAL, "Yes, I know but this is important.");
				stage = 30;
				break;
			}
			break;
		case 30:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Ok, I'm listening. Tell me what's so important.");
			stage = 31;
			break;
		case 31:
			if (quest.getStage() == 20) {
				player("There's a demon who wants to invade the city.");
				stage = 600;
				return;
			}
			interpreter.sendDialogues(player, FacialExpression.NORMAL, "Erm... I forgot.");
			stage = 32;
			break;
		case 32:
			end();
			break;
		case 21:
			end();
			break;
		case 20:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Well, yes I suppose we'd let him up, He doesn't", "generally want to come up here, but if he did want to,", "he could.");
			stage = 21;
			break;
		case 10:
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "No, you're not! I know all the palace guards.");
			stage = 11;
			break;
		case 11:
			end();
			break;
		case 600:
			if (player.getInventory().containsItem(DemonSlayer.SECOND_KEY) || player.getBank().containsItem(DemonSlayer.SECOND_KEY)) {
				npc("Yes, you said before, haven't you killed it yet?");
				stage = 620;
				return;
			}
			npc("Is it a powerful demon?");
			stage = 601;
			break;
		case 601:
			player("Yes, very.");
			stage = 602;
			break;
		case 602:
			npc("As good as the palace guards are, I don't know if", "they're up to taking on a very powerful demon.");
			stage = 603;
			break;
		case 603:
			player("It's not them who are going to fight the demon, it's me.");
			;
			stage = 604;
			break;
		case 604:
			npc("What, all by yourself? How are you going to do that?");
			stage = 605;
			break;
		case 605:
			player("I'm going to use the powerful sword Silverlight, which I", "believe you have one of the keys for?");
			stage = 606;
			break;
		case 606:
			npc("Yes, I do. But why should I give it to you?");
			stage = 607;
			break;
		case 607:
			player("Sir Prysin said you would give me the key.");
			stage = 608;
			break;
		case 608:
			npc("Oh, he did, did he? Well I don't report to Sir Prysin, I", "report directly to the king!");
			stage = 609;
			break;
		case 609:
			npc("I didn't work my way up through the ranks of the", "palace guards so I could take orders from an ill-bred", "moron who only has his job because his great-", "grandfather was a hero with a silly name!");
			stage = 610;
			break;
		case 610:
			player("Why did he give you one of the keys then?");
			stage = 611;
			break;
		case 611:
			npc("Only because the king ordered him to! The king", "couldn't get Sir Prysin to part with his precious", "ancestral sword, but he made him lock it up so he", "couldn't loose it.");
			stage = 612;
			break;
		case 612:
			npc("I got one key and I think some wizard got another.", "Now what happened to the third one?");
			stage = 613;
			break;
		case 613:
			player("Sir Prysin dropped it down a drain!");
			stage = 614;
			break;
		case 614:
			npc("Ha ha ha! The idiot!");
			stage = 615;
			break;
		case 615:
			npc("Okay, I'll give you the key, just so that it's you that", "kills the demon and not Sir Prysin!");
			stage = 616;
			break;
		case 616:
			if (player.getInventory().freeSlots() == 0) {
				npc("Talk to me againw hen you have free inventory space.");
				stage = 617;
				return;
			}
			if (player.getInventory().add(DemonSlayer.SECOND_KEY)) {
				interpreter.sendItemMessage(DemonSlayer.SECOND_KEY.getId(), "Captain Rovin hands you a key.");
				stage = 618;
			}
			break;
		case 617:
			end();
			break;
		case 618:
			end();
			break;
		case 620:
			player("Well I'm going to use the powerful sword Silverlight", "which I believe you have one of the keys for?");
			stage = 621;
			break;
		case 621:
			npc("I already gave you my key. Check your pockets.");
			stage = 622;
			break;
		case 622:
			end();
			break;
		}
	}

	@Override
	public int[] getIds() {
		return new int[] { 884 };
	}

}