package org.arios.game.content.skill.member.construction;

/**
 * Represents the room properties.
 * @author Emperor >ORDINAL BOUND<
 */
public enum RoomProperties {

	/**
	 * All the possible rooms one can build inside the player owned house.
	 */
	/*
	 * The parlour room
	 */
	PARLOUR(1000, 1, 0, 0, 7, new Hotspot(BuildHotspot.PARLOUR_CHAIRSPACE_2, 4, 3)),

	/*
	 * The garden. (centrepiece has to be first!)
	 */
	GARDEN(1000, 1, 0, 0, 1, new Hotspot(BuildHotspot.GARDEN_CENTREPIECE_1, 3, 3), new Hotspot(BuildHotspot.GARDEN_BIG_TREE_1, 6, 6)),

	/*
	 * The kitchen room
	 */
	KITCHEN(5000, 5, 0, 2, 7, new Hotspot(BuildHotspot.KITCHEN_TABLE_SPACE, 3, 3)),

	/*
	 * The dining room int cost, int level, int z, int chunkX, int chunkY
	 */
	DINING_ROOM(5000, 10, 0, 4, 7, new Hotspot(BuildHotspot.KITCHEN_TABLE_SPACE, 2, 3)),

	/*
	 * The workshop room
	 */
	WORKSHOP(10000, 15, 0, 0, 5, new Hotspot(BuildHotspot.WORKSHOP_WORKBENCH_SPACE, 3, 4)),

	/*
	 * The bedroom
	 */
	BEDROOM(10000, 20, 0, 6, 7, new Hotspot(BuildHotspot.BEDROOM_BED_SPACE, 3, 6)),

	/*
	 * The hall room (skilling trophies)
	 */
	HALL_SKILL_TROPHIES(15000, 25, 0, 1, 6, new Hotspot(BuildHotspot.SKILL_HALL_TROPHY_SPACE, 6, 7)),

	/*
	 * The games room
	 */
	GAMES_ROOM(25000, 30, 0, 5, 4, new Hotspot(BuildHotspot.GAMES_STONE_SPACE, 2, 4)),

	/*
	 * The combat room
	 */
	COMBAT_ROOM(25000, 32, 0, 3, 4, new Hotspot(BuildHotspot.COMBAT_ROOM_STORAGE_SPACE, 3, 7)),

	/*
	 * The hall room (quest trophies)
	 */
	HALL_QUEST_TROPHIES(25000, 35, 0, 4, 6, new Hotspot(BuildHotspot.QUEST_HALL_BOOKCASE, 0, 1)),

	/*
	 * The study room
	 */
	STUDY(50000, 40, 0, 4, 5, new Hotspot(BuildHotspot.STUDY_LECTERN_SPACE, 2, 2)),

	/*
	 * The costume room
	 */
	COSTUME_ROOM(50000, 42, 0, 6, 1, new Hotspot(BuildHotspot.COSTUME_FANCY_DRESS_BOX, 3, 3)),

	/*
	 * The chapel room
	 */
	CHAPEL(50000, 45, 0, 2, 5, new Hotspot(BuildHotspot.CHAPEL_ALTAR_SPACE, 3, 5)),

	/*
	 * The portal chamber room
	 */
	PORTAL_CHAMBER(100000, 50, 0, 1, 4, new Hotspot(BuildHotspot.PORTAL_CHAMBER_CENTREPIECE_SPACE, 3, 3)),

	/*
	 * The formal garden room
	 */
	FORMAL_GARDEN(75000, 55, 0, 2, 1, new Hotspot(BuildHotspot.GARDEN_CENTREPIECE_1, 3, 3));

	/**
	 * The amount this room costs.
	 */
	private final int cost;

	/**
	 * The level required.
	 */
	private final int level;

	/**
	 * The chunk plane.
	 */
	private final int z;

	/**
	 * The chunk x-offset.
	 */
	private final int chunkX;

	/**
	 * The chunk y-offset.
	 */
	private final int chunkY;

	/**
	 * The hotspots in this room.
	 */
	private final Hotspot[] hotspots;

	/**
	 * Constructs a new {@code RoomProperties} {@code Object}.
	 * @param z The chunk plane.
	 * @param chunkX The chunk x-offset.
	 * @param chunkY The chunk y-offset.
	 * @param regionId The region id to copy from.
	 * @param hotspots The hotspots.
	 */
	private RoomProperties(int cost, int level, int z, int chunkX, int chunkY, Hotspot... hotspots) {
		this.cost = cost;
		this.level = level;
		this.z = z;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.hotspots = hotspots;
	}

	/**
	 * Gets the z.
	 * @return The z.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Gets the chunkX.
	 * @return The chunkX.
	 */
	public int getChunkX() {
		return chunkX;
	}

	/**
	 * Gets the chunkY.
	 * @return The chunkY.
	 */
	public int getChunkY() {
		return chunkY;
	}

	/**
	 * Gets the hotspots.
	 * @return The hotspots.
	 */
	public Hotspot[] getHotspots() {
		return hotspots;
	}

	/**
	 * Gets the cost.
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Gets the level.
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
}