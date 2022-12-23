package plugin.npc;

import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.object.GameObject;
import org.arios.game.node.object.ObjectBuilder;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;

/**
 * Represents the Suit of armour NPC used in Taverly dungeon (entering Cauldron
 * of thunder room).
 * @author Emperor
 */
public final class ArmourSuitNPC extends AbstractNPC {

	/**
	 * Constructs a new {@code ArmourSuitNPC} {@code Object}.
	 */
	public ArmourSuitNPC() {
		this(453, null);
	}

	/**
	 * Constructs a new {@code ArmourSuitNPC} {@code Object}.
	 * @param id The NPC id.
	 * @param location The location.
	 */
	public ArmourSuitNPC(int id, Location location) {
		super(id, location);
	}

	@Override
	public void init() {
		super.init();
		super.setRespawn(false);
		GameWorld.submit(new Pulse(50, this) {
			@Override
			public boolean pulse() {
				if (!getProperties().getCombatPulse().isAttacking() && !inCombat()) {
					clear();
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void clear() {
		super.clear();
		ObjectBuilder.add(new GameObject(818, getProperties().getSpawnLocation(), 1));
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new ArmourSuitNPC(id, location);
	}

	@Override
	public int[] getIds() {
		return new int[] { 453 };
	}

}