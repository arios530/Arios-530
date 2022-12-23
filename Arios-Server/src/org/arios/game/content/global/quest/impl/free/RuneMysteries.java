package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the rune mysteries fortress quest.
 * @author 'Vexia
 */
public class RuneMysteries extends Quest {

	/**
	 * The quest name.
	 */
	public static final String NAME = "Rune Mysteries";

	/**
	 * Constructs a new {@code RuneMysteries} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public RuneMysteries(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		if (getStage() == 0) {
			line(getBlue() + "I can start this quest by speaking to " + getRed() + "Duke Horacio " + getBlue() + "of", 4+ 7);
			line(getRed() + "Lumbridge, " + getBlue() + "upstairs in " + getRed() + "Lumbridge Castle.", 5+ 7);
		}
		if (getStage() == 10) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line(getBlue() + "I need to find the " + getRed() + "Head Wizard " + getBlue() + "and give him the " + getRed() + "Talisman", 8+ 7);
		}
		if (getStage() == 20) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line("<str>I gave the Talisman to the Wizard but I didn't want to help", 8+ 7);
			line("<str>him in his research right now.", 9+ 7);
			line(getBlue() + "I should talk to " + getRed() + "Sedridor " + getBlue() + "again to continue this quest.", 10+ 7);
		}
		if (getStage() == 30) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line("<str>I gave the Talisman to the Head of the Tower and", 8+ 7);
			line("<str>agreed to help him with his research into rune stones.", 9+ 7);
			line(getBlue() + "I should take this " + getRed() + "Research Package " + getBlue() + "to " + getRed() + "Aubury " + getBlue() + "in " + getRed() + "Varrock", 10+ 7);
		}
		if (getStage() == 40) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line("<str>I gave the Talisman to the Head of the Tower and", 8+ 7);
			line("<str>agreed to help him with his research into rune stones.", 9+ 7);
			line("<str>I took the research package to Varrock and delivered it.", 10+ 7);
			line(getBlue() + "I should speak to " + getRed() + "Aubury " + getBlue() + "again when he has finished", 11+ 7);
			line(getBlue() + "examining the " + getRed() + "research package " + getBlue() + " I have delivered to him.", 12+ 7);
		}
		if (getStage() == 50) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line("<str>I gave the Talisman to the Head of the Tower and", 8+ 7);
			line("<str>agreed to help him with his research into rune stones.", 9+ 7);
			line("<str>I took the research package to Varrock and delivered it.", 10+ 7);
			line("<str>Aubury was interested in the research package and gave", 11+ 7);
			line("<str>me his own research notes to deliver to Sedridor.", 12+ 7);
			line(getBlue() + "I should take the " + getRed() + "notes " + getBlue() + "to " + getRed() + "Sedridor " + getBlue() + "and see what he says", 13+ 7);
		}
		if (stage == 100) {
			line("<str>I spoke to Duke Horacio and he showed me a strange", 4+ 7);
			line("<str>talisman that had been found by one of his subjects.", 5+ 7);
			line("<str>I agreed to take it to the Wizards' Tower, South West of", 6+ 7);
			line("<str>Lumbridge for further examination by the wizards.", 7+ 7);
			line("<str>I gave the Talisman to the Head of the Tower and", 8+ 7);
			line("<str>agreed to help him with his research into rune stones.", 9+ 7);
			line("<str>I took the research package to Varrock and delivered it.", 10+ 7);
			line("<str>Aubury was interested in the research package and gave", 11+ 7);
			line("<str>me his own research notes to deliver to Sedridor.", 12+ 7);
			line("<str>I brought Sedridor the research notes that Aubury had", 13+ 7);
			line("<str>compiled so that he could compare their research. They", 14+ 7);
			line("<str>They discovered that it was now possible to create new rune", 15+ 7);
			line("<str>stones, a skill that had been thought lost forever.", 16+ 7);
			line("<str>In return for all of my help they taught me how to do this,", 17+ 7);
			line("<str>and will teleport me to mine blank runes anytime.", 18+ 7);
			line("<col=FF0000>QUEST COMPLETE!", 20 + 7);
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("Runecrafting skill", 277, 9 + 2);
		player.getPacketDispatch().sendString("Air talisman", 277, 10 + 2);
		player.getPacketDispatch().sendString("You have completed the Rune Mysteries Quest!", 277, 2 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(1438, 240, 277, 3 + 2);
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 63, 6 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 63, 1 };
		} else {
			return new int[] { 63, 0 };
		}
	}
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}


	@Override
	public int getIndex() {
		return 27;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getQuestPoints() {
		return 1;
	}
}
