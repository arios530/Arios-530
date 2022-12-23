package org.arios.game.content.global;

import org.arios.game.node.entity.player.Player;

/**
 * Holds the data for the Boss Killcounter.    
 * @author Splinter
 */
public enum BossKillCounter {
	KING_BLACK_DRAGON(new int[] { 50 }, "kbd", "King Black Dragon"), BORK(new int[] { 7133, 7134 }, "bork", "Bork"), DAGANNOTH_SUPREME(new int[] { 2881 }, "supreme", "Dagannoth Supreme"), DAGANNOTH_PRIME(new int[] { 2882 }, "prime", "Dagannoth Prime"), DAGANNOTH_REX(new int[] { 2883 }, "rex", "Dagannoth Rex"), CHAOS_ELEMENTAL(new int[] { 3200 }, "chaosele", "Chaos Elemental"), GIANT_MOLE(new int[] { 3340 }, "mole", "Giant Mole"), SARADOMIN(new int[] { 6247 }, "zilyana", "Commander Zilyana"), ZAMORAK(new int[] { 6203 }, "zamorak", "K'ril Tsutsaroth"), BANDOS(new int[] { 6260 }, "bandos", "General Graardor"), ARMADYL(new int[] { 6222 }, "armadyl", "Kree'arra"), JAD(new int[] { 2745 }, "tzhaarjad", "Tz-Tok Jad"), KALPHITE_QUEEN(new int[] { 1160 }, "kqueen", "Kalphite Queen"), ;

	/**
	 * The npcs that can increase the killcounter
	 */
	private final int[] npc;

	/**
	 * The attribute data to be saved.
	 */
	private final String data;

	/**
	 * The name of the NPC, to be displayed as a sendMessage
	 */
	private final String name;

	/**
	 * Constructs a new {@code BossKillCounter} {@code Object}.
	 * @param npc the npc.
	 * @param data the attribute data
	 * @param name the npc's string name
	 */
	BossKillCounter(final int[] npc, final String data, final String name) {
		this.npc = npc;
		this.data = data;
		this.name = name;
	}

	/**
	 * Gets the npc.
	 * @return The npc.
	 */
	public int[] getNpc() {
		return npc;
	}

	/**
	 * Gets the attribute data
	 * @return data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Gets the NPC's name
	 * @return their name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the type for the npc.
	 * @param npc the npc.
	 * @return the BossKillcounter
	 */
	public static BossKillCounter forNPC(final int npc) {
		for (BossKillCounter kc : BossKillCounter.values()) {
			for (int i : kc.getNpc()) {
				if (npc == i) {
					return kc;
				}
			}
		}
		return null;
	}

	/**
	 * Add to the player's killcount for that particular boss.
	 * @param killer The player who killed the npc
	 * @param npcid the ID of the npc that just died
	 */
	public static void addtoKillcount(Player killer, int npcid) {
		if (killer == null) {
			return;
		}
		BossKillCounter boss = BossKillCounter.forNPC(npcid);
		if (killer.getAttribute("bosskc:" + boss.getData() + "") != null) {
			killer.getSavedData().getGlobalData().getBossCounters()[boss.ordinal()] = killer.getAttribute("bosskc:" + boss.getData() + "");
			killer.removeAttribute("bosskc:" + boss.getData());
		}
		killer.getSavedData().getGlobalData().getBossCounters()[boss.ordinal()]++;
		if (killer.getSavedData().getGlobalData().getBossCounters()[boss.ordinal()] == 1) {
			killer.getPacketDispatch().sendMessage("You just killed " + boss.getName() + " for the first time!");
		}
		killer.getPacketDispatch().sendMessage("Your " + boss.getName() + " killcount is now: <col=ff0000>" + killer.getSavedData().getGlobalData().getBossCounters()[boss.ordinal()] + "</col>.");
	}

	/**
	 * Add to the player's save file the amount of chests they raided.
	 * @param player the player
	 */
	public static void addtoBarrowsCount(Player player) {
		if (player == null) {
			return;
		}
		if (player.getAttribute("barrows_chest_counter") != null) {
			player.getSavedData().getGlobalData().setBarrowsLoots((int) player.getAttribute("barrows_chest_counter"));
			player.removeAttribute("barrows_chest_counter");
		}
		player.getSavedData().getGlobalData().setBarrowsLoots(player.getSavedData().getGlobalData().getBarrowsLoots() + 1);
		if (player.getSavedData().getGlobalData().getBarrowsLoots() == 1) {
			player.getPacketDispatch().sendMessage("You've looted the chest for the first time!");
		}
		player.getPacketDispatch().sendMessage("Your Barrows chest count is: <col=ff0000>" + player.getSavedData().getGlobalData().getBarrowsLoots() + "</col>.");
	}

}