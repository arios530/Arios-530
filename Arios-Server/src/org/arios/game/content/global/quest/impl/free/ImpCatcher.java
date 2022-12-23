package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
/**
 * Represents the imp catcher quest.
 * 
 * @author 'Vexia
 *
 */
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;

/**
 * Represents the imp catcher quest.
 * @author 'Vexia
 * @date 15/12/2013
 */
public class ImpCatcher extends Quest {

	/**
	 * Represents the black bead item.
	 */
	private static final Item BLACK_BEAD = new Item(1474);

	/**
	 * Represents the red bead item.
	 */
	private static final Item RED_BEAD = new Item(1470);

	/**
	 * Represents the white bead item.
	 */
	private static final Item WHITE_BEAD = new Item(1476);

	/**
	 * Represents the yellow bead item.
	 */
	private static final Item YELLOW_BEAD = new Item(1472);

	/**
	 * Represents the amulet item.
	 */
	private static final Item AMULET = new Item(1478);

	/**
	 * Constructs a new {@code ImpCatcher} {@code Object}.
	 * @param player the player.
	 */
	public ImpCatcher(Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		if (getStage() == 0) {
			player.getPacketDispatch().sendString(getBlue() + "I can start this quest by speaking to " + getRed() + "Wizard Mizgog " + getBlue() + "who is", 275, 4+ 7);
			player.getPacketDispatch().sendString(getBlue() + "in the " + getRed() + "Wizard's Tower", 275, 5+ 7);
			player.getPacketDispatch().sendString(getBlue() + "There are no requirements for this quest.", 275, 7+ 7);
		} else if (getStage() == 10) {
			line("<str>I have spoken to Wizard Mizgog.", 4+ 7);
			line(getBlue() + "I need to collect some items by killing " + getRed() + " Imps.", 6+ 7);
			if (player.getInventory().containItems(BLACK_BEAD.getId(), RED_BEAD.getId(), YELLOW_BEAD.getId(), WHITE_BEAD.getId())) {
				line(getBlue() + "I have collected all the missing beads and need to return", 6+ 7);
				line(getBlue() + "them to " + getRed() + "Wizard Mizgog.", 7+ 7);
				return;
			}
			if (player.getInventory().containsItem(BLACK_BEAD)) {
				line("<str>1 Black Bead", 7+ 7);
			} else {
				line(getRed() + "1 Black Bead", 7+ 7);
			}
			if (player.getInventory().containsItem(RED_BEAD)) {
				line("<str>1 Red Bead", 8+ 7);
			} else {
				line(getRed() + "1 Red Bead", 8+ 7);
			}
			if (player.getInventory().containsItem(WHITE_BEAD)) {
				line("<str>1 White Bead", 9+ 7);
			} else {
				line(getRed() + "1 White Bead", 9+ 7);
			}
			if (player.getInventory().containsItem(YELLOW_BEAD)) {
				line("<str>1 Yellow Bead", 10+ 7);
			} else {
				line(getRed() + "1 Yellow Bead", 10+ 7);
			}
		} else {
			line("<str>I have spoken to Wizard Mizgog.", 4+ 7);
			line("<str>I have collected all the beads.", 6+ 7);
			line("<str>Wizard Mizgog thanked me for finding his beads and gave", 8+ 7);
			line("<str>me and Amulet of Accuracy.", 9+ 7);
			line("<col=FF0000>QUEST COMPLETE!", 10+ 7);
		}
	}

	@Override
	public void finish() {
		player.unlock();
		player.getPacketDispatch().sendMessage("The Wizard hands you an amulet.");
		super.finalize();
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("875 Magic XP", 277, 9 + 2);
		player.getPacketDispatch().sendString("Amulet of Accuracy", 277, 10 + 2);
		player.getPacketDispatch().sendString("You have completed the Imp Catcher Quest!", 277, 2 + 2);
		player.getPacketDispatch().sendItemZoomOnInterface(AMULET.getId(), 230, 277, 3 + 2);
		player.getSkills().addExperience(Skills.MAGIC, 875);
		// 16170
		GameObject table = RegionManager.getObject(Location.create(3102, 3163, 2));
		if (table.getId() != 16170) {
			ObjectBuilder.replace(table, table.transform(16170), 80);
		}
		if (!player.getInventory().add(AMULET)) {
			GroundItemManager.create(AMULET, player.getLocation(), player);
		}
	}

	@Override
	public int[] getConfig() {
		int id = 160;
		if (state == QuestState.COMPLETED) {
			return new int[] { id, 2 };
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
		return 21;
	}

	@Override
	public String getName() {
		return "Imp Catcher";
	}

	@Override
	public int getQuestPoints() {
		return 1;
	}
}
