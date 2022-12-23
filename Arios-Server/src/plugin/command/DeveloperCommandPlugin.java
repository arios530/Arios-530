package plugin.command;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.content.eco.ge.GrandExchangeDatabase;
import org.arios.game.content.eco.ge.GrandExchangeEntry;
import org.arios.game.content.global.tutorial.CharacterDesign;
import org.arios.game.content.holiday.ItemLimitation;
import org.arios.game.content.skill.member.agility.AgilityHandler;
import org.arios.game.content.skill.member.construction.BuildHotspot;
import org.arios.game.content.skill.member.construction.HouseLocation;
import org.arios.game.content.skill.member.construction.RoomBuilder;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.BankPinManager.PinStatus;
import org.arios.game.node.entity.player.link.diary.AchievementDiary;
import org.arios.game.node.entity.player.link.diary.DiaryType;
import org.arios.game.node.entity.state.EntityState;
import org.arios.game.node.item.Item;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.system.script.ScriptManager;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.zone.RegionZone;
import org.arios.game.world.map.zone.impl.DonatorZone;
import org.arios.game.world.repository.Repository;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ContainerContext;
import org.arios.net.packet.out.ContainerPacket;
import org.arios.plugin.Plugin;

/**
 * Handles the developer commands.
 * @author Vexia
 */
public final class DeveloperCommandPlugin extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.DEVELOPER);
		return this;
	}

	@Override
	public boolean parse(final Player player, String name, String[] args) {
		Player o = null;
		switch (name) {
		case "char":
			CharacterDesign.open(player);
			break;
		case "tt":
			player.getInterfaceManager().openComponent(335);
			player.getPacketDispatch().sendAccessMask(1278, 30, 335, 0, 27);
			player.getPacketDispatch().sendAccessMask(1026, 32, 335, 0, 27);
			player.getPacketDispatch().sendAccessMask(1278, 0, 336, 0, 27);
			player.getPacketDispatch().sendAccessMask(2360446, 0, 335, 0, 27);
		    Object[] opts1 = new Object[]{"", "", "", "", "Remove-X", "Remove-All", "Remove-10", "Remove-5", "Remove", -1, 0, 7, 4, 90, (335 << 16) | 30 };
		    Object[] opts2 = new Object[]{"", "", "", "Lend", "Offer-X", "Offer-All", "Offer-10", "Offer-5", "Offer", -1, 0, 7, 4, 93, (336 << 16) };
		    Object[] opts3 = new Object[]{"", "", "", "", "", "", "", "", "", -1, 0, 7, 4, 91, (335 << 16) | 32 };
		    player.getPacketDispatch().sendRunScript(150, "IviiiIsssssssss", opts1);
		    player.getPacketDispatch().sendRunScript(150, "IviiiIsssssssss", opts2);
		    player.getPacketDispatch().sendRunScript(695, "IviiiIsssssssss", opts3);
		    Item[] items = new Item[28];
		    items[0] = new Item(4151);
		    items[1] = new Item(4153);
		    items[2] = new Item(11694);
		    items[3] = new Item(11696);
		    items[4] = new Item(11698);
		    items[5] = new Item(11700);
		    Item[] items2 = new Item[28];
		    items2[0] = new Item(5698);
		    items2[1] = new Item(11724);
		    items2[2] = new Item(11726);
		    items2[3] = new Item(11732);
		    items2[4] = new Item(11283);
		    PacketRepository.send(ContainerPacket.class, new ContainerContext(player, -1, 64212, 90, items, false));
		    PacketRepository.send(ContainerPacket.class, new ContainerContext(player, 60981, -2, 91, items2, false));
			break;
		case "c":
			player.getBankPinManager().setPin(null);
			return true;
		case "o":
			PacketRepository.send(ContainerPacket.class, new ContainerContext(player, -1, 2, 90, player.getInventory().toArray(), 27, false));	
			return true;
		case "tabs":
			player.getInterfaceManager().openDefaultTabs();
			return true;
		case "rls":
			ScriptManager.load();
			return true;
		case "di":
			DonatorZone.getInstance().invite(player, null);
			return true;
		case "clearhouse":
		case "resethouse":
			player.getHouseManager().reset();
			return true;
		case "findnpc":
			player.sendMessage(Repository.findNPC(toInteger(args[1])).toString());
			return true;
		case "diary":
			AchievementDiary diary = player.getAchievementDiaryManager().getDiary(DiaryType.KARAMJA);
			int index = toInteger(args[1]);
			for (int i = 0; i < diary.getType().getAchievements(index).length; i++) {
				diary.updateTask(player, index, i, true);
			}
			return true;
		case "poison":
			player.getStateManager().register(EntityState.POISONED, false, 68, player);
			return true;
		case "carpet":
			AgilityHandler.walk(player, -1, player.getLocation(), player.getLocation().transform(10, 0, 0), null, 0.0, null);
			return true;
		case "debugil":
			for (int itemId : ItemLimitation.getItems().keySet()) {
				player.getPacketDispatch().sendMessage(itemId + ": " + ItemDefinition.forId(itemId).getName() + " is limited to " + ItemLimitation.getItems().get(itemId) + ".");
				System.out.println(itemId + ": " + ItemDefinition.forId(itemId).getName() + " is limited to " + ItemLimitation.getItems().get(itemId) + ".");
			}
			return true;
		case "setil":
			if (args.length < 3) {
				player.getPacketDispatch().sendMessage("Syntax is ::setil itemId limit.");
				return true;
			}
			player.getPacketDispatch().sendMessage("Limited item " + args[1] + " to " + args[2] + ".");
			ItemLimitation.register(toInteger(args[1]), toInteger(args[2]));
			return true;
		case "setpin":
			o = Repository.getPlayerByDisplay(args[1]);
			if (o != null) {
				o.getBankPinManager().setPin(args[2]);
				o.getBankPinManager().setStatus(PinStatus.ACTIVE);
				player.sendMessage("Set " + o.getUsername() + "'s pin to " + args[2] + ".");
				o.sendMessage("Your pin was set to " + args[2] + "!");
			}
			return true;
		case "setcredits":
			o = Repository.getPlayerByDisplay(args[1]);
			if (o == null) {
				player.sendMessage("Player is offline or null.");
				return true;
			}
			o.getDetails().getShop().syncCredits();
			o.getDetails().getShop().setCredits(o.getDetails().getShop().getCredits() + Integer.parseInt(args[2]), true);
			o.getDetails().save();
			player.sendMessage("You gave " + o.getUsername() + ", " + Integer.parseInt(args[2]) + " arios credits!");
			return true;
		case "resetxp":
			player.getSavedData().getGlobalData().setDoubleExpDelay(0);
			player.getDetails().getShop().getMapping().remove("double_xp");
			return true;
		case "syncperks":
			player.getDetails().getShop().syncPerks();
			return true;
		case "bi":
			RoomBuilder.openBuildInterface(player, BuildHotspot.KITCHEN_TABLE_SPACE);
			return true;
		case "testicon":
			player.sendMessage("<img=" + Integer.parseInt(args[1]) + ">");
			return true;
		case "unlock":
			player.unlock();
			return true;
		case "vexhouse":
			player.teleport(Location.create(2451, 4649, 0));
			return true;
		case "modroom":
			player.teleport(new Location(2843, 5214, 0));
			return true;
		case "poh":
			if (!player.getHouseManager().hasHouse()) {
				player.getHouseManager().create(HouseLocation.RIMMINGTON);
			}
			player.getHouseManager().enter(player, true);
			return true;
		case "debug":
			player.toggleDebug();
			return true;
		case "zones":
			for (RegionZone z : player.getZoneMonitor().getZones()) {
				player.getPacketDispatch().sendMessage("Region zone " + z.getZone().getName() + " active...");
			}
			return true;
		case "setotask":
			o = Repository.getPlayerByDisplay(args[1]);
			if (o != null) {
				o.getSlayer().clear();
				player.sendMessage("You reset " + args[1] + " slayer task.");
			}
			return true;
		case "additem":
		case "deleteitem":
			if (name.equals("additem")) {
				addItem(player, args);
			} else {
				deleteItem(player, args);
			}
			break;
		case "setvalue":
			int itemId = toInteger(args[1]);
			int value = toInteger(args[2]);
			Item item = new Item(itemId);
			GrandExchangeEntry entry = GrandExchangeDatabase.getDatabase().get(itemId);
			if (entry == null) {
				player.getPacketDispatch().sendMessage("Could not find G.E entry for item [id=" + itemId + ", name=" + item.getName() + "]!");
				break;
			}
			entry.setValue(value);
			player.getPacketDispatch().sendMessage("Set Grand Exchange value for item [id=" + itemId + ", name=" + item.getName() + "] to " + value + "gp!");
			break;
		case "deleteitemb":
			deleteItem(player, args);
			return true;
		}
		return false;
	}

	/**
	 * Adds an item to a players item.
	 * @param player the player.
	 * @param args the args.
	 */
	private void addItem(Player player, String[] args) {
		Player t = Repository.getPlayerByDisplay(args[1]);
		if (t == null) {
			return;
		}
		int id = toInteger(args[2]);
		int amount = toInteger(args[3]);
		Item item = new Item(id, amount);
		t.getInventory().add(item);
		player.getPacketDispatch().sendMessage("You just gave " + t.getUsername() + " the item - " + item);
	}

	/**
	 * Deletes an item from a players item.
	 * @param player the player.
	 * @param args the args.
	 */
	private void deleteItem(Player player, String[] args) {
		Player t = Repository.getPlayerByDisplay(args[1]);
		if (t == null) {
			return;
		}
		int id = toInteger(args[2]);
		int amount = toInteger(args[3]);
		Item item = new Item(id, amount);
		if (args[0].equals("deleteitemb")) {
			t.getBank().remove(item);
		} else {
			t.getInventory().remove(item);
		}
		player.getPacketDispatch().sendMessage("You just removed" + t.getUsername() + " the item - " + item);
	}

}
