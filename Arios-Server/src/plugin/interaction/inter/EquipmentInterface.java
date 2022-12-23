package plugin.interaction.inter;

import org.arios.cache.def.impl.ItemDefinition;
import org.arios.game.component.CloseEvent;
import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.container.Container;
import org.arios.game.container.ContainerEvent;
import org.arios.game.container.ContainerListener;
import org.arios.game.container.access.BitregisterAssembler;
import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.global.action.EquipHandler;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.interaction.OptionHandler;
import org.arios.game.node.entity.combat.DeathTask;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.player.link.prayer.PrayerType;
import org.arios.game.node.item.Item;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ContainerContext;
import org.arios.net.packet.out.ContainerPacket;
import org.arios.plugin.Plugin;

/**
 * Represents the equipment interface.
 * @author Emperor
 * 
 */
public final class EquipmentInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(387, this);
		ComponentDefinition.put(667, this);
		return this;
	}

	@Override
	public boolean handle(final Player p, Component component, int opcode, int button, final int slot, final int itemId) {
		if (component.getId() == 667) {
			System.out.println("Roar " + opcode + ", " + button);
			if (button != 20) {
				return false;
			}
			switch (opcode) {
			case 177:
				p.getPulseManager().clear();
				GameWorld.submit(new Pulse(1, p) {
					@Override
					public boolean pulse() {
						EquipHandler.unequip(p, slot, itemId);
						return true;
					}
				});
				return true;
			case 206:
				GameWorld.submit(new Pulse(1, p) {
					@Override
					public boolean pulse() {
						operate(p, slot, itemId);
						return true;
					}
				});
				return true;
			}
			return false;
		}
		switch (opcode) {
		case 206:
			if (button != 28) {
				return false;
			}
			GameWorld.submit(new Pulse(1, p) {
				@Override
				public boolean pulse() {
					operate(p, slot, itemId);
					return true;
				}
			});
			return true;
		default:
			switch (button) {
			case 52:
				if (TutorialSession.getExtension(p).getStage() <= TutorialSession.MAX_STAGE) {
					return true;
				}
				if (p.getInterfaceManager().isOpened() && p.getInterfaceManager().getOpened().getId() == 102) {
					return true;
				}
				boolean skulled = p.getSkullManager().isSkulled();
				boolean usingProtect = p.getPrayer().get(PrayerType.PROTECT_ITEMS);
				p.getInterfaceManager().openComponent(102);
				p.getPacketDispatch().sendAccessMask(211, 0, 2, 6684690, 4);
				p.getPacketDispatch().sendAccessMask(212, 0, 2, 6684693, 42);
				Container[] itemArray = DeathTask.getContainers(p);
				Container kept = itemArray[0];
				int state = 0; // 1=familiar carrying items
				int keptItems = skulled ? (usingProtect ? 1 : 0) : (usingProtect ? 4 : 3);
				int zoneType = p.getZoneMonitor().getType();
				int pvpType = p.getSkullManager().isWilderness() ? 0 : 1;
				Object[] params = new Object[] { 11510, 12749, "", state, pvpType, kept.getId(3), kept.getId(2), kept.getId(1), kept.getId(0), keptItems, zoneType };
				PacketRepository.send(ContainerPacket.class, new ContainerContext(p, 149, 0, 91, itemArray[1], false));
				p.getPacketDispatch().sendRunScript(118, "iiooooiisii", params);
				break;
			case 28:
				if (opcode == 81) {
					p.getPulseManager().clear();
					GameWorld.submit(new Pulse(1, p) {
						@Override
						public boolean pulse() {
							EquipHandler.unequip(p, slot, itemId);
							return true;
						}
					});
					return true;
				}
				break;
			case 55:
				if (TutorialSession.getExtension(p).getStage() <= TutorialSession.MAX_STAGE) {
					return true;
				}
				if (p.getInterfaceManager().isOpened() && p.getInterfaceManager().getOpened().getId() == 667) {
					return true;
				}
				final ContainerListener listener = new ContainerListener() {
					@Override
					public void update(Container c, ContainerEvent e) {
						PacketRepository.send(ContainerPacket.class, new ContainerContext(p, -1, -1, 98, e.getItems(), false, e.getSlots()));
					}

					@Override
					public void refresh(Container c) {
						PacketRepository.send(ContainerPacket.class, new ContainerContext(p, -1, -1, 98, c, false));
					}
				};
				p.getInterfaceManager().openComponent(667).setCloseEvent(new CloseEvent() {
					@Override
					public boolean close(Player player, Component c) {
						player.removeAttribute("equip_stats_open");
						player.getInterfaceManager().restoreTabs();
						player.getInventory().getListeners().remove(listener);
						return true;
					}
				});
				p.setAttribute("equip_stats_open", true);
				EquipmentContainer.update(p);
				p.getInterfaceManager().removeTabs(0, 1, 2, 5, 6, 7, 8, 9, 10, 11, 12, 13);
				p.getInterfaceManager().openTab(3, new Component(149));
				p.getInventory().getListeners().add(listener);
				p.getInventory().refresh();
				ItemDefinition.statsUpdate(p);
				p.getPacketDispatch().sendAccessMask(BitregisterAssembler.calculateRegister(2), 20, 667, 0, 13);
				break;
			}
		}
		return true;
	}

	/**
	 * Operates an item.
	 * @param player The player.
	 * @param slot The container slot.
	 * @param itemId The item id.
	 */
	public void operate(Player player, int slot, int itemId) {
		if (slot < 0 || slot > 13) {
			return;
		}
		Item item = player.getEquipment().get(slot);
		if (item == null) {
			return;
		}
		OptionHandler handler = item.getOperateHandler();
		if (handler != null && handler.handle(player, item, "operate")) {
			return;
		}
		player.getPacketDispatch().sendMessage("You can't operate that.");
	}

}