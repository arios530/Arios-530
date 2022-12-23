package org.arios.tools.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.ByteBuffer;

import org.arios.ServerConstants;
import org.arios.cache.ServerStore;
import org.arios.game.world.GameWorld;
import org.arios.parser.misc.XTEAKeyParser;

/**
 * Packs XTEA keys into the static store.
 * @author Emperor
 *
 */
public final class XTEAKeyPacker {

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String... args) throws Throwable {
		GameWorld.prompt(false);
		// load xteas
		roar: {
			for (File f : new File("./data/region_xtea/").listFiles()) {
				String name = f.getName();
				if (name.endsWith(".txt")) {
					name = name.replaceAll(".txt", "");
					for (char c : name.toCharArray()) {
						if (!Character.isDigit(c)) {
							System.err.println("Invalid region XTEA file found - name: " + name + "!");
							break roar;
						}
					}
					int regionId = Integer.parseInt(name);
					BufferedReader in = new BufferedReader(new FileReader(f));
					int[] keys = new int[4];
					for (int i = 0; i < 4; i++) {
						keys[i] = Integer.parseInt(in.readLine());
					}
					in.close();
					XTEAKeyParser.getRegionXTEA().put(regionId, keys);
				}
			}
		}
		packXTEA();
		System.out.println("Packed " + XTEAKeyParser.getRegionXTEA().size() + " region XTEA keys!");
	}

	/**
	 * Packs the XTEA keys on the static store.
	 */
	public static void packXTEA() {
		ByteBuffer buffer = ByteBuffer.allocate(1024 << 6);

		/* pack region XTEA keys */
		buffer.put((byte) 1);
		for (int regionId : XTEAKeyParser.getRegionXTEA().keySet()) {
			buffer.putInt(regionId);
			for (int key : XTEAKeyParser.getRegionXTEA().get(regionId)) {
				buffer.putInt(key);
			}
		}
		buffer.putInt(-1);

		/* End of file indicating opcode */
		buffer.put((byte) 0);

		buffer.flip();
		ServerStore.setArchive("xtea_keys", buffer, false);
		ServerStore.createStaticStore(ServerConstants.STORE_PATH);
	}
}