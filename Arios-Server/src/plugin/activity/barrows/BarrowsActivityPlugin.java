package plugin.activity.barrows;

import org.arios.game.component.Component;
import org.arios.game.content.activity.ActivityPlugin;
import org.arios.game.content.global.action.ClimbActionHandler;
import org.arios.game.content.global.action.DoorActionHandler;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.interaction.Option;
import org.arios.game.node.Node;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.DeathTask;
import org.arios.game.node.entity.combat.ImpactHandler.HitsplatType;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.npc.agg.AggressiveBehavior;
import org.arios.game.node.entity.npc.agg.AggressiveHandler;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.info.portal.Perks;
import org.arios.game.node.entity.player.link.ActivityData;
import org.arios.game.node.object.GameObject;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.CameraContext;
import org.arios.net.packet.context.CameraContext.CameraType;
import org.arios.net.packet.context.MinimapStateContext;
import org.arios.net.packet.out.CameraViewPacket;
import org.arios.net.packet.out.MinimapState;
import org.arios.plugin.PluginManager;
import org.arios.tools.RandomFunction;

/**
 * Handles the barrows activity plugin.
 * @author Emperor
 */
public final class BarrowsActivityPlugin extends ActivityPlugin {

	/**
	 * The tunnel configuration values.
	 */
	private static final int[] TUNNEL_CONFIGS = { 55328769, 2867201, 44582944, 817160, 537688072, 40763408, 44320784, 23478274 };

	/**
	 * The overlay.
	 */
	private static final Component OVERLAY = new Component(24);

	/**
	 * The activity handling pulse.
	 */
	private static final Pulse PULSE = new Pulse(3) {
		@Override
		public boolean pulse() {
			boolean end = true;
			for (Player p : RegionManager.getRegionPlayers(14231)) {
				end = false;
				int index = p.getAttribute("barrow:drain-index", -1);
				if (index > -1) {
					p.removeAttribute("barrow:drain-index");
					p.getPacketDispatch().sendItemOnInterface(-1, 1, 24, index);
					continue;
				}
				if (p.getLocation().getZ() == 0 && p.getAttribute("barrow:looted", false)) {
					if (RandomFunction.random(15) == 0) {
						p.getImpactHandler().manualHit(p, RandomFunction.random(5), HitsplatType.NORMAL);
						Graphics.send(Graphics.create(405), p.getLocation());
					}
				}
				if (p.getLocks().isLocked("barrow:drain")) {
					continue;
				}
				int drain = 8;
				for (boolean killed : p.getSavedData().getActivityData().getBarrowBrothers()) {
					if (killed) {
						drain += 1;
					}
				}
				if (p.hasPerk(Perks.BARROWS_BEFRIENDER)) {
					drain /= 2;
				}
				p.getSkills().decrementPrayerPoints(drain);
				p.getLocks().lock("barrow:drain", (3 + RandomFunction.random(15)) * 3);
				index = 1 + RandomFunction.random(6);
				p.setAttribute("barrow:drain-index", index);
				p.getPacketDispatch().sendItemZoomOnInterface(4761 + RandomFunction.random(12), 100, 24, index);
				p.getPacketDispatch().sendAnimationInterface(9810, 24, index);
			}
			return end;
		}
	};

	/**
	 * Constructs a new {@code BarrowsActivityPlugin} {@code Object}.
	 */
	public BarrowsActivityPlugin() {
		super("Barrows", false, false, false);
	}

	@Override
	public boolean enter(Entity e) {
		if (e instanceof Player) {
			Player player = (Player) e;
			PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 2));
			player.getInterfaceManager().openOverlay(OVERLAY);
			player.getConfigManager().set(0, 1);
			if (player.getConfigManager().get(452) == 0) {
				shuffleCatacombs(player);
			}
			sendConfiguration(player);
			if (!PULSE.isRunning()) {
				PULSE.restart();
				PULSE.start();
				GameWorld.submit(PULSE);
			}
		} else {
			((NPC) e).setAggressive(true);
			((NPC) e).setAggressiveHandler(new AggressiveHandler(e, new AggressiveBehavior() {
				@Override
				public boolean canSelectTarget(Entity entity, Entity target) {
					if (!target.isActive() || DeathTask.isDead(target)) {
						return false;
					}
					if (!target.getProperties().isMultiZone() && target.inCombat()) {
						return false;
					}
					return true;
				}
			}));
		}
		return super.enter(e);
	}

	@Override
	public boolean leave(Entity e, boolean logout) {
		if (e instanceof Player) {
			Player player = (Player) e;
			PacketRepository.send(MinimapState.class, new MinimapStateContext(player, 0));
			player.getInterfaceManager().closeOverlay();
			NPC npc = player.getAttribute("barrow:npc");
			if (npc != null && !DeathTask.isDead(npc)) {
				npc.clear();
			}
			player.removeAttribute("barrow:solvedpuzzle");
			player.removeAttribute("barrow:opened_chest");
			player.removeAttribute("crusade-delay");
			if (!logout && player.getAttribute("barrow:looted", false)) {
				for (int i = 0; i < 6; i++) {
					player.removeAttribute("brother:" + i);
					player.getSavedData().getActivityData().getBarrowBrothers()[i] = false;
				}
				player.removeAttribute("barrow:looted");
				shuffleCatacombs(player);
				player.getSavedData().getActivityData().setBarrowTunnelIndex(RandomFunction.random(6));
				player.getSavedData().getActivityData().setBarrowKills(0);
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.RESET, 0, 0, 0, 0, 0));
			}
		}
		return super.leave(e, logout);
	}

	/**
	 * "Shuffles" the catacomb gates.
	 * @param player The player.
	 */
	public static void shuffleCatacombs(Player player) {
		int value = TUNNEL_CONFIGS[RandomFunction.random(TUNNEL_CONFIGS.length)];
		value |= 1 << (6 + RandomFunction.random(4));
		player.getConfigManager().set(452, value);
	}

	@Override
	public boolean death(Entity e, Entity killer) {
		Player player = null;
		if (killer instanceof Player) {
			player = (Player) killer;
		} else if (killer instanceof Familiar) {
			player = ((Familiar) killer).getOwner();
		}
		if (player != null && e instanceof NPC) {
			player.getSavedData().getActivityData().setBarrowKills(player.getSavedData().getActivityData().getBarrowKills() + 1);
			sendConfiguration(player);
		}
		return false;
	}

	@Override
	public boolean interact(Entity e, Node target, Option option) {
		if (target instanceof GameObject) {
			GameObject object = (GameObject) target;
			Player player = (Player) e;
			if (object.getId() >= 6702 && object.getId() <= 6707) {
				ClimbActionHandler.climb((Player) e, ClimbActionHandler.CLIMB_UP, BarrowsCrypt.getCrypt(object.getId() - 6702).getExitLocation());
				return true;
			}
			if (object.getId() >= 6708 && object.getId() <= 6712) {
				ClimbActionHandler.climb((Player) e, ClimbActionHandler.CLIMB_UP, BarrowsCrypt.getCrypt(player.getSavedData().getActivityData().getBarrowTunnelIndex()).getEnterLocation());
				return true;
			}
			switch (object.getWrapper().getId()) {
			case 6727:
			case 6724:
			case 6746:
			case 6743:
				if (player.getAttribute("barrow:solvedpuzzle", false)) {
					break;
				}
				player.setAttribute("barrow:puzzledoor", object);
				BarrowsPuzzle.open(player);
				return true;
			}
			switch (object.getId()) {
			case 6714:
			case 6733:
				int index = -1;
				for (int i = 0; i < player.getSavedData().getActivityData().getBarrowBrothers().length; i++) {
					if (!player.getSavedData().getActivityData().getBarrowBrothers()[i] && RandomFunction.random(15) == 0 && !player.getAttribute("brother:" + i, false)) {
						index = i;
						break;
					}
				}
				if (index > -1) {
					BarrowsCrypt.getCrypt(index).spawnBrother(player, RegionManager.getTeleportLocation(target.getLocation(), 1));
				}
				DoorActionHandler.handleAutowalkDoor(e, (GameObject) target);
				return true;
			case 6821:
				BarrowsCrypt.getCrypt(BarrowsCrypt.AHRIM).openSarcophagus((Player) e, object);
				return true;
			case 6771:
				BarrowsCrypt.getCrypt(BarrowsCrypt.DHAROK).openSarcophagus((Player) e, object);
				return true;
			case 6773:
				BarrowsCrypt.getCrypt(BarrowsCrypt.GUTHAN).openSarcophagus((Player) e, object);
				return true;
			case 6822:
				BarrowsCrypt.getCrypt(BarrowsCrypt.KARIL).openSarcophagus((Player) e, object);
				return true;
			case 6772:
				BarrowsCrypt.getCrypt(BarrowsCrypt.TORAG).openSarcophagus((Player) e, object);
				return true;
			case 6823:
				BarrowsCrypt.getCrypt(BarrowsCrypt.VERAC).openSarcophagus((Player) e, object);
				return true;
			case 6774:
				player.lock(1);
				for (int i = 0; i < player.getSavedData().getActivityData().getBarrowBrothers().length; i++) {
					if (!player.getSavedData().getActivityData().getBarrowBrothers()[i] && !player.getAttribute("brother:" + i, false)) {
						BarrowsCrypt.getCrypt(i).spawnBrother(player, RegionManager.getTeleportLocation(target.getCenterLocation(), 4));
					}
				}
				player.setAttribute("barrow:opened_chest", true);
				sendConfiguration(player);
				return true;
			case 6775:
				if (option.getName().equals("Close")) {
					player.removeAttribute("barrow:opened_chest");
					sendConfiguration(player);
					return true;
				}
				if (player.getAttribute("barrow:looted", false)) {
					player.getPacketDispatch().sendMessage("The chest is empty.");
					return true;
				}
				if ((!player.hasPerk(Perks.CRUSADER) || player.getAttribute("crusader", 0) > GameWorld.getTicks()) || (player.hasPerk(Perks.CRUSADER) && RandomFunction.random(100) > 25)) {
					player.setAttribute("/save:barrow:looted", true);
				} else {
					player.setAttribute("crusader", GameWorld.getTicks() + 500);
					player.sendMessage("You are given the chance for a second loot.");
				}
				RewardChest.reward(player);
				PacketRepository.send(CameraViewPacket.class, new CameraContext(player, CameraType.SHAKE, 3, 2, 2, 2, 2));
				return true;
			}
		}
		return false;
	}

	/**
	 * Sends the kill count configuration.
	 * @param player The player.
	 */
	public static void sendConfiguration(Player player) {
		ActivityData data = player.getSavedData().getActivityData();
		int config = data.getBarrowKills() << 17;
		for (int i = 0; i < data.getBarrowBrothers().length; i++) {
			if (data.getBarrowBrothers()[i]) { // This actually wasn't in 498
				// but we'll keep it anyways.
				config |= 1 << i;
			}
		}
		if (player.getAttribute("barrow:opened_chest", false)) {
			config |= 1 << 16;
		}
		System.out.println(config);
		player.getConfigManager().set(453, config);
	}

	@Override
	public boolean actionButton(Player player, int interfaceId, int buttonId, int slot, int itemId, int opcode) {
		return false;
	}

	@Override
	public boolean continueAttack(Entity e, Node target, CombatStyle style, boolean message) {
		if (target instanceof BarrowBrother) {
			Player p = null;
			if (e instanceof Player) {
				p = (Player) e;
			} else if (e instanceof Familiar) {
				p = ((Familiar) e).getOwner();
			}
			if (p != null && p != ((BarrowBrother) target).getPlayer()) {
				p.getPacketDispatch().sendMessage("He's not after you.");
				return false;
			}
		}
		return super.continueAttack(e, target, style, message);
	}

	@Override
	public ActivityPlugin newInstance(Player p) throws Throwable {
		return this;
	}

	@Override
	public Location getSpawnLocation() {
		return null;
	}

	@Override
	public void configure() {
		BarrowsEquipment.init();
		PluginManager.definePlugin(new TunnelEntranceDialogue());
		PluginManager.definePlugin(BarrowsPuzzle.SHAPES);
		registerRegion(14231);
		BarrowsCrypt.init();
		PULSE.stop();
	}

}