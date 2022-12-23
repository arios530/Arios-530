package org.arios.game.node.entity.player.info.portal;

import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.tools.RandomFunction;
import org.arios.tools.StringUtils;

/**
 * Represents a perk.
 * @author Vexia
 */
public enum Perks {
	DOUBLE_XP(1), STAMINA_BOOST(2, 0.40), GREEN_THUMB(4, 0.25), BIRD_MAN(5, .25), STONER(6, .25), GNOME_COPTER(7), MINE_CART(8), CHARTER_BARTER(9), UNBREAKABLE_FORGE(11), OUT_OF_GRAVE_DANGER(12), SLEIGHT_OF_HAND(13), MASTER_CHEF(14), DIVINE_INTERVENTION(16), FAMILIAR_WHISPERER(17), BARROWS_BEFRIENDER(18), ABYSS_BEFRIENDER(19), PROWLER(20), CHARGE_BEFRIENDER(21), GOLDEN_NEEDLE(22), SLAYER_BETRAYER(24), THIRST_QUENCHER(26), DOUBLE_TROUBLE(27), BORKAHOLIC(28, 5), GWD_BEFRIENDER(29, 30), PRAYER_BETRAYER(30, 50), SPELL_SWAP(31, 3), DWARF_BEFRIENDER(32), POWERPOINT(33), CLEAN_SLATE(34), CHARM_COLLECTOR(35), REGULAR_DONATOR(1000), EXTREME_DONATOR(1001), DETECTIVE(36), SHARD_BARGAINER(37), EVENT_AVOIDER(38), OVERCHARGE(40), UNBREAKABLE_CRYSTAL(41), CRUSADER(42),
	// COSMETICS
	SANTA_HAT(43, new Item(1050)), BLUE_HWEEN(44, new Item(1055)), GREEN_HWEEN(45, new Item(1053)), RED_HWEEN(46, new Item(1057)), CHRISTMAS_CRACKER(47, new Item(962)), RED_PHAT(48, new Item(1038)), YELLOW_PHAT(49, new Item(1040)), BLUE_PHAT(50, new Item(1042)), GREEN_PHAT(51, new Item(1044)), PURPLE_PHAT(52, new Item(1046)), WHITE_PHAT(53, new Item(1048)), CRATE_WITH_ZANIK(54, new Item(8871)), EASTER_RING(55, new Item(7927));

	/**
	 * The default rand value.
	 */
	private static final int DEFAULT_RAND = 100;

	/**
	 * The product id.
	 */
	private final int productId;

	/**
	 * Any data for the perk.
	 */
	private final Object[] data;

	/**
	 * The info for the perk.
	 */
	private final String info;

	/**
	 * Constructs a new {@code Perks} {@code Object}
	 * @param productId the product id.
	 * @param info the info.
	 * @param type the type.
	 * @param data data.
	 */
	private Perks(final int productId, String info, final Object... data) {
		this.productId = productId;
		this.data = data;
		this.info = info;
	}

	/**
	 * Constructs a new {@code Perks} {@code Object}.
	 * @param productId the product id.
	 * @param data the data.
	 */
	private Perks(final int productId, final Object... data) {
		this(productId, null, data);
	}

	/**
	 * Gets the double of an item if they have the perk.
	 * @param player the player.
	 * @param original the original.
	 * @param ground if we should add it to the ground.
	 * @param maxRand the maximum rand value.
	 * @return the item.
	 */
	public static boolean addDouble(Player player, Item original, boolean ground, int maxRand) {
		boolean addOriginal = !player.hasPerk(DOUBLE_TROUBLE);
		if (!addOriginal) {
			addOriginal = RandomFunction.random(maxRand) > 10;
		}
		if (addOriginal) {
			if (ground) {
				player.getInventory().add(original, player);
			} else {
				player.getInventory().add(original);
			}
			return false;
		}
		Item doubleI = new Item(original.getId(), original.getAmount() * 2);
		player.getInventory().add(doubleI);
		player.sendMessage("<col=FF0000>You get 2x " + original.getName().toLowerCase() + ".");
		return true;
	}

	/**
	 * Adds the double of an item.
	 * @param player the player.
	 * @param original the original.
	 */
	public static boolean addDouble(Player player, Item original, boolean ground) {
		return addDouble(player, original, ground, DEFAULT_RAND);
	}

	/**
	 * Adds the double of an item.
	 * @param player the player.
	 * @param original the original.
	 */
	public static boolean addDouble(Player player, Item original) {
		return addDouble(player, original, false, DEFAULT_RAND);
	}

	/**
	 * Adds the double of an item.
	 * @param player the player.
	 * @param original the original.
	 * @param maxRand the maximum rand value.
	 */
	public static boolean addDouble(Player player, Item original, int maxRand) {
		return addDouble(player, original, false, maxRand);
	}

	/**
	 * Gets the perk for the item.
	 * @param item the item.
	 * @return the perk.
	 */
	public static Perks forItem(Item item) {
		for (Perks perk : values()) {
			if (perk.getData() != null && perk.getData().length > 0) {
				for (Object o : perk.getData()) {
					if (o != null && o instanceof Item && ((Item) o).getId() == item.getId()) {
						return perk;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets a perk by the product id.
	 * @param productId the product id.
	 * @return the perk.
	 */
	public static Perks forId(int productId) {
		for (Perks perk : values()) {
			if (perk.getProductId() == productId) {
				return perk;
			}
		}
		return null;
	}

	/**
	 * Gets the info name.
	 * @return the name.
	 */
	public String getInfoName() {
		String name = getName();
		if (info == null || info.length() == 0) {
			return name;
		}
		return name + " " + info;
	}

	/**
	 * Gets the name.
	 * @return the name.
	 */
	public String getName() {
		return StringUtils.formatDisplayName(name().toLowerCase().replace("_", " "));
	}

	/**
	 * Gets the productId.
	 * @return The productId.
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Gets the data.
	 * @return The data.
	 */
	public Object[] getData() {
		return data;
	}

	/**
	 * Gets the info.
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

}