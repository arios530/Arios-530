package org.arios.game.content.global.action;

import org.arios.game.node.entity.player.Player;

/**
 * Handles a digging action.
 * @author Emperor
 */
public interface DigAction {

	/**
	 * Runs the digging action.
	 * @param player The player.
	 */
	void run(Player player);

}