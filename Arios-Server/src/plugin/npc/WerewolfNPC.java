package plugin.npc;

import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.world.map.Location;

/**
 * Represents the tutorial chicken npc.
 * @author 'Vexia
 * @version 1.0
 */
public final class WerewolfNPC extends AbstractNPC {

	/**
	 * Constructs a new {@code WerewolfNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public WerewolfNPC(int id, Location location) {
		super(id, location, true);
	}

	/**
	 * Constructs a new {@code WerewolfNPC} {@code Object}.
	 */
	public WerewolfNPC() {
		super(0, null);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new WerewolfNPC(id, location);
	}

	@Override
	public void finalizeDeath(Entity killer) {
		super.finalizeDeath(killer);
		NPC nn = NPC.create(getAttribute("original", 6026), getAttribute("loc", getLocation()));
		nn.init();
	}

	@Override
	public int[] getIds() {
		return new int[] { 6006 };
	}

}
