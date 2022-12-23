package org.arios.parser.object;

import java.nio.ByteBuffer;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.parser.Parser;

/**
 * The object definitions parser.
 * @author Emperor
 */
public final class ObjectDefinitionsParser implements Parser {

	@Override
	public boolean parse() throws Throwable {
		for (int objectId = 0; objectId < Cache.getObjectDefinitionsSize(); objectId++) {
			byte[] data = Cache.getIndexes()[16].getFileData(getContainerId(objectId), objectId & 0xff);
			if (data == null) {
				ObjectDefinition.getDefinitions().put(objectId, new ObjectDefinition());
				System.err.println("Could not load object definitions for id " + objectId + " - no data!");
				continue;
			}
			ObjectDefinition def = ObjectDefinition.parseDefinition(objectId, ByteBuffer.wrap(data));
			if (def == null) {
				System.err.println("Could not load object definitions for id " + objectId + " - no definitions found!");
				return false;
			}
			ObjectDefinition.getDefinitions().put(objectId, def);
			data = null;
		}
		return true;
	}

	/**
	 * Get the container id.
	 * @param id The object id.
	 * @return The container id.
	 */
	public static int getContainerId(int id) {
		return id >>> 1998118472;
	}
}