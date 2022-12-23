package org.arios.game.content.skill.member.construction;

import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.tools.StringUtils;

/**
 * The house locations.
 * @author Emperor
 * @author Vexia
 */
public enum HouseLocation {
	NOWHERE(-1, null, -1, -1), RIMMINGTON(15478, Location.create(2953, 3224, 0), 1, 5000), TAVERLY(15477, Location.create(2893, 3465, 0), 10, 5000), POLLNIVNEACH(15479, Location.create(3340, 3003, 0), 20, 7500), RELLEKKA(15480, Location.create(2670, 3631, 0), 30, 10000), BRIMHAVEN(15481, Location.create(2757, 3178, 0), 40, 15000), YANILLE(15482, Location.create(2544, 3096, 0), 50, 25000);

	/**
	 * The portal object id for this location.
	 */
	private final int portalId;

	/**
	 * The exit location.
	 */
	private final Location exitLocation;

	/**
	 * The construction level required.
	 */
	private final int level;

	/**
	 * The cost to move the house.
	 */
	private final int cost;

	/**
	 * Constructs a new {@code HouseLocation} {@code Object}
	 * @param portalId The portal id.
	 * @param exitLocation The exit location.
	 * @param level the con level required.
	 * @param cost the cost to move the house.
	 */
	private HouseLocation(int portalId, Location exitLocation, int level, int cost) {
		this.portalId = portalId;
		this.exitLocation = exitLocation;
		this.level = level;
		this.cost = cost;
	}

	/**
	 * Gets the house location for the portal id.
	 * @param portalId the portal id.
	 * @return the house location.
	 */
	public static HouseLocation forId(int portalId) {
		for (HouseLocation location : values()) {
			if (location.getPortalId() == portalId) {
				return location;
			}
		}
		return null;
	}

	/**
	 * Checks if the player has the level.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public boolean hasLevel(Player player) {
		return player.getSkills().getStaticLevel(Skills.CONSTRUCTION) >= level;
	}

	/**
	 * Gets the name formatted.
	 * @return the formatted name.
	 */
	public String getName() {
		return StringUtils.formatDisplayName(name().toLowerCase());
	}

	/**
	 * Gets the portalId.
	 * @return the portalId
	 */
	public int getPortalId() {
		return portalId;
	}

	/**
	 * Gets the exitLocation.
	 * @return the exitLocation
	 */
	public Location getExitLocation() {
		return exitLocation;
	}

	/**
	 * Gets the cost.
	 * @return the cost.
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