package org.arios.parser.object;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.arios.cache.ServerStore;
import org.arios.parser.Parser;

/**
 * Handles the door definition parsing.
 * @author Emperor
 */
public final class DoorDefinitionsParser implements Parser {

	/**
	 * The doors mapping.
	 */
	private static final Map<Integer, Door> DOORS = new HashMap<>();

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buffer = ServerStore.getArchive("doors");
		int id;
		while ((id = buffer.getShort()) != -1) {
			Door door = new Door(id & 0xFFFF);
			door.replaceId = buffer.getShort() & 0xFFFF;
			door.fence = buffer.get() == 1;
			DOORS.put(door.getId(), door);
		}
		return true;
	}

	/**
	 * Gets the door for the given object id.
	 * @param id The object id.
	 * @return The door.
	 */
	public static Door forId(int id) {
		return DOORS.get(id);
	}

	/**
	 * Gets the doors.
	 * @return The doors.
	 */
	public static Map<Integer, Door> getDoors() {
		return DOORS;
	}

	/**
	 * Represents a door.
	 * @author Emperor
	 */
	public static class Door {

		/**
		 * The door's object id.
		 */
		private final int id;

		/**
		 * The door's replace object id.
		 */
		private int replaceId;

		/**
		 * If the door is closed.
		 */
		private boolean fence;

		/**
		 * If the player should automaticly walk through it.
		 */
		private boolean autoWalk;

		/**
		 * Constructs a new {@code DoorManager} {@code Object}.
		 */
		public Door(int id) {
			this.id = id;
		}

		/**
		 * Gets the id.
		 * @return The id.
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets the replaceId.
		 * @return The replaceId.
		 */
		public int getReplaceId() {
			return replaceId;
		}

		/**
		 * Sets the replaceId.
		 * @param replaceId The replaceId to set.
		 */
		public void setReplaceId(int replaceId) {
			this.replaceId = replaceId;
		}

		/**
		 * Gets the autoWalk.
		 * @return The autoWalk.
		 */
		public boolean isAutoWalk() {
			return autoWalk;
		}

		/**
		 * Sets the autoWalk.
		 * @param autoWalk The autoWalk to set.
		 */
		public void setAutoWalk(boolean autoWalk) {
			this.autoWalk = autoWalk;
		}

		/**
		 * Gets the fence.
		 * @return The fence.
		 */
		public boolean isFence() {
			return fence;
		}

		/**
		 * Sets the fence.
		 * @param fence The fence to set.
		 */
		public void setFence(boolean fence) {
			this.fence = fence;
		}

	}

}