package plugin.quest.merlincrystal;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.MerlinCrystal;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.plugin.Plugin;

/**
 * Represents the quest node plugin handler.
 * @author Splinter
 */
public class MerlinCrystalOptionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		NPCDefinition.forId(247).getConfigurations().put("option:attack", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final Quest quest = player.getQuestRepository().getQuest(MerlinCrystal.NAME);
		int id = node instanceof GameObject ? ((GameObject) node).getId() : ((NPC) node).getId();
		switch (id) {
		case 247:
			if (quest.getStage() < 40) {
				player.getProperties().getCombatPulse().stop();
				player.getPacketDispatch().sendMessage("You have no reason to attack this valiant knight.");
				return true;
			}
			if (quest.getStage() == 40) {
				player.getProperties().getCombatPulse().attack(node);
			}
			if (quest.getStage() > 40) {
				player.getProperties().getCombatPulse().stop();
				player.getPacketDispatch().sendMessage("You've already bested Sir Mordred in combat.");
				return true;
			}
			break;
		}
		return true;
	}

}
