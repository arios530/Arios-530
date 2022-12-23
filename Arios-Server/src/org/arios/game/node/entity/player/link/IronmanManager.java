package org.arios.game.node.entity.player.link;

import java.nio.ByteBuffer;

import org.arios.game.node.entity.player.Player;
import org.arios.parser.player.SavingModule;

/**
 * Manages the iron man mode of an account.
 * @author Vexia
 */
public class IronmanManager implements SavingModule {

	/**
	 * The player instance.
	 */
	private final Player player;

	/**
	 * The iron man mode.
	 */
	private IronmanMode mode = IronmanMode.NONE;

	/**
	 * If the mode is permanent.
	 */
	private boolean permanent;

	/**
	 * Constructs a new {@code IronmanManager} {@code Object}
	 * @param player the player.
	 */
	public IronmanManager(Player player) {
		this.player = player;
	}

	@Override
	public void save(ByteBuffer buffer) {
		if (mode != IronmanMode.NONE) {
			buffer.put((byte) 1);
			buffer.put((byte) mode.ordinal());
			if (permanent) {
				buffer.put((byte) 2);
			}
		}
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get()) != 0) {
			switch (opcode) {
			case 1:
				mode = IronmanMode.values()[buffer.get()];
				break;
			case 2:
				permanent = true;
				break;
			}
		}
	}

	/**
	 * Checks the restriction.
	 * @return {@code True} if so.
	 */
	public boolean checkRestriction() {
		return checkRestriction(IronmanMode.STANDARD);
	}

	/**
	 * Checks the restriction.
	 * @return {@code True} if so.
	 */
	public boolean checkRestriction(IronmanMode mode) {
		if (isIronman() && this.mode.ordinal() >= mode.ordinal()) {
			player.sendMessage("You can't do that as an Ironman.");
			return true;
		}
		return false;
	}

	/**
	 * Checks if the player is an ironman.
	 * @return {@code True} if one.
	 */
	public boolean isIronman() {
		return mode != IronmanMode.NONE;
	}

	/**
	 * Gets the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the permanent.
	 * @return the permanent
	 */
	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * Sets the permanent.
	 * @param permanent the permanent to set.
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * Gets the mode.
	 * @return the mode
	 */
	public IronmanMode getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 * @param mode the mode to set.
	 */
	public void setMode(IronmanMode mode) {
		this.mode = mode;
	}

}
