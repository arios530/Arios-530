package plugin.skill.slayer;

import org.arios.game.content.skill.member.slayer.MirrorShieldHandler;
import org.arios.game.node.entity.combat.BattleState;
import org.arios.game.node.entity.combat.CombatSwingHandler;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.world.map.Location;

/**
 * Handles a cockatrice npc.
 * @author Vexia
 */
public final class CockatriceNPC extends AbstractNPC {

	/**
	 * Constructs a new {@code CockatriceNPC} {@code Object}.
	 */
	public CockatriceNPC() {
		super(0, null);
	}

	/**
	 * Constructs a new {@code CockatriceNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public CockatriceNPC(int id, Location location) {
		super(id, location);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new CockatriceNPC(id, location);
	}

	@Override
	public CombatSwingHandler getSwingHandler(boolean swing) {
		return MirrorShieldHandler.SINGLETON;
	}

	@Override
	public void checkImpact(BattleState state) {
		super.checkImpact(state);
		MirrorShieldHandler.SINGLETON.checkImpact(state);
	}

	@Override
	public int[] getIds() {
		return new int[] { 1620, 1621 };
	}

}
