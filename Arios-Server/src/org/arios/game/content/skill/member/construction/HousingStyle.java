package org.arios.game.content.skill.member.construction;

/**
 * The styles of houses.
 * @author Emperor
 */
public enum HousingStyle {

	BASIC_WOOD(1, 5000, 7503, 0, 15313), BASIC_STONE(10, 5000, 7503, 1, 15307), WHITEWASHED_STONE(20, 7500, 7503, 2, 15309), FREMENNIK_STYLE_WOOD(30, 10000, 7503, 3, 15311), TROPICAL_WOOD(40, 15000, 7759, 0, 15305), FANCY_STONE(50, 25000, 7759, 1, 15315);

	/**
	 * The level required.
	 */
	private final int level;

	/**
	 * The cost.
	 */
	private final int cost;

	/**
	 * The region id.
	 */
	private final int regionId;

	/**
	 * The plane.
	 */
	private final int plane;

	/**
	 * The door id.
	 */
	private final int doorId;

	/**
	 * Constructs a new {@code HousingStyle} {@code Object}
	 * @param level The level required.
	 * @param cost The cost of the style.
	 * @param regionId The region id for this style.
	 * @param plane The plane for this style.
	 * @param doorId The door object id used in this style.
	 */
	private HousingStyle(int level, int cost, int regionId, int plane, int doorId) {
		this.level = level;
		this.cost = cost;
		this.regionId = regionId;
		this.plane = plane;
		this.doorId = doorId;
	}

	/**
	 * Gets the level.
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Gets the cost.
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Gets the regionId.
	 * @return the regionId
	 */
	public int getRegionId() {
		return regionId;
	}

	/**
	 * Gets the plane.
	 * @return the plane
	 */
	public int getPlane() {
		return plane;
	}

	/**
	 * Gets the door used in this style.
	 * @return The door object id.
	 */
	public int getDoorId() {
		return doorId;
	}
}