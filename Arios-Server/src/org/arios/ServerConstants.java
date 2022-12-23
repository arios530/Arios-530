package org.arios;

import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.tools.RandomFunction;

/**
 * A class holding constants of the server.
 * @author Emperor
 * @author Vexia
 */
public final class ServerConstants {

	/**
	 * The start locations for the tutorial.
	 */
	public static final Location[] START_LOCS = new Location[] { Location.create(3094, 3107, 0), Location.create(3094, 3473, 0) };

	/**
	 * The home locations for economy & spawn.
	 */
	public static final Location[] HOME_LOCS = new Location[] { Location.create(3221, 3219 + RandomFunction.random(0, 3), 0), Location.create(3087, 3491, 0) };

	/**
	 * The teleport destinations.
	 */
	public static final Object[][] TELEPORT_DESTINATIONS = { { Location.create(2659, 2649, 0), "pc", "pest control", "pest" }, { Location.create(3293, 3184, 0), "al kharid", "alkharid", "kharid" }, { Location.create(3222, 3217, 0), "lumbridge", "lumby" }, { Location.create(3110, 3168, 0), "wizard tower", "wizards tower", "tower", "wizards" }, { Location.create(3083, 3249, 0), "draynor", "draynor village" }, { Location.create(3019, 3244, 0), "port sarim", "sarim" }, { Location.create(2956, 3209, 0), "rimmington" }, { Location.create(2965, 3380, 0), "fally", "falador" }, { Location.create(2895, 3436, 0), "taverly" }, { Location.create(3080, 3423, 0), "barbarian village", "barb" }, { Location.create(3213, 3428, 0), "varrock" }, { Location.create(3164, 3485, 0), "grand exchange", "ge" }, { Location.create(2917, 3175, 0), "karamja" }, { Location.create(2450, 5165, 0), "tzhaar" }, { Location.create(2795, 3177, 0), "brimhaven" }, { Location.create(2849, 2961, 0), "shilo village", "shilo" }, { Location.create(2605, 3093, 0), "yanille" }, { Location.create(2663, 3305, 0), "ardougne", "ardy" }, { Location.create(2450, 3422, 0), "gnome stronghold", "gnome" }, { Location.create(2730, 3485, 0), "camelot", "cammy", "seers" }, { Location.create(2805, 3435, 0), "catherby" }, { Location.create(2659, 3657, 0), "rellekka" }, { Location.create(2890, 3676, 0), "trollheim" }, { Location.create(2914, 3746, 0), "godwars", "gwd", "god wars" }, { Location.create(3180, 3684, 0), "bounty hunter", "bh" }, { Location.create(3272, 3687, 0), "clan wars", "clw" }, { Location.create(3090, 3957, 0), "mage arena", "mage", "magearena", "arena" }, { Location.create(3069, 10257, 0), "king black dragon", "kbd" }, { Location.create(3359, 3416, 0), "digsite" }, { Location.create(3488, 3489, 0), "canifis" }, { Location.create(3428, 3526, 0), "slayer tower", "slayer" }, { Location.create(3502, 9483, 2), "kalphite queen", "kq", "kalphite hive", "kalphite" }, { Location.create(3233, 2913, 0), "pyramid" }, { Location.create(3419, 2917, 0), "nardah" }, { Location.create(3482, 3090, 0), "uzer" }, { Location.create(3358, 2970, 0), "pollnivneach", "poln" }, { Location.create(3305, 2788, 0), "sophanem" }, { Location.create(2898, 3544, 0), "burthorpe", "burthorp" }, { Location.create(3088, 3491, 0), "edge", "edgeville" }, { Location.create(3169, 3034, 0), "bedabin" }, { Location.create(3565, 3289, 0), "barrows" } };

	/**
	 * The cache path.
	 */
	public static final String CACHE_PATH = "data/cache/";

	/**
	 * The store path.
	 */
	public static final String STORE_PATH = "data/store/";

	/**
	 * The player account path.
	 */
	public static final String PLAYER_SAVE_PATH = "data/players/";

	/**
	 * The player details save path.
	 */
	public static final String PLAYER_DETAILS_PATH = "data/players/details/";

	/**
	 * The maximum amount of players.
	 */
	public static final int MAX_PLAYERS = (1 << 11) - 1;

	/**
	 * The maximum amount of NPCs.
	 */
	public static final int MAX_NPCS = (1 << 15) - 1;
	
	/**
	 * Constructs a new {@Code ServerConstants} {@Code Object}
	 */
	private ServerConstants() {
		/*
		 * empty.
		 */
	}

	/**
	 * Gets the startLocation.
	 * @return the startLocation
	 */
	public static Location getStartLocation() {
		return GameWorld.getSettings().isEconomy() ? START_LOCS[0] : START_LOCS[1];
	}

	/**
	 * Gets the homeLocation.
	 * @return the homeLocation
	 */
	public static Location getHomeLocation() {
		return GameWorld.getSettings().isEconomy() ? HOME_LOCS[0] : HOME_LOCS[1];
	}

}
