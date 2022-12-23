package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;

/**
 * Represents The KnightSword quest.
 * @author 'Vexia
 * @date 26/12/2013
 */
public class TheKnightsSword extends Quest {

	/**
	 * Constructs a new {@code TheKnightsSword} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public TheKnightsSword(final Player player) {
		super(player);
	}

	/**
	 * Represents the portrait item.
	 */
	private static final Item PORTRAIT = new Item(666);

	@Override
	public void update() {
		super.clear();
		switch (stage) {
		case 0:
			player.getPacketDispatch().sendString(getBlue() + "I can start this quest by speaking to the " + getRed() + "Squire " + getBlue() + "in the", 275, 4+ 7);
			player.getPacketDispatch().sendString(getBlue() + "courtyard of the " + getRed() + "White Knights' Castle " + getBlue() + "in " + getRed() + "southern Falador", 275, 5+ 7);
			player.getPacketDispatch().sendString(getBlue() + "To complete this quest I need:", 275, 6+ 7);
			player.getPacketDispatch().sendString(getRed() + "Level 10 Mining", 275, 7+ 7);
			player.getPacketDispatch().sendString(getBlue() + "and to be unafraid of " + getRed() + "Level 57 Ice Warriors.", 275, 8+ 7);
			break;
		case 10:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could only be made by an Imcando Dwarf.", 5+ 7);
			line(getBlue() + "The Squire suggests I speak to " + getRed() + "Reldo " + getBlue() + "in the " + getRed() + " Varrock Palace", 6+ 7);
			line(getRed() + "Library " + getBlue() + "for information about the " + getRed() + "Imcando Dwarves", 7+ 7);
			break;
		case 20:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could only be made by an Imcando Dwarf.", 5+ 7);
			line(getBlue() + "Reldo couldn't give me much information about the", 6+ 7);
			line(getRed() + "Imcando " + getBlue() + "except a few live on the " + getRed() + "southern peninsula of", 7+ 7);
			line(getRed() + "Asgarnia, " + getBlue() + "they dislike stangers, and LOVE " + getRed() + "redberry pies.", 8+ 7);
			break;
		case 30:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could only be made by an Imcando Dwarf.", 5+ 7);
			line("<str>I found an Imcando Dwarf named Thurgo thanks to", 6+ 7);
			line("<str>information provided by Reldo. He wasn't very talkative", 7+ 7);
			line("<str>until I gave him a Redberry pie, which he gobbled up.", 8+ 7);
			line(getBlue() + "He will help me now I have gained his trust thorugh " + getRed() + "pie", 9+ 7);
			break;
		case 40:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could only be made by an Imcando Dwarf.", 5+ 7);
			line("<str>I found an Imcando Dwarf named Thurgo thanks to", 6+ 7);
			line("<str>information provided by Reldo. He wasn't very talkative", 7+ 7);
			line("<str>until I gave him a Redberry pie, which he gobbled up.", 8+ 7);
			line(getRed() + "Thurgo " + getBlue() + "needs a " + getRed() + "picture of the sword " + getBlue() + "before he can help.", 9+ 7);
			line(getBlue() + "I should probably ask the " + getRed() + "Squire " + getBlue() + "about obtaining one", 10+ 7);
			break;
		case 50:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could only be made by an Imcando Dwarf.", 5+ 7);
			line("<str>I found an Imcando Dwarf named Thurgo thanks to", 6+ 7);
			line("<str>information provided by Reldo. He wasn't very talkative", 7+ 7);
			line("<str>until I gave him a Redberry pie, which he gobbled up.", 8+ 7);
			line("<str>Thurgo needed a picture of the sword to replace.", 9+ 7);
			if (!player.getInventory().containsItem(PORTRAIT)) {
				line(getBlue() + "The Squire told me about a " + getRed() + "portrait ", 10+ 7);
				line(getBlue() + "which has a " + getRed() + "picture of the sword " + getBlue() + "in " + getRed() + "Sir Vyvin's room", 11+ 7);
			} else {
				line(getBlue() + "I now have a picture of the " + getRed() + "Knight's Sword " + getBlue() + "- I should take it", 10+ 7);
				line(getBlue() + "to " + getRed() + "Thurgo " + getBlue() + "so that he can duplicate it.", 11+ 7);
			}
			break;
		case 60:
			line("<str>I told the Squire I would help him to replace the sword he", 4+ 7);
			line("<str>has lost. It could onfly be made by an Imcando Dwarf.", 5+ 7);
			line("<str>I found an Imcando Dwarf named Thurgo thanks to", 6+ 7);
			line("<str>information provided by Reldo. He wasn't very talkative", 7+ 7);
			line("<str>until I gave him a Redberry pie, which he gobbled up.", 8+ 7);
			line("<str>Thurgo needed a picture of the sword before he could", 9+ 7);
			line("<str>start work on a replacement. I took him a portrait of it.", 10+ 7);
			if (player.getInventory().contains(667, 1) || player.getEquipment().contains(667, 1) || player.getBank().contains(667, 1)) {
				line("<str>Thurgo has now smithed me a replica of Sir Vyvin's sword.", 11+ 7);
				line(getBlue() + "I should return it to the " + getRed() + "Squire " + getBlue() + "for my " + getRed() + "reward", 13+ 7);
			} else {
				line(getBlue() + "according to " + getRed() + "Thurgo " + getBlue() + "to make a " + getRed() + "replica sword " + getBlue() + "he will need", 11+ 7);
				line(getRed() + "two Iron Bars " + getBlue() + "and some " + getRed() + "Blurite Ore. Blurite Ore " + getBlue() + "can only be", 12+ 7);
				line(getBlue() + "found " + getRed() + "deep in the caves below Thurgo's house", 13+ 7);
			}
			break;
		case 100:
			line("<str>Thurgo needed a picture of the sword before he could", 4+ 7);
			line("<str>start work on a replacement. I took him a portrait of it.", 5+ 7);
			line("<str>After bringing Thurgo two iron bars and some blurite ore", 6+ 7);
			line("<str>he made me a fine replica of Sir Vyvin's Sword, which I", 7+ 7);
			line("<str>returned to the Squire for a reward.", 8+ 7);
			line("<col=FF0000>QUEST COMPLETE!</col>", 10+ 7);
			break;
		}

	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("12,725 Smithing XP", 277, 9 + 2);
		player.getPacketDispatch().sendString("You have completed the Knight's Sword Quest!", 277, 2 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(667, 230, 277, 3 + 2);
		player.getSkills().addExperience(Skills.SMITHING, 12725);
	}

	@Override
	public int[] getConfig() {
		int id = 122;
		if (state == QuestState.COMPLETED) {
			return new int[] { id, 7 };
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
		return 22;
	}

	@Override
	public String getName() {
		return "The Knight's Sword";
	}

	@Override
	public int getQuestPoints() {
		return 1;
	}
}
