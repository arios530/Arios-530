package plugin.skill.fletching;

import org.arios.game.content.global.quest.impl.member.TouristTrap;
import org.arios.game.content.skill.member.fletching.items.darts.Dart;
import org.arios.game.interaction.NodeUsageEvent;
import org.arios.game.interaction.UseWithHandler;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to create a dart.
 * @author 'Vexia
 * @version 1.0
 */
public final class DartCreatePlugin extends UseWithHandler {

	/**
	 * Constructs a new {@code DartCreatePlugin} {@code Object}.
	 */
	public DartCreatePlugin() {
		super(314);
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		for (Dart dart : Dart.values()) {
			addHandler(dart.getItem().getId(), ITEM_TYPE, this);
		}
		return this;
	}

	@Override
	public boolean handle(NodeUsageEvent event) {
		if (event.getPlayer().getQuestRepository().getQuest(TouristTrap.NAME).getStage() < 60) {
			event.getPlayer().getPacketDispatch().sendMessage("You need to start Tourist Trap in order to do this.");
			return true;
		}
		event.getPlayer().getDialogueInterpreter().open(328933, event.getUsedItem(), event.getBaseItem());
		return true;
	}

}
