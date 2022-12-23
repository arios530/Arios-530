package org.arios.game.content.global;

import org.arios.game.node.entity.player.Player;

/**
 * Holds the data for the Daily tasks.
 * TODO: Probably should make it like Adam Rod's Achievement Diary system instead of this shit I have now.
 * @author Splinter
 */
public enum DailyTasks {

	KBD_KILLS(new int[] { 0 }, "King Black Dragon kills"), PC_GAME_COMPLETED(new int[] { 1 }, "Complete a game of Pest Control"), KILL_REVENANTS(new int[] { 2 }, "Kill 20 Revenants"),

	;

	/**
	 * The name of the task to be completed, to be displayed visually to the player.
	 */
	private final String name;

	/**
	 * The ID of the task.
	 */
	private final int[] taskId;

	/**
	 * Constructs a new {@code DailyTasks} {@code Object}.
	 * @param data the attribute data
	 * @param name the npc's string name
	 */
	DailyTasks(final int[] taskId, final String name) {
		this.taskId = taskId;
		this.name = name;
	}

	/**
	 * Gets the task name
	 * @return the name of the task
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the taskID
	 * @return the ID
	 */
	public int[] getTaskId() {
		return taskId;
	}

	/**
	 * Gets the type for the ID
	 * @param id the id of the task
	 * @return the DailyTask
	 */
	public static DailyTasks forTask(final int id) {
		for (DailyTasks task : DailyTasks.values()) {
			for (int i : task.getTaskId()) {
				if (id == i) {
					return task;
				}
			}
		}
		return null;
	}

	/**
	 * Add to the player's task completion for the task
	 * @param p the player who advanced their task
	 * @param id the task ID
	 * @param count the amount to add
	 */
	public static void addtoTasks(Player p, int taskid, int count) {
		if (p == null || taskid < 0 || count <= 0) {
			p.sendMessage("Error in increasing task amount, please report to staff.");
			return;
		}
		DailyTasks tasks = DailyTasks.forTask(taskid);
		p.getSavedData().getGlobalData().getDailyTasks()[tasks.ordinal()] += count;
		p.sendMessage("incr task: name=" + tasks.getName() + ", taskid=" + tasks.ordinal() + ", count=" + p.getSavedData().getGlobalData().getDailyTasks()[tasks.ordinal()] + " ");
	}

}