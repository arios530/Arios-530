package org.arios.game.content.global.quest.impl.free;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;

/**
 * Doric the dwarf is happy to let you use his anvils but first he would like
 * you to run an errand for him.
 * @author 'Vexia
 */
public final class DoricsQuest extends Quest {

	/**
	 * Constructs a new {@code DoricsQuest} {@code Object}.
	 * @param player the player.
	 */
	public DoricsQuest(Player player) {
		super(player);
	}

	@Override
	public void update() {
		clear();
		switch (stage) {
		case 0:
			player.getPacketDispatch().sendString("<col=08088A>I can start this quest by speaking to <col=8A0808>Doric</col> <col=08088A>who is <col=8A0808>North of", 275, 4+ 7);
			player.getPacketDispatch().sendString("<col=8A0808>Falador", 275, 5+ 7);
			player.getPacketDispatch().sendString("<col=08088A>There aren't any requirements but <col=8A0808>Level 15 Mining <col=08088A>will help", 275, 7+ 7);
			break;
		case 1:
			player.getPacketDispatch().sendString("<str>I have spoken to <col=8A0808>Doric</str>", 275, 4+ 7);
			player.getPacketDispatch().sendString("<col=08088A>I need to collect some items and bring them to <col=8A0808>Doric</col>", 275, 6+ 7);
			player.getPacketDispatch().sendString(player.getInventory().contains(434, 6) ? "<str>6 Clay</str>" : "<col=8A0808>6 Clay", 275, 7+ 7);
			player.getPacketDispatch().sendString(player.getInventory().contains(436, 4) ? "<str>4 Copper Ore</str>" : "<col=8A0808>4 Copper Ore", 275, 8+ 7);
			player.getPacketDispatch().sendString(player.getInventory().contains(440, 2) ? "<str>2 Iron Ore</str>" : "<col=8A0808>2 Iron Ore", 275, 9+ 7);
			break;
		case 100:
			player.getPacketDispatch().sendString("<str>I have spoken to <col=8A0808>Doric</str>", 275, 4+ 7);
			player.getPacketDispatch().sendString("<col=FF0000>QUEST COMPLETE!", 275, 10+ 7);
			player.getPacketDispatch().sendString("<str> I have collected some Clay, Copper Ore, and Iron Ore", 275, 6+ 7);
			player.getPacketDispatch().sendString("<str>Doric rewarded me for all my hard work", 275, 8+ 7);
			player.getPacketDispatch().sendString("<str>I can now use Doric's Anvils whenever I want", 275, 9+ 7);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("1 Quest Point", 277, 8 + 2);
		player.getPacketDispatch().sendString("1300 Mining XP", 277, 9 + 2);
		player.getPacketDispatch().sendString("180 coins", 277, 10 + 2);
		player.getPacketDispatch().sendString("Use of Doric's Anvils", 277, 11 + 2);
		if (!player.getInventory().add(new Item(995, 180))) {
			GroundItemManager.create(new Item(995, 180), player.getLocation());
		}
		player.getSkills().addExperience(Skills.MINING, 1300);
		player.getPacketDispatch().sendItemZoomOnInterface(1269, 240, 277, 5);
		player.getInterfaceManager().closeChatbox();
		player.getPacketDispatch().sendString("You have completed Doric's Quest!", 277, 2 + 2);
		setStage(100);
		setState(QuestState.COMPLETED);
		player.getPacketDispatch().sendMessage("Congratulations! Quest complete!");
	}

	@Override
	public String getName() {
		return "Doric's Quest";
	}

	@Override
	public int[] getConfig() {
		if (state == QuestState.COMPLETED) {
			return new int[] { 31, 100 };
		} else if (state == QuestState.STARTED) {
			return new int[] { 31, 1 };
		} else {
			return new int[] { 31, 0 };
		}
	}
	
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}


	@Override
	public int getIndex() {
		return 17;
	}

	@Override
	public int getQuestPoints() {
		return 1;
	}

}
