package plugin.skill.construction;

import org.arios.game.component.Component;
import org.arios.game.component.ComponentDefinition;
import org.arios.game.component.ComponentPlugin;
import org.arios.game.content.skill.member.construction.BuildHotspot;
import org.arios.game.content.skill.member.construction.Decoration;
import org.arios.game.content.skill.member.construction.HouseManager;
import org.arios.game.content.skill.member.construction.RoomBuilder;
import org.arios.game.content.skill.member.construction.RoomProperties;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.object.GameObject;
import org.arios.plugin.Plugin;

/**
 * Handles the creating of a decoration object.
 * @author Emperor
 */
public final class ConstructionInterface extends ComponentPlugin {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ComponentDefinition.put(396, this);
		ComponentDefinition.put(398, this);
		ComponentDefinition.put(400, this);
		ComponentDefinition.put(402, this);
		return this;
	}

	@Override
	public boolean handle(Player player, Component component, int opcode, int button, int slot, int itemId) {
		switch (component.getId()) {
		case 396:
			switch (button) {
			case 132:
				slot = getSlotIndex(slot);
				player.getInterfaceManager().close();
				BuildHotspot hotspot = player.getAttribute("con:hotspot");
				GameObject object = player.getAttribute("con:hsobject");
				if (hotspot == null || object == null) {
					break;
				}
				if (slot >= hotspot.getDecorations().length) {
					break;
				}
				Decoration deco = hotspot.getDecorations()[slot];
				RoomBuilder.buildDecoration(player, hotspot, deco, object);
				return true;
			}
			break;
		case 398:
			switch (button) {
			case 14:
			case 1:
				player.getHouseManager().toggleBuildingMode(player, button == 14);
				return true;
			case 15:
				if (!player.getHouseManager().isInHouse()) {
					player.getPacketDispatch().sendMessage("You're not in a house.");
					player.getPacketDispatch().sendMessage("You can't expel people from the world, even if you don't like them!");
					return true;
				}
				player.getHouseManager().expelGuests(player);
				return true;
			case 13:
				if (!player.getHouseManager().isInHouse()) {
					player.getPacketDispatch().sendMessage("You're not in a house.");
					return true;
				}
				HouseManager.leave(player);
				return true;
			}
			break;
		case 402:
			int index = button - 160;
			if (index > -1 && index < RoomProperties.values().length) {
				player.getDialogueInterpreter().open("con:room", RoomProperties.values()[index]);
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * Gets the real slot index.
	 * @param slot the slot.
	 * @return the indexed slot.
	 */
	private int getSlotIndex(int slot) {
		if (slot == 0) {
			return slot;
		}
		if (slot % 2 == 0 && slot != 0) {
			return slot == 2 ? 1 : slot == 4 ? 2 : 3;
		}
		return slot == 1 ? 4 : slot == 3 ? 5 : slot == 5 ? 6 : 7;
	}

}