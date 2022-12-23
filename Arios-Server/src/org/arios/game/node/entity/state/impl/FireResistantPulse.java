package org.arios.game.node.entity.state.impl;

import java.nio.ByteBuffer;

import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.state.StatePulse;
import org.arios.game.world.GameWorld;

/**
 * The pulse used for fire resistant.
 * @author Vexia
 */
public class FireResistantPulse extends StatePulse {

	/**
	 * The time to finish.
	 */
	private static final int END_TIME = GameWorld.getSettings().isDevMode() ? 30 : 600;

	/**
	 * The current tick.
	 */
	private int currentTick;

	/**
	 * Constructs a new {@Code FireResistantPulse} {@Code Object}
	 * @param entity the entity.
	 * @param ticks the ticks.
	 */
	public FireResistantPulse(Entity entity, int ticks, int currentTick) {
		super(entity, ticks);
		this.currentTick = currentTick;
	}

	@Override
	public boolean isSaveRequired() {
		return true;
	}

	@Override
	public void save(ByteBuffer buffer) {
		buffer.putInt(currentTick);
	}

	@Override
	public StatePulse parse(Entity entity, ByteBuffer buffer) {
		return new FireResistantPulse(entity, 1, buffer.getInt());
	}

	@Override
	public StatePulse create(Entity entity, Object... args) {
		return new FireResistantPulse(entity, 1, 0);
	}

	@Override
	public boolean pulse() {
		if (entity instanceof Player) {
			if (currentTick == (END_TIME - 25)) {
				entity.asPlayer().getPacketDispatch().sendMessage("<col=4C0E00>Your resistance to dragonfire is about to run out.");
			} else if (currentTick == (END_TIME - 1)) {
				entity.asPlayer().getPacketDispatch().sendMessage("<col=4C0E00>Your resistance to dragonfire has run out.");
			}
		}
		return ++currentTick >= END_TIME;
	}

}