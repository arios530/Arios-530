package plugin.activity.gwd;

import java.util.ArrayList;
import java.util.List;

import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.DeathTask;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.npc.agg.AggressiveBehavior;
import org.arios.game.node.entity.npc.agg.AggressiveHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;

/**
 * Handles a god wars NPC.
 * @author Emperor
 */
public final class GodWarsNPC extends AbstractNPC {

	/**
	 * The aggressive behavior.
	 */
	private static final AggressiveBehavior AGGRO_BEHAVIOR = new AggressiveBehavior() {

		@Override
		public boolean canSelectTarget(Entity entity, Entity target) {
			if (!target.isActive() || DeathTask.isDead(target) || DeathTask.isDead(entity)) {
				return false;
			}
			if (!target.getProperties().isMultiZone() && target.inCombat()) {
				return false;
			}
			if (target instanceof GodWarsNPC) {
				if (((GodWarsNPC) target).faction != ((GodWarsNPC) entity).faction) {
					return true;
				}
			} else if (target instanceof Player) {
				if (!((GodWarsNPC) entity).faction.isProtected((Player) target)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public List<Entity> getPossibleTargets(Entity entity, int radius) {
			List<Entity> targets = new ArrayList<>();
			for (Player player : RegionManager.getLocalPlayers(entity, radius)) {
				if (canSelectTarget(entity, player)) {
					targets.add(player);
				}
			}
			if (!targets.isEmpty()) {
				return targets;
			}
			for (NPC npc : RegionManager.getLocalNpcs(entity, radius)) {
				if (canSelectTarget(entity, npc)) {
					targets.add(npc);
				}
			}
			return targets;
		}
	};

	/**
	 * The god wars faction (0=armadyl, 1=bandos, 2=saradomin, 3=zamorak).
	 */
	private GodWarsFaction faction;

	/**
	 * Constructs a new {@code GodWarsNPC} {@code Object}.
	 */
	public GodWarsNPC() {
		super(-1, null);
	}

	/**
	 * Constructs a new {@code GodWarsNPC} {@code Object}.
	 * @param id The NPC id.
	 * @param location The location.
	 */
	public GodWarsNPC(int id, Location location) {
		super(id, location);
	}

	@Override
	public void init() {
		super.init();
		setWalks(true);
		faction = GodWarsFaction.forId(getId());
	}

	@Override
	public void setDefaultBehavior() {
		setAggressive(true);
		aggressiveHandler = new AggressiveHandler(this, AGGRO_BEHAVIOR);
	}

	@Override
	public boolean isAttackable(Entity entity, CombatStyle style) {
		if (style == CombatStyle.MELEE && faction == GodWarsFaction.ARMADYL && entity instanceof Player) {
			((Player) entity).getPacketDispatch().sendMessage("The aviansie is flying too high for you to attack using melee.");
			return false;
		}
		return super.isAttackable(entity, style);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new GodWarsNPC(id, location);
	}

	@Override
	public int[] getIds() {
		return new int[] { 6210, 6211, 6212, 6213, 6214, 6215, 6216, 6217, 6218, 6219, 6220, 6221, 6229, 6230, 6231, 6232, 6233, 6234, 6235, 6236, 6237, 6238, 6239, 6240, 6241, 6242, 6243, 6244, 6245, 6246, 6254, 6255, 6256, 6257, 6258, 6259, 6267, 6268, 6269, 6270, 6271, 6272, 6273, 6274, 6275, 6276, 6277, 6278, 6279, 6280, 6281, 6282, 6283 };
	}

	/**
	 * The god wars factions.
	 * @author Emperor
	 */
	static enum GodWarsFaction {
		ARMADYL(6222, 6246, 87, 11694, 11718, 11720, 11722, 12670, 12671), BANDOS(6260, 6283, 11061, 11696, 11724, 11726, 11728), SARADOMIN(6247, 6259, 1718, 2412, 2415, 2661, 2663, 2665, 2667, 3479, 3675, 3489, 3840, 4682, 6762, 8055, 10384, 10386, 10388, 10390, 10440, 10446, 10452, 10458, 10464, 10470, 11181, 11698, 11730), ZAMORAK(6203, 6221, 11716, 11700, 1724, 2414, 2417, 2653, 2655, 2657, 2659, 3478, 3674, 3841, 3842, 3852, 4683, 6764, 8056, 10368, 10370, 10372, 10374, 10444, 10450, 10456, 10460, 10468, 10474, 10776, 10786, 10790);

		/**
		 * The start NPC id.
		 */
		private final int startId;

		/**
		 * The end NPC id.
		 */
		private final int endId;

		/**
		 * The protection items.
		 */
		private final int[] protectionItems;

		/**
		 * Constructs a new {@code GodWarsFaction} {@code Object}.
		 * @param startId The start NPC id.
		 * @param endId The end NPC id.
		 * @param protectionItems The protection items for this faction.
		 */
		private GodWarsFaction(int startId, int endId, int... protectionItems) {
			this.startId = startId;
			this.endId = endId;
			this.protectionItems = protectionItems;
		}

		/**
		 * Gets the god wars faction for the given NPC id.
		 * @param npcId The NPC id.
		 * @return The faction for this NPC.
		 */
		public static GodWarsFaction forId(int npcId) {
			for (GodWarsFaction faction : values()) {
				if (npcId >= faction.getStartId() && npcId <= faction.getEndId()) {
					return faction;
				}
			}
			return null;
		}

		/**
		 * Checks if the player is protected from this faction.
		 * @param player The player.
		 * @return {@code True} if no NPCs of this faction should attack the
		 * player.
		 */
		public boolean isProtected(Player player) {
			for (Item item : player.getEquipment().toArray()) {
				if (item != null) {
					for (int id : protectionItems) {
						if (item.getId() == id) {
							return true;
						}
					}
				}
			}
			return false;
		}

		/**
		 * Gets the startId.
		 * @return The startId.
		 */
		public int getStartId() {
			return startId;
		}

		/**
		 * Gets the endId.
		 * @return The endId.
		 */
		public int getEndId() {
			return endId;
		}

		/**
		 * Gets the protectionItems.
		 * @return The protectionItems.
		 */
		public int[] getProtectionItems() {
			return protectionItems;
		}
	}
}