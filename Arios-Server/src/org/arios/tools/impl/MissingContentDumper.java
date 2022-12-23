package org.arios.tools.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Region;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.map.RegionPlane;
import org.arios.game.world.map.zone.ZoneBorders;

/**
 * Useful tool which dumps out all unhandled options in a certain region.
 * @author Emperor
 */
public final class MissingContentDumper {

	/**
	 * The x-coordinate of the south west corner of the region we're checking.
	 */
	private static final ZoneBorders SEARCHING_AREA = new ZoneBorders(3200, 3200, 3264, 3264);

	/**
	 * The NPCs count.
	 */
	private static int npcCount;

	/**
	 * The objects count.
	 */
	private static int objectCount;

	/**
	 * The unhandled NPCs count.
	 */
	private static int unhandledNPCs;

	/**
	 * The unhandled objects count.
	 */
	private static int unhandledObjects;

	/**
	 * The ids of the nodes with unhandled options.
	 */
	static Map<Integer, Boolean> unhandled = new HashMap<>();

	/**
	 * The main method.
	 * @param args The arguments cast on runtime.
	 * @throws Throwable When an exception occurs.
	 */
	public static void main(String[] args) throws Throwable {
		GameWorld.prompt(false);
		BufferedWriter bw = new BufferedWriter(new FileWriter("./missing-content.txt", true));
		for (NPCDefinition d : NPCDefinition.getDefinitions().values()) {
			if (d != null) {
				for (String s : d.getOptions()) {
					if (s != null && NPCDefinition.getOptionHandler(d.getId(), s.toLowerCase()) == null) {
						// bw.append("Unhandled NPC option - " + d.getId() +
						// ", " + d.getName() + ", " + s);
						// bw.newLine();
						unhandled.put(d.getId(), true);
					}
				}
			}
		}
		for (ObjectDefinition d : ObjectDefinition.getDefinitions().values()) {
			if (d != null) {
				for (String s : d.getOptions()) {
					if (s != null && !s.equalsIgnoreCase("hidden") && ObjectDefinition.getOptionHandler(d.getId(), s.toLowerCase()) == null) {
						// bw.append("Unhandled object option - " + d.getId() +
						// ", " + d.getName() + ", " + s);
						// bw.newLine();
						unhandled.put(1 << 16 | d.getId(), true);
					}
				}
			}
		}
		for (int x = SEARCHING_AREA.getSouthWestX() >> 6; x < (SEARCHING_AREA.getNorthEastX() >> 6) + 1; x++) {
			for (int y = SEARCHING_AREA.getSouthWestY() >> 6; y < (SEARCHING_AREA.getNorthEastY() >> 6) + 1; y++) {
				int regionId = x << 8 | y;
				Region r = RegionManager.forId(regionId);
				if (r == null) {
					continue;
				}
				dump(r, bw);
			}
		}
		bw.flush();
		bw.close();
		System.out.println("Finished dumping zone [" + SEARCHING_AREA.getSouthWestX() + ", " + SEARCHING_AREA.getSouthWestY() + ", " + SEARCHING_AREA.getNorthEastX() + ", " + SEARCHING_AREA.getNorthEastY() + "] - " + unhandledNPCs + "/" + npcCount + " unhandled NPCs and " + unhandledObjects + "/" + objectCount + " unhandled objects!");
	}

	/**
	 * Dumps a region.
	 * @param r The region to dump.
	 * @param bw The buffered writer.
	 * @throws Throwable When an exception occurs.
	 */
	private static void dump(Region r, BufferedWriter bw) throws Throwable {
		if (bw == null) {
			bw = new BufferedWriter(new FileWriter("./missing-content_r" + r.getId() + ".txt"));
		}
		Region.load(r);
		bw.append("--------===========   Region " + r.getId() + " [" + (r.getX() << 6) + ", " + (r.getY() << 6) + "]   ===========--------");
		bw.newLine();
		for (RegionPlane p : r.getPlanes()) {
			for (NPC n : p.getNpcs()) {
				npcCount++;
				if (unhandled.get(n.getId()) == Boolean.TRUE) {
					bw.append("Unhandled NPC - " + n.getId() + ", " + n.getName() + ", " + n.getLocation() + ".");
					bw.newLine();
					unhandledNPCs++;
				}
			}
			for (GameObject[] objects : p.getObjects()) {
				for (GameObject obj : objects) {
					if (obj == null) {
						continue;
					}
					objectCount++;
					if (unhandled.get(1 << 16 | obj.getId()) == Boolean.TRUE) {
						bw.append("Unhandled object - " + obj.getId() + ", " + obj.getName() + ", " + obj.getLocation() + ".");
						bw.newLine();
						unhandledObjects++;
					}
				}
			}
		}
	}

}