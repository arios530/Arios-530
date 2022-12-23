package org.arios.net.packet.context;

import org.arios.game.content.eco.ge.GrandExchangeOffer;
import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.Context;

/**
 * The packet context of the grand exchange update packet.
 * @author Emperor
 */
public class GrandExchangeContext implements Context {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The offer to update.
	 */
	private final GrandExchangeOffer offer;

	/**
	 * Constructs a new {@code GrandExchangeContext} {@code Object}.
	 * @param player The player.
	 * @param offer The grand exchange offer to update.
	 */
	public GrandExchangeContext(Player player, GrandExchangeOffer offer) {
		this.player = player;
		this.offer = offer;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the offer.
	 * @return The offer.
	 */
	public GrandExchangeOffer getOffer() {
		return offer;
	}

}
