package org.arios.tools.npc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.node.item.ChanceItem;
import org.arios.game.world.GameWorld;
import org.arios.tools.impl.URLParser;

/**
 * Dumps the NPC drops data from www.runescape.wikia/com.
 * @author Emperor
 */
public final class NPCDropsDumper {

	/**
	 * The amount of npcs to dump per interval.
	 */
	private static final int INTERVAL = 10;

	/**
	 * The website to dump from.
	 */
	private static final String WEBSITE = "http://www.runescape.wikia.com/wiki/";

	/**
	 * The npc name data (to evade non-existing webpages due to wrong name).
	 */
	private static final Object[][] NAME_DATA = {}; // [npc id, name to dump

	// as];

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		int start = 120;
		int end = INTERVAL;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("./data/tool/failed_npcs_dropdump.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		for (;;) {
			System.out.println("Dumping NPCs " + start + " to " + end + "...");
			List<String> urls = new ArrayList<String>();
			for (int i = start; i < end; i++) {
				if (new File("./data/essentials/tool/npc_drops/" + i + ".txt").exists()) {
					continue;
				}
				NPCDefinition def = NPCDefinition.forId(i);
				if (def != null) {
					String name = def.getName();
					for (Object[] o : NAME_DATA) {
						if (((Integer) o[0]) == i) {
							name = (String) o[1];
							System.out.println("Replaced " + def.getName() + " with " + name + " for id " + i + ".");
						}
					}
					String url = new StringBuilder(WEBSITE).append(name.replaceAll(" ", "_")).toString();
					if (!urls.contains(url)) {
						urls.add(url);
					}
				}
			}
			URLParser parser = new URLParser(urls, 600, 4);
			parser.parse();
			try {
				handleWebpage(start, end, parser, writer);
			} catch (Throwable t) {
				t.printStackTrace();
				writer.append("Exception caught while parsing " + start + " to " + end + ": " + t);
				writer.newLine();
				writer.flush();
			}
			parser.terminate();
			System.gc();
			start = end;
			end += INTERVAL;
			if (end > NPCDefinition.getDefinitions().size()) {
				end = NPCDefinition.getDefinitions().size();
			}
			if (start == NPCDefinition.getDefinitions().size()) {
				break;
			}
		}
		writer.close();
		System.out.println("Done!");
		System.exit(0);
	}

	private static void handleWebpage(int start, int end, URLParser parser, BufferedWriter writer) throws Throwable {
		for (int i = start; i < end; i++) {
			NPCDefinition def = NPCDefinition.forId(i);
			if (def != null) {
				String name = def.getName();
				for (Object[] o : NAME_DATA) {
					if (((Integer) o[0]) == i) {
						name = (String) o[1];
						System.out.println("Replaced " + def.getName() + " with " + name + " for id " + i + ".");
					}
				}
				String url = new StringBuilder(WEBSITE).append(name.replaceAll(" ", "_")).toString();
				List<String> data = parser.getData().get(url);
				if (data == null) {
					writer.append("Could not locate webpage for URL " + url + ".");
					writer.newLine();
					writer.flush();
					continue;
				}
				BufferedWriter w = new BufferedWriter(new FileWriter("./data/essentials/tool/npc_drops/" + i + ".txt"));
				List<ChanceItem> defaultDropTable = new ArrayList<>();
				List<ChanceItem> charmDropTable = new ArrayList<>();
				List<ChanceItem> mainDropTable = new ArrayList<>();
				List<ChanceItem> table = defaultDropTable;
				for (int j = 0; j < data.size(); j++) {
					String str = data.get(j);
					if (str.contains("<h3><span class=\"mw-headline\" id=\"100.25")) { // id=\"100.25_drop
						table = defaultDropTable;
						w.append("table=default");
						w.newLine();
						System.out.println("Dumping default drops for NPC [" + def.getId() + ", " + def.getName() + "]...");
					} else if (str.contains("<h3><span class=\"mw-headline\" id=\"Charms\">Charms")) {
						table = charmDropTable;
						w.append("table=charm");
						w.newLine();
						System.out.println("Dumping charm drops for NPC [" + def.getId() + ", " + def.getName() + "]...");
					} else if (str.contains("<h3><span class=\"mw-headline\" id=\"Weapon") || str.contains("<h3><span class=\"mw-headline\" id=\"Ammo") || str.contains("<h3><span class=\"mw-headline\" id=\"Rune") || str.contains("<h3><span class=\"mw-headline\" id=\"Other") || str.contains("<h3><span class=\"mw-headline\" id=\"Herb") || str.contains("<h3><span class=\"mw-headline\" id=\"Armour") || str.contains("<h3><span class=\"mw-headline\" id=\"Consumables") || str.contains("<h3><span class=\"mw-headline\" id=\"Seed")) {
						if (table != mainDropTable) {
							table = mainDropTable;
							w.append("table=main");
							w.newLine();
						}
					} else if (str.contains("data-image-key=\"Blue_charm.png")) {
						if (table == charmDropTable) {
							j -= 3;
							w.append(parseCharm("none", data.get(j + 7)));
							w.newLine();
							w.append(parseCharm("gold", data.get(j + 10)));
							w.newLine();
							w.append(parseCharm("green", data.get(j + 13)));
							w.newLine();
							w.append(parseCharm("crimson", data.get(j + 16)));
							w.newLine();
							try {
								w.append(parseCharm("blue", data.get(j + 19)));
								w.newLine();
							} catch (Throwable t) {
								System.out.println("Exception caught while parsing " + i + "  " + def.getName() + ": " + t);
							}
							j += 3;
							j += 19;
							table = mainDropTable;
							w.append("table=main");
							w.newLine();
						}
					} else if (str.contains("</td><td style=\"text-align: left;\"><a href=")) {
						str = str.replace("</td><td style=\"text-align: left;\"><a href=", "");
						String n = str.substring(str.indexOf('>') + 1, str.indexOf('<'));
						str = data.get(++j).replace("</td><td>", "");
						boolean noted = str.contains("noted");
						if (noted) {
							str = str.replace(" noted", "").replace(" (noted)", "");
						}
						str = str.replace("<sub></sub>", "");
						String amount = (str = str.replaceAll("&#8211;", "-"));
						String amount2 = null;
						if (str.contains(", ")) {
							String[] arg = (str = str.replace(", ", ";")).split(";");
							amount = arg[0];
							amount2 = arg[1];
							amount2 = amount2.replace(",", "");
						}
						amount = amount.replace(",", "");
						String rarity = data.get(++j);
						if (rarity.contains("<sup id=\"cite_")) {
							rarity = rarity.substring(0, rarity.indexOf("<sup id=\"cite") - 1);
						}
						rarity = rarity.substring(rarity.lastIndexOf(" ") + 1);
						if (table == defaultDropTable) {
							w.append(n + " - " + amount + (noted ? " - noted" : ""));
							w.newLine();
							if (amount2 != null) {
								w.append(n + " - " + amount2 + (noted ? " - noted" : ""));
								w.newLine();
							}
						} else {
							w.append(n + " - " + amount + " - " + rarity + (noted ? " - noted" : ""));
							w.newLine();
							if (amount2 != null) {
								w.append(n + " - " + amount2 + " - " + rarity + (noted ? " - noted" : ""));
								w.newLine();
							}
						}
					}// <h3><span class="mw-headline" id="Rare_drop_table_drops
					else if (str.contains("><span class=\"mw-headline\" id=\"Rare_drop")) {
						w.append("rare_dt_slot - 1 - Rare");
						w.newLine();
						break;
					} else if (str.contains("WikiaArticleCategories") || str.contains("<span class=\"mw-headline\" id=\"Pickpocketing_Loot")) {
						break;
					}
				}
				w.flush();
				w.close();
			}
		}
	}

	private static String parseCharm(String charmName, String str) {
		str = str.replace("<p>", "").replace("%", "");
		String[] arg = str.split("&#8211;");
		double ratio = Double.parseDouble(arg[0]);
		if (arg.length > 1) {
			double maxRatio = Double.parseDouble(arg[1]);
			if (ratio == 0) {
				ratio = maxRatio;
			} else {
				ratio = (ratio + maxRatio) / 2;
			}
		}
		return charmName + " - " + (ratio) + "%";
	}
}