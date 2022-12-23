package plugin.npc.familiar;

import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.impl.Projectile;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;

/**
 * Represents the Arctic Bear familiar.
 * @author Aero
 */
public class ArcticBearNPC extends Familiar {

	/**
	 * Constructs a new {@code ArcticBearNPC} {@code Object}.
	 */
	public ArcticBearNPC() {
		this(null, 6839);
	}

	/**
	 * Constructs a new {@code ArcticBearNPC} {@code Object}.
	 * @param owner The owner.
	 * @param id The id.
	 */
	public ArcticBearNPC(Player owner, int id) {
		super(owner, id, 2800, 12057, 6, WeaponInterface.STYLE_CONTROLLED);
	}

	@Override
	public Familiar construct(Player owner, int id) {
		return new ArcticBearNPC(owner, id);
	}

	@Override
	protected boolean specialMove(FamiliarSpecial special) {
		Entity target = special.getTarget();
		if (!canCombatSpecial(target)) {
			return false;
		}
		animate(Animation.create(4926));
		graphics(Graphics.create(1405));
		Projectile p = Projectile.magic(this, target, 1406, 40, 40, 1, 10);
		p.setSpeed(25);
		p.send();
		sendFamiliarHit(target, 15, Graphics.create(1407));
		return true;
	}

	@Override
	public int[] getIds() {
		return new int[] { 6839, 6840 };
	}

}