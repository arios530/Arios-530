package org.arios.game.node.entity.player.info.portal;

import org.arios.game.node.entity.player.info.portal.IconManager.Icon;

/**
 * A donator type. (ordinal bound)
 * @author Vexia
 */
public enum DonatorType {
	REGULAR(Icon.GREEN, "006600", 5), EXTREME(Icon.RED, "FF0800", 0);

	/**
	 * The icon for the donator type.
	 */
	private final Icon icon;

	/**
	 * The color prefix for yell.
	 */
	private final String color;

	/**
	 * The cooldown between yells.
	 */
	private final int cooldown;

	/**
	 * Constructs a new {@code DonatorType} {@code Object}
	 * @param icon the icon.
	 * @param color the color.
	 */
	private DonatorType(Icon icon, String color, int cooldown) {
		this.icon = icon;
		this.color = color;
		this.cooldown = cooldown;
	}

	/**
	 * Gets the color.
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Gets the icon.
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Gets the cooldown.
	 * @return the cooldown
	 */
	public int getCooldown() {
		return cooldown;
	}

}
