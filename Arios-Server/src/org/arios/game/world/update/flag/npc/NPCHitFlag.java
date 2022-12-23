package org.arios.game.world.update.flag.npc;

import org.arios.game.node.entity.Entity;
import org.arios.game.world.update.flag.UpdateFlag;
import org.arios.game.world.update.flag.context.HitMark;
import org.arios.net.packet.IoBuffer;

/**
 * The NPC's main hit update flag.
 * @author Emperor
 *
 */
public final class NPCHitFlag extends UpdateFlag<HitMark> {

	/**
	 * Constructs a new {@code NPCHitFlag} {@code Object}.
	 * @param context The hit mark.
	 */
	public NPCHitFlag(HitMark context) {
		super(context);
	}

	@Override
	public void write(IoBuffer buffer) {
		Entity e = context.getEntity();
		int max = e.getSkills().getMaximumLifepoints();
		int ratio = max > 0 ? (e.getSkills().getLifepoints() * 255 / max) : 0;
		buffer.put(context.getDamage()).putC(context.getType()).putS(ratio);
	}

	@Override
	public int data() {
		return maskData();
	}

	@Override
	public int ordinal() {
		return 0;
	}

	/**
	 * Gets the mask data.
	 * @return The mask data.
	 */
	public static int maskData() {
		return 0x40;
	}

}