package org.arios.game.node.entity.player.info.portal;

import java.nio.ByteBuffer;

import org.arios.parser.player.SavingModule;

/**
 * Manages the players donator icon.
 * @author Vexia
 */
public final class IconManager implements SavingModule {

	/**
	 * The portal.
	 */
	private final PlayerPortal portal;

	/**
	 * The current icon.
	 */
	private Icon icon = Icon.GREEN;

	/**
	 * Constructs a new {@Code IconManager} {@Code Object}
	 * @param shop the shop.
	 */
	public IconManager(final PlayerPortal shop) {
		this.portal = shop;
	}

	@Override
	public void save(ByteBuffer buffer) {
		if (icon != Icon.GREEN && portal.isDonator()) {
			buffer.put((byte) 1);
			buffer.putInt(icon.getId());
		}
		buffer.put((byte) 0);
	}

	@Override
	public void parse(ByteBuffer buffer) {
		int opcode;
		while ((opcode = buffer.get() & 0xFFFF) != 0) {
			switch (opcode) {
			case 1:
				icon = Icon.forId(buffer.getInt());
				break;
			}
		}
	}

	/**
	 * Gets the id.
	 * @return the id.
	 */
	public int getId() {
		return icon.getIndexId();
	}

	/**
	 * Gets the icon.
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Sets an icon for the id.
	 * @param id the id.
	 */
	public void setIcon(int id) {
		this.icon = Icon.values()[id];
	}

	/**
	 * Sets a donator icon.
	 * @param icon the icon.
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Gets the shop.
	 * @return the shop
	 */
	public PlayerPortal getPortal() {
		return portal;
	}

	/**
	 * An icon.
	 * @author Vexia
	 */
	public enum Icon {
		NOTHING(0, 0), GREEN(1, 3), RED(2, 4), YELLOW(3, 5), BLUE(4, 6), ORANGE(5, 7), PINK(6, 8), PURPLE(7, 9), BROWN(8, 10);

		/**
		 * The id.
		 */
		private final int id;

		/**
		 * The index id.
		 */
		private final int indexId;

		/**
		 * Constructs a new {@Code Icons} {@Code Object}
		 * @param id the id.
		 * @param indexId the indexid.
		 */
		private Icon(int id, int indexId) {
			this.id = id;
			this.indexId = indexId;
		}

		/**
		 * Gets an icon for the id.
		 * @param id the id.
		 * @return the id.S
		 */
		public static Icon forId(int id) {
			for (Icon icon : values()) {
				if (icon.getId() == id) {
					return icon;
				}
			}
			return GREEN;
		}

		/**
		 * Gets the id.
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets the indexId.
		 * @return the indexId
		 */
		public int getIndexId() {
			return indexId;
		}

	}

}
