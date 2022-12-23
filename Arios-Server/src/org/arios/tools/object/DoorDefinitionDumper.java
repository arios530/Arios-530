package org.arios.tools.object;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.world.GameWorld;
import org.arios.parser.object.DoorDefinitionsParser.Door;

/**
 * Dumps the door definitions.
 * @author Emperor
 */
public final class DoorDefinitionDumper {

	/**
	 * The closed fence gates object ids.
	 */
	private static int[] FENCE_GATES = { 1551, 1553, 1558, 1589, 3015, 3016, 3726, 4311, 7050, 8811, 9140, 12987, 12986, 14233, 14235, 14237, 14239, 14241, 14243, 14245, 14247, 15510, 15512, 15514, 15516, 20046, 20341, 21403, 21405, 21753, 23216, 23917, 24369, 24370, 24536, 27065, 27848, 27852, 28514, 30116, 30118, 30120, 30122, 30124, 30126, 30128, 30130, 31834, 34779, 35551, 36913, 36915, 36917, 36919, 37352, 40746, 40930 };

	/**
	 * The mapping of door entries.
	 */
	private static final Map<Integer, DoorEntry> ENTRIES = new HashMap<>();

	/**
	 * The doors mapping.
	 */
	private static final Map<Integer, Door> DOORS = new HashMap<>();

	/**
	 * Manually changes door settings.
	 */
	private static void handleManual() {
		/*
		 * Make custom changes here! DOORS.get(doorId).setReplaceId(0); //Sets
		 * autowalk
		 */
		DOORS.get(2406).setReplaceId(2407);
	}

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable when an exception occurs.
	 */
	public static void main(String[] args) throws Throwable {
		Cache.init(ServerConstants.CACHE_PATH);
		GameWorld.prompt(false);
		for (int i = 0; i < Cache.getObjectDefinitionsSize(); i++) {
			ObjectDefinition def = ObjectDefinition.forId(i);
			String name = def.getName().toLowerCase();
			if (!name.contains("door") && !name.contains("gate") && !name.contains("fence") && !name.contains("wall") && !name.contains("exit") && !name.contains("entrance")) {
				continue;
			}
			if (def.hasAction("open") || def.hasAction("close")) {
				Door door = new Door(i);
				door.setAutoWalk(true);
				DOORS.put(i, door);
				for (int id : FENCE_GATES) {
					if (i == id) {
						door.setFence(true);
						break;
					}
				}
			}
		}
		int fail = 0;
		int count = 0;
		for (int id : DOORS.keySet()) {
			Door door = DOORS.get(id);
			ObjectDefinition def = ObjectDefinition.forId(id);
			if (def.hasAction("open")) {
				Arrays.sort(def.getModelIds());
				List<DoorEntry> replacements = new ArrayList<>();
				for (int checkId : DOORS.keySet()) {
					ObjectDefinition d = ObjectDefinition.forId(checkId);
					if (checkId == id || !d.hasAction("close") || !d.getName().equals(def.getName())) {
						continue;
					}
					Arrays.sort(d.getModelIds());
					if (!Arrays.equals(def.getModelIds(), d.getModelIds())) {
						continue;
					}
					DoorEntry entry = ENTRIES.get(checkId);
					if (entry == null) {
						ENTRIES.put(checkId, entry = new DoorEntry(checkId));
					}
					replacements.add(entry);
				}
				if (replacements.isEmpty()) {
					fail++;
					continue;
				}
				count++;
				DoorEntry entry = getLogicalReplacement(id, replacements);
				entry.used = true;
				door.setReplaceId(entry.objectId);
				door.setAutoWalk(false);
			}
		}
		System.out.println("Found " + count + "/" + (count + fail) + " open door replacements!");
		handleManual();
		ByteBuffer buffer = ByteBuffer.allocate(200000);
		for (Door d : DOORS.values()) {
			if (d.getReplaceId() < 1) {
				continue;
			}
			buffer.putShort((short) d.getId()).putShort((short) d.getReplaceId()).put((byte) (d.isFence() ? 1 : 0));
		}
		buffer.putShort((short) -1);
		buffer.flip();
		ServerStore.setArchive("doors", buffer, false);
		ServerStore.createStaticStore(ServerConstants.STORE_PATH);
		try (RandomAccessFile raf = new RandomAccessFile(new File("./data/world/node/object/door.config498"), "rw")) {
			raf.getChannel().write(buffer);
			raf.close();
		}
		System.out.println("Done!");
		System.exit(0);
	}

	/**
	 * Gets the logical replacement.
	 * @param id The object id.
	 * @param replacements The replacements.
	 * @return The door replacement.
	 */
	private static DoorEntry getLogicalReplacement(int id, List<DoorEntry> replacements) {
		Collections.sort(replacements);
		DoorEntry entry = replacements.get(0);
		boolean hasUnused = !entry.used;
		int cost = Math.abs(id - entry.objectId);
		for (DoorEntry e : replacements) {
			if (hasUnused && e.used) {
				break;
			}
			int diff = Math.abs(id - e.objectId);
			if (diff < cost) {
				cost = diff;
				entry = e;
			}
		}
		return entry;
	}

	/**
	 * Represents a door replacement entry.
	 * @author Emperor
	 */
	static class DoorEntry implements Comparable<DoorEntry> {

		/**
		 * The object id.
		 */
		private int objectId;

		/**
		 * If the entry was used before.
		 */
		private boolean used;

		/**
		 * Constructs a new {@code DoorEntry} {@code Object}.
		 * @param objectId The object id.
		 */
		public DoorEntry(int objectId) {
			this.objectId = objectId;
		}

		@Override
		public int compareTo(DoorEntry door) {
			if (equals(door) || used == door.used) {
				return 0;
			}
			if (used) {
				return 1;
			}
			return -1;
		}

	}
}