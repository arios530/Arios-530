package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the witch's potion quest.
 * @author 'Vexia
 */
public class WitchsPotion extends Quest {

	public static final String NAME = "Witch's Potion";

	/**
	 * Constructs a new {@code WitchsPotion} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public WitchsPotion(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (getStage()) {
		case 0:
			line(getBlue() + "I can start this quest by speaking to " + getRed() + "Hetty " + getBlue() + "in her house in", 4+ 7);
			line(getRed() + "Rimmington" + getBlue() + ", West of " + getRed() + "Port Sarim", 5+ 7);
			break;
		case 20:
			line("<str>I spoke to Hetty in her house at Rimmington. hetty told me", 4+ 7);
			line("<str>she could increase my magic power if I can bring her", 5+ 7);
			line("<str>certain ingredients for a potion.", 6+ 7);
			line(getBlue() + "Hetty needs me to bring her the following:", 8+ 7);
			if (player.getInventory().contains(1957, 1)) {
				line("<str>I have an onion with me", 9+ 7);
			} else {
				line(getRed() + "An onion", 9+ 7);
			}
			if (player.getInventory().contains(1957, 1)) {
				line("<str>I have an onion with me", 9+ 7);
			} else {
				line(getRed() + "An onion", 9+ 7);
			}
			if (player.getInventory().contains(300, 1)) {
				line("<str>I have a rat's tail with me", 10+ 7);
			} else {
				line(getRed() + "A rat's tail", 10+ 7);
			}
			if (player.getInventory().contains(2146, 1)) {
				line("<str>I have a piece of burnt meat with me", 11+ 7);
			} else {
				line(getRed() + "A piece of burnt meat", 11+ 7);
			}
			if (player.getInventory().contains(221, 1)) {
				line("<str>I have an eye of newt with me", 12+ 7);
			} else {
				line(getRed() + "An eye of newt", 12+ 7);
			}
			break;
		case 40:
			line("<str>I brought her an onion, a rat's tail, a piece of burnt meat", 4+ 7);
			line("<str>and eye of newt which she used to make a potion.", 5+ 7);
			line(getBlue() + "I should drink from the " + getRed() + "cauldron" + getBlue() + " and improve my magic!", 7+ 7);
			break;
		case 100:
			line("<str>I brought her an onion, a rat's tail, a piece of burnt meat", 4+ 7);
			line("<str>and an eye of newt which she used to make a potion.", 5+ 7);
			line("<str>I drank from the cauldron and my magic power increased!", 7+ 7);
			line("<col=FF0000>QUEST COMPLETE!", 9+ 7);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getConfigManager().set(101, player.getQuestRepository().getPoints());
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("325 Magic XP", 277, 9 + 2);
		player.getSkills().addExperience(Skills.MAGIC, 325);
		player.getInterfaceManager().closeChatbox();
		player.getPacketDispatch().sendItemZoomOnInterface(221, 240, 277, 3 + 2);
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 67, 3 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 67, 1 };
		} else {
			return new int[] { 67, 0 };
		}
	}
	
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}


	@Override
	public int getIndex() {
		return 31;
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
