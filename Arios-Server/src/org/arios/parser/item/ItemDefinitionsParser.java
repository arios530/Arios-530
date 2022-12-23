package org.arios.parser.item;

import java.nio.ByteBuffer;

import org.arios.cache.Cache;
import org.arios.cache.def.impl.ItemDefinition;
import org.arios.parser.Parser;

/**
 * The item definitions parser.
 * @author Emperor
 */
public final class ItemDefinitionsParser implements Parser {

	@Override
	public boolean parse() throws Throwable {
		for (int itemId = 0; itemId < Cache.getItemDefinitionsSize(); itemId++) {
			byte[] data = Cache.getIndexes()[19].getFileData(itemId >>> 8, itemId & 0xFF);
			if (data == null) {
				ItemDefinition.getDefinitions().put(itemId, new ItemDefinition());
				continue;
			}
			ItemDefinition def = ItemDefinition.parseDefinition(itemId, ByteBuffer.wrap(data));
			if (def == null) {
				System.err.println("Could not load item definitions for id " + itemId + " - no definitions found!");
				return false;
			}
			ItemDefinition.getDefinitions().put(itemId, def);
		}
		ItemDefinition.defineTemplates();
		return true;
	}

}