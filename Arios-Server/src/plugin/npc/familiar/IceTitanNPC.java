package plugin.npc.familiar;

import org.arios.game.content.skill.Skills;
import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;

/**
 * Represents the Ice Titan familiar.
 * @author Aero
 */
public class IceTitanNPC extends Familiar {

	/**
	 * Constructs a new {@code IceTitanNPC} {@code Object}.
	 */
	public IceTitanNPC() {
		this(null, 7359);
	}

	/**
	 * Constructs a new {@code IceTitanNPC} {@code Object}.
	 * @param owner The owner.
	 * @param id The id.
	 */
	public IceTitanNPC(Player owner, int id) {
		super(owner, id, 6400, 12806, 20, WeaponInterface.STYLE_ACCURATE);
	}

	@Override
	public Familiar construct(Player owner, int id) {
		return new IceTitanNPC(owner, id);
	}

	@Override
	protected boolean specialMove(FamiliarSpecial special) {
		owner.getSkills().updateLevel(Skills.DEFENCE, (int) ((int) owner.getSkills().getStaticLevel(Skills.DEFENCE) * 0.12));
		return true;
	}

	@Override
	public void visualizeSpecialMove() {
		owner.visualize(new Animation(7660), new Graphics(1306));
	}

	@Override
	public int[] getIds() {
		return new int[] { 7359, 7360 };
	}

}