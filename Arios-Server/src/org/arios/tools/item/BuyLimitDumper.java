package org.arios.tools.item;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.eco.ge.GrandExchangeDatabase;
import org.arios.game.world.GameWorld;
import org.arios.tools.impl.URLParser;

public final class BuyLimitDumper {

	private static final String URL = "http://runescape.wikia.com/wiki/Grand_Exchange/Buying_limits";

	public static void main(String[] args) throws Throwable {
		GameWorld.prompt(false);
		List<String> list = new ArrayList<>();
		list.add(URL);
		URLParser parser = new URLParser(list, 1070);
		parser.parse();
		list = parser.getData().get(URL);
		Map<String, Integer> limits = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (s.startsWith("<td><a href=")) {
				s = s.substring(0, s.lastIndexOf("<"));
				String name = s.substring(s.lastIndexOf(">") + 1).toLowerCase();
				s = list.get(i + 1);
				s = s.replace("</td><td>", "").replace(",", "").replaceAll(" ", "");
				try {
					name = name.replace("shieldbow", "longbow");
					limits.put(name, Integer.parseInt(s));
					System.out.println("Dumped limit " + name + " - " + s);
				} catch (Throwable t) {
					System.out.println(name + " had exception when parsing " + s + ": " + t);
				}
			}
		}
		int count = 0;
		int total = 0;
		BufferedWriter bw = new BufferedWriter(new FileWriter("./data/list/ge_buy_limits.txt"));
		for (int id : GrandExchangeDatabase.getDatabase().keySet()) {
			ItemDefinition def = ItemDefinition.forId(id);
			if (def == null) {
				System.out.println("Missing definition for item " + id);
				continue;
			}
			total++;
			String key = null;
			for (int i = 0; i < 6; i++) {
				String name = def.getName().toLowerCase();
				if (i > 1) {
					if (name.contains("(") && name.charAt(name.indexOf("(") - 1) != ' ') {
						if (i < 4) {
							name = name.substring(0, name.indexOf("(")) + " " + name.substring(name.indexOf("("));
						} else {
							name = name.substring(0, name.indexOf("("));
						}
						// System.out.println("name: " + name);
					}
				}
				List<String> keys = new ArrayList<>();
				for (String n : limits.keySet()) {
					if (n.equals(name)) {
						key = n;
						keys.clear();
						break;
					}
					if ((i % 2) == 0 ? n.contains(name) : name.contains(n)) {
						keys.add(n);
					}
				}
				if (key == null && !keys.isEmpty()) {
					key = keys.get(0);
				}
				if (key != null) {
					count++;
					break;
				}
			}
			String name = def.getName().toLowerCase();
			int value = -1;
			if (key == null) {
				if (name.contains("(t)") || name.contains("(g)") || name.contains("(h")) {
					value = 2;
				} else {
					System.out.println("Missing data for " + name);
				}
			} else {
				value = limits.get(key);
			}
			bw.append(id + ": " + (value == -1 ? "N/A" : value) + " //" + name);
			bw.newLine();
		}
		bw.flush();
		bw.close();
		System.out.println("Roar " + count + "/" + total + " - " + (total - count));
		System.exit(0);
	}

}