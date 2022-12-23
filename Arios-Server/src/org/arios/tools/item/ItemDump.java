package org.arios.tools.item;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.world.GameWorld;

/**
 * This class is used to dump the items and there information.
 * @author 'Vexia
 */
public final class ItemDump {
	/**
	 * The main method.
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		try {
			System.out.println(Integer.MAX_VALUE);
			GameWorld.prompt(false);
			ItemDefinition def = null;
			System.out.println(Cache.getItemDefinitionsSize());
			for (int i = 0; i < Cache.getItemDefinitionsSize(); i++) {
				def = ItemDefinition.forId(i);
				if (def.getName().toLowerCase().contains("bones") && def.hasAction("bury")) {
					System.out.println(def.getId() + ",");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void createArray(ItemDefinition def) {
		System.out.println("{");
		System.out.println("}");
	}
}
