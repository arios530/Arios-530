package plugin.interaction.inter;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.diary.AchievementDiary;
import org.arios.game.node.entity.player.link.diary.DiaryType;
import org.arios.game.world.GameWorld;
import org.arios.plugin.Plugin;

/**
 * Handles the quest tab action buttons.
 * @author Emperor
 * @author Vexia
 */
public class QuestTabInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(274, this); // Quests
		ComponentDefinition.put(259, this); // Achievement diary
		return this;
	}

	@Override
	public boolean handle(Player p, Component component, int opcode, int button, int slot, int itemId) {
		if (TutorialSession.getExtension(p).getStage() < TutorialSession.MAX_STAGE) {
			return true;
		}
		p.getPulseManager().clear();
		switch (component.getId()) {
		case 274:
			if (!GameWorld.isEconomyWorld()) {
				p.getSavedData().getSpawnData().handleButton(p, button);
			}
			switch (button) {
			case 3:
				p.getAchievementDiaryManager().openTab();
				return true;
			case 10:
				break;
			default:
				if (GameWorld.isEconomyWorld()) {
					Quest quest = p.getQuestRepository().getQuest(button);
					if (quest != null) {
						p.getInterfaceManager().open(new Component(275));
						quest.update();
						return true;
					}
				}
				return false;
			}
			break;
		case 259:
			switch (button) {
			case 8:
				p.getInterfaceManager().openTab(2, new Component(274));
				return true;
			default:
				AchievementDiary diary = p.getAchievementDiaryManager().getDiary(DiaryType.forChild(button));
				if (diary != null) {
					diary.open(p);
				}
				return true;
			}
		}
		return true;
	}

}