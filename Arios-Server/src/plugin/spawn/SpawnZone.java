package plugin.spawn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.arios.game.content.global.shop.Shop;
import org.arios.game.interaction.Option;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.system.task.TaskExecutor;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.zone.MapZone;
import org.arios.game.world.map.zone.ZoneBorders;
import org.arios.game.world.map.zone.ZoneBuilder;
import org.arios.game.world.repository.Repository;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.parser.item.ItemConfiguration;
import org.arios.plugin.Plugin;
import org.arios.tools.RandomFunction;

/**
 * Handles the spawn zone.
 * @author Vexia
 */
public class SpawnZone extends MapZone implements Plugin<Object> {

	/**
	 * The shop locations.
	 */
	private static final Location[] SHOP_LOCATIONS = new Location[] { Location.create(3091, 3489, 0), Location.create(3091, 3492, 0), Location.create(3091, 3498, 0), Location.create(3091, 3500, 0), Location.create(3094, 3500, 0), Location.create(3099, 3499, 0), Location.create(3099, 3496, 0), Location.create(3099, 3488, 0), Location.create(3098, 3482, 0), Location.create(3095, 3483, 0), Location.create(3092, 3483, 0), Location.create(3090, 3483, 0), Location.create(3087, 3483, 0), Location.create(3080, 3487, 0), Location.create(3077, 3500, 0), Location.create(3079, 3506, 0), Location.create(3085, 3512, 0) };

	/**
	 * If the weekend shop is enabled.
	 */
	private static boolean WEEKEND;

	/**
	 * The shop npc.
	 */
	private static NPC shopNpc;

	/**
	 * Constructs a new {@Code SpawnZone} {@Code Object}
	 */
	public SpawnZone() {
		super("Spawn Zone", true);
	}

	@Override
	public void configure() {
		register(new ZoneBorders(3076, 3460, 3225, 3520));
	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		if (GameWorld.isEconomyWorld()) {
			return this;
		}
		ZoneBuilder.configure(this);
		return this;
	}

	@Override
	public boolean continueAttack(Entity e, Node target, CombatStyle style, boolean message) {
		if (target instanceof Player) {
			Player p = target.asPlayer();
			if (!p.getSavedData().getSpawnData().hasCompletedTutorial()) {
				return false;
			}
		}
		return super.continueAttack(e, target, style, message);
	}

	@Override
	public boolean interact(Entity e, Node target, Option option) {
		if (e instanceof Player) {
			switch (target.getId()) {
			case 410:
				if (target.asNpc().getShop() != null) {
					target.asNpc().openShop(e.asPlayer());
				}
				break;
			}
		}
		return super.interact(e, target, option);
	}

	@Override
	public boolean enter(Entity entity) {
		if (entity instanceof Player) {
			checkWeekend();
		}
		return super.enter(entity);
	}

	@Override
	public boolean leave(Entity e, boolean logout) {
		return super.leave(e, logout);
	}

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

	/**
	 * Checks if it is the weekend.
	 */
	private static void checkWeekend() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Toronto"));
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		boolean weekend = day == Calendar.SATURDAY || day == Calendar.SUNDAY;
		if (WEEKEND && !weekend) {// toggles the weekend shop off
			WEEKEND = false;
			toggleNpc();
		} else if (!WEEKEND && weekend) {// toggles the weekend shop on
			WEEKEND = true;
			toggleNpc();
		}
	}

	/**
	 * Toggles the npc shop.
	 */
	private static void toggleNpc() {
		Location location = RandomFunction.getRandomElement(SHOP_LOCATIONS);
		if (shopNpc == null) {
			shopNpc = NPC.create(410, location);
			shopNpc.init();
		}
		if (WEEKEND) {
			shopNpc.setShop(new WeekendShop(getItems()));
			shopNpc.setHidden(false);
			shopNpc.graphics(Graphics.create(86), 1);
			shopNpc.sendChat("The weekend shop is back in town!");
			Repository.sendNews("Gather your credits; The weekend shop is here!");
		} else {
			shopNpc.graphics(Graphics.create(86));
			shopNpc.sendChat("See you next week!");
			GameWorld.submit(new Pulse(2) {

				@Override
				public boolean pulse() {
					shopNpc.clear();
					shopNpc = null;
					return true;
				}

			});
			Repository.sendNews("The weekend shop owner has left town, make sure to check back next week!");
		}
	}

	/**
	 * Gets the items to put in the shop
	 * @return the items.
	 */
	public static Item[] getItems() {
		List<Item> items = new ArrayList<>();
		while (items.size() < 3) {
			Item i = RandomFunction.getRandomElement(WeekendShop.SHOP_ITEMS);
			if (items.contains(i)) {
				continue;
			}
			items.add(i);
		}
		return items.toArray(new Item[] {});
	}

	/**
	 * The credit weekend shop.
	 * @author Vexia
	 */
	public static class WeekendShop extends Shop {

		/**
		 * The shop items.
		 */
		public static Item[] SHOP_ITEMS = new Item[] { new Item(12887, 1), // druidic
				new Item(12894, 1), new Item(12901, 1), new Item(12964, 1), // combat
				new Item(12971, 1), new Item(12978, 1), new Item(12866, 1), // battle
				new Item(12873, 1), new Item(12880, 1), new Item(13006, 1), // gloves
				new Item(12860, 1), new Item(12861, 1), new Item(12862, 1), new Item(7806, 1), // angry
				new Item(7807, 1), new Item(7808, 1), new Item(7809, 1), new Item(11789, 1), new Item(12915, 1), new Item(12929, 1), new Item(12922, 1), new Item(4567, 1), new Item(13168) };

		/**
		 * Constructs a new {@Code CreditShop} {@Code Object}
		 * @param items the items.
		 */
		public WeekendShop(Item... items) {
			super("Weekend Credit Shop", items, false);
			setPointShop(true);
		}

		@Override
		public void open(final Player player) {
			TaskExecutor.executeSQL(new Runnable() {

				@Override
				public void run() {
					WeekendShop.super.open(player);
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
}
