package org.arios.game.content.global.quest.impl.free;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;

/**
 * Represents the demon slayer quest.
 * @author 'Vexia
 * @date 3/1/14
 */
public class DemonSlayer extends Quest {

	/**
	 * Represents the silverlight item.
	 */
	public static final Item SILVERLIGHT = new Item(2402);

	/**
	 * Represents the incantations we can use to generate a total incantation.
	 */
	private static final String[] INCANTATIONS = new String[] { "Carlem", "Aber", "Purchai", "Camerinthum", "Gabindo" };

	/**
	 * Represents the first key item.
	 */
	public static final Item FIRST_KEY = new Item(2401);

	/**
	 * Represents the second key item.
	 */
	public static final Item SECOND_KEY = new Item(2400);

	/**
	 * Represents the third key item.
	 */
	public static final Item THIRD_KEY = new Item(2399);

	/**
	 * Constructs a new {@code DemonSlayer} {@code Object}.
	 * @param player
	 */
	public DemonSlayer(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (getStage()) {
		case 0:
			player.getPacketDispatch().sendString(getBlue() + "I can start this quest by speaking to the " + getRed() + "Gypsy " + getBlue() + "in the " + getRed() + "tent", 275, 4 + 8);
			player.getPacketDispatch().sendString(getBlue() + "in " + getRed() + "Varrock's main square", 275, 5+ 8);
			player.getPacketDispatch().sendString(getBlue() + "I must be able to defeat a level 27 " + getRed() + "apocalyptic demon" + getBlue() + " !", 275, 7+ 8);
			break;
		case 10:
			line("<str>I spoke to the Gypsy in Varrock Square who saw my future.", 4+ 8);
			line("<str>Unfortunately it involved killing a demon who nearly", 5+ 8);
			line("<str>destroyed Varrock over 150 years ago.", 6+ 7);
			line(BLUE + "To defeat the " + RED + "demon " + BLUE + "I need the magical sword " + RED + "Silverlight.", 8+ 8);
			line(BLUE + "I should ask " + RED + "Sir Prysin " + BLUE + "in " + RED + "Varrock Palace " + BLUE + "where it is.", 9+ 8);
			break;
		case 20:
			line("<str>I spoke to the Gypsy in Varrock Square who saw my future.", 4+ 8);
			line("<str>Unfortunately it involved killing a demon who nearly", 5+ 8);
			line("<str>destroyed Varrock over 150 years ago.", 6+ 8);
			line(BLUE + "To defeat the " + RED + "demon " + BLUE + "I need the magical sword " + RED + "Silverlight.", 8+ 8);
			line(RED + "Sir Prysin " + BLUE + "needs " + RED + "3 keys " + BLUE + "before he can give me " + RED + "Silverlight.", 9+ 8);
			if (player.getInventory().containsItem(FIRST_KEY) && player.getInventory().containsItem(SECOND_KEY) && player.getInventory().containsItem(THIRD_KEY)) {
				line(BLUE + "Now I have " + RED + "all 3 keys " + BLUE + "I should go and speak to " + RED + "Sir Prysin", 9+ 8);
				line(BLUE + "and collect the magical sword " + RED + "Silverlight " + BLUE + "from him.", 10+ 8);
			} else {
				line(hasKey(player, FIRST_KEY) ? "<str>I have the 1st Key with me." : BLUE + "The " + RED + "1st Key " + BLUE + "was dropped down the palace kitchen drains.", 11+ 8);
				line(hasKey(player, SECOND_KEY) ? "<str>I have the 2nd Key with me." : BLUE + "The " + RED + "2nd Key " + BLUE + "is with Captain Rovin in Varrock Palace.", 12+ 8);
				line(hasKey(player, THIRD_KEY) ? "<str>I Have the 3rd key with me." : BLUE + "The " + RED + "3rd Key " + BLUE + "is with the Wizard Traiborn at the Wizards' Tower.", 13+ 8);
				if (player.getAttribute("demon-slayer:traiborn", false)) {
					line(BLUE + "The " + RED + "3rd Key " + BLUE + "is with Wizard Traiborn at the Wizards' Tower.", 13);
					line(RED + "Traiborn " + BLUE + "needs " + RED + "25  " + BLUE + "more " + RED + "bones.", 14);
				}
			}
			break;
		case 30:
			line("<str>I spoke to the Gypsy in Varrock Square who saw my future.", 4+ 8);
			line("<str>Unfortunately it involved killing a demon who nearly", 5+ 8);
			line("<str>destroyed Varrock over 150 years ago.", 6+ 8);
			line("<str>I reclaimed the magical sword Silverlight from Sir Prysin.", 8+ 8);
			line(BLUE + "Now I should go to the stone circle south of the city and", 9+ 8);
			line(BLUE + "destroy " + RED + "Delrith " + BLUE + "using " + RED + "Silverlight" + BLUE + "!", 10+ 8);
			break;
		case 100:
			line("<str>I spoke to the Gypsy in Varrock Square who saw my future.", 4+ 8);
			line("<str>Unfortunately it involved killing a demon who nearly", 5+ 8);
			line("<str>destroyed Varrock over 150 years ago.", 6+ 7);
			line("<str>I reclaimed the magical sword Silverlight from Sir Prysin.", 8+ 8);
			line("<str>Using its power I managed to destroy the demon Delrith", 9+ 8);
			line("<str>like the great hero Wally did many years before.", 10+ 8);
			line("<col=FF0000>QUEST COMPLETE!", 12+ 7);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("3 Quests Points", 277, 8+ 2);
		player.getPacketDispatch().sendString("Silverlight", 277, 9+ 2);
		player.getPacketDispatch().sendItemZoomOnInterface(SILVERLIGHT.getId(), 230, 277, 5);
		player.removeAttribute("demon-slayer:traiborn");
		player.removeAttribute("demon-slayer:incantation");
		player.removeAttribute("demon-slayer:poured");
		player.removeAttribute("demon-slayer:received");
	}

	@Override
	public int[] getConfig() {
		int id = 222;
		if (state == QuestState.COMPLETED) {
			return new int[] { id, 3 };
		} else if (state == QuestState.STARTED) {
			return new int[] { id, 1 };
		} else {
			return new int[] { id, 0 };
		}
	}

	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 16;
	}

	@Override
	public String getName() {
		return "Demon Slayer";
	}

	@Override
	public int getQuestPoints() {
		return 3;
	}

	/**
	 * Method used to check if the player has the key.
	 * @param item the item.
	 * @return <code>True</code> if they have the key.
	 */
	private final boolean hasKey(final Player player, final Item item) {
		return player.getInventory().containsItem(item) || player.getBank().containsItem(item);
	}

	/**
	 * Gets the incantation the player was given.
	 * @param player the player.
	 * @return the incantation given.
	 */
	public static String getIncantation(final Player player) {
		return player.getAttribute("demon-slayer:incantation", generateIncantation(player));
	}

	/**
	 * Method used to generate an incantation.
	 * @param player the player.
	 * @return the incantation.
	 */
	public static String generateIncantation(final Player player) {
		final String incantation = player.getAttribute("demon-slayer:incantation", generateIncantation());
		player.setAttribute("/save:demon-slayer:incantation", incantation);
		return incantation;
	}

	/**
	 * Gets the generated incantation.
	 * @return the incantation.
	 */
	private final static String generateIncantation() {
		List<String> incantations = new ArrayList<>();
		for (String s : INCANTATIONS) {
			incantations.add(s);
		}
		Collections.shuffle(incantations);
		return incantations.get(0) + "... " + incantations.get(1) + "... " + incantations.get(2) + "... " + incantations.get(3) + "... " + incantations.get(4);
	}
}
