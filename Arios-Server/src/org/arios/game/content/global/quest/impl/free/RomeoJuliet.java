package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the romeo and juliet quest.
 * @author 'Vexia
 */
public class RomeoJuliet extends Quest {

	/**
	 * Constructs a new {@code RomeoJuliet} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public RomeoJuliet(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (getStage()) {
		case 0:
			line(getBlue() + "I can start this quest by speaking to " + getRed() + "Romeo " + getBlue() + "in " + getRed() + "Varrock", 4 + 7);
			line(getBlue() + "central square by the " + getRed() + "fountain.", 5+ 7);
			break;
		case 10:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line(getBlue() + "All I need to do now is find " + getRed() + "Juliet.", 6+ 7);
			break;
		case 20:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message + 7+ 7); take back", 7+ 7);
			line(getBlue() + "I should take the " + getRed() + "message " + getBlue() + "from" + getRed() + " Juliet " + getBlue() + "to" + getRed() + " Romeo.", 8+ 7);
			break;
		case 30:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message to take back", 7+ 7);
			line("<str>I delivered the message to Romeo, and he was sad to hear", 8+ 7);
			line("<str>that Juliet's father opposed their marriage. However, he", 9+ 7);
			line("<str>said that Father Lawrence might be able to overcome this.", 10+ 7);
			line(getBlue() + "I should find" + getRed() + " Father Lawrence " + getBlue() + "and see how we can help.", 11+ 7);
			break;
		case 40:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message to take back", 7+ 7);
			line("<str>I delivered the message to Romeo, and he was sad to hear", 8+ 7);
			line("<str>that Juliet's father opposed their marriage. However, he", 9+ 7);
			line("<str>said that Father Lawrence might be able to overcome this.", 10+ 7);
			line("<str>I found Father Lawrence and he suggested the use of a", 11+ 7);
			line("<str>potion to fool Juliet's father that she is dead so that", 12+ 7);
			line("<str>Romeo and Juliet can be together in peace.", 13+ 7);
			line(getBlue() + "I need to find the " + getRed() + "Apothecary" + " " + getBlue() + "to make a " + getRed() + "cadava potion.", 14+ 7);
			break;
		case 50:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message to take back", 7+ 7);
			line("<str>I delivered the message to Romeo, and he was sad to hear", 8+ 7);
			line("<str>that Juliet's father opposed their marriage. However, he", 9+ 7);
			line("<str>said that Father Lawrence might be able to overcome this.", 10+ 7);
			line("<str>I found Father Lawrence and he suggested the use of a", 11+ 7);
			line("<str>potion to fool Juliet's father that she is dead so that", 12+ 7);
			line("<str>Romeo and Juliet can be together in peace.", 13+ 7);
			line("<str>I went to the Apothecary regarding making this cadava", 14+ 7);
			line("<str>potion, and he told me to bring him some cadava berries", 15+ 7);
			if (!player.getInventory().contains(753, 1)) {
				line(getBlue() + "I will have to find some " + getRed() + "Cadava berries" + getBlue() + " somewhere!", 16+ 7);
			} else {
				line(getBlue() + "I should take these " + getRed() + "cadava berries" + getBlue() + " to the " + getRed() + "Apothechary.", 16+ 7);
			}
			break;
		case 60:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message to take back", 7+ 7);
			line("<str>I delivered the message to Romeo, and he was sad to hear", 8+ 7);
			line("<str>that Juliet's father opposed their marriage. However, he", 9+ 7);
			line("<str>said that Father Lawrence might be able to overcome this.", 10+ 7);
			line("<str>I found Father Lawrence and he suggested the use of a", 11+ 7);
			line("<str>potion to fool Juliet's father that she is dead so that", 12+ 7);
			line("<str>Romeo and Juliet can be together in peace.", 13+ 7);
			line("<str>I went to the Apothecary regarding making this cadava", 14+ 7);
			line("<str>potion, and he told me to bring him some cadava berries", 15+ 7);
			line(getBlue() + "I should take this " + getRed() + "cadava potion " + getBlue() + "to " + getRed() + "Juliet.", 16+ 7);
			break;
		case 70:
			line("<str>I have agreed to find Juliet for Romeo and tell her how he", 4+ 7);
			line("<str>feels. For some reason he can't just do this himself.", 5+ 7);
			line("<str>I found Juliet on the Western edge of Varrock, and told", 6+ 7);
			line("<str>her about Romeo. She gave me a message to take back", 7+ 7);
			line("<str>I delivered the message to Romeo, and he was sad to hear", 8+ 7);
			line("<str>that Juliet's father opposed their marriage. However, he", 9+ 7);
			line("<str>said that Father Lawrence might be able to overcome this.", 10+ 7);
			line("<str>I found Father Lawrence and he suggested the use of a", 11+ 7);
			line("<str>potion to fool Juliet's father that she is dead so that", 12+ 7);
			line("<str>Romeo and Juliet can be together in peace.", 13+ 7);
			line("<str>I went to the Apothecary regarding making this cadava", 14+ 7);
			line("<str>potion, and he told me to bring him some cadava berries", 15+ 7);
			line("<str>After the Apothecary made me the potion, I delivered it to", 16+ 7);
			line("<str>Juliet. She asked me to tell Romeo the plan.", 17+ 7);
			line(getBlue() + "I have to find " + getRed() + "Romeo" + getBlue() + " and tell him what's happened.", 18+ 7);
			break;
		case 100:
			line("<str>Romeo and Juliet can be together in peace.", 4+ 7);
			line("<str>I went to the Apothecary regarding making this cadava", 5+ 7);
			line("<str>potion, and he told me to bring him some cadava berries.", 6+ 7);
			line("<str>After the Apothecary made me the potion, I delivered it to", 7+ 7);
			line("<str>Juliet. She asked me to tell Romeo the plan.", 8+ 7);
			line("<str>I told Romeo what was going to happen, but I'm not exactly", 9+ 7);
			line("<str>sure he understood what was happening. Ah well, I was", 10+ 7);
			line("<str>rewarded for all of my help regardless.", 11+ 7);
			line("<col=FF0000>QUEST COMPLETE!", 12+ 7);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("5 Quest Points", 277, 8 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(756, 240, 277, 3 + 2);
		player.getQuestRepository().update(player);
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 144, 100 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 144, 1 };
		} else {
			return new int[] { 144, 0 };
		}
	}

	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	
	@Override
	public int getIndex() {
		return 26;
	}

	@Override
	public String getName() {
		return "Romeo & Juliet";
	}

	@Override
	public int getQuestPoints() {
		return 5;
	}
}
