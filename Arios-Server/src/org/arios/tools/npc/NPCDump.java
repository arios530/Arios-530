package org.arios.tools.npc;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.world.GameWorld;

public class NPCDump {

	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		for (int i = 0; i < Cache.getNPCDefinitionsSize(); i++) {
			NPCDefinition def = NPCDefinition.forId(i);
			if (def == null) {
				continue;
			}
			if (def.getName().toLowerCase().contains("corp")) {
				System.out.println(def.getId() + "," + def.getName());
			}
		}
	}
}
