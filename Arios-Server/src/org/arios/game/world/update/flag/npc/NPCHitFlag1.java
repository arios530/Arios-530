package org.arios.game.world.update.flag.npc;

import org.arios.game.world.update.flag.UpdateFlag;
import org.arios.game.world.update.flag.context.HitMark;
import org.arios.net.packet.IoBuffer;

/**
 * The NPC's supporting hit update flag.
 * @author Emperor
 *
 */
public class NPCHitFlag1 extends UpdateFlag<HitMark> {

	/**
	 * Constructs a new {@code NPCHitFlag1} {@code Object}.
	 * @param context The hit mark.
	 */
	public NPCHitFlag1(HitMark context) {
		super(context);
	}

	@Override
	public void write(IoBuffer buffer) {
		buffer.putC(context.getDamage()).putS(context.getType());
	}

	@Override
	public int data() {
		return maskData();
	}

	@Override
	public int ordinal() {
		return 1;
	}

	/**
	 * Gets the mask data.
	 * @return The mask data.
	 */
	public static int maskData() {
		return 0x2;
	}

}