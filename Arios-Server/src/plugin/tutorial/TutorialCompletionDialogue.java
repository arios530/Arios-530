package plugin.tutorial;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.dialogue.FacialExpression;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.global.tutorial.TutorialStage;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.HintIconManager;
import org.arios.game.node.entity.player.link.IronmanMode;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.net.amsc.MSPacketRepository;

/**
 * Handles the tutorial completion dialogue (skippy, magic instructor)
 * @author Vexia
 */
public class TutorialCompletionDialogue extends DialoguePlugin {

	/**
	 * The starter pack of items.
	 */
	private static final Item[] STARTER_PACK = new Item[] { new Item(1351, 1), new Item(590, 1), new Item(303, 1), new Item(315, 1), new Item(1925, 1), new Item(1931, 1), new Item(2309, 1), new Item(1265, 1), new Item(1205, 1), new Item(1277, 1), new Item(1171, 1), new Item(841, 1), new Item(882, 25), new Item(556, 25), new Item(558, 15), new Item(555, 6), new Item(557, 4), new Item(559, 2) };

	/**
	 * Represents the rune items.
	 */
	private static final Item[] RUNES = new Item[] { new Item(556, 5), new Item(558, 5) };

	/**
	 * If ironman is enabled.
	 */
	private static final boolean IRONMAN = true;

	/**
	 * Constructs a new {@code TutorialCompletionDialogue} {@code Object}
	 */
	public TutorialCompletionDialogue() {
		/*
		 * empty.
		 */
	}

	/**
	 * Constructs a new {@code TutorialCompletionDialogue} {@code Object}
	 * @param player the player.
	 */
	public TutorialCompletionDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new TutorialCompletionDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc = (NPC) args[0];
		if (npc == null) {
			return true;
		}
		if (npc.getId() == 946) {
			switch (TutorialSession.getExtension(player).getStage()) {
			case 67:
				interpreter.sendDialogues(player, null, "Hello.");
				stage = 0;
				return true;
			case 69:
				interpreter.sendDialogues(946, null, "Good. This is a list of your spells. Currently you can", "only cast one offensive spell called Wind Strike. Let's", "try it out on one of those chickens.");
				stage = 0;
				return true;
			case 70:
				if (!player.getInventory().contains(556, 1) && !player.getInventory().contains(558, 1)) {
					if (player.getInventory().hasSpaceFor(RUNES[0]) && player.getInventory().hasSpaceFor(RUNES[1])) {
						interpreter.sendDoubleItemMessage(RUNES[0], RUNES[1], "Terrova gives you five <col=08088A>air runes</col> and five <col=08088A>mind runes</col>!");
						player.getInventory().add(RUNES);
						stage = 3;
					} else {
						GroundItemManager.create(RUNES, player.getLocation(), player);
						stage = 3;
					}
				} else {
					end();
					TutorialStage.load(player, 70, false);
				}
				return true;
			case 71:
				interpreter.sendDialogues(946, null, "Well you're all finished here now. I'll give you a", "reasonable number of runes when you leave.");
				stage = -2;
				return true;
			}
		} else {
			interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Hey. Do you wanna skip the Tutorial?", "I can send you straight to Lumbridge, easy.");
			stage = 0;
		}
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		if (npc.getId() == 2796 || TutorialSession.getExtension(player).getStage() >= 71) {
			switch (stage) {
			case -2:
				interpreter.sendOptions("Do you want to go to the mainland?", "Yes.", "No.");
				stage = -1;
				break;
			case -1:
				switch (buttonId) {
				case 1:
					interpreter.sendDialogues(946, null, "When you get to the mainland you will find yourself in", "the town of Lumbridge. If you want some ideas on", "where to go next talk to my friend the Lumbridge", "Guide. You can't miss him; he's holding a big staff with");
					stage = 13;
					break;
				case 2:
					end();
					TutorialStage.load(player, 71, false);
					break;
				}
				break;
			case 0:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "If I do, you won't be able to come back here", "afterwards. It's a one-way trip. What do you say?");
				stage = 1;
				break;
			case 1:
				interpreter.sendOptions("What would you like to say?", "Send me to Lumbridge now.", "Who are you?", "Can I decide later?", "I'll stay here for the Tutorial.");
				stage = 2;
				break;
			case 2:
				switch (buttonId) {
				case 1:
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "Send me to lumbridge now.");
					stage = 10;
					break;
				case 2:
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "Who are you?");
					stage = 20;
					break;
				case 3:
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "Can I decide later?");
					stage = 30;
					break;
				case 4:
					interpreter.sendDialogues(player, FacialExpression.NORMAL, "I'll stay here for the Tutorial.");
					stage = 40;
					break;
				}
				break;
			case 30:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Sure. You'll find me all over this land.", "Ask me again any time you like.");
				stage = 31;
				break;
			case 31:
				interpreter.sendOptions("What would you like to say?", "Send me to Lumbridge now.", "Who are you?", "Can I decide later?", "I'll stay here for the Tutorial.");
				stage = 2;
				break;
			case 40:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Good choice. Let me know if you change your mind.");
				stage = 99;
				break;
			case 20:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "My name's Skippy. Normally I live down by", "Rimmington. You should come and see me when you're", "passing.");
				stage = 21;
				break;
			case 21:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Just lately the Council wants to let people skip the", "Tutorial, so - ha ha ha - they dump the job on Skippy.", "Bah!");
				stage = 22;
				break;
			case 22:
				interpreter.sendOptions("What would you like to say?", "Send me to Lumbridge now.", "Who are you?", "Can I decide later?", "I'll stay here for the Tutorial.");
				stage = 2;
				break;
			case 10:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "Right on. I'll read you the official messages first, then", "send you on your way. Ahem..");
				stage = 11;
				break;
			case 11:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "When you get to the mainland you will find yourself in", "the town of Lumbridge. If you want some ideas on", "where to go next, talk to my friend the Lumbridge", "Guide. You can't miss him; he's holding a big staff with");
				stage = 12;
				break;
			case 12:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "a question mark on the end. He also has  white beard", "and carries a rucksack full of scrolls. There are also", "many tutors willing to teach you about the many skills", "you could learn.");
				stage = 13;
				break;
			case 13:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "When you get to Lumbridge, look for '?' icon on your", "mini-map. The Lumbridge  Guide or one of the other", "tutors should be standing near there. The Lumbridge", "Guide should be standing slightly to the north-east of");
				stage = 14;
				break;
			case 14:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "the castle's courtyard and the others you will find", "scattered around Lumbridge.");
				stage = 15;
				break;
			case 15:
				interpreter.sendDialogues(npc, FacialExpression.NORMAL, "If all else fails, visit the " + GameWorld.getName() + " website for a whole", "chestload of information on quests, skills and minigames", "as well as a very good starter's guide.");
				stage = 500;
				if (!IRONMAN) {
					stage = 520;
				}
				break;
			case 500:
				npc("The last thing for you to do is to decide", "if you would like an Ironman account?");
				stage++;
				break;
			case 501:
				player.removeAttribute("ironMode");
				player.removeAttribute("ironPermanent");
				options("Yes, please make me an Ironman.", "What is an Ironman account?", "No, thanks. I don't want to be an Ironman.");
				stage++;
				break;
			case 502:
				switch (buttonId) {
				case 1:
					player("Yes, please make me an Ironman.");
					stage = 506;
					break;
				case 2:
					player("What is an Ironman account?");
					stage = 530;
					break;
				case 3:
					player("No, thanks. I don't want to be an Ironman.");
					stage++;
					break;
				}
				break;
			case 503:
				npc("Are you sure you don't want an Ironman", "account?");
				stage++;
				break;
			case 504:
				options("Yes I am sure.", "No, let me decide.");
				stage++;
				break;
			case 505:
				player(buttonId == 1 ? "Yes I am sure." : "No, let me decide.");
				stage = buttonId == 1 ? 520 : 500;
				break;
			case 506:
				interpreter.sendOptions("Select a Mode", "Standard", "<col=FF0000>Ultimate</col>", "Go back.");
				stage++;
				break;
			case 507:
				switch (buttonId) {
				case 1:
				case 2:
					npc("You have chosen the: " + (buttonId == 1 ? "Standard" : "<col=FF0000>Ultimate</col>") + " Ironman mode.");
					stage = 508;
					player.setAttribute("ironMode", IronmanMode.values()[buttonId]);
					break;
				case 3:
					npc("The last thing for you to do is to decide", "if you would like an Ironman account mode?");
					stage = 501;
					break;
				}
				break;
			case 508:
				interpreter.sendOptions("Are you sure?", "Yes.", "No.");
				stage++;
				break;
			case 509:
				switch (buttonId) {
				case 1:
					player("Yes, I am sure.");
					stage++;
					break;
				case 2:
					player("No, I want to change it.");
					stage = 506;
					break;
				}
				break;
			case 510:
				npc("You have the ability to remove the Ironman restrictions", "once you get to the mainland, however, you can only", "do this once. You can also make this permanent and", "never have the ability to remove the Ironman restriction.");
				stage++;
				break;
			case 511:
				npc("Would you like this mode to be permanent?");
				stage++;
				break;
			case 512:
				options("Yes, please.", "No, thanks.");
				stage++;
				break;
			case 513:
				player.setAttribute("ironPermanent", buttonId == 1);
				npc("Are you sure?");
				stage++;
				break;
			case 514:
				options("Yes.", "No.");
				stage++;
				break;
			case 515:
				player(buttonId == 1 ? "Yes." : "No.");
				stage = buttonId == 1 ? 516 : 511;
				break;
			case 516:
				player.getIronmanManager().setPermanent(player.getAttribute("ironPermanent", false));
				player.getIronmanManager().setMode(player.getAttribute("ironMode", IronmanMode.NONE));
				MSPacketRepository.sendInfoUpdate(player);
				npc("Congratulations, you have setup your Ironman account.", "You will travel into the mainland now.");
				stage = 520;
				break;
			case 530:
				npc("An Ironman account is a style of playing where players", "are completely self-sufficient.");
				stage++;
				break;
			case 531:
				npc("A standard Ironman does not receieve items or", "assistance from other players. They cannot trade, stake,", "receieve PK loot, scavenge dropped items, nor player", "certain minigames.");
				stage++;
				break;
			case 532:
				npc("In addition to the standard Ironman rules. An", "Ultimate Ironman cannot use banks, nor retain any", "items on death in dangerous areas.");
				stage = 501;
				break;
			case 520:
				player.removeAttribute("tut-island");
				player.getConfigManager().set(1021, 0);
				player.getConfigManager().set(281, 1000, true); //Collapsible sidestones.
				player.getProperties().setTeleportLocation(Location.create(3233, 3230, 0));
				TutorialSession.getExtension(player).setStage(72);
				player.getInterfaceManager().closeOverlay();
				player.getInventory().clear();
				player.getEquipment().clear();
				player.getBank().clear();
				player.getBank().add(new Item(995, 25));
				player.getInterfaceManager().openDefaultTabs();
				player.getInventory().add(STARTER_PACK);
				interpreter.sendDialogue("Welcome to Lumbridge! To get more help click on the Lumbridge ", "Guide or one of the Tutors - these can be found by looking", "for the question mark icon on your mini-map. If you are lost", "at any time, look for a signpost or use the Lumbridge Home Teleport.");
				player.getPacketDispatch().sendMessage("Welcome to " + GameWorld.getName() + ".");
				player.unlock();
				TutorialSession.getExtension(player).setStage(TutorialSession.MAX_STAGE + 1);
				stage = 7;
				if (player.getIronmanManager().isIronman() && player.getSettings().isAcceptAid()) {
					player.getSettings().toggleAcceptAid();
				}
				MSPacketRepository.sendInfoUpdate(player);
				int slot = player.getAttribute("tut-island:hi_slot", -1);
				if (slot < 0 || slot >= HintIconManager.MAXIMUM_SIZE) {
					break;
				}
				player.removeAttribute("tut-island:hi_slot");
				HintIconManager.removeHintIcon(player, slot);
				break;
			case 7:
				end();
				break;
			case 99:
				end();
				TutorialStage.load(player, TutorialSession.getExtension(player).getStage(), false);
				break;
			}
			return true;
		}
		switch (TutorialSession.getExtension(player).getStage()) {
		case 67:
			switch (stage) {
			case 0:
				interpreter.sendDialogues(946, null, "Good day, newcomer. My name is Terrova. I'm here", "to tell you about Magic. Let's start by opening your", "spell list.");
				stage = 1;
				break;
			case 1:
				end();
				TutorialStage.load(player, 68, false);
				break;
			}
			break;
		case 69:
			switch (stage) {
			case 0:
				if (!player.getInventory().contains(556, 1) && !player.getInventory().contains(558, 1)) {
					if (player.getInventory().freeSlots() > 2) {
						interpreter.sendDoubleItemMessage(RUNES[0], RUNES[1], "Terrova gives you five <col=08088A>air runes</col> and five <col=08088A>mind runes</col>!");
						player.getInventory().add(RUNES[0], RUNES[1]);
						stage = 3;
					}
				} else {
					end();
					TutorialStage.load(player, 70, false);
				}
				break;
			case 3:
				end();
				TutorialStage.load(player, 70, false);
				break;
			}
			break;
		case 70:
			switch (stage) {
			case 0:
				break;
			case 3:
				end();
				TutorialStage.load(player, 70, false);
				break;
			}
			break;
		}
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] {/* skippy */2796, /* magic instructor */946 };
	}

}
