package org.arios.game.content.skill.member.summoning.familiar;

import org.arios.game.container.Container;
import org.arios.game.container.ContainerEvent;
import org.arios.game.container.ContainerListener;
import org.arios.game.node.entity.player.Player;
import org.arios.net.packet.PacketRepository;
import org.arios.net.packet.context.ContainerContext;
import org.arios.net.packet.out.ContainerPacket;

/**
 * The beast of burden container listener.
 * @author Emperor
 */
public final class BurdenContainerListener implements ContainerListener {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Constructs a new {@code BurdenContainerListener} {@code Object}.
	 * @param player The player.
	 */
	public BurdenContainerListener(Player player) {
		this.player = player;
	}

	@Override
	public void update(Container c, ContainerEvent event) {
		PacketRepository.send(ContainerPacket.class, new ContainerContext(player, -1, -2, 30, event.getItems(), false, event.getSlots()));
	}

	@Override
	public void refresh(Container c) {
		PacketRepository.send(ContainerPacket.class, new ContainerContext(player, -1, -2, 30, c.toArray(), c.capacity(), false));
	}

}