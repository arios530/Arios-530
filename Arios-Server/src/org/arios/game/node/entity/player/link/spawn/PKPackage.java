package org.arios.game.node.entity.player.link.spawn;

import org.arios.game.node.item.Item;

/**
 * A pk package.
 * @author Vexia
 */
public enum PKPackage {
	MELEE("Melee Set", 0, 2, new Item(10828), new Item(6524), new Item(7460), new Item(4131), new Item(1127), new Item(1079), new Item(1725), new Item(6568), new Item(4151), new Item(5698), new Item(2550), new Item(2440), new Item(2436), new Item(6685), new Item(3024, 3), new Item(557, 1000), new Item(560, 1000), new Item(9075, 1000), new Item(7060, 18)), RANGE("Range Set", 0, 2, new Item(861), new Item(892, 500), new Item(3749), new Item(6328), new Item(10499), new Item(1712), new Item(2550), new Item(2503), new Item(2497), new Item(9185), new Item(9244, 500), new Item(2444), new Item(6685), new Item(3024, 3), new Item(557, 1000), new Item(560, 1000), new Item(9075, 1000), new Item(7060, 19)), MAGIC("Mage Set", 0, 1, new Item(10828), new Item(7460), new Item(1712, 1), new Item(4091, 1), new Item(4093), new Item(4097), new Item(4675), new Item(3842), new Item(2550), new Item(2414), new Item(6685), new Item(3024, 3), new Item(555, 1000), new Item(560, 1000), new Item(565, 1000), new Item(7060, 21)), HYBRID("Hybrid Set", 0, 1, new Item(10828, 1), new Item(4091, 1), new Item(1712), new Item(2550), new Item(4093, 1), new Item(4675, 1), new Item(3842, 1), new Item(7460, 1), new Item(2413, 1), new Item(3105, 1), new Item(4151), new Item(6524), new Item(1127), new Item(1079), new Item(5698), new Item(2497), new Item(2503), new Item(6685, 2), new Item(3024, 3), new Item(555, 1000), new Item(560, 1000), new Item(565, 1000), new Item(7060, 13)), PURE("Pure Set", 0, 1, new Item(4587), new Item(9244, 500), new Item(6107), new Item(10499), new Item(2497), new Item(1725), new Item(3842), new Item(3105), new Item(7458), new Item(2550), new Item(6109), new Item(4153), new Item(5698), new Item(9185), new Item(2440), new Item(2436), new Item(2444), new Item(6685), new Item(3024, 3), new Item(7060, 18)), RANGE_TANK("Range Tank Set", 0, 2, new Item(3749), new Item(1712), new Item(10499), new Item(9185), new Item(9244, 500), new Item(2503), new Item(1079), new Item(6328), new Item(6524), new Item(7460), new Item(2550), new Item(2444), new Item(6685), new Item(3024, 3), new Item(557, 1000), new Item(560, 1000), new Item(9075, 1000), new Item(7060, 20)), SHARKS("Sharks", 1, -1, new Item(385, 28)), SUPER_POT("Super Potions", 1, -1, new Item(2436), new Item(2440), new Item(6685, 2)), SUPER_RESTORE("Super Restores", 1, -1, new Item(3024, 3), new Item(6685, 3)), RANGING_POTS("Ranging Potions", 1, -1, new Item(2444, 2)), BARRAGE_RUNE("Barrage Runes", 1, -1, new Item(560, 5000), new Item(565, 5000), new Item(555, 5000)), VENGEANCE_RUNE("Vengeance Runes", 1, -1, new Item(557, 10000), new Item(9075, 5000), new Item(560, 5000)), ENTANGE_RUNE("Entangle Runes", 1, -1, new Item(557, 10000), new Item(555, 5000), new Item(561, 5000));

	/**
	 * The name.
	 */
	private final String name;

	/**
	 * The package type.
	 */
	private int type;

	/**
	 * The spell book.
	 */
	private int spellBook;

	/**
	 * The items in the package.
	 */
	private final Item[] items;

	/**
	 * Constructs a new {@Code PKPackage} {@Code Object}
	 * @param name the name.
	 * @param items the items.
	 */
	PKPackage(String name, int type, int spellBook, Item... items) {
		this.name = name;
		this.type = type;
		this.spellBook = spellBook;
		this.items = items;
	}

	/**
	 * Gets the spell book.
	 * @return the spell book.
	 */
	public int getSpellBook() {
		return spellBook;
	}

	/**
	 * Gets the type.
	 * @return the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the items.
	 * @return the items.
	 */
	public Item[] getItems() {
		return items;
	}
}
