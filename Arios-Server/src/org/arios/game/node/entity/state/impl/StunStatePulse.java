package org.arios.game.node.entity.state.impl;

import java.nio.ByteBuffer;

import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.entity.state.StatePulse;
import org.arios.game.world.update.flag.context.Graphics;

/**
 * Handles the stunned state.
 * @author Emperor
 */
public final class StunStatePulse extends StatePulse {

	/**
	 * The stun graphic.
	 */
	private static final Graphics STUN_GRAPHIC = new Graphics(80, 96);

	/**
	 * Constructs a new {@code FrozenStatePulse} {@code Object}.
	 * @param entity The entity.
	 * @param ticks The ticks to freeze for.
	 */
	public StunStatePulse(Entity entity, int ticks) {
		super(entity, ticks);
	}

	@Override
	public void start() {
		super.start();
		entity.getWalkingQueue().reset();
		entity.getLocks().lock(getDelay());
		entity.graphics(STUN_GRAPHIC);
		if (entity instanceof Player) {
			entity.asPlayer().getAudioManager().send(3201);
			((Player) entity).getPacketDispatch().sendMessage("You have been stunned!");
		}
	}

	@Override
	public StatePulse create(Entity entity, Object... args) {
		return new StunStatePulse(entity, (Integer) args[0]);
	}

	@Override
	public boolean pulse() {
		if (entity.getAnimator().getGraphics() == STUN_GRAPHIC) {
			entity.graphics(Graphics.create(-1));
		}
		return true;
	}

	@Override
	public boolean isSaveRequired() {
		return false;
	}

	@Override
	public void save(ByteBuffer buffer) {
		/*
		 * empty.
		 */
	}

	@Override
	public StatePulse parse(Entity entity, ByteBuffer buffer) {
		return null;
	}

}