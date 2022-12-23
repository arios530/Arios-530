package org.arios.parser.misc;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.arios.cache.ServerStore;
import org.arios.cache.StoreFile;
import org.arios.parser.Parser;

/**
 * Handles XTEA key parsing.
 * @author Emperor
 *
 */
public final class XTEAKeyParser implements Parser {

	/**
	 * The XTEA keys.
	 */
	public static final int[] DEFAULT_REGION_KEYS = new int[] { 0, 0, 0, 0 };// 14881828,
																				// -6662814,
																				// 58238456,
																				// 146761213
																				// };

	/**
	 * Region XTEA-keys.
	 */
	private static final Map<Integer, int[]> REGION_XTEA = new HashMap<>();

	@Override
	public boolean parse() throws Throwable {
		StoreFile file = ServerStore.get("xtea_keys");
		if (file == null) {
			System.err.println("No XTEA key storage found!");
			return true;
		}
		ByteBuffer buf = file.data();
		int opcode;
		while ((opcode = buf.get() & 0xFF) != 0) {
			switch (opcode) {
			case 1:
				int regionId = -1;
				while ((regionId = buf.getInt()) != -1) {
					REGION_XTEA.put(regionId, new int[] { buf.getInt(), buf.getInt(), buf.getInt(), buf.getInt() });
				}
				break;
			}
		}
		return true;
	}

	/**
	 * Gets the XTEA keys to decrypt the region's object mapping.
	 * @param regionId The region's id.
	 * @return The region XTEA keys.
	 */
	public static int[] getRegionXTEA(int regionId) {
		int[] keys = REGION_XTEA.get(regionId);
		if (keys == null) {
			return DEFAULT_REGION_KEYS;
		}
		return keys;
	}

	/**
	 * Gets the region XTEA key mapping.
	 * @return The mapping.
	 */
	public static Map<Integer, int[]> getRegionXTEA() {
		return REGION_XTEA;
	}
}