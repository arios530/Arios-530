package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the vampie quest.
 * @author 'Vexia
 */
public class VampireSlayer extends Quest {

	/**
	 * Constructs a new {@code VampireSlayer} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public VampireSlayer(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		if (getStage() == 0) {
			line(getBlue() + "I can start this quest by speaking to " + getRed() + "Morgan who is in", 4+ 7);
			line(getRed() + "Draynor Village.", 5+ 7);
			line(getBlue() + "Requirements:", 7+ 7);
			line(getBlue() + "Must be able to kill a level 34 " + getRed() + "Vampire.", 8+ 7);
		}
		if (getStage() == 10) {
			line("<str>I spoke to Morgan in Draynor Village. He told me that the", 4+ 7);
			line("<str>locals are being attacked by a terrifying Vampire!", 5+ 7);
			line(getBlue() + "I need to speak to " + getRed() + "Dr Harlow " + getBlue() + "who can normally be found in", 7+ 7);
			line(getBlue() + "the " + getRed() + " Blue Moon Inn" + getBlue() + " in " + getRed() + "Varrock.", 8+ 7);
		}
		if (getStage() == 20) {
			line("<str>I spoke to Morgan in Draynor Village. He told me that the", 4+ 7);
			line("<str>locals are being attacked by a terrifying Vampire!", 5+ 7);
			line("<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7+ 7);
			line("<str>he kept asking me to buy him drinks.", 8+ 7);
			line(getBlue() + "I should see what advice " + getRed() + "Dr Harlow" + getBlue() + " can give me about killing", 10+ 7);
			line(getRed() + "Vampires.", 11+ 7);
			line(getBlue() + "When I'm ready, I should go to " + getRed() + "Draynor Manor" + getBlue() + ", north of", 12+ 7);
			line(getBlue() + "Draynor to kill the " + getRed() + "Vampire" + getBlue() + " that's living in the basement.", 13+ 7);
		}
		if (getStage() == 30) {
			line("<str>I spoke to Morgan in Draynor Village. He told me that the", 4+ 7);
			line("<str>locals are being attacked by a terrifying Vampire!", 5+ 7);
			line("<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7+ 7);
			line("<str>he kept asking me to buy him drinks.", 8+ 7);
			line("<str>Dr Harlow gave me a stake to finish off the Vampire then", 10+ 7);
			line("<str>I'm fighting it. I've got a hammer to drive the stake deep", 11+ 7);
			line("<str>into the Vampire's chest, and I have some garlic which", 12+ 7);
			line("<str>should weaken the Vampire.", 13+ 7);
			line(getBlue() + "When i'm ready, I should go to " + getRed() + "Draynor Manor" + getBlue() + ", north of", 14+ 7);
			line(getBlue() + "Draynor to kill the " + getRed() + "Vampire" + getBlue() + " that's living in the basement.", 15+ 7);
		}
		if (getStage() == 100) {
			line("<str>I spoke to Morgan in Draynor Village. He told me that the", 4+ 7);
			line("<str>locals are being attacked by a terrifying Vampire!", 5+ 7);
			line("<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7+ 7);
			line("<str>he kept asking me to buy him drinks.", 8+ 7);
			line("<str>I have killed the Vampire, Count Draynor. Draynor Village is", 10+ 7);
			line("<str>now safe!", 11+ 7);
			line("<col=FF0000>QUEST COMPLETE!", 12+ 7);
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getSkills().addExperience(Skills.ATTACK, 4825);
		player.getPacketDispatch().sendString("3 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("4825 Attack XP", 277, 9 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(1549, 260, 277, 3 + 2);
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 178, 3 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 178, 1 };
		} else {
			return new int[] { 178, 0 };
		}
	}
	
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}


	@Override
	public int getIndex() {
		return 30;
	}

	@Override
	public String getName() {
		return "Vampire Slayer";
	}

	@Override
	public int getQuestPoints() {
		return 3;
	}
}
