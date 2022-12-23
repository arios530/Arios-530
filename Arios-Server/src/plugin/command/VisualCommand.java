package plugin.command;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.arios.ServerConstants;
import org.arios.cache.Cache;
import org.arios.cache.ServerStore;
import org.arios.game.content.skill.member.farming.wrapper.PatchWrapper;
import org.arios.game.node.entity.combat.ImpactHandler.HitsplatType;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.audio.Audio;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.system.SystemLogger;
import org.arios.game.system.command.CommandPlugin;
import org.arios.game.system.command.CommandSet;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.repository.Repository;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.InterfaceContext;
import org.arios.net.packet.out.Interface;
import org.arios.parser.item.ItemConfiguration;
import org.arios.parser.npc.NPCConfiguration;
import org.arios.plugin.Plugin;

/**
 * Represents the the command plugin used for visual commands.
 * @author 'Vexia
 * @author Emperor
 */
public final class VisualCommand extends CommandPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		link(CommandSet.DEVELOPER);
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean parse(final Player player, String name, String[] args) {
		Location location = null;
		GameObject object = null;
		int id;
		Player o = null;
		switch (name) {
		case "maxkc":
			for (int i = 0; i < 6; i++) {
				player.getSavedData().getActivityData().getBarrowBrothers()[i] = true;
			}
			String[] names = new String[] { "Ahrim", "Dharok", "Guthan", "Karil", "Torag", "Verac" };
			player.getSavedData().getActivityData().setBarrowKills(50);
			player.getPacketDispatch().sendMessage("Flagged all barrow brothers killed and 50 catacomb kills, current entrance: " + names[player.getSavedData().getActivityData().getBarrowTunnelIndex()] + ".");
			return true;
		case "1hko":
			player.setAttribute("1hko", !player.getAttribute("1hko", false));
			player.getPacketDispatch().sendMessage("1-hit KO mode " + (player.getAttribute("1hko", false) ? "on." : "off."));
			return true;
		case "anim":
		case "emote":
			if (args.length < 2) {
				player.debug("syntax error: id (optional) delay");
				return true;
			}
			final Animation animation = new Animation(Integer.parseInt(args[1]), args.length > 2 ? Integer.parseInt(args[2]) : 0);
			player.animate(animation);
			return true;
		case "gfx":
		case "graphic":
		case "graphics":
			if (args.length < 2) {
				player.debug("syntax error: id (optional) height delay");
				return true;
			}
			player.graphics(new Graphics(Integer.parseInt(args[1]), args.length > 2 ? Integer.parseInt(args[2]) : 0, args.length > 3 ? Integer.parseInt(args[3]) : 0));
			return true;
		case "sync":
		case "visual":
			if (args.length < 3) {
				player.debug("syntax error: anim_id gfx_id (optional) height");
				return true;
			}
			int animId = toInteger(args[1]);
			int gfxId = toInteger(args[2]);
			int height = args.length > 3 ? toInteger(args[3]) : 0;
			player.visualize(Animation.create(animId), new Graphics(gfxId, height));
			return true;
		case "pos_graphic":
		case "position_gfx":
		case "pos_gfx":
		case "lgfx":
			if (args.length < 2) {
				player.debug("syntax error: id x y (optional) height delay");
				return true;
			}
			location = Location.create(Integer.parseInt(args[2]), Integer.parseInt(args[3]), args.length > 4 ? Integer.parseInt(args[4]) : 0);
			player.getPacketDispatch().sendPositionedGraphic(Integer.parseInt(args[1]), args.length > 5 ? Integer.parseInt(args[5]) : 0, args.length > 6 ? Integer.parseInt(args[6]) : 0, location);
			break;
		case "npc":
		case "npcpk":
			if (args.length < 2) {
				player.debug("syntax error: id (optional) direction");
				return true;
			}
			NPC npc = NPC.create(toInteger(args[1]), player.getLocation());
			npc.setAttribute("spawned:npc", true);
			npc.setRespawn(false);
			npc.setDirection(player.getDirection());
			npc.init();
			npc.setWalks(args.length > 2 ? true : false);
			if (name.equals("npcpk")) {
				npc.setAttribute("pk-spawn", true);
				player.sendMessage("You just spawned a SPAWN pk npc.");
			}
			return true;
		case "npcsquad":
			if (args.length < 2) {
				player.debug("syntax error: id (optional) sizeX sizeY");
				return true;
			}
			int sizeX = 3;
			int sizeY = 3;
			if (args.length > 2) {
				sizeX = toInteger(args[2]);
				if (args.length > 3) {
					sizeY = toInteger(args[3]);
				} else {
					sizeY = sizeX;
				}
			}
			boolean aggressive = args.length > 4;
			for (int x = 0; x < sizeX; x++) {
				for (int y = 0; y < sizeY; y++) {
					npc = NPC.create(toInteger(args[1]), player.getLocation().transform(1 + x, 1 + y, 0));
					npc.setAttribute("spawned:npc", true);
					npc.setAggressive(aggressive);
					npc.init();
					npc.setRespawn(false);
					npc.setWalks(aggressive);
				}
			}
			return true;
		case "object":
		case "obj":
			if (args.length < 2) {
				player.debug("syntax error: id (optional) type rotation or rotation");
				return true;
			}
			object = args.length > 3 ? new GameObject(toInteger(args[1]), player.getLocation(), toInteger(args[2]), toInteger(args[3])) : args.length == 3 ? new GameObject(toInteger(args[1]), player.getLocation(), toInteger(args[2])) : new GameObject(toInteger(args[1]), player.getLocation());
			ObjectBuilder.add(object);
			SystemLogger.log("object = " + object);
			return true;
		case "oa":
		case "object_anim":
		case "obj_anim":
		case "objectanim":
		case "objanim":
			if (args.length < 2) {
				player.debug("syntax error: x y id");
				return true;
			}
			location = args.length > 2 ? Location.create(Integer.parseInt(args[1]), Integer.parseInt(args[2]), 3) : player.getLocation();
			object = RegionManager.getObject(location);
			if (object == null) {
				player.debug("error: object not found in region cache.");
				return true;
			}
			player.getPacketDispatch().sendObjectAnimation(object, new Animation(toInteger(args[args.length - 1])));
			return true;
		case "inter":
		case "component":
		case "interface":
			if (args.length < 2) {
				player.debug("syntax error: interface-id");
				return true;
			}
			int componentId = toInteger(args[1]);
			if (componentId < 0 || componentId > Cache.getInterfaceDefinitionsSize()) {
				player.debug("Invalid component id [id=" + componentId + ", max=" + Cache.getInterfaceDefinitionsSize() + "].");
				return true;
			}
			player.getInterfaceManager().openComponent(componentId);
			return true;
		case "iconfig":
		case "inter_config":
			if (args.length < 2) {
				player.debug("syntax error: interface-id child hidden");
				return true;
			}
			boolean hidden = args.length > 3 ? Boolean.parseBoolean(args[3]) : true;
			player.getPacketDispatch().sendInterfaceConfig(toInteger(args[1]), toInteger(args[2]), hidden);
			player.getPacketDispatch().sendMessage("Interface child (id=" + args[1] + ", child=" + args[2] + ") is " + (hidden ? "hidden." : "visible."));
			return true;
		case "saveconfig":
		case "config":
			if (args.length < 2) {
				player.debug("syntax error: config-id (optional) value");
				return true;
			}
			if (name.equals("saveconfig")) {
				player.getConfigManager().set(toInteger(args[1]), args.length > 2 ? toInteger(args[2]) : 0, true);
			} else {
				player.getConfigManager().send(toInteger(args[1]), args.length > 2 ? toInteger(args[2]) : 0);
			}
			return true;
		case "loop_inter":
			final int st = toInteger(args[1]);
			final int en = args.length > 2 ? toInteger(args[2]) : 740;
			GameWorld.submit(new Pulse(3, player) {
				int id = st;

				@Override
				public boolean pulse() {
					PacketRepository.send(Interface.class, new InterfaceContext(player, 548, 77, id, false));
					player.debug("Interface id: " + id);
					return ++id >= en;
				}
			});
			return true;
		case "loop_config":
		case "config_loop":
			if (args.length < 4) {
				player.debug("syntax error: config-id start end value");
				return true;
			}
			int value = toInteger(args[3]);
			for (int i = toInteger(args[1]); i < toInteger(args[2]); i++) {
				player.getConfigManager().set(i, value);
			}
			return true;
		case "string":
			if (args.length < 3) {
				player.debug("syntax error: interface child text");
				return true;
			}
			player.getPacketDispatch().sendString(args[3], toInteger(args[1]), toInteger(args[2]));
			return true;
		case "loop_string":
		case "string_loop":
			if (args.length < 3) {
				player.debug("syntax error: interface min max");
				return true;
			}
			int interfaceId = toInteger(args[1]);
			for (int i = toInteger(args[2]); i < toInteger(args[3]); i++) {
				player.getPacketDispatch().sendString("child=" + i, interfaceId, i);
			}
			return true;
		case "loop_oa":
			final int startId = toInteger(args[1]);
			final int endId = args.length > 2 ? toInteger(args[2]) : 11000;
			GameWorld.submit(new Pulse(3, player) {
				int id = startId;

				@Override
				public boolean pulse() {
					GameObject object = RegionManager.getObject(player.getLocation());
					if (object == null) {
						player.debug("error: object not found in region cache.");
						return true;
					}
					player.getPacketDispatch().sendObjectAnimation(object, new Animation(id));
					player.debug("Animation id: " + id);
					return ++id >= endId;
				}
			});
			return true;
		case "loop_anim":
			final int start = toInteger(args[1]);
			final int end = args.length > 2 ? toInteger(args[2]) : 11000;
			GameWorld.submit(new Pulse(3, player) {
				int id = start;

				@Override
				public boolean pulse() {
					player.animate(Animation.create(id));
					player.debug("Animation id: " + id);
					return ++id >= end;
				}
			});
			return true;
		case "loop_gfx":
			final int s = toInteger(args[1]);
			final int e = args.length > 2 ? toInteger(args[2]) : 11000;
			GameWorld.submit(new Pulse(3, player) {
				int id = s;

				@Override
				public boolean pulse() {
					Projectile.create(player.getLocation(), player.getLocation().transform(0, 3, 0), id, 42, 36, 46, 75, 5, 11).send();
					player.graphics(new Graphics(id, 96));
					player.debug("Graphic id: " + id);
					return ++id >= e;
				}
			});
			return true;
		case "flag":

			return true;
		case "removenpc":
			player.setAttribute("removenpc", !player.getAttribute("removenpc", false));
			player.debug("You have set remove npc value to " + player.getAttribute("removenpc", false) + ".");
			return true;
		case "removeobj":
			GameObject oo = RegionManager.getObject(player.getLocation());
			if (oo != null && oo.getId() == toInteger(args[1])) {
				ObjectBuilder.remove(oo);
			}
			return true;
		case "removeunder":
			if (args.length < 2) {
				player.debug("syntax error: id");
				return true;
			}
			id = Integer.parseInt(args[1]);
			List<NPC> npcs = RegionManager.getLocalNpcs(player, 1);
			for (NPC n : npcs) {
				if (n.getId() == id) {
					n.clear();
				}
			}
			return true;
			/*
			 * case "tg": for (RegionChunk[] chunks :
			 * player.getViewport().getChunks()) { for (RegionChunk chunk :
			 * chunks) { for (int x = 0; x < RegionChunk.SIZE; x++) { for (int y
			 * = 0; y < RegionChunk.SIZE; y++) { GroundItemManager.create(new
			 * Item(4151), chunk.getCurrentBase().transform(x, y, 0), player); }
			 * } } } return true;
			 */
		case "pnpc":
			if (args.length < 2) {
				player.debug("syntax error: id");
				return true;
			}
			player.getAppearance().transformNPC(toInteger(args[1]));
			return true;
		case "itemoni":
			int inter = toInteger(args[1]);
			int child = toInteger(args[2]);
			int item = args.length > 3 ? toInteger(args[3]) : 1038;
			player.getPacketDispatch().sendItemZoomOnInterface(item, 270, inter, child);
			return true;
		case "o":
			int start1 = toInteger(args[1]);
			int end1 = start1 + 10;
			int count = 1;
			for (int i = start1; i <= end1; i++) {
				player.getPacketDispatch().sendPositionedGraphic(i, 0, 1, player.getLocation().transform(count, 0, 0));
				if (count == 5) {
					count = -1;
				} else if (count <= -1) {
					count--;
				} else if (count >= 1) {
					count++;
				}
			}
			return true;
		case "resetn":
			try {
				ServerStore.init(ServerConstants.STORE_PATH);
				new NPCConfiguration().parse();
				new ItemConfiguration().parse();
				for (NPC npc1 : Repository.getNpcs()) {
					npc1.initConfig();
					npc1.configure();
				}
				player.sendMessage("REPARSED!");
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
			return true;
		case "hit":
			player.getImpactHandler().manualHit(player, toInteger(args[1]), HitsplatType.NORMAL);
			return true;
		case "sound":
			player.getAudioManager().send(new Audio(Integer.parseInt(args[1]), 10, 1));
			return true;
		case "noclip":
			player.setAttribute("no_clip", !player.getAttribute("no_clip", false));
			return true;
		case "grow":
			for (PatchWrapper wrapper : player.getFarmingManager().getPatches()) {
				if (wrapper == null || wrapper.getPatch() == null || wrapper.getCycle() == null || wrapper.getCycle().getGrowthHandler() == null) {
					continue;
				}
				wrapper.getCycle().getGrowthHandler().handle();
			}
			return true;
		case "disabledisease":
			player.setAttribute("stop-disease", !player.getAttribute("stop-disease", false));
			player.sendMessage("Disable disease=" + player.getAttribute("stop-disease", false));
			return true;
		case "rake":
			for (PatchWrapper wrapper : player.getFarmingManager().getPatches()) {
				wrapper.getCycle().clear(player);
			}
			return true;
		case "stump":
			PatchWrapper tree = player.getFarmingManager().getPatchWrapper(8390);
			tree.addConfigValue(tree.getNode().getStumpBase());
			tree.getCycle().setGrowthTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(100 + 10));
			return true;
		case "full":
			for (PatchWrapper wrapper : player.getFarmingManager().getPatches()) {
				for (int i = 0; i < 20; i++) {
					if (wrapper == null || wrapper.getPatch() == null || wrapper.getCycle() == null || wrapper.getCycle().getGrowthHandler() == null) {
						continue;
					}
					wrapper.getCycle().getGrowthHandler().handle();
				}
			}
			return true;
		case "clear":
			if (args.length > 1) {
				o = Repository.getPlayerByDisplay(args[1]);
			}
			if (o != null) {
				for (PatchWrapper wrapper : o.getFarmingManager().getPatches()) {
					wrapper.getCycle().clear(o);
				}
				return true;
			}
			for (PatchWrapper wrapper : player.getFarmingManager().getPatches()) {
				wrapper.getCycle().clear(player);
			}
			return true;
		}
		return false;
	}

}
