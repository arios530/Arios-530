package org.arios.game.content.skill.member.construction;

/**
 * Represents a hotspot.
 * @author Emperor
 */
public final class Hotspot {

	/**
	 * The hotspot.
	 */
	private final BuildHotspot hotspot;

	/**
	 * The chunk x coordinate.
	 */
	private final int chunkX;

	/**
	 * The chunk y coordinate.
	 */
	private final int chunkY;

	/**
	 * The current decoration index.
	 */
	private int decorationIndex = -1;

	/**
	 * Constructs a new {@code Hotspot} {@code Object}.
	 * @param hotspot The hotspot.
	 * @param chunkX The chunk x-coordinate.
	 * @param chunkY The chunk y-coordinate.
	 */
	public Hotspot(BuildHotspot hotspot, int chunkX, int chunkY) {
		this.hotspot = hotspot;
		this.chunkX = chunkX;
		this.chunkY = chunkY;
	}

	/**
	 * Copies the hotspot.
	 * @return The hotspot.
	 */
	public Hotspot copy() {
		return new Hotspot(hotspot, chunkX, chunkY);
	}

	/**
	 * Gets the hotspot.
	 * @return The hotspot.
	 */
	public BuildHotspot getHotspot() {
		return hotspot;
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
	 * Gets the decorationIndex.
	 * @return The decorationIndex.
	 */
	public int getDecorationIndex() {
		return decorationIndex;
	}

	/**
	 * Sets the decorationIndex.
	 * @param decorationIndex The decorationIndex to set.
	 */
	public void setDecorationIndex(int decorationIndex) {
		this.decorationIndex = decorationIndex;
	}

}