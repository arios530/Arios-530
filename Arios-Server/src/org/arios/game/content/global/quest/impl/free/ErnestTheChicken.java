package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;

/**
 * Represents the ernest the chicken quest.
 * @author 'Vexia
 */
public final class ErnestTheChicken extends Quest {

	/**
	 * The name of the quest.
	 */
	public static String NAME = "Ernest the Chicken";

	/**
	 * Represents the oil can item.
	 */
	private static final Item OIL_CAN = new Item(277);

	/**
	 * Represents the pressure guage item.
	 */
	private static final Item PRESSURE_GAUGE = new Item(271);

	/**
	 * Represents the rubber tube item.
	 */
	private static final Item RUBBER_TUBE = new Item(276);

	/**
	 * Represents the coins item.
	 */
	private static final Item COINS = new Item(995, 300);

	/**
	 * Constructs a new {@code ErnestTheChicken} {@code Object}.
	 * @param player the player.
	 */
	public ErnestTheChicken(Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		if (getStage() == 0) {
			player.getPacketDispatch().sendString(getBlue() + "I can start this quest by speaking to " + getRed() + "Veronica " + getBlue() + "who is", 275, 4+ 7);
			player.getPacketDispatch().sendString(getBlue() + "outside " + getRed() + "Draynor Manor", 275, 5+ 7);
			player.getPacketDispatch().sendString(getBlue() + "There aren't any requirements for this quest.", 275, 7+ 7);
		} else if (stage == 10) {
			line("<str>I have spoken to Veronica", 4+ 7);
			line(getBlue() + "I need to find " + getRed() + "Ernest", 6+ 7);
			line(getBlue() + "He went into " + getRed() + "Draynor Manor " + getBlue() + "and hasn't returned", 7+ 7);
		} else if (stage == 20) {
			line("<str>I have spoken to Veronica", 4+ 7);
			line("<str>I've spoken to Dr Oddenstein, and discovered Ernest is a", 6+ 7);
			line("<str>chicken", 7+ 7);
			line(getBlue() + "I need to bring " + getRed() + "Dr Oddenstein " + getBlue() + "parts for his machine", 9+ 7);
			line(player.getInventory().containsItem(OIL_CAN) ? "<str>1 Oil Can" : getRed() + "1 Oil Can", 10);
			line(player.getInventory().containsItem(PRESSURE_GAUGE) ? "<str>1 Pressure Gauge" : getRed() + "1 Pressure Gauge", 11);
			line(player.getInventory().containsItem(RUBBER_TUBE) ? "<str>1 Rubber Tube" : getRed() + "1 Rubber Tube", 12);
		} else if (stage == 100) {
			line("<str>I have spoken to Veronica", 4+ 7);
			line("<str>I have collected all the parts for the machine", 6+ 7);
			line("<str>Dr Oddenstein thanked me for helping fix his machine", 8+ 7);
			line("<str>We turned Ernest back to normal and he rewarded me", 9+ 7);
			line("<col=FF0000>QUEST COMPLETE!</col>", 10+ 7);
		}
	}

	@Override
	public void finish() {
		player.unlock();
		player.getPacketDispatch().sendMessage("Ernest hands you 300 coins.");
		super.finalize();
		player.getPacketDispatch().sendString("4 Quest Points", 277, 8 + 2);
		player.getPacketDispatch().sendString("300 coins", 277, 9 + 2);
		player.getPacketDispatch().sendString("You have completed the Ernest The Chicken Quest!", 277, 2 + 2);
		player.getGameAttributes().removeAttribute("piranhas-killed");
		player.getGameAttributes().removeAttribute("pressure-gauge");
		player.getPacketDispatch().sendItemZoomOnInterface(314, 230, 277, 3 + 2);// feather
		// on
		// inter.
		if (!player.getInventory().add(COINS)) {// 300 coins
			GroundItemManager.create(COINS, player.getLocation(), player);
		}
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 32, 3 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 32, 1 };
		} else {
			return new int[] { 32, 0 };
		}
	}
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 19;
	}

	@Override
	public int getQuestPoints() {
		return 4;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
