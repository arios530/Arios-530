package org.arios.game.node.entity.player.info.portal;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import org.arios.game.node.entity.player.info.PlayerDetails;
import org.arios.game.system.SystemLogger;
import org.arios.parser.player.SavingModule;

/**
 * Represents the player portal of a player.
 * @author Vexia
 */
public final class PlayerPortal implements SavingModule {

	/**
	 * The portal rewards.
	 */
	private final PortalShop shop = new PortalShop(this);

	/**
	 * The icon manager.
	 */
	private final IconManager iconManager = new IconManager(this);

	/**
	 * The player details.
	 */
	private final PlayerDetails details;

	/**
	 * The donator type value.
	 */
	private DonatorType donatorType;

	/**
	 * The amount of black marks.
	 */
	private int blackMarks;

	/**
	 * The messages.
	 */
	private int messages;

	/**
	 * The time the player is muted for.
	 */
	private long muteTime;

	/**
	 * Constructs a new {@code PlayerPortal} {@code Object}.
	 * @param details the details.
	 */
	public PlayerPortal(final PlayerDetails details) {
		this.details = details;
	}

	@Override
	public void save(ByteBuffer buffer) {
		buffer.put((byte) 4);
		buffer.putInt(messages);
		buffer.putInt(blackMarks);
		shop.save(buffer.put((byte) 5));
		if (donatorType != null) {
			buffer.put((byte) 6).put((byte) donatorType.ordinal());
		}
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get() & 0xFF) != 0) {
			switch (opcode) {
			case 1:
				buffer.getLong();
				continue;
			case 2:
				buffer.getLong();
				break;
			case 3:
				donatorType = DonatorType.REGULAR;
				break;
			case 4:
				messages = buffer.getInt();
				blackMarks = buffer.getInt();
				break;
			case 5:
				shop.parse(buffer);
				break;
			case 6:
				donatorType = DonatorType.values()[buffer.get()];
				break;
			default:
				SystemLogger.log("Portal Parsed an unknown opcode." + opcode);
				break;
			}
		}
	}

	/**
	 * Gets the donator value.
	 * @return the value as an integer.
	 */
	public int getDonatorValue() {
		return donatorType == null ? -1 : donatorType.ordinal();
	}

	/**
	 * Gets the donator type.
	 * @return the donator type.
	 */
	public DonatorType getDonatorType() {
		return donatorType;
	}

	/**
	 * Gets the blackMarks.
	 * @return The blackMarks.
	 */
	public int getBlackMarks() {
		return blackMarks;
	}

	/**
	 * Sets the blackMarks.
	 * @param blackMarks The blackMarks to set.
	 */
	public void setBlackMarks(int blackMarks) {
		this.blackMarks = blackMarks;
	}

	/**
	 * Used to increment the black marks.
	 * @param increment the increment.
	 */
	public void incrementMarks(final int increment) {
		this.blackMarks += increment;
	}

	/**
	 * Checks if the player is a donator.
	 * @return {@code True} if so.
	 */
	public boolean isDonator() {
		return donatorType != null;
	}

	/**
	 * Sets the donator type.
	 * @param type the type.
	 */
	@Deprecated
	public void setDonatorType(DonatorType type) {
		this.donatorType = type;
	}

	/**
	 * Gets the messages.
	 * @return The messages.
	 */
	public int getMessages() {
		return messages;
	}

	/**
	 * Sets the messages.
	 * @param messages The messages to set.
	 */
	@Deprecated
	public void setMessages(int messages) {
		this.messages = messages;
	}

	/**
	 * Gets the rewards.
	 * @return The rewards.
	 */
	public PortalShop getShop() {
		return shop;
	}

	/**
	 * Gets the details.
	 * @return The details.
	 */
	public PlayerDetails getDetails() {
		return details;
	}

	/**
	 * Gets the manager.
	 * @return the manager
	 */
	public IconManager getIconManager() {
		return iconManager;
	}

	/**
	 * Checks if the mute is permanent.
	 * @return {@code True} if so.
	 */
	public boolean isPermMute() {
		return TimeUnit.MILLISECONDS.toDays(muteTime - System.currentTimeMillis()) > 356;
	}

	/**
	 * Checks if the player is muted.
	 * @return {@code True} if so.
	 */
	public boolean isMuted() {
		return muteTime > System.currentTimeMillis();
	}

	/**
	 * Gets the muteTime.
	 * @return the muteTime.
	 */
	public long getMuteTime() {
		return muteTime;
	}

	/**
	 * Sets the muteTime.
	 * @param muteTime the muteTime to set
	 */
	public void setMuteTime(long muteTime) {
		this.muteTime = muteTime;
	}

}
