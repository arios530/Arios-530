package org.arios.game.container.access;

import org.arios.game.node.entity.player.Player;

/**
 * Contains the mask value methods. The 'access mask' is actually just a bit
 * register that contains permissions for a specific interface (@Peterbjornx)
 * @date 5/02/2013
 * @author Stacx
 */
public final class BitregisterAssembler {

	/**
	 * Holds the first 16 (32 total) values for our bitregister. Each one holds
	 * a 'permission' for the interface. (ie. draggable, option) Left out the
	 * rest 16 because they're mostly for other things than interface options.
	 * {@link http
	 * ://www.rune-server.org/runescape-development/rs-503-client-server
	 * /informative-threads/184175-about-optionmask-values.html}
	 */
	protected static final int[] FLAG_CACHE = {
	/* 1 */
	0x2, 0x4, 0x8,
	/* 2 */
	0x10, 0x20, 0x40, 0x80,
	/* 3 */
	0x100, 0x200, 0x400, 0x800,
	/* 4 */
	0x1000, 0x2000, 0x4000, 0x8000,
	/* 5 */
	0x10000, 0x20000, 0x40000, 0x80000,
	/* 6 */
	0x100000, 0x200000, 0x400000, 0x800000,
	/* 7 */
	0x1000000, 0x2000000, 0x4000000, 0x8000000,
	/* 8 */
	0x10000000, 0x20000000, 0x40000000, 0x80000000 };

	/**
	 * <b>Send</b> and assemble a bit register for our
	 * @param player , the player instance
	 * @param interfaceIndex , the interface index
	 * @param childIndex , the child index for our interface
	 * @param offset , the offset for the loop in client
	 * @param length , the length of our loop
	 */
	public static void send(Player player, int interfaceIndex, int childIndex, int offset, int length, int count) {
		if (offset >= length) {
			throw new RuntimeException("Offset cannot excess length. length = " + length);
		}
		player.getPacketDispatch().sendAccessMask(calculateRegister(count), childIndex, interfaceIndex, offset, length);
	}

	/**
	 * Calculates the <b>bit register</b>
	 * @param count , the amount of options we want to send.
	 * @return mask
	 */
	public static int calculateRegister(int count) {
		int mask = 0;
		while (count != -1) {
			mask |= FLAG_CACHE[count--];
		}
		return mask;
	}

	/* todo: identify the flag permissions */

}
