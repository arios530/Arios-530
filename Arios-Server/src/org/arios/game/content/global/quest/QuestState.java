package org.arios.game.content.global.quest;

/**
 * Represents a quest state.
 * @author 'Vexia
 */
public enum QuestState {
	NOT_STARTED, STARTED, COMPLETED;

	/**
	 * Gets the value by the state.
	 * @param quest the quest.
	 * @param config the config.
	 * @param started if started.
	 * @param finished if finished.
	 * @return the config value.
	 */
	public static int[] getForConfig(Quest quest, int config, int started, int finished) {
		return new int[] { config, (quest.getState() == NOT_STARTED ? 0 : quest.getState() == STARTED ? started : finished) };
	}
}
