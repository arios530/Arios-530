package org.arios.game.content.skill.member.construction;

import java.nio.ByteBuffer;

import org.arios.game.component.Component;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Direction;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.Region;
import org.arios.game.world.map.RegionChunk;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.map.RegionPlane;
import org.arios.game.world.map.build.DynamicRegion;
import org.arios.game.world.map.zone.ZoneBorders;
import org.arios.parser.player.SavingModule;

/**
 * Manages the player's house.
 * @author Emperor
 * @author Vexia
 */
public final class HouseManager implements SavingModule {

	/**
	 * The player instance.
	 */
	private final Player player;

	/**
	 * The current region.
	 */
	private DynamicRegion region;

	/**
	 * The house location.
	 */
	private HouseLocation location = HouseLocation.NOWHERE;

	/**
	 * The house style.
	 */
	private HousingStyle style = HousingStyle.BASIC_WOOD;

	/**
	 * The player's rooms.
	 */
	private final Room[][][] rooms = new Room[4][8][8];

	/**
	 * If building mode is enabled.
	 */
	private boolean buildingMode;

	/**
	 * Constructs a new {@code HouseManager} {@code Object}.
	 * @param player the player.
	 */
	public HouseManager(Player player) {
		this.player = player;
	}

	@Override
	public void save(ByteBuffer buffer) {
		buffer.put((byte) location.ordinal());
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Room room = rooms[z][x][y];
					if (room != null) {
						buffer.put((byte) z).put((byte) x).put((byte) y);
						buffer.put((byte) room.getProperties().ordinal());
						buffer.put((byte) room.getRotation().toInteger());
						for (int i = 0; i < room.getHotspots().length; i++) {
							if (room.getHotspots()[i].getDecorationIndex() > -1) {
								buffer.put((byte) i);
								buffer.put((byte) room.getHotspots()[i].getDecorationIndex());
							}
						}
						buffer.put((byte) -1);
					}
				}
			}
		}
		buffer.put((byte) -1);// Eof
	}

	@Override
	public void parse(ByteBuffer buffer) {
		location = HouseLocation.values()[buffer.get() & 0xFF];
		int z = 0;
		while ((z = buffer.get()) != -1) {
			Room room = rooms[z][buffer.get()][buffer.get()] = new Room(RoomProperties.values()[buffer.get() & 0xFF]);
			room.configure(style);
			room.setRotation(Direction.get(buffer.get() & 0xFF));
			int spot = 0;
			while ((spot = buffer.get()) != -1) {
				room.getHotspots()[spot].setDecorationIndex(buffer.get() & 0xFF);
			}
		}
	}

	/**
	 * Opens the house settings component.
	 */
	public void openSettings() {
		player.getInterfaceManager().openSingleTab(new Component(398));
	}

	/**
	 * Enters the player's house.
	 * @param player The player entering.
	 * @param buildingMode If building mode is enabled.
	 */
	public void enter(final Player player, boolean buildingMode) {
		if (this.buildingMode != buildingMode || !isLoaded()) {
			this.buildingMode = buildingMode;
			construct();
		}
		player.setAttribute("poh_entry", this);
		player.lock(2);
		player.getProperties().setTeleportLocation(getEnterLocation());
		player.getInterfaceManager().openComponent(399);
		player.getConfigManager().set(261, buildingMode);
		player.getConfigManager().set(262, getRoomAmount());
		GameWorld.submit(new Pulse(1, player) {
			@Override
			public boolean pulse() {
				player.getInterfaceManager().close();
				return true;
			}
		});
	}

	/**
	 * Leaves this house.
	 * @param player The player leaving.
	 */
	public static void leave(Player player) {
		HouseManager house = player.getAttribute("poh_entry", player.getHouseManager());
		player.getProperties().setTeleportLocation(house.location.getExitLocation());
	}

	/**
	 * Toggles the building mode.
	 * @param player The house owner.
	 * @param enable If the building mode should be enabled.
	 */
	public void toggleBuildingMode(Player player, boolean enable) {
		if (!isInHouse()) {
			player.sendMessage("Building mode really only helps if you're in a house.");
			return;
		}
		if (buildingMode != enable) {
			if (enable) {
				expelGuests(player);
			}
			reload(player, enable);
			player.sendMessage("Building mode is now " + (buildingMode ? "on" : " off") + ".");
		}
	}

	/**
	 * Reloads the house.
	 * @param player The player.
	 * @param buildingMode If building mode should be enabled.
	 */
	private void reload(Player player, boolean buildingMode) {
		int diffX = player.getLocation().getX() - region.getBaseLocation().getX();
		int diffY = player.getLocation().getY() - region.getBaseLocation().getY();
		int diffZ = player.getLocation().getZ() - region.getBaseLocation().getZ();
		enter(player, buildingMode);
		player.getProperties().setTeleportLocation(region.getBaseLocation().transform(diffX, diffY, diffZ));
	}

	/**
	 * Expels the guests from the house.
	 * @param player The house owner.
	 */
	public void expelGuests(Player player) {
		if (isLoaded()) {
			for (RegionPlane plane : region.getPlanes()) {
				for (Player p : plane.getPlayers()) {
					if (p != player) {
						leave(p);
					}
				}
			}
		} else {
			player.sendMessages("You're not in a house! You can't expel people from the world, even if you don't like", "them!");
			;
		}
	}

	/**
	 * Gets the entering location.
	 * @return The entering location.
	 */
	public Location getEnterLocation() {
		if (region == null) {
			System.err.println("House wasn't constructed yet!");
			return null;
		}
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Room room = rooms[z][x][y];
					if (room != null) {
						for (Hotspot h : room.getHotspots()) {
							if (h.getDecorationIndex() > -1) {
								Decoration d = h.getHotspot().getDecorations()[h.getDecorationIndex()];
								if (d == Decoration.PORTAL) {
									return region.getBaseLocation().transform(x * 8 + h.getChunkX(), y * 8 + h.getChunkY() + 2, 0);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Redecorates the house.
	 * @param style The new style.
	 */
	public void redecorate(HousingStyle style) {
		this.style = style;
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Room room = rooms[z][x][y];
					if (room != null) {
						room.decorate(style);
					}
				}
			}
		}
	}

	/**
	 * Creates the default house.
	 * @param location The house location.
	 */
	public void create(HouseLocation location) {
		Room room1 = rooms[0][4][4] = new Room(RoomProperties.GARDEN);
		room1.configure(style);
		room1.getHotspots()[0].setDecorationIndex(0);
		this.location = location;
	}

	/**
	 * Constructs the dynamic region for the house.
	 * @param buildingMode If the building mode is enabled.
	 * @return The region.
	 */
	public DynamicRegion construct() {
		Region from = RegionManager.forId(style.getRegionId());
		Region.load(from, true);
		RegionChunk defaultChunk = from.getPlanes()[style.getPlane()].getRegionChunk(1, 0);
		ZoneBorders borders = DynamicRegion.reserveArea(8, 8);
		region = new DynamicRegion(-1, borders.getSouthWestX() >> 6, borders.getSouthWestY() >> 6);
		region.setBorders(borders);
		RegionManager.getRegionCache().put(region.getId(), region);
		for (int z = 0; z < 1; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Room room = rooms[z][x][y];
					if (room == null) {
						region.setChunk(z, x, y, defaultChunk.copy(region.getPlanes()[0]));
					} else {
						/*
						 * BuildRegionChunk copy = (BuildRegionChunk)
						 * room.getChunk().copy(region.getPlanes()[0]);
						 * region.replaceChunk(z, x, y, copy,
						 * room.getChunk().getPlane().getRegion());
						 * room.loadDecorations(copy, buildingMode);
						 */
					}
				}
			}
		}
		return region;
	}

	/**
	 * Resets the house.
	 */
	public void reset() {
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					getRooms()[z][x][y] = null;
				}
			}
		}
		create(getLocation());
	}

	/**
	 * Checks if the player is in the house.
	 * @return {@code True} if so.
	 */
	public boolean isInHouse() {
		return isLoaded() && player.getHouseManager().getRegion() == player.getViewport().getRegion();
	}

	/**
	 * Checks if an exit exists on the given room.
	 * @param roomX The x-coordinate of the room.
	 * @param roomY The y-coordinate of the room.
	 * @param direction The exit direction.
	 * @return {@code True} if so.
	 */
	public boolean hasExit(int z, int roomX, int roomY, Direction direction) {
		Room room = rooms[z][roomX][roomY];
		boolean horizontal = direction.toInteger() % 2 != 0;
		int index = (direction.toInteger() + (horizontal ? 1 : 3)) % 4;
		if (room != null && room.getExits()[index]) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the amount of rooms.
	 * @return The amount of rooms.
	 */
	public int getRoomAmount() {
		int count = 0;
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (rooms[z][x][y] != null) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Checks if the house region was constructed and active.
	 * @return {@code True} if an active region for the house exists.
	 */
	public boolean isLoaded() {
		return region != null && region.isActive();
	}

	/**
	 * Gets the hasHouse.
	 * @return The hasHouse.
	 */
	public boolean hasHouse() {
		return location != HouseLocation.NOWHERE;
	}

	/**
	 * Gets the rooms.
	 * @return The rooms.
	 */
	public Room[][][] getRooms() {
		return rooms;
	}

	/**
	 * Gets the location.
	 * @return The location.
	 */
	public HouseLocation getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 * @param location The location to set.
	 */
	public void setLocation(HouseLocation location) {
		this.location = location;
	}

	/**
	 * Checks if the building mode is enabled.
	 * @return {@code True} if so.
	 */
	public boolean isBuildingMode() {
		return buildingMode;
	}

	/**
	 * Gets the region.
	 * @return The region.
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Gets the style.
	 * @return the style
	 */
	public HousingStyle getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 * @param style the style to set.
	 */
	public void setStyle(HousingStyle style) {
		this.style = style;
	}

}