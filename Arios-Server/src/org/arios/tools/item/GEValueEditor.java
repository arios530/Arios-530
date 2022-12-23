package org.arios.tools.item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.eco.ge.GrandExchangeDatabase;
import org.arios.game.content.eco.ge.GrandExchangeEntry;
import org.arios.game.world.GameWorld;
import org.arios.parser.item.ItemConfiguration;

/**
 * Handles changing the G.E value.
 * @author Emperor
 */
public final class GEValueEditor {

	/**
	 * The main method.
	 * @param args The arguments.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		File file = new File("./data/list/ge_dump_result.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s;
		while ((s = br.readLine()) != null) {
			// Item price [id=50, value=5].
			if (s.startsWith("Item price [")) {
				s = s.replace("Item price [id=", "").replace(" value=", "").replace("].", "");
				String[] arg = s.split(",");
				int itemId = Integer.parseInt(arg[0]);
				int value = Integer.parseInt(arg[1]);
				GrandExchangeEntry entry = GrandExchangeDatabase.getDatabase().get(itemId);
				if (entry == null) {
					System.err.println("Entry for item " + itemId + ":" + ItemDefinition.forId(itemId).getName() + " does not exist!");
					continue;
				}
				ItemDefinition.forId(itemId).getConfigurations().put(ItemConfiguration.GE_PRICE, value);
			}
		}
		ItemConfigurationEditor.dump();
		br.close();
		System.out.println("Set G.E prices!");
	}
}