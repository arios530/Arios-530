package org.arios.game.content.skill.member.slayer;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.diary.DiaryType;
import org.arios.parser.player.SavingModule;
import org.arios.tools.RandomFunction;

/**
 * Manages the players slayer task.
 * @author Vexia
 */
public final class SlayerManager implements SavingModule {

	/**
	 * Represents the player instance.
	 */
	private final Player player;

	/**
	 * Represents the current slayer master.
	 */
	private Master master;

	/**
	 * Represents the current task.
	 */
	private Task task;

	/**
	 * Represents the amount of creatures killed.
	 */
	private int amount;

	/**
	 * Constructs a new {@code SlayerCredentials.java} {@Code Object}.
	 * @param player the player.
	 */
	public SlayerManager(Player player) {
		this.player = player;
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get() & 0xFF) != 0) {
			switch (opcode) {
			case 1:
				master = Master.forId(buffer.getInt());
				break;
			case 2:
				task = Tasks.values()[buffer.getInt()].getTask();
				break;
			case 3:
				setAmount(buffer.getInt());
				break;
			}
		}
	}

	@Override
	public void save(ByteBuffer buffer) {
		if (master != null) {
			buffer.put((byte) 1);
			buffer.putInt(master.getNpc());
		}
		if (task != null) {
			buffer.put((byte) 2);
			buffer.putInt(Tasks.forValue(task).ordinal());
		}
		if (task != null) {
			buffer.put((byte) 3);
			buffer.putInt(getAmount());
		}
		buffer.put((byte) 0);
	}

	/**
	 * Assigns a task to the manager.
	 * @param task the task.
	 * @param master the master.
	 */
	public void assign(Task task, final Master master) {
		if (master == Master.DURADEL) {
			if (!player.getAchievementDiaryManager().getDiary(DiaryType.KARAMJA).isComplete(2, 3)) {
				player.getAchievementDiaryManager().getDiary(DiaryType.KARAMJA).updateTask(player, 2, 3, true);
			}
		} else if (master == Master.VANNAKA) {
			if (!player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).isComplete(1, 5)) {
				player.getAchievementDiaryManager().getDiary(DiaryType.VARROCK).updateTask(player, 1, 5, true);
			}
		}
		setMaster(master);
		setTask(task);
		setAmount(getRandomAmount(task.getRanges(master)));
	}

	/**
	 * Method used to assign a new task for a player.
	 * @param master the master to give the task.
	 */
	public void generate(Master master) {
		final List<Task> tasks = Arrays.asList(Tasks.getTasks(master));
		Collections.shuffle(tasks, RandomFunction.RANDOM);
		for (Task task : tasks) {
			if (!task.canAssign(player, master)) {
				continue;
			}
			assign(task, master);
			break;
		}
	}

	/**
	 * Clears the manager of a task.
	 */
	public void clear() {
		setTask(null);
		setAmount(0);
	}

	/**
	 * Gets a random amount.
	 * @param ranges the ranges.
	 * @return the amt.
	 */
	private int getRandomAmount(int[] ranges) {
		return RandomFunction.random(ranges[0], ranges[1]);
	}

	/**
	 * Gets the task name.
	 * @return the name.
	 */
	public String getTaskName() {
		if (task == null) {
			return "null";
		}
		if (task.getNpcs() == null) {
			return "no npcs report me";
		}
		if (task.getNpcs().length < 1) {
			return "npc length to small report me";
		}
		return NPCDefinition.forId(task.getNpcs()[0]).getName().toLowerCase();
	}

	/**
	 * Gets the task.
	 * @return The task.
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 * @param task The task to set.
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the master.
	 * @return The master.
	 */
	public Master getMaster() {
		return master;
	}

	/**
	 * Sets the master.
	 * @param master The master to set.
	 */
	public void setMaster(Master master) {
		this.master = master;
	}

	/**
	 * Checks if a <b>Player</b> contains a task.
	 * @return {@code True} if so.
	 */
	public boolean hasTask() {
		if (task == null) {
			return false;
		}
		if (getAmount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Method used to check if the task is completed.
	 * @return <code>True</code> if so.
	 */
	public boolean isCompleted() {
		return amount <= 0;
	}

	/**
	 * Gets the amount.
	 * @return The amount.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 * @param amount The amount to set.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Method used to decrement an amount.
	 * @param amount the amount.
	 */
	public void decrementAmount(int amount) {
		this.amount -= amount;
	}

	/**
	 * Method used to check if the player has started slayer.
	 * @return {@code True} if so.
	 */
	public boolean hasStarted() {
		return master != null;
	}

}
