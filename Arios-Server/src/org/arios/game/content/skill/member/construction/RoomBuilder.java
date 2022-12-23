package org.arios.game.content.skill.member.construction;

import java.util.Arrays;

import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.component.Component;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ContainerContext;
import org.arios.net.packet.out.ContainerPacket;

/**
 * Used for building a room.
 * @author Emperor
 */
public final class RoomBuilder {

	/**
	 * The directions array.
	 */
	public static final Direction[] DIRECTIONS = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

	/**
	 * Opens the building furniture interface.
	 * @param player The player.
	 * @param hotspot The hotspot.
	 */
	public static void openBuildInterface(Player player, BuildHotspot hotspot) {
		player.getInterfaceManager().open(new Component(396));
		Item[] items = new Item[7];
		int value = 0;
		for (int i = 0; i < 7; i++) {
			int offset = i * 5;
			if (i >= hotspot.getDecorations().length) {
				for (int j = 97; j < 102; j++) {
					player.getPacketDispatch().sendString("", 396, j + offset);
				}
				player.getPacketDispatch().sendString("", 396, 140 + i);
				value |= 1 << (i + 1);
				continue;
			}
			Decoration decoration = hotspot.getDecorations()[i];
			if (i < 4) {
				items[i * 2] = new Item(decoration.getInterfaceItem());
			} else {
				items[1 + ((i - 4) * 2)] = new Item(decoration.getInterfaceItem());
			}
			player.getPacketDispatch().sendString(ObjectDefinition.forId(decoration.getObjectId()).getName(), 396, 97 + offset);
			boolean hasRequirements = true;
			for (int j = 0; j < 4; j++) {
				if (j >= decoration.getItems().length) {
					player.getPacketDispatch().sendString("", 396, 98 + offset + j);
				} else {
					Item item = decoration.getItems()[j];
					if (!player.getInventory().containsItem(item)) {
						hasRequirements = false;
					}
					player.getPacketDispatch().sendString(item.getName() + ": " + item.getAmount(), 396, 98 + offset + j);
				}
			}
			player.getPacketDispatch().sendString("Lvl " + decoration.getLevel(), 396, 140 + i);
			if (hasRequirements) {
				value |= 1 << (i + 1);
			}
		}
		player.getConfigManager().set(261, value);
		PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 396, 132, 91, items, false));
	}

	/**
	 * Builds a decoration object.
	 * @param player The player.
	 * @param deco The decoration.
	 * @param object The object.
	 */
	public static void buildDecoration(Player player, BuildHotspot hotspot, Decoration deco, GameObject object) {
		if (!player.getInventory().containsItems(deco.getItems())) {
			player.getPacketDispatch().sendMessage("You don't have the required items to build this.");
			return;
		}
		if (deco.getCondition() != null && !deco.getCondition().hasRequirements(player, true)) {
			return;
		}
		if (deco.getCondition() != null) {
			deco.getCondition().remove(player);
		}
		player.getInventory().remove(deco.getItems());
		ObjectBuilder.replace(object, object.transform(deco.getObjectId()));
		Location l = object.getLocation();
		Room room = player.getHouseManager().getRooms()[l.getZ()][l.getLocalX() >> 3][l.getLocalY() >> 3];
		for (int i = 0; i < room.getProperties().getHotspots().length; i++) {
			if (room.getProperties().getHotspots()[i].getHotspot() == hotspot) {
				room.getHotspots()[i].setDecorationIndex(hotspot.getDecorationIndex(deco));
			}
		}
	}

	/**
	 * Builds a room.
	 * @param player The player.
	 * @param room The room to build.
	 * @param door The door hotspot.
	 */
	public static void buildRoom(Player player, Room room, int z, int x, int y) {
		player.getHouseManager().getRooms()[z][x][y] = room;
		player.getPacketDispatch().sendMessage("Building room " + room.getProperties() + ".");
	}

	/**
	 * Gets the room offset.
	 * @param door The door.
	 * @return The room offset [x, y].
	 */
	public static int[] getRoomPosition(GameObject door) {
		Location l = door.getLocation();
		switch (door.getRotation()) {
		case 0: // West
			return new int[] { (l.getLocalX() >> 3) - 1, l.getLocalY() >> 3 };
		case 1: // North
			return new int[] { l.getLocalX() >> 3, (l.getLocalY() >> 3) + 1 };
		case 2: // East
			return new int[] { (l.getLocalX() >> 3) + 1, l.getLocalY() >> 3 };
		case 3: // South
			return new int[] { l.getLocalX() >> 3, (l.getLocalY() >> 3) - 1 };
		}
		return null;
	}

	/**
	 * Gets the available rotations of the room to build.
	 * @param exits The exits of the room.
	 * @param door The door hotspot used.
	 * @param roomX The room x-coordinate.
	 * @param roomY The room y-coordinate.
	 * @return The available rotations for the room [NORTH, EAST, SOUTH, WEST].
	 */
	public static Direction[] getAvailableRotations(Player player, boolean[] exits, GameObject door, int roomX, int roomY) {
		Direction[] directions = Arrays.copyOf(DIRECTIONS, DIRECTIONS.length);
		boolean[] exit = Arrays.copyOf(exits, exits.length);
		// Exits go to: (0=east, 1=south, 2=west, 3=north)
		// Door goes to: (0=west, 1=north, 2=east, 3=south)
		int[] info = getExitRequirements(player, door.getLocation().getZ(), roomX, roomY);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if ((info[j] == 1 && !exit[j]) || (info[j] == -1 && exit[j])) {
					directions[i] = null;
				}
			}
			boolean b = exit[0];
			for (int j = 0; j < exit.length - 1; j++) {
				exit[j] = exit[j + 1];
			}
			exit[exit.length - 1] = b;
		}
		return directions;
	}

	/**
	 * Gets the exit requirements for the given room.
	 * @param roomX The room x-coordinate.
	 * @param roomY The room y-coordinate.
	 * @return The disabled exit indexes.
	 */
	private static int[] getExitRequirements(Player player, int z, int roomX, int roomY) {
		int[] exits = new int[4];
		if (roomX == 0) {
			exits[0] = -1;
		} else if (player.getHouseManager().hasExit(z, roomX - 1, roomY, Direction.EAST)) {
			exits[0] = 1;
		} else if (player.getHouseManager().getRooms()[z][roomX - 1][roomY] != null) {
			exits[0] = -1;
		}
		if (roomX == 7) {
			exits[2] = -1;
		} else if (player.getHouseManager().hasExit(z, roomX + 1, roomY, Direction.WEST)) {
			exits[2] = 1;
		} else if (player.getHouseManager().getRooms()[z][roomX + 1][roomY] != null) {
			exits[2] = -1;
		}
		if (roomY == 0) {
			exits[1] = -1;
		} else if (player.getHouseManager().hasExit(z, roomX, roomY - 1, Direction.NORTH)) {
			exits[1] = 1;
		} else if (player.getHouseManager().getRooms()[z][roomX][roomY - 1] != null) {
			exits[1] = -1;
		}
		if (roomY == 7) {
			exits[3] = -1;
		} else if (player.getHouseManager().hasExit(z, roomX, roomY + 1, Direction.SOUTH)) {
			exits[3] = 1;
		} else if (player.getHouseManager().getRooms()[z][roomX][roomY + 1] != null) {
			exits[3] = -1;
		}
		return exits;
	}
}