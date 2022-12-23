package plugin.npc.familiar;

import org.arios.game.content.skill.member.summoning.familiar.Familiar;
import org.arios.game.content.skill.member.summoning.familiar.FamiliarSpecial;
import org.arios.game.node.entity.combat.equipment.WeaponInterface;
import org.arios.game.node.entity.player.Player;

/**
 * Represents the Iron Titan familiar.
 * @author Aero
 */
public class IronTitanNPC extends Familiar {

	/**
	 * Constructs a new {@code IronTitanNPC} {@code Object}.
	 */
	public IronTitanNPC() {
		this(null, 7375);
	}

	/**
	 * Constructs a new {@code IronTitanNPC} {@code Object}.
	 * @param owner The owner.
	 * @param id The id.
	 */
	public IronTitanNPC(Player owner, int id) {
		super(owner, id, 6000, 12822, 12, WeaponInterface.STYLE_DEFENSIVE);
	}

	@Override
	public Familiar construct(Player owner, int id) {
		return new IronTitanNPC(owner, id);
	}

	@Override
	protected boolean specialMove(FamiliarSpecial special) {
		return false;
	}

	@Override
	public int[] getIds() {
		return new int[] { 7375, 7376 };
	}

}