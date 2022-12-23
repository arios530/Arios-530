package plugin.interaction.object;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the witchs potion plugin.
 * @author 'Vexia
 * @version 1.0
 */
public final class WitchsPotionPlugin extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(2024).getConfigurations().put("option:drink from", this);
		ObjectDefinition.forId(2024).getConfigurations().put("option:Drink From", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		final Quest quest = player.getQuestRepository().getQuest("Witch's Potion");
		switch (quest.getStage()) {
		case 20:
		case 100:
			player.getDialogueInterpreter().sendDialogues(player, null, "As nice as that looks I think I'll give it a miss for now.");
			break;
		case 40:
			player.getDialogueInterpreter().open(307, true, 1);
			break;
		}
		return true;
	}

}
