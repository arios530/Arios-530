package org.arios.game.component;

import org.arios.game.node.entity.player.Player;
import org.arios.plugin.Plugin;

/**
 * Represents the plugin used to handle a component action.
 * @author 'Vexia
 */
public abstract class ComponentPlugin implements Plugin<Object> {

	/**
	 * Handles the interface interaction.
	 * @param player The player.
	 * @param component The component.
	 * @param opcode The opcode.
	 * @param slot The slot.
	 * @param itemId The item id.
	 * @return {@code True} if succesfully handled.
	 */
	public abstract boolean handle(final Player player, Component component, final int opcode, final int button, int slot, int itemId);

	@Override
	public Object fireEvent(String identifier, Object... args) {
		return null;
	}

}
