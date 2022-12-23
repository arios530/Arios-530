package org.arios.game.node.entity.npc.drop;

import java.util.ArrayList;
import java.util.List;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.eco.EcoStatus;
import org.arios.game.content.eco.EconomyManagement;
import org.arios.game.content.eco.ge.GrandExchangeDatabase;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.portal.Perks;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.system.SystemLogger;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.repository.Repository;
import org.arios.parser.item.ItemConfiguration;
import org.arios.parser.npc.NPCConfiguration;
import org.arios.tools.RandomFunction;

/**
 * Holds and handles the NPC drop tables.
 * @author Emperor
 */
public final class NPCDropTables {

	/**
	 * The drop rates (0=common, 1=uncommon, 2=rare, 3=very rare).
	 */
	public static final int[] DROP_RATES = { 750, 150, 15, 5 };

	/**
	 * The default drop table (holding the 100% drops).
	 */
	private final List<ChanceItem> defaultTable = new ArrayList<>();

	/**
	 * The charms drop table (holding the charm drops).
	 */
	private final List<ChanceItem> charmTable = new ArrayList<>();

	/**
	 * The main drop table (holding the main drops).
	 */
	private final List<ChanceItem> mainTable = new ArrayList<>();

	/**
	 * The NPC definitions.
	 */
	private final NPCDefinition def;

	/**
	 * The main drop table size.
	 */
	private int mainTableSize;

	/**
	 * The mod rate used with this table.
	 */
	private double modRate;

	/**
	 * Constructs a new {@code NPCDropTables} {@code Object}.
	 * @param def The NPC definitions.
	 */
	public NPCDropTables(NPCDefinition def) {
		this.def = def;
	}

	/**
	 * Handles the dropping.
	 * @param npc The NPC dropping the loot.
	 * @param looter The entity gaining the loot.
	 */
	public void drop(NPC npc, Entity looter) {
		Player p = looter instanceof Player ? (Player) looter : null;
		for (ChanceItem item : defaultTable) {
			int amount = RandomFunction.random(item.getMinimumAmount(), item.getMaximumAmount() + 1);
			if (npc.getName().startsWith("Revenant") && item.getName().equalsIgnoreCase("coins")) {
				break;
			}
			createDrop(new Item(item.getId(), amount), p, npc, npc.getLocation());
		}
		if (!charmTable.isEmpty()) {
			int slot = RandomFunction.random(1000);
			for (ChanceItem item : charmTable) {
				if ((item.getTableSlot() & 0xFFFF) <= slot && (item.getTableSlot() >> 16) > slot) {
					if (p != null && item != null && item.getId() != 0) {
						if (p.hasPerk(Perks.CHARM_COLLECTOR) && p.getInventory().hasSpaceFor(item)) {
							p.getInventory().add(item);
							if (item.getAmount() == 1) {
								p.sendMessage("<col=FF0000>You have collected a charm!");
							} else {
								p.sendMessage("<col=FF0000>You have collected some charms!");
							}
							break;
						}
					}
					createDrop(item, p, npc, npc.getLocation());
					break;
				}
			}
		}
		if (!mainTable.isEmpty()) {
			if (mainTable.size() == 1 && RandomFunction.random(400) > 10 && npc.getId() != 49) {
				return;// temp for npcs with 1 drop size table.
			}
			if (p != null && npc.getDefinition().getConfigurations().containsKey(NPCConfiguration.CLUE_LEVEL) && p.hasPerk(Perks.DETECTIVE) && RandomFunction.random(100) <= 5) {
				createDrop(new Item(2677), p, npc, npc.getLocation());
				return;
			}
			boolean hasWealthRing = p != null && p.getEquipment().getNew(EquipmentContainer.SLOT_RING).getId() == 2572;
			int slot = RandomFunction.random(mainTableSize);
			for (ChanceItem item : mainTable) {
				if (item.getSetRate() != -1) {
					int rand = RandomFunction.random(item.getSetRate());
					if (rand == 1) {
						createDrop(item.getRandomItem(), p, npc, npc.getLocation());
						return;
					}
				}
			}
			for (ChanceItem item : mainTable) {
				boolean isRDTSlot = item.getId() == RareDropTable.SLOT_ITEM_ID;
				boolean create = false;
				if (item.getSetRate() == -1) {
					create = ((item.getTableSlot() & 0xFFFF) <= slot && (item.getTableSlot() >> 16) > slot) || (isRDTSlot && hasWealthRing && RandomFunction.random(8) == 1);
				}
				if (create) {
					if (isRDTSlot) {
						item = RareDropTable.retrieve();
					}
					if (item != null && p != null && npc != null) {
						createDrop(item.getRandomItem(), p, npc, npc.getLocation());
					}
					break;
				}
			}
		}
	}

	/**
	 * Creates a dropped item.
	 * @param item The item to drop.
	 * @param player The player getting the loot (or null).
	 * @param npc the npc.
	 * @param l The location of the NPC dropping the loot.
	 */
	public void createDrop(Item item, Player player, NPC npc, Location l) {
		if (item == null || item.getId() == 0 || l == null || item.getName().equals("null")) {
			return;
		}
		if (item.hasItemPlugin() && player != null) {
			if (!item.getPlugin().createDrop(item, player, npc, l)) {
				return;
			}
			item = item.getPlugin().getItem(item, npc);
		}
		if (!item.getDefinition().isStackable() && item.getAmount() > 1) {
			for (int i = 0; i < item.getAmount(); i++) {
				GroundItemManager.create(new Item(item.getId()), l, player);
			}
			return;
		}
		if (item.getDefinition().getConfiguration(ItemConfiguration.RARE_ITEM, false)) {
			Repository.sendNews(player.getUsername() + " has just received: " + item.getAmount() + " x " + item.getName() + ".");
		}
		if (player != null) {
			GroundItemManager.create(item, l, getLooter(player, npc, item));
		} else {
			if (item != null) {
				GroundItemManager.create(item, l);
			}
		}
	}

	/**
	 * Prepares the tables for use in the game.
	 */
	public void prepare(double modRate) {
		// Prepare the charms drop table.
		this.setModRate(modRate);
		int slot = 0;
		int index = -1;
		int amount = 0;
		for (int i = 0; i < charmTable.size(); i++) {
			ChanceItem charm = charmTable.get(i);
			int rate = (int) (charm.getChanceRate() * 1000);
			if (rate > amount) {
				index = i;
				amount = rate;
			}
			slot += rate;
		}
		amount = 1000 - slot;
		slot = 0;
		for (ChanceItem charm : charmTable) {
			int rate = (int) (charm.getChanceRate() * 1000.0);
			if (index-- == 0) {
				rate += amount;
			}
			charm.setTableSlot(slot | (int) (slot += rate) << 16);
		}
		if (!charmTable.isEmpty() && slot != 1000) {
			if (slot == 0) {
				charmTable.clear();
			} else {
				System.err.println("Charm percentage incorrect! [" + (slot * .1) + "%, " + def.getId() + "]");
			}
		}
		// Prepare the main drop table.
		slot = 0;
		if (!mainTable.isEmpty()) {
			double ratio = getStabilizerRatio();
			int commonAmount = 0;
			for (ChanceItem item : mainTable) {
				if (item.getDropFrequency() == DropFrequency.COMMON) {
					commonAmount++;
					continue;
				}
				double rate = 1.0;
				if (item.getChanceRate() > 0.0) {
					rate = item.getChanceRate();
				}
				if (modRate != 0.0) {
					rate += modRate;
				}
				int rarity = (int) (DROP_RATES[item.getDropFrequency().ordinal() - 1] * rate);
				item.setTableSlot(slot | ((slot += rarity) << 16));
			}
			mainTableSize = (int) (10_000 * ratio);
			if (slot >= mainTableSize) {
				mainTableSize = (int) (slot + ((1 - (commonAmount / mainTable.size())) * mainTableSize));
			}
			if (commonAmount > 0) {
				int count = commonAmount;
				int rarity = (mainTableSize - slot) / commonAmount;
				if (rarity < DROP_RATES[0]) {
					mainTableSize = slot + (commonAmount * DROP_RATES[0]);
					rarity = DROP_RATES[0];
				}
				for (ChanceItem item : mainTable) {
					if (item.getDropFrequency() == DropFrequency.COMMON) {
						if (--count == 0) {
							rarity = mainTableSize - slot;
						}
						item.setTableSlot(slot | ((slot += rarity) << 16));
					}
				}
			}
		}
	}

	/**
	 * Prepares the table.
	 */
	public void prepare() {
		double rate = 0.0;
		if (EconomyManagement.getEcoState() == EcoStatus.BOOSTING) {
			rate = EconomyManagement.getModificationRate();
		}
		prepare(rate);
	}

	/**
	 * Gets the looting player.
	 * @param player the player.
	 * @param item the item.
	 * @return the player.
	 */
	public Player getLooter(Player player, NPC npc, Item item) {
		int itemId = item.getDefinition().isUnnoted() ? item.getId() : item.getNoteChange();
		if (player != null && npc.getProperties().isMultiZone() && (GrandExchangeDatabase.getDatabase().get(itemId) != null || item.getName().endsWith("charm")) && player.getCommunication().getClan() != null && player.getCommunication().isLootShare() && player.getCommunication().getLootRequirement().ordinal() >= player.getCommunication().getClan().getLootRequirement().ordinal() && !player.getIronmanManager().isIronman()) {
			Player looter = player;
			List<Player> players = RegionManager.getLocalPlayers(npc, 16);
			List<Player> looters = new ArrayList<>();
			for (Player p : players) {
				if (p != null && p.getCommunication().getClan() != null && p.getCommunication().getClan() == player.getCommunication().getClan() && p.getCommunication().isLootShare() && p.getCommunication().getLootRequirement().ordinal() >= p.getCommunication().getClan().getLootRequirement().ordinal() && npc.getImpactHandler().getImpactLog().containsKey(p)) {
					looters.add(p);
				}
			}
			if (looters.size() > 1) {
				int highestLsp = 0;
				for (Player p : looters) {
					if (p.getGlobalData().getLootSharePoints() > highestLsp && RandomFunction.random(10) >= 2) {
						highestLsp = p.getGlobalData().getLootSharePoints();
						looter = p;
					}
				}
				int price = item.getName().endsWith("charm") ? 100 : GrandExchangeDatabase.getDatabase().get(itemId).getValue();
				looter.getGlobalData().setLootSharePoints(looter.getGlobalData().getLootSharePoints() - (price) + ((price / looters.size())));
				looter.sendMessage("<col=009900>You received: " + item.getAmount() + " " + item.getName());
				for (Player p : looters) {
					if (p != looter) {
						p.sendMessage("<col=009900>" + looter.getUsername() + " received: " + item.getAmount() + " " + item.getName());
						p.getGlobalData().setLootSharePoints(p.getGlobalData().getLootSharePoints() + price / looters.size());
					}
				}
			}
			return looter;
		}
		return player;
	}

	/**
	 * Gets the ratio for stabilizing NPC combat difficulty & drop rates.
	 * @return The ratio.
	 */
	public double getStabilizerRatio() {
		double value = 0;
		try {
			value = (1 / (1 + def.getCombatLevel())) * 10;
		} catch (Exception e) {
			if (def.getCombatLevel() == -1) {
				System.err.println("NPC with empty combat level: " + def.getName() + " NPC ID: " + def.getId());
			} else {
				SystemLogger.log("Exception has occurred: " + e.getMessage());
			}
			//e.printStackTrace();
		}

		return value;
	}

	/**
	 * @return the defaultTable.
	 */
	public List<ChanceItem> getDefaultTable() {
		return defaultTable;
	}

	/**
	 * @return the charmTable.
	 */
	public List<ChanceItem> getCharmTable() {
		return charmTable;
	}

	/**
	 * @return the mainTable.
	 */
	public List<ChanceItem> getMainTable() {
		return mainTable;
	}

	/**
	 * @return the mainTableSize.
	 */
	public int getMainTableSize() {
		return mainTableSize;
	}

	/**
	 * @param mainTableSize the mainTableSize to set.
	 */
	public void setMainTableSize(int mainTableSize) {
		this.mainTableSize = mainTableSize;
	}

	/**
	 * Gets the modRate.
	 * @return The modRate.
	 */
	public double getModRate() {
		return modRate;
	}

	/**
	 * Sets the modRate.
	 * @param modRate The modRate to set.
	 */
	public void setModRate(double modRate) {
		this.modRate = modRate;
	}

}