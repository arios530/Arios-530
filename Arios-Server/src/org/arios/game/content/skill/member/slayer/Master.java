package org.arios.game.content.skill.member.slayer;

import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;

/**
 * Represents a slayer master.
 * @author 'Vexia
 */
public enum Master {
	TURAEL(70, 15, 50), MAZCHNA(1596, 30, 70) {
		@Override
		public boolean hasRequirment(Player player) {
			return player.getProperties().getCurrentCombatLevel() >= 20;
		}
	},
	VANNAKA(1597, 30, 80) {
		@Override
		public boolean hasRequirment(Player player) {
			return player.getProperties().getCurrentCombatLevel() >= 40;
		}
	},
	CHAELDAR(1598, 110, 170) {
		@Override
		public boolean hasRequirment(Player player) {
			return player.getProperties().getCurrentCombatLevel() >= 70 && player.getSkills().getLevel(Skills.SLAYER) >= 25;
		}
	},
	DURADEL(1599, 50, 199) {
		@Override
		public boolean hasRequirment(Player player) {
			return player.getProperties().getCurrentCombatLevel() >= 100 && player.getSkills().getLevel(Skills.SLAYER) >= 50;
		}
	};

	/**
	 * Represents the npc id.
	 */
	private final int npc;

	/**
	 * The ranges of task amts.
	 */
	private final int[] ranges;

	/**
	 * Constructs a new {@code Master} {@Code Object}.
	 */
	Master(int npc, int... ranges) {
		this.npc = npc;
		this.ranges = ranges;
	}

	/**
	 * Checks if the player has the requiremnts.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public boolean hasRequirment(Player player) {
		return true;
	}

	/**
	 * Gets the npc.
	 * @return The npc.
	 */
	public int getNpc() {
		return npc;
	}

	/**
	 * returns the value from the integer specification.
	 * @param id the id.
	 * @return @app value.
	 */
	public static Master forId(int id) {
		for (Master master : Master.values()) {
			if (master == null) {
				continue;
			}
			if (master.getNpc() == id) {
				return master;
			}
		}
		return null;
	}

	/**
	 * Gets the ranges.
	 * @return The ranges.
	 */
	public int[] getRanges() {
		return ranges;
	}

	/**
	 * Checks if two masters share the same task.
	 * @param master the master.
	 * @param myMaster the players master.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public static boolean hasSameTask(Master master, Master myMaster, Player player) {
		Task task = player.getSlayer().getTask();
		if (master == myMaster) {
			return true;
		}
		if (task.hasMaster(master)) {
			return true;
		}
		return false;
	}
}
