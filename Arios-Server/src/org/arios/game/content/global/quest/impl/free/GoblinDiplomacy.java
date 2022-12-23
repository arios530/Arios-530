package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;

/**
 * Represents the demon slayer quest.
 * @author 'Vexia
 */
public class GoblinDiplomacy extends Quest {

	/**
	 * The name of the quest.
	 */
	public static final String NAME = "Goblin Diplomacy";

	/**
	 * Repreresents the orange goblin mail.
	 */
	private static final Item ORANGE_MAIL = new Item(286);

	/**
	 * Represents the blue goblin mail.
	 */
	private static final Item BLUE_MAIL = new Item(287);

	/**
	 * Represents the goblin mail.
	 */
	private static final Item GOBLIN_MAIL = new Item(288);

	/**
	 * Represents the gold bar item.
	 */
	private static final Item GOLD_BAR = new Item(2357);

	/**
	 * Constructs a new {@code GoblinDiplomacy} {@code Object}.
	 * @param player the player.
	 */
	public GoblinDiplomacy(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (stage) {
		case 0:
			player.getPacketDispatch().sendString(getBlue() + "I can start this quest by speaking to " + getRed() + "Generals Wartface", 275, 4 + 7);
			player.getPacketDispatch().sendString(getRed() + "and Bentnoze " + getBlue() + "in the " + getRed() + " Goblin Village.", 275, 5+ 7);
			player.getPacketDispatch().sendString(getBlue() + "There are no requirements for this quest.", 275, 6+ 7);
			break;
		case 10:
			line("<str>I spoke to Generals Wartface and Bentnoze in the Goblin", 4+ 7);
			line("<str>Village and found that the goblins were on the bring of civil", 5+ 7);
			line("<str>war over the colour of their armour. I offered to help the", 6+ 7);
			line("<str>generals by finding another colour that they both like.", 7+ 7);
			if (player.getInventory().containsItem(ORANGE_MAIL)) {
				line(BLUE + "I have some " + RED + "Orange Goblin Armour. " + BLUE + "I should show it to the", 9+ 7);
				line(BLUE + "generals.", 10+ 7);
			} else {
				line(BLUE + "I should bring the goblins some " + RED + "Orange Goblin Armour", 9+ 7);
				line(BLUE + "Maybe the generals will know where to get some.", 10+ 7);
			}
			break;
		case 20:
			line("<str>I spoke to Generals Wartface and Bentnoze in the Goblin", 4+ 7);
			line("<str>Village and found that the goblins were on the bring of civil", 5+ 7);
			line("<str>war over the colour of their armour. I offered to help the", 6+ 7);
			line("<str>generals by finding another colour that they both like.", 7+ 7);
			line("<str>I brought the goblins some orange goblin armour, but they", 9+ 7);
			line("<str>didn't like it.", 10+ 7);
			if (player.getInventory().containsItem(BLUE_MAIL)) {
				line(BLUE + "I have some " + RED + "Blue Goblin Armour. " + BLUE + "I should show it to the", 12+ 7);
				line(BLUE + "generals.", 13+ 7);
			} else {
				line(BLUE + "I should bring the goblins s+ 7+ 7);e " + RED + "Blue Goblin Armour", 12+ 7);
				line(BLUE + "Maybe the generals will know where to get some.", 13+ 7);
			}
			break;
		case 30:
			line("<str>I spoke to Generals Wartface and Bentnoze in the Goblin", 4+ 7);
			line("<str>Village and found that the goblins were on the bring of civil", 5+ 7);
			line("<str>war over the colour of their armour. I offered to help the", 6+ 7);
			line("<str>generals by finding another colour that they both like.", 7+ 7);
			line("<str>I brought the goblins some orange goblin armour, but they", 9+ 7);
			line("<str>didn't like it.", 10+ 7);
			line("<str>I brought the goblins some blue goblin armour, but they", 12+ 7);
			line("<str>didn't like it.", 13+ 7);
			if (player.getInventory().containsItem(GOBLIN_MAIL)) {
				line(BLUE + "I have some " + RED + "Brown Goblin Armour. " + BLUE + "I should show it to the", 12+ 7);
				line(BLUE + "generals.", 13+ 7);
			} else {
				line(BLUE + "I should bring the goblins some " + RED + "Brown Goblin Armour", 12+ 7);
				line(BLUE + "Maybe the generals will know where to get some.", 13+ 7);
			}
			break;
		case 100:
			line("<str>I spoke to Generals Wartface and Bentnoze in the Goblin", 4+ 7);
			line("<str>Village and found that the goblins were on the bring of civil", 5+ 7);
			line("<str>war over the colour of their armour. I offered to help the", 6+ 7);
			line("<str>generals by finding another colour that they both like.", 7+ 7);
			line("<str>I brought the goblins some orange goblin armour, but they", 9+ 7);
			line("<str>didn't like it.", 10+ 7);
			line("<str>I brought the goblins some blue goblin armour, but they", 12+ 7);
			line("<str>didn't like it.", 13+ 7);
			line("<str>Unfortunately the goblins were very stupid, and it turned", 15+ 7);
			line("<str>out that they liked the original colour the most. That's goblins", 16+ 7);
			line("<str>for you I guess.", 17+ 7);
			line("<col=FF0000>QUEST COMPLETE!</col>", 18+ 7);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("5 Quests Points", 277, 8 + 2);
		player.getPacketDispatch().sendString("200 Crafting XP", 277, 9 + 2);
		player.getPacketDispatch().sendString("A gold bar", 277, 10 + 2);
		player.getPacketDispatch().sendString("You have completed the Goblin Diplomacy Quest!", 277, 2 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(288, 230, 277, 3 + 2);
		player.getSkills().addExperience(Skills.CRAFTING, 200);
		if (!player.getInventory().add(GOLD_BAR)) {
			GroundItemManager.create(GOLD_BAR, player);
		}
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.NOT_STARTED) {
			return new int[] { 62, 0 };
		}
		if (stage == 10) {
			return new int[] { 62, 1 };
		} else if (stage == 20) {
			return new int[] { 62, 4 };
		} else if (stage == 30) {
			return new int[] { 62, 5 };
		} else if (stage == 100) {
			return new int[] { 62, 6 };
		}
		return new int[] { 62, 0 };
	}
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 20;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getQuestPoints() {
		return 5;
	}
}
