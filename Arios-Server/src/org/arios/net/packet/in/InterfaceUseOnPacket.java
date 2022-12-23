package org.arios.net.packet.in;

import org.arios.ServerConstants;
import org.arios.game.content.skill.free.magic.MagicSpell;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.interaction.MovementPulse;
import org.arios.game.node.entity.combat.CombatSwingHandler;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.SpellBookManager.SpellBook;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.node.object.GameObject;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.map.RegionManager;
import org.arios.game.world.repository.Repository;
import org.arios.net.packet.IncomingPacket;
import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.PlayerContext;
import org.arios.net.packet.out.ClearMinimapFlag;

/**
 * Handles the Interface "Use" on packets.
 * @author Stacx
 */
public class InterfaceUseOnPacket implements IncomingPacket {

	@SuppressWarnings("unused")
	@Override
	public void decode(final Player player, int opcode, IoBuffer buffer) {
		int payload;
		int interfaceId;
		int componentId;
		int itemId;
		int x;
		int y;
		switch (buffer.opcode()) {
		case 73: // Interface On GroundItem
			componentId = buffer.getShort();
			interfaceId = buffer.getShort();
			y = buffer.getShort();
			itemId = buffer.getLEShortA();
			x = buffer.getLEShortA();
			final int spell = buffer.getLEShort();
			final Item groundItem = GroundItemManager.get(itemId, Location.create(x, y, player.getLocation().getZ()), player);
			if (groundItem == null || !player.getLocation().withinDistance(groundItem.getLocation())) {
				break;
			}
			if (player.getAttribute("magic:delay", -1) > GameWorld.getTicks()) {
				break;
			}
			if (player.getZoneMonitor().clickButton(interfaceId, componentId, spell, itemId, opcode)) {
				break;
			}
			if (CombatSwingHandler.isProjectileClipped(player, groundItem, false)) {
				MagicSpell.castSpell(player, SpellBook.MODERN, spell, groundItem);
			} else {
				player.getPulseManager().run(new MovementPulse(player, groundItem) {
					@Override
					public boolean update() {
						if (CombatSwingHandler.isProjectileClipped(player, groundItem, false)) {
							super.destination = player.getLocation();
						}
						boolean finished = super.update();
						if (finished) {
							player.getWalkingQueue().reset();
						}
						return finished;

					}

					@Override
					public boolean pulse() {
						MagicSpell.castSpell(player, SpellBook.MODERN, spell, groundItem);
						return true;
					}
				}, "movement");
			}
			break;
		case 195: // Interface On Player
			payload = buffer.getShortA();
			componentId = buffer.getLEShort();
			interfaceId = buffer.getLEShort();
			int targetIndex = buffer.getLEShortA();
			// Logger.log("Interface:" + interfaceId+ " Component:" +
			// componentId + " Target Index:"+ targetIndex);
			//System.out.println("magic on player component = "+componentId+"!");
			final Player target = Repository.getPlayers().get(targetIndex);
			if (target == null || !player.getLocation().withinDistance(target.getLocation())) {
				PacketRepository.send(ClearMinimapFlag.class, new PlayerContext(player));
				break;
			}
			switch (interfaceId) {
			case 192:
				MagicSpell.castSpell(player, SpellBook.MODERN, componentId, target);
				break;
			case 193:
				MagicSpell.castSpell(player, SpellBook.ANCIENT, componentId, target);
				break;
			case 430:
				MagicSpell.castSpell(player, SpellBook.LUNAR, componentId, target);
				break;
			case 662:
				switch (componentId) {
				case 67:
				case 69:
				case 119:
				case 121:
				default:
					if (!player.getFamiliarManager().hasFamiliar()) {
						player.getPacketDispatch().sendMessage("You don't have a familiar.");
					} else {
						player.getFamiliarManager().getFamiliar().executeSpecialMove(new FamiliarSpecial(target));
					}
					break;
				}
				break;
			default:
				player.debug("Option usage [inter=" + interfaceId + ", child=" + componentId + ", target=" + target + "].");
			}
			break;
		case 191: // Interface On Object
			itemId = buffer.getShort();
			y = buffer.getLEShort();
			payload = buffer.getInt();
			interfaceId = payload >> 16;
			componentId = payload & 0xFFFF;
			x = buffer.getShort();
			int objectId = buffer.getLEShort();
			GameObject object = RegionManager.getObject(player.getLocation().getZ(), x, y);
			if (object == null) {
				object = RegionManager.getObject(Location.create(x, y, 0));
			}
			if (object != null) {
				object = object.getChild(player);
			}
			if (object == null || (object.getId() != objectId && object.getWrapper().getId() != objectId) || !player.getLocation().withinDistance(object.getLocation())) {
				PacketRepository.send(ClearMinimapFlag.class, new PlayerContext(player));
				break;
			}
			switch (interfaceId) {
			case 430:
				MagicSpell.castSpell(player, SpellBook.LUNAR, componentId, object);
				break;
			case 192:
				MagicSpell.castSpell(player, SpellBook.MODERN, componentId, object);
				break;
			case 662:
				switch (componentId) {
				case 137:
				default:
					player.getFamiliarManager().getFamiliar().executeSpecialMove(new FamiliarSpecial(object));
					break;
				}
				break;
			}
			break;
		case 239: // Interface On NPC
			componentId = buffer.getLEShort();
			interfaceId = buffer.getLEShort();
			int unknown = buffer.getShortA();
		    int index = buffer.getLEShortA();
			if (index < 1 || index > ServerConstants.MAX_NPCS) {
				PacketRepository.send(ClearMinimapFlag.class, new PlayerContext(player));
				break;
			}

			NPC npc = Repository.getNpcs().get(index);
			if (npc == null || !player.getLocation().withinDistance(npc.getLocation())) {
				PacketRepository.send(ClearMinimapFlag.class, new PlayerContext(player));
				break;
			}
			if (player.getAttribute("magic:delay", -1) > GameWorld.getTicks()) {
				break;
			}
			switch (interfaceId) {
			case 430:
				MagicSpell.castSpell(player, SpellBook.LUNAR, componentId, npc);
				break;
			case 192:
				MagicSpell.castSpell(player, SpellBook.MODERN, componentId, npc);
				break;
			case 193:
				MagicSpell.castSpell(player, SpellBook.ANCIENT, componentId, npc);
				break;
			case 662:
				switch (componentId) {
				case 67:
				case 69:
				case 177:
				case 121:
				case 119:
				default:
					if (!player.getFamiliarManager().hasFamiliar()) {
						player.getPacketDispatch().sendMessage("You don't have a familiar.");
					} else {
						player.getFamiliarManager().getFamiliar().executeSpecialMove(new FamiliarSpecial(npc));
					}
					break;
				}
				break;
			default:
				player.debug("Option usage [inter=" + interfaceId + ", child=" + componentId + ", target=" + npc + "].");
			}
			break;
		case 253: // Interface On Item
			componentId = buffer.getLEShort();
			interfaceId = buffer.getLEShort();
			int itemSlot = buffer.getLEShortA();
			itemId = buffer.getShortA();
			unknown = buffer.getShortA();
			if (itemSlot < 0 || itemSlot > 27) {
				break;
			}
			Item item = player.getInventory().get(itemSlot);
			if (item == null) {
				break;
			}
			switch (interfaceId) {
			case 430:
				if (player.getAttribute("magic:delay", -1) > GameWorld.getTicks()) {
					break;
				}
				MagicSpell.castSpell(player, SpellBook.LUNAR, componentId, item);
				break;
			case 192:
				if (player.getAttribute("magic:delay", -1) > GameWorld.getTicks()) {
					break;
				}
				MagicSpell.castSpell(player, SpellBook.MODERN, componentId, item);
				break;
			case 662:
				if (player.getFamiliarManager().hasFamiliar()) {
					player.getFamiliarManager().getFamiliar().executeSpecialMove(new FamiliarSpecial(item, interfaceId, componentId, item));
				} else {
					player.getPacketDispatch().sendMessage("You don't have a follower.");
				}
				break;
			default:
				player.debug("Option usage [inter=" + interfaceId + ", child=" + componentId + ", target=" + item + "].");
			}
			break;
		}
	}
}