package org.arios.parser;

import java.util.ArrayList;
import java.util.List;

import org.arios.game.content.holiday.ItemLimitation;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.parser.item.ItemConfiguration;
import org.arios.parser.item.ItemDefinitionsParser;
import org.arios.parser.misc.ComponentParser;
import org.arios.parser.misc.MusicPropertiesParser;
import org.arios.parser.misc.RangeDataParser;
import org.arios.parser.misc.XTEAKeyParser;
import org.arios.parser.npc.NPCConfiguration;
import org.arios.parser.object.DoorDefinitionsParser;
import org.arios.parser.object.ObjectDefinitionsParser;

/**
 * Handles the parsing sequence.
 * @author Emperor
 * @author Vexia
 */
public final class ParserSequence {

	/**
	 * The parsers.
	 */
	private static List<Parser> parsers;

	/**
	 * Constructs a new {@code GameWorld.java} {@code Object}.
	 */
	private ParserSequence() {
		/**
		 * empty.
		 */
	}

	/**
	 * Method used to prepare the parsed being parsed.
	 */
	public static void prepare() {
		List<Parser> parsers = getSequence();
		parsers.add(new XTEAKeyParser());
		parsers.add(new ObjectDefinitionsParser());
		parsers.add(new DoorDefinitionsParser());
		parsers.add(new ItemDefinitionsParser());
		parsers.add(new ItemConfiguration());
		parsers.add(new NPCConfiguration());
		parsers.add(new ComponentParser());
		parsers.add(new RangeDataParser());
		parsers.add(new MusicPropertiesParser());
		parsers.add(new ItemLimitation());
	}

	/**
	 * Parses all of the parsers.
	 * @throws Throwable the throwable.
	 */
	public static void parse() throws Throwable {
		if (parsers == null) {
			throw new IllegalStateException("Cannot parse unprepared sequence!");
		}
		for (Parser parser : parsers) {
			if (!parser.parse()) {
				System.err.println("Failed to parse - [parser=" + parser + ", class=" + parser.getClass().getSimpleName() + "].");
				return;
			}
		}
		parsers.clear();
		parsers = null;
	}

	/**
	 * Initializes the part of the world after the plugins are loaded.
	 * @return {@code True} if successful.
	 * @throws Throwable When an exception occurs.
	 */
	public static void post() throws Throwable {
		GameObject cwObject = RegionManager.getObject(Location.create(2445, 3083, 0));
		if (cwObject != null) {
			ObjectBuilder.replace(cwObject, cwObject.transform(cwObject.getId(), 3));
		}
	}

	/**
	 * Gets the sequence.
	 * @return the sequence.
	 */
	public static List<Parser> getSequence() {
		if (parsers == null) {
			parsers = new ArrayList<Parser>();
		}
		return parsers;
	}
}