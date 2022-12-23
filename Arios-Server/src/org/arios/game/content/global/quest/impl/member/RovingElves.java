package org.arios.game.content.global.quest.impl.member;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;

/**
 * The Roving Elves quest.
 * @author Splinter
 */
public class RovingElves extends Quest {

	/**
	 * The name of the quest.
	 */
	public static final String NAME = "Roving Elves";

	/**
	 * Crystal Bow (full)
	 */
	public static final Item CRYSTAL_BOW_FULL = new Item(4214);

	/**
	 * Crystal Shield (full)
	 */
	public static final Item CRYSTAL_SHIELD_FULL = new Item(4225);

	/**
	 * The little seed.
	 */
	public static final Item CONSECRATION_SEED = new Item(4205);

	/**
	 * The little seed. (charged)
	 */
	public static final Item CONSECRATION_SEED_CHARGED = new Item(4206);

	/**
	 * Crystal seed, ready to be made into equipment
	 */
	public static final Item CRYSTAL_SEED = new Item(4207);

	/**
	 * Constructs a new {@Code RovingElves} {@Code Object}
	 * @param player the player.
	 */
	public RovingElves(Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (stage) {
		case 0:
			line("<blue>I can start this quest by talking to <red>Islwyn<blue> found in <red>Isafdar.<n><n><blue>Minimum requirements:<n><red>I must have completed the Waterfall quest<n><red>I must be able to kill a level 84 moss giant unarmed", 11);
			break;
		case 10:
			line("<blue>It appears that when I recovered <red>Glarial's ashes<blue> from<n><blue>her <red>tomb<blue> near the waterfall, I disturbed her peace.<n><n><blue>I talked to <red>Islwyn,<blue> an elf that I found in the woods.<n><blue>When I offered to help he said I should talk with <red>Eluned,<n><blue>she will tell me how <red>elven consecration<blue> is done.", 11);
			break;
		case 15:
			if (player.getInventory().contains(CONSECRATION_SEED.getId(), 1)) {
				line("<str>It appears that when I recovered Glarial's ashes from<n><str>her tomb near the waterfall, I disturbed her peace.<n><str>I talked to Islwyn, an elf that I found in the woods.<n><str>When I offered to help he said I should talk with Eluned,<n><str>she will tell me how elven consecration is done.<n><n><str>Eluned told me that I must acquire the<str> old consecration<n><str> seed <str>from the <str>guardian spirit<str> in <str>Glarial's old tomb.<n><n><str>Once I have the old seed I will need to return to Eluned, who<n><str>will re-enchant it for me.<n><n><blue>I have the <red>consecration seed. <blue>I should return to <red>Eluned<n><blue>and have her enchant it for me.", 11);
			} else {
				line("<str>It appears that when I recovered Glarial's ashes from<n><str>her tomb near the waterfall, I disturbed her peace.<n><str>I talked to Islwyn, an elf that I found in the woods.<n><str>When I offered to help he said I should talk with Eluned,<n><str>she will tell me how elven consecration is done.<n><n><blue>Eluned told me that I must acquire the<red> old consecration<n><red> seed <blue>from the <red>guardian spirit<blue> in <red>Glarial's old tomb.<n><n><blue>Once I have the old seed I will need to return to Eluned, who<n><blue>will re-enchant it for me.", 11);
			}
			if (player.getInventory().contains(CONSECRATION_SEED_CHARGED.getId(), 1)) {
				line("<str>It appears that when I recovered Glarial's ashes from<n><str>her tomb near the waterfall, I disturbed her peace.<n><str>I talked to Islwyn, an elf that I found in the woods.<n><str>When I offered to help he said I should talk with Eluned,<n><str>she will tell me how elven consecration is done.<n><n><str>Eluned told me that I must acquire the<str> old consecration<n><str> seed <str>from the <str>guardian spirit<str> in <str>Glarial's old tomb.<n><n><str>Once I have the old seed I will need to return to Eluned, who<n><str>will re-enchant it for me.<n><n><blue>I have the <red>charged consecration seed<blue>.<n><blue>I need to head to the treasure room under the <red>waterfall <blue>and<n><blue> plant the <red>consecration seed<blue> near the <red>chalice<blue> in order to<n><blue> free Glarial's spirit.", 11);
			}
			break;
		case 20:
			line("<str>It appears that when I recovered Glarial's ashes from<n><str>her tomb near the waterfall, I disturbed her peace.<n><str>I talked to Islwyn, an elf that I found in the woods.<n><str>When I offered to help he said I should talk with Eluned,<n><str>she will tell me how elven consecration is done.<n><n><str>Eluned told me that I must acquire the<str> old consecration<n><str> seed <str>from the <str>guardian spirit<str> in <str>Glarial's old tomb.<n><n><str>Once I have the old seed I will need to return to Eluned, who<n><str>will re-enchant it for me.<n><n><str>I have the <str>charged consecration seed<str>.<n><str>I need to head to the treasure room under the <str>waterfall <str>and<n><str> plant the <str>consecration seed<str> near the <str>chalice<str> in order to<n><str> free Glarial's spirit.<n><n><blue>I have freed <red>Glarial's spirit <blue>by planting the consecration seed near<n><blue>the chalice under the waterfall. The seed I planted<n><blue> vanished. I should go speak to <red>Eluned<blue> again.", 11);
			break;
		case 100:
			line("<str>It appears that when I recovered Glarial's ashes from<n><str>her tomb near the waterfall, I disturbed her peace.<n><str>I talked to Islwyn, an elf that I found in the woods.<n><str>When I offered to help he said I should talk with Eluned,<n><str>she will tell me how elven consecration is done.<n><n><str>Eluned told me that I must acquire the<str> old consecration<n><str> seed <str>from the <str>guardian spirit<str> in <str>Glarial's old tomb.<n><n><str>Once I have the old seed I will need to return to Eluned, who<n><str>will re-enchant it for me.<n><n><str>I have the <str>charged consecration seed<str>.<n><str>I need to head to the treasure room under the <str>waterfall <str>and<n><str> plant the <str>consecration seed<str> near the <str>chalice<str> in order to<n><str> free Glarial's spirit.<n><n><str>I have freed <str>Glarial's spirit <str>by planting the consecration seed near<n><str>the chalice under the waterfall. The seed I planted<n><str> vanished. I should go speak to <str>Eluned<str> again.<n><n><col=FF0000>QUEST COMPLETE!</col>", 11);
			break;
		}
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8+ 2);
		player.getPacketDispatch().sendString("Used elf equipment.", 277, 9+ 2);
		player.getPacketDispatch().sendString("10000 Strength XP", 277, 10+ 2);
		player.getPacketDispatch().sendString("You have completed Roving Elves!", 277, 2+ 2);
		player.getPacketDispatch().sendItemZoomOnInterface(CRYSTAL_BOW_FULL.getId(), 235, 277, 3+ 2);
		player.getSkills().addExperience(Skills.STRENGTH, 10000);
		player.getQuestRepository().update(player);
	}

	@Override
	public boolean hasRequirements() {
		return player.getQuestRepository().isComplete(WaterFall.NAME);
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 402, 6 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 402, 1 };
		} else {
			return new int[] { 402, 0 };
		}
	}

	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 105;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getQuestPoints() {
		return 1;
	}

	@Override
	public boolean isInDevelopment() {
		return false;
	}

}
