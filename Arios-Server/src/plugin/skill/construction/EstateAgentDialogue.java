package plugin.skill.construction;

import org.arios.game.content.dialogue.DialoguePlugin;
import org.arios.game.content.skill.member.construction.ConConstants;
import org.arios.game.content.skill.member.construction.HouseLocation;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.GameWorld;

/**
 * Handles the estate agent dialogue.
 * @author Vexia
 */
public class EstateAgentDialogue extends DialoguePlugin {

	/**
	 * Constructs a new {@code EstateAgentDialogue} {@code Object}
	 * @param player the player.
	 */
	public EstateAgentDialogue(Player player) {
		super(player);
	}

	@Override
	public DialoguePlugin newInstance(Player player) {
		return new EstateAgentDialogue(player);
	}

	@Override
	public boolean open(Object... args) {
		npc("Hello. Welcome to the " + GameWorld.getName() + " Housing Agency!", "What can I do for you?");
		return true;
	}

	@Override
	public boolean handle(int interfaceId, int buttonId) {
		switch (stage) {
		case 0:
			if (!player.getHouseManager().hasHouse()) {
				options("How can I get a house?", "Tell me about houses");
				stage = 1;
			} else {
				options("Can you move my house please?", "Can you redecorate my house please?", "Could I have a Construction guidebook?", "Tell me about houses", "Tell me about that skillcape you're wearing.");
				stage = 20;
			}
			break;
		case 1:
			switch (buttonId) {
			case 1:
				player("How can I get a house?");
				stage = 10;
				break;
			case 2:
				player("Tell me about houses!");
				stage = 2;
				break;
			}
			break;
		case 2:
			npc("It all came out of the wizards' experiments. They found", "a way to fold space, so that they could pack many acres", "of land into an area only a foot across.");
			stage++;
			break;
		case 3:
			npc("They created several folded-space regions across", GameWorld.getName() + ". Each one contains hundreds of small plots", "where people can build houses.");
			stage++;
			break;
		case 4:
			player("Ah, so that's how everyone can have a house without", "them cluttering up the world!");
			stage++;
			break;
		case 5:
			npc("Quite. The wizards didn't want to get bogged down in", "the business side of things so they hired me to sell the", "houses.");
			stage++;
			break;
		case 6:
			npc("There are various other people across " + GameWorld.getName() + " who", "can help you furnish your house. You should start by", "buying planks from the sawmill operator in Varrock.");
			stage++;
			break;
		case 7:
			end();
			break;
		case 10:
			npc("I can sell you a starting house in Rimmington for", "1000 coins. As you increase your construction skill you", "will be able to have your house moved to other areas", "and redecorated in other styles.");
			stage++;
			break;
		case 11:
			npc("Do you want to buy a starter house?");
			stage++;
			break;
		case 12:
			options("Yes please!", "No thanks");
			stage++;
			break;
		case 13:
			switch (buttonId) {
			case 1:
				player("Yes please!");
				stage = 16;
				break;
			case 2:
				player("No thanks.");
				stage++;
				break;
			}
			break;
		case 14:
			npc("Well enjoy your player-owned cardboard box or", "wherever you're going to sleep tonight!");
			stage++;
			break;
		case 15:
			end();
			break;
		case 16:
			if (!player.getInventory().contains(995, 1000)) {
				player("But I don't have that money on me.");
				stage++;
				break;
			}
			if (player.getInventory().remove(new Item(995, 1000))) {
				player.getHouseManager().create(HouseLocation.RIMMINGTON);
				npc("Thank you. Go through the Rimmington house portal", "and you will find your house ready for you to start", "building in it.");
				stage = 18;
			}
			break;
		case 17:
			npc("Well come back when you have it then.");
			stage = 15;
		case 18:
			player.getInventory().add(new Item(ConConstants.CONSTRUCTION_GUIDE), player);
			npc("This book will help you start building your house.");
			stage++;
			break;
		case 19:
			end();
			break;
		case 20:
			switch (buttonId) {
			case 1:
				player("Can you move my house please?");
				stage = 60;
				break;
			case 2:
				player("Can you redecorate my house please?");
				stage = 21;
				break;
			case 3:
				player("Could I have a Construction guidebook?");
				stage = 30;
				break;
			case 4:
				player("Tell me about houses!");
				stage = 2;
				break;
			case 5:
				player("Tell me about that skillcape you're wearing.");
				stage = 50;
				break;
			case 21:

				break;
			case 30:
				break;
			case 40:
				break;
			case 50:
				break;
			case 60:
				npc("Certainly. Where would you like it moved to?");
				stage++;
				break;
			case 61:
				options("Rimmington (5,000)", "Taverley (5,000)", "Pollnivneach (7,500)", "Rellekka (10,000)", "More...");
				stage++;
				break;
			case 62:
				switch (buttonId) {
				case 5:
					options("Brimhaven (15,000)", "Yanille (25,000)", "...Previous");
					stage++;
					break;
				default:
					configureMove(HouseLocation.values()[5 + buttonId]);
					break;
				}
				break;
			case 63:
				switch (buttonId) {
				case 3:
					options("Rimmington (5,000)", "Taverley (5,000)", "Pollnivneach (7,500)", "Rellekka (10,000)", "More...");
					stage++;
					break;
				default:
					configureMove(HouseLocation.values()[buttonId]);
					break;
				}
				break;
			case 64:
				HouseLocation moveLoc = player.getAttribute("con:moveLoc", HouseLocation.RIMMINGTON);
				if (player.getHouseManager().getLocation() == moveLoc) {
					npc("Your house is already there!");
					break;
				}
				if (!moveLoc.hasLevel(player)) {
					npc("I'm afraid you don't have a high enough construction", "level to move there. You need to have level " + moveLoc.getLevel() + ".");
					break;
				}
				break;
			}
			break;
		}
		return true;
	}

	/**
	 * Configures the move.
	 * @param string the string.
	 */
	private void configureMove(HouseLocation location) {
		player.setAttribute("con:moveLoc", location);
		player("To " + location.getName() + " please!");
		stage = 64;
	}

	@Override
	public int[] getIds() {
		return new int[] { 4247 };
	}

}
