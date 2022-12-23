package org.arios.tools.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.arios.ServerConstants;
import org.arios.cache.ServerStore;
import org.arios.cache.misc.buffer.ByteBufferUtils;
import org.arios.game.node.entity.player.link.music.MusicEntry;
import org.arios.game.world.map.zone.ZoneBorders;

public final class MusicPropertiesPacker {

	/**
	 * The music zones.
	 */
	private static Map<MusicEntry, ZoneBorders[]> zones = new HashMap<>();

	/**
	 * Configures the music data.
	 */
	private static void configureMusic() {
		// Don't remove anything from this!
		// Also make sure zones don't overlap!
		add(471, "Brew Hoo Hoo", 338, forRegion(14747));
		add(583, "Chef Surprize", 399, forRegion(7507));
		add(576, "Davy Jones's Locker", 394, forRegion(11924));
		add(285, "Etcetera", 227, forRegion(10300));
		add(4, "Hell's Bells", 254, forRegion(11066));
		add(6, "Jolly-R", 65, forRegion(11058));
		add(396, "Land of the Dwarves", 310, forRegion(11423));
		add(264, "Mad Eadgar", 213, forRegion(11677));
		add(505, "Pharaoh's Tomb", 355, forRegion(13356), forRegion(12105));
		add(334, "Pirates of Peril", 262, forRegion(12093));
		add(462, "Spirits of Elid", 331, forRegion(13461));
		add(517, "Subterranea", 362, forRegion(10142));
		add(395, "Tale of Keldagrim", 309, forRegion(11678));
		add(140, "Talking Forest", 119, forRegion(10550));
		add(478, "The Cellar Dwellers", 342, forRegion(10135));
		add(425, "The Chosen", 324, forRegion(9805));
		add(79, "The Desert", 120, forRegion(12591));
		add(461, "The Desolate Isle", 330, forRegion(10042));
		add(403, "The Far Side", 314, forRegion(12111));
		add(464, "The Genie", 332, forRegion(13457));
		add(377, "The Golem", 295, forRegion(13616), forRegion(13872));
		add(407, "The Lost Melody", 315, forRegion(13206));
		add(573, "The Mad Mole", 393, forRegion(6992));
		add(448, "The Monsters Below", 329, forRegion(9886));
		add(316, "The Navigator", 255, forRegion(10652));
		add(355, "The Other Side", 278, forRegion(14646));
		add(413, "The Quiz Master", 318, forRegion(7754));
		add(402, "The Rogues' Den", 313, forRegion(11853), forRegion(12109));
		add(170, "The Shadow", 121, forRegion(11314));
		add(341, "The Slayer", 269, forRegion(11164));
		add(339, "The Terrible Tower", 267, forRegion(13623));
		add(133, "The Tower", 122, forRegion(10292), forRegion(10136));
		add(496, "The Trade Parade", 555, forRegion(12598));
		add(105, "Tomorrow", 163, forRegion(12081));
		add(964, "Too Many Cooks...", 398, forRegion(11930));
		add(392, "Zogre Dance", 306, forRegion(9775));
		add(177, "Adventure", 0, forRegion(12854));
		add(102, "Alone", 2, forRegion(12086), forRegion(10134));
		add(90, "Ambient Jungle", 3, forRegion(11310));
		add(305, "Anywhere", 240, forRegion(10795));
		add(19, "Arabique", 7, forRegion(11417));
		add(160, "Army of Darkness", 8, forRegion(12088));
		add(186, "Arrival", 9, forRegion(11572));
		add(247, "Artistry", 200, forRegion(8010));
		add(24, "Attack1", 10, forRegion(10034));
		add(25, "Attack2", 11, forRegion(11414));
		add(26, "Attack3", 12, forRegion(12192));
		add(27, "Attack4", 13, forRegion(10289), forRegion(10389));
		add(28, "Attack5", 14, forRegion(9033));
		add(29, "Attack6", 15, forRegion(10387));
		add(180, "Attention", 16, forRegion(11825));
		add(2, "Autumn Voyage", 17, forRegion(12851));
		add(497, "Aye Car Rum Ba", 351, forRegion(8527));
		add(248, "Aztec", 201, forRegion(11157));
		add(324, "Background", 18, forRegion(11060), forRegion(7758));
		add(152, "Ballad of Enchantment", 19, forRegion(10290));
		add(263, "Bandit Camp", 214, forRegion(12590));
		add(141, "Barbarianism", 257, forRegion(12341), forRegion(12441));
		add(345, "Barking Mad", 274, forRegion(14234));
		add(99, "Baroque", 20, forRegion(10547));
		add(100, "Beyond", 21, forRegion(11418), forRegion(11419));
		add(83, "Big Chords", 22, forRegion(10032));
		add(498, "Blistering Barnacles", 352, forRegion(8528));
		add(342, "Body Parts", 270, forRegion(13979));
		add(154, "Bone Dance", 183, forRegion(13619));
		add(266, "Bone Dry", 216, forRegion(12946));
		add(64, "Book of Spells", 23, forRegion(12593));
		add(291, "Borderland", 233, forRegion(10809));
		add(132, "Breeze", 194, forRegion(9010));
		add(489, "Bubble and Squeak", 347, forRegion(7753));
		add(104, "Camelot", 24, forRegion(11062));
		add(314, "Castle Wars", 247, forRegion(9520));
		add(481, "Catch Me If You Can", 344, forRegion(10646));
		add(325, "Cave Background", 25, forRegion(12184), forRegion(11929));
		add(357, "Cave of Beasts", 280, forRegion(11165));
		add(389, "Cave of the Goblins", 304, forRegion(12693));
		add(68, "Cavern", 26, forRegion(12193), forRegion(10388));
		add(330, "Cellar Song", 173, forRegion(12697));
		add(63, "Chain of Command", 27, forRegion(10648), forRegion(10905));
		add(282, "Chamber", 225, forRegion(10821), forRegion(11078));
		add(575, "Chickened Out", 395, forRegion(9796));
		add(71, "Chompy Hunt", 178, forRegion(10542), forRegion(10642));
		add(383, "City of the Dead", 300, forRegion(12843), forRegion(13099));
		add(373, "Claustrophobia", 291, forRegion(9293));
		add(67, "Close Quarters", 175, forRegion(12602), forRegion(6234));
		add(269, "Competition", 217, forRegion(8781));
		add(142, "Complication", 258, forRegion(9035));
		add(258, "Contest", 208, forRegion(11576));
		add(418, "Corporal Punishment", 323, forRegion(12619));
		add(178, "Courage", 260, forRegion(11673));
		add(259, "Crystal Castle", 210, forRegion(9011));
		add(181, "Crystal Cave", 28, forRegion(9797));
		add(169, "Crystal Sword", 29, forRegion(12855), forRegion(10647));
		add(59, "Cursed", 186, forRegion(9623));
		add(198, "Dagannoth Dawn", 365, forRegion(7236), forRegion(7748));
		add(380, "Dance of the Undead", 298, forRegion(14131));
		add(336, "Dangerous Road", 263, forRegion(11413));
		add(381, "Dangerous Way", 299, forRegion(14231));
		add(182, "Dangerous", 30, forRegion(13115), forRegion(13114));
		add(326, "Dark", 31, forRegion(13369));
		add(476, "Dead Can Dance", 341, forRegion(12601));
		add(84, "Dead Quiet", 181, forRegion(13621), forRegion(9294));
		add(288, "Deadlands", 230, forRegion(14134));
		add(278, "Deep Down", 224, forRegion(10823), forRegion(10822));
		add(37, "Deep Wildy", 32, forRegion(11835));
		add(465, "Desert Heat", 333, forRegion(13614));
		add(174, "Desert Voyage", 33, forRegion(13102), forRegion(13359));
		add(532, "Diango's Little Helpers", 371, forRegion(8005));
		add(501, "Distant Land", 353, forRegion(13873));
		add(56, "Doorways", 34, forRegion(12599));
		add(361, "Down Below", 284, forRegion(12438));
		add(143, "Down to Earth", 259, forRegion(10571));
		add(358, "Dragontooth Island", 281, forRegion(15159));
		add(327, "Dream", 35, forRegion(12594));
		add(173, "Dunjun", 36, forRegion(11672));
		add(351, "Dynasty", 275, forRegion(13358));
		add(252, "Elven Mist", 202, forRegion(9266));
		add(148, "Emotion", 38, forRegion(10033), forRegion(10309), forRegion(10133));
		add(138, "Emperor", 39, forRegion(11570), forRegion(11670));
		add(17, "Escape", 176, forRegion(10903));
		add(586, "Everlasting Fire", 417, forRegion(13373));
		add(268, "Everywhere", 219, forRegion(8499));
		add(411, "Evil Bob's Island", 316, forRegion(10058));
		add(106, "Expanse", 40, forRegion(12605), forRegion(12852), forRegion(12952));
		add(41, "Expecting", 41, forRegion(9778), forRegion(9878));
		add(153, "Expedition", 42, forRegion(11676));
		add(270, "Exposed", 220, forRegion(8752));
		add(118, "Faerie", 43, forRegion(9540));
		add(337, "Faithless", 265, forRegion(12856));
		add(72, "Fanfare", 44, forRegion(11828));
		add(166, "Fanfare2", 162, forRegion(11823));
		add(167, "Fanfare3", 45, forRegion(10545));
		add(372, "Far Away", 292, forRegion(9265));
		add(344, "Fenkenstrain's Refrain", 271, forRegion(13879));
		add(375, "Fight or Flight", 293, forRegion(7752));
		add(312, "Find My Way", 246, forRegion(10894));
		add(463, "Fire and Brimstone", 334, forRegion(9552));
		add(119, "Fishing", 46, forRegion(11317));
		add(163, "Flute Salad", 47, forRegion(12595));
		add(121, "Forbidden", 185, forRegion(13111));
		add(251, "Forest", 203, forRegion(9009));
		add(98, "Forever", 48, forRegion(12342), forRegion(12442));
		add(409, "Frogland", 336, forRegion(9802));
		add(294, "Frostbite", 236, forRegion(11323));
		add(347, "Fruits de Mer", 273, forRegion(11059));
		add(603, "Funny Bunnies", 406, forRegion(9810));
		add(159, "Gaol", 49, forRegion(12090), forRegion(10031), forRegion(10131));
		add(125, "Garden", 50, forRegion(12853));
		add(22, "Gnome King", 51, forRegion(9782));
		add(33, "Gnome Village", 53, forRegion(9781));
		add(101, "Gnome Village2", 54, forRegion(9269));
		add(112, "Gnomeball", 56, forRegion(9270));
		add(346, "Goblin Game", 252, forRegion(10393));
		add(116, "Greatness", 57, forRegion(12596));
		add(246, "Grotto", 198, forRegion(13720));
		add(128, "Grumpy", 177, forRegion(10286));
		add(46, "Harmony2", 167, forRegion(12950));
		add(277, "Haunted Mine", 222, forRegion(11077));
		add(434, "Have a Blast", 325, forRegion(7757));
		add(612, "Head to Head", 411, forRegion(7504));
		add(190, "Heart and Mind", 174, forRegion(10059));
		add(97, "Hermit", 191, forRegion(9034));
		add(55, "High Seas", 59, forRegion(11057));
		add(18, "Horizon", 60, forRegion(11573));
		add(1, "Iban", 61, forRegion(8519));
		add(87, "Ice Melody", 165, forRegion(11318));
		add(370, "In Between", 290, forRegion(10061));
		add(530, "In the Brine", 370, forRegion(14638));
		add(511, "In the Clink", 360, forRegion(8261));
		add(188, "In the Manor", 62, forRegion(10287));
		add(469, "In the Pits", 335, forRegion(9808));
		add(260, "Insect Queen", 212, forRegion(13972));
		add(96, "Inspiration", 63, forRegion(12087));
		add(412, "Into the Abyss", 317, forRegion(12107));
		add(95, "Intrepid", 64, forRegion(9369));
		add(306, "Island Life", 242, forRegion(10794));
		add(172, "Jungle Island", 66, forRegion(11313), forRegion(11309));
		add(479, "Jungle Troubles", 343, forRegion(11568));
		add(114, "Jungly1", 67, forRegion(11054), forRegion(11154));
		add(115, "Jungly2", 68, forRegion(10802));
		add(117, "Jungly3", 69, forRegion(11055));
		add(9, "Kingdom", 190, forRegion(11319));
		add(191, "Knightly", 70, forRegion(10291));
		add(134, "La Mort", 192, forRegion(8779));
		add(287, "Lair", 229, forRegion(13975));
		add(542, "Lament", 381, forRegion(12433));
		add(164, "Landlubber", 169, forRegion(10801));
		add(60, "Lasting", 71, forRegion(10549));
		add(293, "Legend", 235, forRegion(10808));
		add(66, "Legion", 72, forRegion(12089), forRegion(10039));
		add(320, "Lighthouse", 251, forRegion(10040));
		add(113, "Lightness", 73, forRegion(12343));
		add(74, "Lightwalk", 74, forRegion(11061));
		add(168, "Lonesome", 149, forRegion(13203));
		add(161, "Long Ago", 75, forRegion(10544));
		add(12, "Long Way Home", 76, forRegion(11826));
		add(253, "Lost Soul", 204, forRegion(9008));
		add(20, "Lullaby", 77, forRegion(13365), forRegion(10551));
		add(13, "Mage Arena", 78, forRegion(12349), forRegion(10057));
		add(185, "Magic Dance", 79, forRegion(10288));
		add(184, "Magical Journey", 80, forRegion(10805));
		add(544, "Making Waves", 378, forRegion(9273), forRegion(9272));
		add(328, "March", 81, forRegion(10036));
		add(304, "Marooned", 241, forRegion(11562), forRegion(12117));
		add(261, "Marzipan", 211, forRegion(11166), forRegion(11421));
		add(340, "Masquerade", 268, forRegion(10908));
		add(156, "Mausoleum", 184, forRegion(13722));
		add(157, "Medieval", 82, forRegion(13109));
		add(193, "Mellow", 83, forRegion(10293));
		add(317, "Melodrama", 248, forRegion(9776));
		add(254, "Meridian", 205, forRegion(8497));
		add(107, "Miles Away", 84, forRegion(11571), forRegion(10569));
		add(65, "Miracle Dance", 85, forRegion(11083));
		add(388, "Mirage", 303, forRegion(13199));
		add(284, "Miscellania", 226, forRegion(10044));
		add(21, "Monarch Waltz", 86, forRegion(10807));
		add(303, "Monkey Madness", 239, forRegion(11051));
		add(343, "Monster Melee", 272, forRegion(12694));
		add(10, "Moody", 87, forRegion(12600), forRegion(9523));
		add(48, "Morytania", 180, forRegion(13622));
		add(515, "Mudskipper Melody", 361, forRegion(11824));
		add(348, "Narnode's Theme", 513, forRegion(9882));
		add(245, "Natural", 197, forRegion(13620), forRegion(9038));
		add(155, "Neverland", 88, forRegion(9780));
		add(127, "Nightfall", 90, forRegion(12861), forRegion(11827));
		add(594, "No Way Out", 403, forRegion(13209), forRegion(12369), forRegion(12113));
		add(58, "Nomad", 171, forRegion(11056));
		add(587, "Null and Void", 400, forRegion(10537));
		add(103, "Oriental", 91, forRegion(11666));
		add(322, "Out of the Deep", 253, forRegion(10140));
		add(447, "Over To Nardah", 328, forRegion(13613));
		add(256, "Overpass", 207, forRegion(9267));
		add(7, "Overture", 92, forRegion(10806));
		add(93, "Parade", 93, forRegion(13110));
		add(393, "Path of Peril", 307, forRegion(10575));
		add(588, "Pest Control", 401, forRegion(10536));
		add(354, "Phasmatys", 277, forRegion(14746));
		add(419, "Pheasant Peasant", 321, forRegion(10314));
		add(149, "Principality", 188, forRegion(11575));
		add(158, "Quest", 94, forRegion(10315));
		add(482, "Rat a Tat Tat", 345, forRegion(11599));
		add(491, "Rat Hunt", 349, forRegion(11343));
		add(318, "Ready for Battle", 249, forRegion(9620));
		add(329, "Regal", 95, forRegion(13117));
		add(78, "Reggae", 96, forRegion(11565));
		add(89, "Reggae2", 97, forRegion(11567));
		add(289, "Rellekka", 231, forRegion(10297));
		add(262, "Righteousness", 223, forRegion(9803));
		add(91, "Riverside", 98, forRegion(10803), forRegion(8496));
		add(335, "Romancing the Crone", 264, forRegion(11068));
		add(390, "Romper Chomper", 312, forRegion(9263));
		add(53, "Royale", 99, forRegion(11671));
		add(57, "Rune Essence", 100, forRegion(11595));
		add(5, "Sad Meadow", 101, forRegion(10035), forRegion(11081));
		add(290, "Saga", 232, forRegion(10296));
		add(359, "Sarcophagus", 283, forRegion(12945));
		add(490, "Sarim's Vermin", 348, forRegion(11926));
		add(144, "Scape Cave", 102, forRegion(12698), forRegion(12437));
		add(331, "Scape Sad", 104, forRegion(13116));
		add(54, "Scape Soft", 159, forRegion(11829));
		add(332, "Scape Wild", 105, forRegion(12857), forRegion(12604));
		add(352, "Scarab", 282, forRegion(12589));
		add(92, "Sea Shanty", 106, forRegion(11569));
		add(35, "Sea Shanty2", 107, forRegion(12082));
		add(110, "Serenade", 108, forRegion(9521));
		add(52, "Serene", 109, forRegion(11837), forRegion(11936), forRegion(11339));
		add(356, "Settlement", 279, forRegion(11065));
		add(286, "Shadowland", 228, forRegion(13618), forRegion(13875), forRegion(8526));
		add(120, "Shining", 160, forRegion(12858));
		add(353, "Shipwrecked", 276, forRegion(14391));
		add(311, "Showdown", 245, forRegion(10895));
		add(257, "Sojourn", 209, forRegion(11321));
		add(80, "Soundscape", 111, forRegion(9774));
		add(387, "Sphinx", 302, forRegion(13100));
		add(175, "Spirit", 112, forRegion(12597));
		add(77, "Splendour", 113, forRegion(11574));
		add(129, "Spooky Jungle", 115, forRegion(11053), forRegion(11668));
		add(333, "Spooky", 114, forRegion(12340));
		add(11, "Spooky2", 218, forRegion(13718));
		add(241, "Stagnant", 193, forRegion(13876), forRegion(8782));
		add(108, "Starlight", 116, forRegion(11925), forRegion(12949));
		add(151, "Start", 117, forRegion(12339));
		add(111, "Still Night", 118, forRegion(13108));
		add(319, "Stillness", 250, forRegion(13977));
		add(292, "Stranded", 234, forRegion(11322), forRegion(11578));
		add(243, "Stratosphere", 195, forRegion(8523));
		add(267, "Sunburn", 215, forRegion(12846), forRegion(13357));
		add(265, "Superstition", 261, forRegion(11153));
		add(397, "Tears of Guthix", 311, forRegion(12948));
		add(296, "Technology", 238, forRegion(10310));
		add(376, "Temple of Light", 294, forRegion(7496));
		add(307, "Temple", 243, forRegion(11151));
		add(109, "Theme", 123, forRegion(10294), forRegion(10138));
		add(242, "Time Out", 196, forRegion(11591));
		add(369, "Time to Mine", 289, forRegion(11422));
		add(338, "Tiptoe", 266, forRegion(12440));
		add(51, "Trawler Minor", 125, forRegion(7755));
		add(38, "Trawler", 124, forRegion(7499));
		add(130, "Tree Spirits", 126, forRegion(9268));
		add(187, "Tremble", 189, forRegion(11320));
		add(162, "Tribal Background", 127, forRegion(11312), forRegion(11412));
		add(165, "Tribal", 128, forRegion(11311));
		add(94, "Tribal2", 129, forRegion(11566));
		add(192, "Trinity", 130, forRegion(10804), forRegion(10904));
		add(183, "Troubled", 131, forRegion(11833));
		add(88, "Twilight", 179, forRegion(10906));
		add(473, "TzHaar!", 339, forRegion(9551));
		add(176, "Undercurrent", 170, forRegion(12345));
		add(323, "Underground Pass", 134, forRegion(9621));
		add(179, "Underground", 132, forRegion(13368), forRegion(11416));
		add(131, "Understanding", 187, forRegion(9547));
		add(3, "Unknown Land", 133, forRegion(12338));
		add(70, "Upcoming", 135, forRegion(10546));
		add(75, "Venture", 136, forRegion(13364));
		add(45, "Venture2", 168, forRegion(13464));
		add(528, "Victory is Mine", 368, forRegion(12696));
		add(61, "Village", 182, forRegion(13878));
		add(85, "Vision", 137, forRegion(12337), forRegion(12436));
		add(30, "Voodoo Cult", 138, forRegion(9545), forRegion(11665));
		add(32, "Voyage", 139, forRegion(10038));
		add(49, "Wander", 140, forRegion(12083));
		add(295, "Warrior", 237, forRegion(10653));
		add(82, "Waterfall", 141, forRegion(10037), forRegion(10137));
		add(244, "Waterlogged", 199, forRegion(13877), forRegion(8014));
		add(394, "Wayward", 308, forRegion(9875));
		add(271, "Well of Voyage", 221, forRegion(9366));
		add(475, "Wild Side", 340, forRegion(12092));
		add(435, "Wilderness", 142, forRegion(11832), forRegion(12346));
		add(42, "Wilderness2", 143, forRegion(12091));
		add(43, "Wilderness3", 144, forRegion(11834));
		add(8, "Wildwood", 256, forRegion(12344));
		add(14, "Witching", 145, forRegion(13113));
		add(529, "Woe of the Wyvern", 369, forRegion(12181));
		add(34, "Wonder", 146, forRegion(11831));
		add(81, "Wonderous", 147, forRegion(10548));
		add(255, "Woodland", 206, forRegion(8498));
		add(15, "Workshop", 148, forRegion(12084));
		add(524, "Xenophobe", 366, forRegion(7492), forRegion(11589));
		add(145, "Yesteryear", 161, forRegion(12849));
		add(146, "Zealot", 172, forRegion(10827));
		add(428, "Altar Ego", 501, forRegion(13131));
		// Bounty hunter craters
		add(444, "Bounty Hunter Level 1", 517, new ZoneBorders(2688, 5632, 2879, 5823));
		add(443, "Bounty Hunter Level 2", 518, new ZoneBorders(2944, 5632, 3135, 5823));
		add(445, "Bounty Hunter Level 3", 519, new ZoneBorders(3200, 5632, 3391, 5823));
		// Al kharid/desert
		add(50, "Al Kharid", 1, forRegion(13105), forRegion(13361));
		add(123, "Arabian2", 5, forRegion(13107));
		add(124, "Arabian3", 6, forRegion(12848));
		add(36, "Arabian", 4, forRegion(13106), forRegion(13617));
		add(47, "Duel Arena", 164, forRegion(13362));
		add(122, "Shine", 110, forRegion(13363));
		add(69, "Egypt", 37, forRegion(13104));
		// Brimhaven
		add(363, "7th Realm", 285, forRegion(10645), forRegion(10644));
		add(362, "Karamja Jam", 286, forRegion(10900), forRegion(10899));
		add(364, "Pathways", 287, forRegion(10901));
		// Tutorial island
		add(62, "Newbie Melody", 89, new ZoneBorders(3052, 3055, 3155, 3135));
		// Lumbridge
		add(76, "Harmony", 58, forRegion(12850));
		// Clan wars
		add(442, "Clan Wars", 515);
		add(634, "Warriors' Guild", 423);

		// God wars dungeon
		ZoneBorders godwars = new ZoneBorders(2816, 5248, 2943, 5375);
		ZoneBorders borders = new ZoneBorders(2823, 5250, 2844, 5310);
		ZoneBorders borders1 = new ZoneBorders(2844, 5250, 2878, 5280);
		godwars.addException(borders);
		godwars.addException(borders1);
		add(404, "Armadyl Alliance", 493, borders, borders1);

		borders = new ZoneBorders(2879, 5340, 2944, 5366);
		borders1 = new ZoneBorders(2909, 5316, 2944, 5340);
		godwars.addException(borders);
		godwars.addException(borders1);
		add(391, "Zamorak Zoo", 490, borders, borders1);

		borders = new ZoneBorders(2820, 5312, 2849, 5369);
		borders1 = new ZoneBorders(2849, 5349, 2880, 5374);
		godwars.addException(borders);
		godwars.addException(borders1);
		add(386, "Bandos Battalion", 492, borders, borders1);

		borders = new ZoneBorders(2885, 5253, 2934, 5278);
		borders1 = new ZoneBorders(2913, 5278, 2937, 5306);
		godwars.addException(borders);
		godwars.addException(borders1);
		add(408, "Strength of Saradomin", 491, borders, borders1);
		add(399, "Armageddon", 494, godwars);
		add(432, "Grimly Fiendish", 509, forRegion(6475), forRegion(6731));
	}

	/**
	 * Adds a new music entry.
	 * @param musicId The music id.
	 * @param name The song name.
	 * @param index The list index.
	 * @param borders The zone borders.
	 */
	private static void add(int musicId, String name, int index, ZoneBorders... borders) {
		for (ZoneBorders border : borders) {
			for (MusicEntry entry : zones.keySet()) {
				ZoneBorders[] b = zones.get(entry);
				for (ZoneBorders bor : b) {
					if (border.insideBorder(bor.getSouthWestX(), bor.getSouthWestY()) || border.insideBorder(bor.getNorthEastX(), bor.getNorthEastY()) || bor.insideBorder(border.getSouthWestX(), border.getSouthWestY()) || bor.insideBorder(border.getNorthEastX(), border.getNorthEastY())) {
						throw new IllegalStateException("Zone for music " + name + " is overlapping music zone " + entry.getName() + "!");
					}
				}
				if (entry.getIndex() == index || entry.getId() == musicId) {
					throw new IllegalStateException("Already contained music " + name + " [id=" + musicId + ", index=" + index + "]!");
				}
			}
		}
		zones.put(new MusicEntry(musicId, name, index), borders);
	}

	/**
	 * Gets the zone borders for a region.
	 * @param regionId The region id.
	 * @return The zone borders.
	 */
	private static ZoneBorders forRegion(int regionId) {
		int baseX = ((regionId >> 8) & 0xFF) << 6;
		int baseY = (regionId & 0xFF) << 6;
		return new ZoneBorders(baseX, baseY, baseX + 63, baseY + 63);
	}

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String[] args) throws Throwable {
		ServerStore.init(ServerConstants.STORE_PATH);
		configureMusic();
		ByteBuffer buffer = ByteBuffer.allocate(1 << 20);
		int count = 0;
		for (MusicEntry entry : zones.keySet()) {
			buffer.putShort((short) entry.getId());
			ByteBufferUtils.putString(entry.getName(), buffer);
			buffer.putShort((short) entry.getIndex());
			ZoneBorders[] borders = zones.get(entry);
			buffer.put((byte) borders.length);
			for (ZoneBorders zone : borders) {
				buffer.put((byte) (zone.getExceptions() != null ? zone.getExceptions().size() : 0));
				buffer.putShort((short) zone.getSouthWestX());
				buffer.putShort((short) zone.getSouthWestY());
				buffer.putShort((short) zone.getNorthEastX());
				buffer.putShort((short) zone.getNorthEastY());
				if (zone.getExceptions() != null) {
					for (ZoneBorders z : zone.getExceptions()) {
						buffer.putShort((short) z.getSouthWestX());
						buffer.putShort((short) z.getSouthWestY());
						buffer.putShort((short) z.getNorthEastX());
						buffer.putShort((short) z.getNorthEastY());
					}
				}
			}
			count++;
		}
		buffer.putShort((short) -1);
		buffer.flip();
		ServerStore.setArchive("music", buffer, false);
		ServerStore.createStaticStore(ServerConstants.STORE_PATH);
		System.out.println("Packed " + count + " music entries!");
		System.exit(0);
	}
}