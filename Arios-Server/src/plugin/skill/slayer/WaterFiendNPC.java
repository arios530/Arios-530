package plugin.skill.slayer;

import org.arios.game.content.skill.member.slayer.Tasks;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.CombatSwingHandler;
import org.arios.game.node.entity.combat.equipment.SwitchAttack;
import org.arios.game.node.entity.combat.handlers.MultiSwingHandler;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.impl.Animator.Priority;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;

/**
 * Handles the water fiend npc.
 * @author Vexia
 */
public final class WaterFiendNPC extends AbstractNPC {

	/**
	 * Handles the combat.
	 */
	private final CombatSwingHandler combatAction = new MultiSwingHandler(true, new SwitchAttack(CombatStyle.MAGIC.getSwingHandler(), new Animation(1581, Priority.HIGH), null, null, Projectile.create((Entity) null, null, 500, 15, 30, 50, 50, 14, 255)), new SwitchAttack(CombatStyle.RANGE.getSwingHandler(), new Animation(1581, Priority.HIGH), null, null, Projectile.create((Entity) null, null, 16, 15, 30, 50, 50, 14, 255)));

	/**
	 * Constructs a new {@code WaterFiendNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public WaterFiendNPC(int id, Location location) {
		super(id, location);
	}

	/**
	 * Constructs a new {@code WaterFiendNPC} {@code Object}.
	 */
	public WaterFiendNPC() {
		super(0, null);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new WaterFiendNPC(id, location);
	}

	@Override
	public CombatSwingHandler getSwingHandler(boolean swing) {
		return combatAction;
	}

	@Override
	public int[] getIds() {
		return Tasks.WATERFIENDS.getTask().getNpcs();
	}

}
