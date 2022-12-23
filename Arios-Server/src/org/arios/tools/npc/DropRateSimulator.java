package org.arios.tools.npc;

import java.util.HashMap;
import java.util.Map;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.eco.EcoStatus;
import org.arios.game.content.eco.EconomyManagement;
import org.arios.game.node.entity.npc.drop.NPCDropTables;
import org.arios.game.node.entity.npc.drop.RareDropTable;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.world.GameWorld;
import org.arios.tools.RandomFunction;

/**
 * Used for simulating the drop rates (so we don't fuck up our economy when
 * changing an NPC's drops).
 * @author Emperor
 */
public final class DropRateSimulator {

	/**
	 * The NPC id.
	 */
	private static final int NPC_ID = 1615;

	/**
	 * The drop log.
	 */
	private static final Map<Integer, Integer> DROP_LOG = new HashMap<>();

	/**
	 * If testing just RDT.
	 */
	private static boolean RDT = true;

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		GameWorld.prompt(false);
		int count = 100000;
		for (int j = 0; j < 1; j++) {
			if (j == 2) {
				EconomyManagement.update(EcoStatus.BOOSTING, 0.5);
				DROP_LOG.clear();
			} else if (j == 1) {
				DROP_LOG.clear();
			}
			NPCDropTables tables = NPCDefinition.forId(NPC_ID).getDropTables();
			System.out.println("Starting round - " + j + " using mod rate - " + tables.getModRate());
			for (int i = 0; i < count; i++) {
				if (RDT) {
					int slot = RandomFunction.random(tables.getMainTableSize());
					for (ChanceItem item : tables.getMainTable()) {
						if ((item.getTableSlot() & 0xFFFF) <= slot && (item.getTableSlot() >> 16) > slot) {
							if (item.getId() != 31) {
								continue;
							}
							if (item.getId() == 31) {
								item = RareDropTable.retrieve();
							}
							if (item != null) {
								log(item.getId());
							}
							break;
						}
					}
					continue;
				}
				if (tables.getDefaultTable() != null) {
					for (ChanceItem item : tables.getDefaultTable()) {
						log(item.getId());
					}
				}
				if (!tables.getCharmTable().isEmpty()) {
					int slot = RandomFunction.random(1000);
					for (ChanceItem item : tables.getCharmTable()) {
						if ((item.getTableSlot() & 0xFFFF) <= slot && (item.getTableSlot() >> 16) > slot) {
							if (item.getId() != 0) { // No charm drop
								log(item.getId());
							}
							break;
						}
					}
				}
				if (!tables.getMainTable().isEmpty()) {
					int slot = RandomFunction.random(tables.getMainTableSize());
					for (ChanceItem item : tables.getMainTable()) {
						if ((item.getTableSlot() & 0xFFFF) <= slot && (item.getTableSlot() >> 16) > slot) {
							if (item.getId() == 31) {
								item = RareDropTable.retrieve();
							}
							if (item != null) {
								log(item.getId());
							}
							break;
						}
					}
				}
			}
			for (int key : DROP_LOG.keySet()) {
				int value = DROP_LOG.get(key);
				ItemDefinition def = ItemDefinition.forId(key);
				System.out.println("[DUMP=" + j + "]- Item " + key + " dropped [name=" + def.getName() + ", times dropped=" + value + ", est.rate=1/" + (count / value) + "]");
			}
		}
		System.exit(0);
	}

	/**
	 * Logs a drop.
	 * @param id The item id.
	 */
	private static void log(int id) {
		Integer value = DROP_LOG.get(id);
		if (value == null) {
			value = 0;
		}
		DROP_LOG.put(id, value += 1);
	}

}
