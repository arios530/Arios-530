package org.arios.game.content.global.quest.impl.member;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.zone.ZoneBorders;

/**
 * The main type for the tourist trap quest.
 * @author Aero
 * @author 'Vexia
 */
public final class TouristTrap extends Quest {

	/**
	 * The name of the quest.
	 */
	public static final String NAME = "The Tourist Trap";

	/**
	 * The metal key item.
	 */
	public static final Item METAL_KEY = new Item(1839);

	/**
	 * The desert geat item.
	 */
	public static final Item[] DESERT_CLOTHES = new Item[] { new Item(1833), new Item(1835), new Item(1837) };

	/**
	 * The slave clothes items.
	 */
	public static final Item[] SLAVE_CLOTHES = new Item[] { new Item(1846), new Item(1844), new Item(1845) };

	/**
	 * Teh tenti pineapple item.
	 */
	public static final Item TENTI_PINEAPPLE = new Item(1851);

	/**
	 * The cell door key item.
	 */
	public static final Item CELL_DOOR_KEY = new Item(1840);

	/**
	 * The wrought iron key item.
	 */
	public static final Item WROUGHT_IRON_KEY = new Item(1843);

	/**
	 * The bedabin key item.
	 */
	public static final Item BEDABIN_KEY = new Item(1852);

	/**
	 * The config id for this quest.
	 */
	public static final int CONFIG_ID = 907;

	/**
	 * The tehcnical plans item.
	 */
	public static final Item TECHNICAL_PLANS = new Item(1850);

	/**
	 * The prototype dart tip item.
	 */
	public static final Item PROTOTYPE_DART_TIP = new Item(1853);

	/**
	 * The prototype dart item.
	 */
	public static final Item PROTOTYPE_DART = new Item(1849);

	/**
	 * The barrel item.
	 */
	public static final Item BARREL = new Item(1841);

	/**
	 * The anna barrel item.
	 */
	public static final Item ANNA_BARREL = new Item(1842);

	/**
	 * The jail location.
	 */
	public static final Location JAIL = Location.create(3285, 3034, 0);

	/**
	 * The zone borders.
	 */
	public static final ZoneBorders JAIL_BORDER = new ZoneBorders(3284, 3032, 3287, 3037);

	/**
	 * The slots to check for items on.
	 */
	public static final int[] SLOTS = new int[] { EquipmentContainer.SLOT_WEAPON, EquipmentContainer.SLOT_FEET, EquipmentContainer.SLOT_SHIELD, EquipmentContainer.SLOT_HAT, EquipmentContainer.SLOT_CHEST, EquipmentContainer.SLOT_LEGS };

	/**
	 * Constructs a new {@code TouristTrap} {@code Object}.
	 * @param player The player to construct the class for.
	 */
	public TouristTrap(final Player player) {
		super(player);
	}

	@Override
	public void update() {
		super.clear();
		switch (getStage()) {
		case 0:
			line("<blue>I can start this quest by speaking to <red>Irena <blue>after I have<br><br><blue>gone through the <red>Shantay Pass, South of Al-Kharid.<br><br><blue>To complete this quest I need:-<br><br>" + (player.getSkills().getStaticLevel(Skills.FLETCHING) > 9 ? "<str>" : "<blue>") + "Level 10 Fletching<br><br>" + (player.getSkills().getStaticLevel(Skills.SMITHING) > 19 ? "<str>" : "<blue>") + "Level 20 Smithing" + (hasRequirements() ? "<br><br><blue>I have all the <red>requirements<blue> to begin and complete this<br><br><red>quest." : ""), 11);
			break;
		case 10:
		case 11:
		case 30:
		case 40:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I need to head into <red>the desert<blue> and search for <red>Ana", 11);
			break;
		case 50:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I need to find the guard a <red>Tenti Pineapple<blue> for the guard.", 11);
			break;
		case 51:
		case 52:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I have found a way to get <red>Tenti Pineapple<blue> I need to find<br><br><blue>the research plans that <red>Captain Siad<blue> has.", 11);
			break;
		case 53:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I have found a way to get <red>Tenti Pineapple<blue><br><br><blue>I found the technical plans <red>Al Shabim<blue> was looking for.", 11);
			break;
		case 54:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I have found a way to get <red>Tenti Pineapple<blue><br><br><blue>I need to manufacture the <red>Prototype<blue> weapon for <red>Al Shabim<blue>.", 11);
			break;
		case 60:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I manufactured the <red>Prototype<blue> weapon and received<br><br><blue>a tasty <red>Tenti Pineapple<blue>.", 11);
			break;
		case 61:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I finally found <red>Anna<blue>. I just need to find a way to smuggle<br><br><blue>her out of here.", 11);
			break;
		case 71:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I need to operate the <red>Winch<blue> to lift <red>Ana<blue> back up here.", 11);
			break;
		case 72:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I need to get <red>Ana<blue> from one of the barrels lifted.", 11);
			break;
		case 80:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I loaded <red>Ana<blue> into the <red>Cart<blue> I now need to get the <red>cart driver<blue> <br><br><blue>to transport it.", 11);
			break;
		case 90:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I payed the <red>Mine cart driver<blue> and he agreed to smuggle me and <red>Anna<blue><br><br><blue>out of the <red>Mining camp<blue>.", 11);
			break;
		case 95:
		case 98:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I smuggled both me and <red>Anna<blue> from the <red>Mining camp<blue>. I should<br><br><blue>go tell <red>Irena<blue> straight away.", 11);
			break;
		case 100:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><str>I returned <str>Ana<str> back to her mother and was rewarded<br><br><str>with a <str>key<str> and the knowledge in two skills.<br><br><br><br><col=FF0000>QUEST COMPLETE!", 11);
			break;
		default:
			line("<str>Irena was distraught that her daughter Ana had vanished<br><br><str>somewhere in the desert, and I agreed to help find her.<br><br><blue>I need to head into <red>the desert<blue> and search for <red>Ana", 11);
			break;
		}
	}

	@Override
	public void finish() {
		super.finalize();
		player.getPacketDispatch().sendString("2 Quest Points", 277, 8+ 2);
		player.getPacketDispatch().sendString("4650 XP in each of the two skills", 277, 9+ 2);
		player.getPacketDispatch().sendString("Ability to make throwing darts", 277, 10+ 2);
		player.getPacketDispatch().sendString("Access to desert mining camp", 277, 11+ 2);
		player.getPacketDispatch().sendString("mithril and adamantite rocks.", 277, 12+ 2);
		player.getPacketDispatch().sendItemZoomOnInterface(806, 230, 277, 3+ 2);
		player.getQuestRepository().update(player);
		player.getInventory().remove(new Item(1842, player.getInventory().getAmount(ANNA_BARREL)));
		player.getBank().remove(new Item(1842, player.getBank().getAmount(ANNA_BARREL)));
	}

	/**
	 * Sends the player to jail.
	 * @param player the player.
	 */
	public static void jail(final Player player, String dialogue) {
		player.getDialogueInterpreter().sendDialogues(4999, null, dialogue);
		player.lock();
		GameWorld.submit(new Pulse(1) {
			int counter;

			@Override
			public boolean pulse() {
				switch (counter++) {
				case 1:
					player.lock();
					player.getInterfaceManager().openComponent(115);
					break;
				case 3:
					player.getProperties().setTeleportLocation(Location.create(3285, 3034, 0));
					player.getPacketDispatch().sendMessage("You are roughed up by the guards and manhandled into a cell.");
					player.getInterfaceManager().close();
					player.unlock();
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * Jails a player.
	 * @param player the player.
	 */
	public static void jail(final Player player) {
		jail(player, "Hey you! You're not supposed to be in here!");
	}

	/**
	 * Checks if the player is jailable.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public static boolean isJailable(final Player player) {
		if (inJail(player)) {
			return false;
		}
		if (player.getEquipment().itemCount() > 3 || (!hasDesertClothes(player) && !hasSlaveClothes(player))) {
			for (int i : SLOTS) {
				if (player.getEquipment().get(i) != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a config value pertaining to this quest.
	 * @param player the player.
	 * @param the value.
	 */
	public static void addConfig(final Player player, final int value) {
		player.getConfigManager().set(CONFIG_ID, value, true);
	}

	/**
	 * Checks if the player has desert gear.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public static boolean hasDesertClothes(final Player player) {
		for (Item i : DESERT_CLOTHES) {
			if (!player.getEquipment().containsItem(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the player has slave clothes.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public static boolean hasSlaveClothes(final Player player) {
		for (Item i : SLAVE_CLOTHES) {
			if (!player.getEquipment().containsItem(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the player has armour.
	 * @param player the player.
	 * @return
	 */
	public static boolean hasArmour(final Player player) {
		return player.getEquipment().itemCount() > 0 && !hasDesertClothes(player) && !hasSlaveClothes(player);
	}

	/**
	 * Checks if the player is in jail.
	 * @param player the player.
	 * @return {@code True} if so.
	 */
	public static boolean inJail(final Player player) {
		return JAIL_BORDER.insideBorder(player);
	}

	@Override
	public boolean hasRequirements() {
		return player.getSkills().getStaticLevel(Skills.FLETCHING) > 9 && player.getSkills().getStaticLevel(Skills.SMITHING) > 19;
	}

	@Override
	public int[] getConfig() {
		return new int[] { 197, getState() == QuestState.COMPLETED ? 30 : getState() == QuestState.NOT_STARTED ? 0 : 1 };
	}
	
	@Override
	public int getButtonId() {
		return getIndex() - 1;
	}

	@Override
	public int getIndex() {
		return 123;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public int getQuestPoints() {
		return 2;
	}

}