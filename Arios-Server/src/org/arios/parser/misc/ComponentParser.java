package org.arios.parser.misc;

import java.nio.ByteBuffer;

import org.arios.cache.ServerStore;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.system.SystemLogger;
import org.arios.parser.Parser;

/**
 * The component definitions parser.
 * @author Emperor
 */
public final class ComponentParser implements Parser {

	@Override
	public boolean parse() throws Throwable {
		ByteBuffer buf = ServerStore.getArchive("component_config");
		int componentId = -1;
		int count = 0;
		while ((componentId = buf.getShort()) != -1) {
			ComponentDefinition.getDefinitions().put(componentId, ComponentDefinition.parse(buf));
			count++;
		}
		SystemLogger.log("Loaded " + count + " component definitions!");
		return true;
	}

}