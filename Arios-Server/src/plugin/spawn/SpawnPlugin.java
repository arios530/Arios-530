package plugin.spawn;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.cache.def.impl.ObjectDefinition;
import org.arios.game.content.global.shop.Shop;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.Node;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.spawn.PKScoreBoard;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.system.mysql.impl.ShopSQLHandler;
import org.arios.game.system.task.TaskExecutor;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.parser.item.ItemConfiguration;
import org.arios.plugin.Plugin;

/**
 * Handles the default spawn plugin.
 * @author Vexia
 */
public class SpawnPlugin extends OptionHandler {

	/**
	 * The objects to spawn.
	 */
	private static final Object[][] OBJECTS = new Object[][] { { 409, new Location(3092, 3506, 0) }, { 6552, new Location(3095, 3506, 0) }, { 17010, new Location(3099, 3504, 0) }, { 3192, new Location(3084, 3497, 0) }, { 4150, new Location(3090, 3499, 0) }, { 4151, new Location(3090, 3498, 0) } };

	/**
	 * The npc shops.
	 */
	private static final int[] SHOP_NPCS = new int[] { 3117, 5503, 649, 707, 3050, 6537, 3788, 410 };

	/**
	 * The scoreboard.
	 */
	private static final int SCOREBOARD = 3192;

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		if (GameWorld.isEconomyWorld()) {
			return this;
		}
		for (int i = 0; i < SHOP_NPCS.length; i++) {
			NPCDefinition.forId(SHOP_NPCS[i]).getConfigurations().put("option:talk-to", this);
		}
		ObjectDefinition.forId(4150).getConfigurations().put("option:use", this);
		ObjectDefinition.forId(4151).getConfigurations().put("option:use", this);
		ObjectDefinition.forId(SCOREBOARD).getConfigurations().put("option:view", this);
		for (int i = 0; i < OBJECTS.length; i++) {
			ObjectBuilder.add(new GameObject((int) OBJECTS[i][0], (Location) OBJECTS[i][1]));
		}
		ShopSQLHandler.getShops().put(6537, new PKPointShop("Arios PK Shop", new Item[] { new Item(11694, 100), new Item(11696, 100), new Item(11700, 100), new Item(11698, 100), new Item(6737, 100), new Item(6731, 100), new Item(6735, 100), new Item(6733, 100), new Item(6585, 100), new Item(11128, 100), new Item(11724, 100), new Item(11726, 100), new Item(11728, 100), new Item(11718, 100), new Item(11720, 100), new Item(11722, 100), new Item(11732, 100), new Item(11874, 100), new Item(6914, 100), new Item(6889, 100), new Item(11902, 100), new Item(11846, 100), new Item(11854, 100), new Item(11850, 100), new Item(11852, 100), new Item(11848, 100), new Item(11856, 100), new Item(11283, 10), new Item(6570, 5), new Item(11335, 1), new Item(10548, 100), new Item(10551, 100), new Item(8850, 100), new Item(2581, 50), new Item(2577, 50), new Item(11235, 100) }));
		ShopSQLHandler.getShops().put(3788, new PKPointShop("Arios Void PK Shop", new Item[] { new Item(8839, 100), new Item(8840, 100), new Item(8841, 100), new Item(8842, 100), new Item(11663, 100), new Item(11664, 100), new Item(11665, 100) }));
		ShopSQLHandler.getShops().put(3050, new CreditShop());
		return this;
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		switch (option) {
		case "talk-to":
			node.asNpc().openShop(player);
			return true;
		case "view":
			PKScoreBoard.open(player);
			break;
		case "use":
			player.teleport(node.getId() == 4150 ? new Location(3272, 3687, 0) : new Location(3180, 3683, 0));
			player.graphics(Graphics.create(110));
			break;
		}
		return true;
	}

	/**
	 * A credit shop.
	 * @author Vexia
	 */
	public class CreditShop extends Shop {

		/**
		 * Constructs a new {@Code CreditShop} {@Code Object}
		 */
		public CreditShop() {
			super("Arios Credit Shop", new Item[] { new Item(1053, 100), new Item(1055, 100), new Item(1057, 100), new Item(1038, 100), new Item(1040, 100), new Item(1042, 100), new Item(1044, 100), new Item(1046, 100), new Item(1048, 100), new Item(1050, 100), new Item(9470, 100), new Item(10398, 100), new Item(13107, 100), new Item(6082, 100), new Item(10392, 100), new Item(4083, 100), new Item(1419, 100), new Item(13103, 100), new Item(962, 100), new Item(6199, 100), new Item(9946, 100), new Item(4565, 100), new Item(7671, 100), new Item(7673, 100), new Item(1959, 100), new Item(11858), new Item(11862), new Item(11860), new Item(11938), new Item(11940, 1), new Item(9920, 100), new Item(6583), new Item(7927) }, false);
			setPointShop(true);
		}

		@Override
		public void open(final Player player) {
			TaskExecutor.executeSQL(new Runnable() {

				@Override
				public void run() {
					CreditShop.super.open(player);
					player.getDetails().getShop().syncCredits();
				}

			});
		}

		@Override
		public int getBuyPrice(Item item, Player player) {
			return item.getDefinition().getConfiguration(ItemConfiguration.POINT_PRICE, 1000);
		}

		@Override
		public String getPointsName() {
			return "Arios Credit";
		}

		@Override
		public void decrementPoints(Player player, int decrement) {
			player.getDetails().getShop().setCredits(getPoints(player) - decrement, true);
		}

		@Override
		public int getPoints(Player player) {
			return player.getDetails().getShop().getCredits();
		}

	}

	/**
	 * A pk point shop.
	 * @author Vexia
	 */
	public class PKPointShop extends Shop {

		/**
		 * Constructs a new {@Code PKPointShop} {@Code Object}
		 */
		public PKPointShop(String title, Item[] stock) {
			super(title, stock, false);
			setPointShop(true);
		}

		@Override
		public int getBuyPrice(Item item, Player player) {
			return item.getDefinition().getConfiguration(ItemConfiguration.POINT_PRICE, 1000);
		}

		@Override
		public String getPointsName() {
			return "PK Point";
		}

		@Override
		public void decrementPoints(Player player, int decrement) {
			player.getSavedData().getSpawnData().decrementPoints(decrement);
		}

		@Override
		public int getPoints(Player player) {
			return player.getSavedData().getSpawnData().getPkPoints();
		}

	}
}
