package org.arios.game.content.skill.member.construction;

import org.arios.game.content.skill.member.construction.conditions.DecorationCondition;
import org.arios.game.content.skill.member.construction.conditions.WateringcanCondition;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;

/**
 * Represents the decorations.
 * @author Emperor
 */
public enum Decoration {

	/**
	 * Garden centrepiece decorations.
	 */
	PORTAL(13405, 8168, 1, 100.0, new Item(2351, 10)), ROCK(13406, 8169, 5, 100.0, new Item(3420, 5)), POND(13407, 8170, 10, 100.0, new Item(1761, 10)), IMP_STATUE(13408, 8171, 15, 150.0, new Item(3420, 5), new Item(1761, 5)), DUNGEON_ENTRANCE(13409, 8172, 70, 500.0, new Item(8786)),

	/**
	 * Garden big tree decorations.
	 */
	BIG_DEAD_TREE(13411, 8173, 5, 31.0, WateringcanCondition.INSTANCE, new Item(8417)), BIG_TREE(13412, 8174, 10, 44.0, WateringcanCondition.INSTANCE, new Item(8419)), BIG_OAK_TREE(13413, 8175, 15, 70.0, WateringcanCondition.INSTANCE, new Item(8421)), BIG_WILLOW_TREE(13414, 8176, 30, 100.0, WateringcanCondition.INSTANCE, new Item(8423)), BIG_MAPLE_TREE(13415, 8177, 45, 122.0, WateringcanCondition.INSTANCE, new Item(8425)), BIG_YEW_TREE(13416, 8178, 60, 141.0, WateringcanCondition.INSTANCE, new Item(8427)), BIG_MAGIC_TREE(13417, 8179, 75, 223.0, WateringcanCondition.INSTANCE, new Item(8429)),

	/**
	 * Garden tree decorations.
	 */
	DEAD_TREE(13418, 8173, 5, 31.0, WateringcanCondition.INSTANCE, new Item(8417)), TREE(13419, 8174, 10, 44.0, WateringcanCondition.INSTANCE, new Item(8419)), OAK_TREE(13420, 8175, 15, 70.0, WateringcanCondition.INSTANCE, new Item(8421)), WILLOW_TREE(13421, 8176, 30, 100.0, WateringcanCondition.INSTANCE, new Item(8423)), MAPLE_TREE(13422, 8177, 45, 122.0, WateringcanCondition.INSTANCE, new Item(8425)), YEW_TREE(13423, 8178, 60, 141.0, WateringcanCondition.INSTANCE, new Item(8427)), MAGIC_TREE(13424, 8179, 75, 223.0, WateringcanCondition.INSTANCE, new Item(8429)),

	/**
	 * Garden big plant 1 decorations.
	 */
	FERN(13425, 8186, 1, 31.0, WateringcanCondition.INSTANCE, new Item(8431)), BUSH(13426, 8187, 6, 70.0, WateringcanCondition.INSTANCE, new Item(8433)), TALL_PLANT(13427, 8188, 12, 100.0, WateringcanCondition.INSTANCE, new Item(8435)),

	/**
	 * Garden big plant 2 decorations.
	 */
	SHORT_PLANT(13428, 8189, 1, 31.0, WateringcanCondition.INSTANCE, new Item(8431)), LARGE_LEAF_PLANT(13429, 8190, 6, 70.0, WateringcanCondition.INSTANCE, new Item(8433)), HUGE_PLANT(13430, 8191, 12, 100.0, WateringcanCondition.INSTANCE, new Item(8435)),

	/**
	 * Garden small plant 1 decorations.
	 */
	PLANT(13431, 8180, 1, 31.0, new Item(8431)), SMALL_FERN(13432, 8181, 6, 70.0, new Item(8433)), FERN_SP(13433, 8182, 12, 100.0, new Item(8435)),

	/**
	 * Garden small plant 2 decorations.
	 */
	DOCK_LEAF(13434, 8183, 1, 31.0, new Item(8431)), THISTLE(13435, 8184, 6, 70.0, new Item(8433)), REEDS(13436, 8185, 12, 100.0, new Item(8435)),

	/**
	 * Parlour chair spot
	 */
	CRUDE_CHAIR(13581, 8309, 1, 58.0, new Item(960, 2), new Item(ConConstants.NAILS, 2)), WOODEN_CHAIR(13582, 8310, 8, 87.0, new Item(960, 3), new Item(ConConstants.NAILS, 3)), ROCKING_CHAIR(13583, 8311, 14, 87.0, new Item(960, 3), new Item(ConConstants.NAILS, 3)), OAK_CHAIR(13584, 8312, 19, 120.0, new Item(8778, 2)), OAK_ARMCHAIR(13585, 8313, 26, 180.0, new Item(8778, 3)), TEAK_ARMCHAIR(13586, 8314, 35, 180.0, new Item(8780, 2)), MAHOGANY_ARMCHAIR(13587, 8315, 50, 280.0, new Item(8782, 2)),

	/**
	 * Parlour rug spot
	 */
	BROWN_RUG(13588, 8316, 2, 30.0, new Item(8790, 2)), RED_RUG(13591, 8317, 13, 60.0, new Item(8790, 4)), OPULENT_RUG(13594, 8318, 65, 360.0, new Item[] { new Item(8790, 4), new Item(4692, 1) }),

	/**
	 * Parlour fireplaces
	 */
	CLAY_FIREPLACE(13609, 8325, 3, 30.0, new Item(1761, 3)), STONE_FIREPLACE(13611, 8326, 33, 40.0, new Item(3420, 2)), MARBLE_FIREPLACE(13613, 8327, 63, 500.0, new Item(8786, 1)),

	/**
	 * Parlour curtain spot
	 */
	TORN_CURTAINS(13603, 8322, 2, 132.0, new Item[] { new Item(960, 3), new Item(8790, 3) }), CURTAINS(13604, 8323, 18, 225.0, new Item[] { new Item(8778, 3), new Item(8790, 3) }), OPULENT_CURTAINS(13605, 8324, 40, 315.0, new Item[] { new Item(8780, 3), new Item(8790, 3) }),

	/**
	 * Parlour bookcases
	 */
	WOODEN_BOOKCASE(13597, 8319, 4, 132.0, new Item(960, 4)), OAK_BOOKCASE(13598, 8320, 29, 225.0, new Item(8778, 3)), MAHOGANY_BOOKCASE(13599, 8321, 40, 315.0, new Item(8782, 3)),

	/**
	 * Kitchen Beer Barrels TODO: These also require cooking levels! Basic: 1,
	 * Cider: 14, Asgarnian: 24, Greenman's: 29, D.Bitter: 39, Chef's: 54
	 */
	BASIC_BEER_BARREL(13568, 8239, 7, 87.0, new Item(960, 3)), CIDER_BARREL(13569, 8240, 12, 91.0, new Item[] { new Item(960, 3), new Item(5763, 8) }), ASGARNIAN_ALE_BARREL(13570, 8241, 18, 184.0, new Item[] { new Item(8778, 3), new Item(1905, 8) }), GREENMANS_ALE_BARREL(13571, 8242, 26, 184.0, new Item[] { new Item(8778, 3), new Item(1909, 8) }), DRAGON_BITTER_BARREL(13572, 8243, 36, 224.0, new Item[] { new Item(8778, 3), new Item(1911, 8), new Item(2353, 2) }), CHEFS_DELIGHT_BARREL(13573, 8244, 48, 224.0, new Item[] { new Item(8778, 3), new Item(5755, 8), new Item(2353, 2) }),

	/**
	 * Kitchen Tables!
	 */
	KITCHEN_WOODEN_TABLE(13577, 8246, 12, 87.0, new Item(960, 3)), KITCHEN_OAK_TABLE(13578, 8247, 32, 180.0, new Item(8778, 3)), KITCHEN_TEAK_TABLE(13579, 8248, 52, 270.0, new Item(8780, 3)),

	/**
	 * Kitchen Stoves
	 */
	BASIC_FIREPIT(13528, 8216, 5, 40.0, new Item[] { new Item(1761, 2), new Item(2353, 1) }), FIREPIT_WITH_HOOK(13529, 8217, 11, 60.0, new Item[] { new Item(1761, 2), new Item(2353, 2) }), FIREPIT_WITH_POT(13531, 8218, 17, 80.0, new Item[] { new Item(1761, 2), new Item(2353, 3) }), SMALL_OVEN(13533, 8219, 24, 80.0, new Item(2353, 4)), LARGE_OVEN(13536, 8220, 29, 100.0, new Item(2353, 5)), BASIC_RANGE(13539, 8221, 34, 120.0, new Item(2353, 6)), FANCY_RANGE(13542, 8222, 42, 160.0, new Item(2353, 8)),

	/**
	 * Kitchen larders
	 */
	WOODEN_LARDER(13565, 8233, 9, 228.0, new Item(960, 8)), OAK_LARDER(13566, 8234, 33, 480.0, new Item(8778, 8)), TEAK_LARDER(13567, 8235, 43, 750.0, new Item[] { new Item(8780, 8), new Item(8790, 2) }),

	/**
	 * Kitchen shelves
	 */
	WOODEN_SHELVES_1(13545, 8223, 6, 87.0, new Item(960, 3)), WOODEN_SHELVES_2(13546, 8224, 12, 147.0, new Item[] { new Item(960, 3), new Item(1761, 6) }), WOODEN_SHELVES_3(13547, 8225, 23, 147.0, new Item[] { new Item(960, 3), new Item(1761, 6) }), OAK_SHELVES_1(13548, 8226, 34, 240.0, new Item[] { new Item(8778, 3), new Item(1761, 6) }), OAK_SHELVES_2(13549, 8227, 45, 240.0, new Item[] { new Item(8778, 3), new Item(1761, 6) }), TEAK_SHELVES_1(13550, 8228, 56, 330.0, new Item[] { new Item(8780, 3), new Item(1761, 6) }), TEAK_SHELVES_2(13551, 8229, 67, 930.0, new Item[] { new Item(8780, 3), new Item(1761, 6), new Item(4692, 2) }),

	/**
	 * Kitchen sinks
	 */
	PUMP_AND_DRAIN(13559, 8230, 7, 100.0, new Item(2353, 5)), PUMP_AND_TUB(13561, 8231, 27, 200.0, new Item(2353, 10)), SINK(13563, 8232, 47, 300.0, new Item(2353, 15)),

	/**
	 * Kitchen cat baskets/blankets
	 */
	CAT_BLANKET(13574, 8236, 5, 15.0, new Item(8790, 1)), CAT_BASKET(13575, 8237, 19, 58.0, new Item(960, 2)), CAST_BASKET_CUSHIONED(13576, 8238, 33, 58.0, new Item[] { new Item(960, 2), new Item(1737, 2) }),

	/**
	 * Dining room tables
	 */
	DINING_TABLE_WOOD(13293, 8246, 10, 115.0, new Item(960, 4)), DINING_TABLE_OAK(13294, 8247, 22, 240.0, new Item(8778, 4)), DINING_TABLE_CARVED_OAK(13295, 8247, 31, 360.0, new Item(8778, 6)), DINING_TABLE_TEAK(13296, 8248, 38, 360.0, new Item(8780, 4)), DINING_TABLE_CARVED_TEAK(13297, 8248, 45, 600.0, new Item[] { new Item(8780, 6), new Item(8790, 4) }), DINING_TABLE_MAHOGANY(13298, 8120, 52, 840.0, new Item(8782, 6)), DINING_TABLE_OPULENT(13299, 8121, 72, 3100.0, new Item[] { new Item(8782, 6), new Item(8790, 4), new Item(4692, 4), new Item(8786, 2) }),

	/**
	 * Dining room benches - why are there so many of these?
	 */
	BENCH_WOODEN(13300, 8108, 10, 115.0, new Item(960, 4)), BENCH_OAK(13301, 8109, 22, 240.0, new Item(8778, 4)), BENCH_CARVED_OAK(13302, 8110, 31, 240.0, new Item(8778, 4)), BENCH_TEAK(13303, 8111, 38, 360.0, new Item(8780, 4)), BENCH_CARVED_TEAK(13304, 8112, 44, 360.0, new Item(8780, 4)), BENCH_MAHOGANY(13305, 8113, 52, 560.0, new Item(8782, 6)), BENCH_GILDED(13306, 8114, 61, 1760.0, new Item[] { new Item(8782, 4), new Item(4692, 4) }),

	/**
	 * Dining room bell-pulls
	 */
	ROPE_PULL(13574, 8236, 5, 15.0, new Item[] { new Item(954, 1), new Item(8778, 1) }), BELL_PULL(13575, 8237, 19, 58.0, new Item[] { new Item(8780, 1), new Item(8790, 2) }), FANCY_BELL_PULL(13576, 8238, 33, 58.0, new Item[] { new Item(8780, 1), new Item(8790, 2), new Item(4692, 1) }),

	WOODEN_BED(13148, 4151, 20, 117.0, new Item(ConConstants.PLANK, 3), new Item(ConConstants.NAILS, 3), new Item(ConConstants.BOLT_OF_CLOTH, 2)),

	CLAY_ATTACK_STONE(13392, 4151, 39, 100.0, new Item(ConConstants.SOFT_CLAY, 10)),

	/**
	 * Lecterns
	 */
	OAK_LECTERN(13642, 4151, 40, 60.0, new Item(ConConstants.OAK_PLANK, 1)),

	/**
	 * Fancy Dress boxes
	 */
	OAK_COSTUME_BOX(18772, 4151, 44, 120, new Item(ConConstants.OAK_PLANK, 2)),

	/**
	 * Altars
	 */
	OAK_ALTAR(13179, 4151, 45, 240.0, new Item(ConConstants.OAK_PLANK, 4)),

	/**
	 * Skipped these: Dining room fireplaces - same as the parlour ones.
	 * CLAY_FIREPLACE, STONE_FIREPLACE, MARBLE_FIRPLACE Dining room curtains -
	 * same as the parlour ones TORN_CURTAINS, CURTAINS, OPULENT_CURTAINS Dining
	 * room decorations - the object id and item id depends on the crest you
	 * buy.
	 */

	/**
	 * Workshop workbench
	 */
	WORKBENCH_WOODEN(13704, 8375, 17, 145.0, new Item(960, 1)), WORKBENCH_OAK(13705, 8376, 32, 300.0, new Item(8778, 5)), WORKBENCH_STEEL_FRAME(13706, 8377, 46, 440.0, new Item[] { new Item(8778, 6), new Item(2353, 4) }), WORKBENCH_WITH_VICE(13707, 8378, 62, 750.0, new Item[] { new Item(8377, 1), new Item(8778, 2), new Item(2353, 1) }), WORKBENCH_WITH_LATHE(13708, 8379, 77, 1000.0, new Item[] { new Item(8376, 1), new Item(8778, 2), new Item(2353, 1) }),

	/**
	 * Workshop repair benches/stands
	 */
	REPAIR_BENCH(13713, 8389, 15, 120.0, new Item(8778, 2)), WHETSTONE(13714, 8390, 35, 260.0, new Item[] { new Item(8778, 4), new Item(3420, 1) }), ARMOUR_STAND(13715, 8391, 55, 500.0, new Item[] { new Item(8778, 8), new Item(3420, 1) }),

	/**
	 * Workshop easels
	 */
	PLUMING_STAND(13716, 8392, 16, 120.0, new Item(8778, 2)), SHIELD_EASEL(13717, 8393, 41, 240.0, new Item(8778, 4)), BANNER_EASEL(13718, 8394, 66, 510.0, new Item[] { new Item(8778, 8), new Item(8790, 2) }),

	/**
	 * Workshop crafting tables TODO: These are upgradable hotspots, therefore
	 * crafting table 3 would require crafting table 2 to be already built in
	 * that spot.
	 */
	CRAFTING_TABLE_1(13709, 8380, 16, 50.0, new Item(8778, 4)), CRAFTING_TABLE_2(13710, 8381, 25, 100.0, new Item(1775, 1)), CRAFTING_TABLE_3(13711, 8382, 34, 175.0, new Item(1775, 2)), CRAFTING_TABLE_4(13712, 8383, 42, 240.0, new Item(8778, 2)),

	/**
	 * Workshop tool stores These are also upgradable just like the tables
	 * above.
	 */
	TOOL_STORE_1(13699, 8384, 15, 120.0, new Item(8778, 2)), TOOL_STORE_2(13700, 8385, 25, 120.0, new Item(8778, 2)), TOOL_STORE_3(13701, 8386, 35, 120.0, new Item(8778, 2)), TOOL_STORE_4(13702, 8387, 44, 120.0, new Item(8778, 2)), TOOL_STORE_5(13703, 8388, 55, 120.0, new Item(8778, 2)),

	/**
	 * Skill hall staircases
	 */
	OAK_STAIRCASE(13497, 8249, 27, 680.0, new Item[] { new Item(8778, 10), new Item(2353, 4) }), TEAK_STAIRCASE(13499, 8252, 48, 980.0, new Item[] { new Item(8780, 10), new Item(2353, 4) }), SPIRAL_STAIRCASE(13503, 8258, 67, 1040.0, new Item[] { new Item(8780, 10), new Item(3420, 7) }), MARBLE_STAIRCASE(13501, 8257, 82, 3200.0, new Item[] { new Item(8782, 5), new Item(8786, 5) }), MARBLE_SPIRAL(13505, 8259, 97, 4400.0, new Item[] { new Item(8780, 10), new Item(8786, 7) }),

	/** TODO: Skipped the castle wars armour stand **/

	/**
	 * Skill hall armour stands TODO: These require a smithing level to place as
	 * well! Mith: 68, Addy: 88, Rune: 99
	 */
	MITHRIL_ARMOUR(13491, 8270, 28, 135.0, new Item[] { new Item(8778, 2), new Item(1159, 1), new Item(1121, 1), new Item(1085, 1) }), ADAMANT_ARMOUR(13492, 8271, 28, 150.0, new Item[] { new Item(8778, 2), new Item(1161, 1), new Item(1123, 1), new Item(1091, 1) }), RUNE_ARMOUR(13493, 8272, 28, 165.0, new Item[] { new Item(8778, 2), new Item(1163, 1), new Item(1127, 1), new Item(1093, 1) }),

	/**
	 * Skill hall trophies TODO: Placing these also gives experience in other
	 * skills. (Some give Slayer, others give attack, defence, etc)
	 */
	CRAWLING_HAND(13481, 8260, 38, 211.0, new Item[] { new Item(8780, 2), new Item(8260, 1) }), COCKATRICE_HEAD(13482, 8261, 38, 224.0, new Item[] { new Item(8780, 2), new Item(8261, 1) }), BASILISK_HEAD(13483, 8262, 38, 243.0, new Item[] { new Item(8780, 2), new Item(8262, 1) }), KURASK_HEAD(13484, 8263, 58, 357.0, new Item[] { new Item(8782, 2), new Item(8263, 1) }), ABYSSAL_DEMON_HEAD(13485, 8264, 58, 389.0, new Item[] { new Item(8782, 2), new Item(8264, 1) }), KBD_HEAD(13486, 8265, 78, 1103.0, new Item[] { new Item(8782, 2), new Item(8265, 1) }), KQ_HEAD(13487, 8266, 78, 1103.0, new Item[] { new Item(8782, 2), new Item(8266, 1) }),

	/**
	 * Skill hall fishing trophies TODO: Mounting these also gives fishing xp
	 * TODO: The only way to get the required items for these is catching
	 * "big bass" or "big shark" while fishing.
	 */
	MOUNTED_BASS(13488, 8267, 36, 151.0, new Item[] { new Item(8778, 2), new Item(7989, 1) }), MOUNTED_SWORDFISH(13489, 8268, 56, 230.0, new Item[] { new Item(8780, 2), new Item(7991, 1) }), MOUNTED_SHARK(13490, 8269, 76, 350.0, new Item[] { new Item(8782, 2), new Item(7993, 1) }),

	/**
	 * Skill hall rune display case TODO: Mounting these also gives runecrafting
	 * xp TODO: To make these, you must have the level to craft all runes they
	 * contain. (1st case level req:14, 2nd case level req:44)
	 */
	RUNE_CASE1(13507, 8095, 41, 190.0, new Item[] { new Item(8780, 2), new Item(1775, 2), new Item(554, 1), new Item(556, 1), new Item(557, 1), new Item(555, 1) }), RUNE_CASE2(13508, 8095, 41, 212.0, new Item[] { new Item(8780, 2), new Item(1775, 2), new Item(559, 1), new Item(564, 1), new Item(562, 1), new Item(561, 1) }),

	/**
	 * Games room attack stones (melee)
	 */
	CLAY_STONE(13392, 8153, 39, 100.0, new Item(1761, 10)), LIMESTONE_STONE(13393, 8154, 59, 200.0, new Item(3420, 10)), MARBLE_STONE(13394, 8155, 79, 2000.0, new Item(8786, 4)),

	/**
	 * Games room attack stones (ranged)
	 */
	HOOP_AND_STICK(13398, 8162, 30, 120.0, new Item(8778, 2)), DARTBOARD(13400, 8163, 54, 290.0, new Item[] { new Item(8780, 3), new Item(2353, 1) }), ARCHERY_TARGET(13402, 8164, 81, 600.0, new Item[] { new Item(8780, 6), new Item(2353, 3) }),

	/**
	 * Games room elemental balance
	 */
	BALANCE_1(13395, 8156, 37, 176.0, new Item[] { new Item(554, 500), new Item(556, 500), new Item(557, 500), new Item(555, 500) }), BALANCE_2(13396, 8157, 57, 252.0, new Item[] { new Item(554, 1000), new Item(556, 1000), new Item(557, 1000), new Item(555, 1000) }), BALANCE_3(13397, 8158, 77, 356.0, new Item[] { new Item(554, 2000), new Item(556, 2000), new Item(557, 2000), new Item(555, 2000) }),

	/**
	 * Games room prize chests
	 */
	OAK_CHEST(13384, 8165, 34, 240.0, new Item(8778, 4)), TEAK_CHEST(13386, 8166, 44, 660.0, new Item[] { new Item(8780, 4), new Item(4692, 1) }), MAHOGANY_CHEST(13388, 8167, 54, 860.0, new Item[] { new Item(8782, 4), new Item(4692, 1) }),

	/**
	 * Games room "games"
	 */
	JESTER(13390, 8159, 39, 360.0, new Item(8780, 4)), TREASURE_HUNT(13379, 8160, 49, 800.0, new Item[] { new Item(8780, 8), new Item(2353, 4) }), HANGMAN(13404, 8161, 59, 1200.0, new Item[] { new Item(8780, 12), new Item(2353, 6) }),

	/**
	 * Combat room - combat rings TODO: Skipped the wall decorations again. The
	 * object id depends on the player's crest that they choose from Sir
	 * Renitee.
	 */

	BOXING_RING(13129, 8023, 32, 570.0, new Item[] { new Item(8778, 6), new Item(8790, 4) }), FENCING_RING(13133, 8024, 41, 570.0, new Item[] { new Item(8770, 8), new Item(7991, 6) }), COMBAT_RING(13137, 8025, 51, 630.0, new Item[] { new Item(8780, 6), new Item(7991, 6) }), RANGING_PEDESTALS(13147, 8026, 71, 720.0, new Item(8780, 8)), BALANCE_BEAM(13142, 8027, 81, 1000.0, new Item[] { new Item(8780, 10), new Item(2353, 5) }),

	/**
	 * Combat room storage space
	 */
	GLOVE_RACK(13381, 8028, 34, 120.0, new Item(8778, 2)), WEAPONS_RACK(13382, 8029, 44, 180.0, new Item(8780, 2)), EXTRA_WEAPONS_RACK(13383, 8030, 54, 440.0, new Item[] { new Item(8780, 4), new Item(2353, 4) }),

	/**
	 * Style related decoration.
	 */
	BASIC_WOOD_WINDOW(13099, -1, 1, 0.0), BASIC_STONE_WINDOW(13091, -1, 1, 0.0), WHITEWASHED_STONE_WINDOW(13099, -1, 1, 0.0), FREMENNIK_WINDOW(13099, -1, 1, 0.0), TROPICAL_WOOD_WINDOW(13099, -1, 1, 0.0), FANCY_STONE_WINDOW(13099, -1, 1, 0.0), ;

	/**
	 * The object id.
	 */
	private final int objectId;

	/**
	 * The item id for the interface.
	 */
	private final int interfaceItem;

	/**
	 * The level requirement.
	 */
	private final int level;

	/**
	 * The experience gained for building this decoration.
	 */
	private final double experience;

	/**
	 * The item required.
	 */
	private final Item[] items;

	/**
	 * The tools required.
	 */
	private final int[] tools;

	/**
	 * The decoration condition used for building.
	 */
	private DecorationCondition condition;

	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectId The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int objectId, int interfaceItem, int level, double experience, Item... items) {
		this(objectId, interfaceItem, level, experience, new int[] { 2347, 8794 }, null, items);
	}

	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectId The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param condition the decoration condition.
	 * @param items The items required.
	 */
	private Decoration(int objectId, int interfaceItem, int level, double experience, DecorationCondition condition, Item... items) {
		this(objectId, interfaceItem, level, experience, new int[] { 2347, 8794 }, condition, items);
	}

	/**
	 * Constructs a new {@code Portal} {@code Object}.
	 * @param objectId The object id.
	 * @param interfaceItem The item id for the building interface.
	 * @param level The level required.
	 * @param experience The experience gained.
	 * @param items The items required.
	 */
	private Decoration(int objectId, int interfaceItem, int level, double experience, int[] tools, DecorationCondition condition, Item... items) {
		this.objectId = objectId;
		this.interfaceItem = interfaceItem;
		this.level = level;
		this.experience = experience;
		this.tools = tools;
		this.items = items;
		this.condition = condition;
	}

	/**
	 * Gets the decoration on the given location.
	 * @param player The player.
	 * @param l The location.
	 * @return The decoration.
	 */
	public static Decoration getDecoration(Player player, Location l) {
		Room room = player.getHouseManager().getRooms()[l.getZ()][l.getLocalX() >> 3][l.getLocalY() >> 3];
		for (Hotspot h : room.getHotspots()) {
			if (h.getChunkX() == l.getChunkOffsetX() && h.getChunkY() == l.getChunkOffsetY()) {
				return h.getHotspot().getDecorations()[h.getDecorationIndex()];
			}
		}
		return null;
	}

	/**
	 * Gets the objectId.
	 * @return The objectId.
	 */
	public int getObjectId() {
		return objectId;
	}

	/**
	 * Gets the level.
	 * @return The level.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Gets the experience.
	 * @return The experience.
	 */
	public double getExperience() {
		return experience;
	}

	/**
	 * Gets the items.
	 * @return The items.
	 */
	public Item[] getItems() {
		return items;
	}

	/**
	 * Gets the tools.
	 * @return The tools.
	 */
	public int[] getTools() {
		return tools;
	}

	/**
	 * Gets the interfaceItem.
	 * @return the interfaceItem
	 */
	public int getInterfaceItem() {
		return interfaceItem;
	}

	/**
	 * Gets the condition.
	 * @return the condition
	 */
	public DecorationCondition getCondition() {
		return condition;
	}

	/**
	 * Sets the condition.
	 * @param condition the condition to set.
	 */
	public void setCondition(DecorationCondition condition) {
		this.condition = condition;
	}
}