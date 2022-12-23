package org.arios.tools.object;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.world.GameWorld;

public class ObjectDump {

	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		for (int i = 0; i < Cache.getObjectDefinitionsSize(); i++) {
			ObjectDefinition def = ObjectDefinition.forId(i);
			if (def.hasAction("bank") || def.hasAction("use-quickly")) {
				System.out.println(def.getId() + ",");
			}
		}
	}
}
