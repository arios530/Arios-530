package plugin.skill.slayer;

import org.arios.game.container.impl.EquipmentContainer;
import org.arios.game.content.skill.member.slayer.Tasks;
import org.arios.game.node.entity.combat.BattleState;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.combat.CombatSwingHandler;
import org.arios.game.node.entity.combat.equipment.SwitchAttack;
import org.arios.game.node.entity.combat.handlers.DragonfireSwingHandler;
import org.arios.game.node.entity.combat.handlers.MultiSwingHandler;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.game.world.update.flag.context.Graphics;
import org.arios.tools.RandomFunction;

/**
 * Handles the skeletal wyvern npc.
 * @author Vexia
 * @author Splinter
 * @version 1.0
 */
public final class SkeletalWyvernNPC extends AbstractNPC {

	/**
	 * The combat swing handler.
	 */
	private static final MultiSwingHandler COMBAT_HANDLER = new MultiSwingHandler(new SwitchAttack(CombatStyle.MELEE.getSwingHandler(), new Animation(2985)), new SwitchAttack(CombatStyle.RANGE.getSwingHandler(), new Animation(2989), new Graphics(499)), DragonfireSwingHandler.get(false, 54, new Animation(2988), new Graphics(501), null, null, false));

	/**
	 * The combat swing handler for far combat (5+ tile distance)
	 */
	private static final MultiSwingHandler COMBAT_HANDLER_FAR = new MultiSwingHandler(new SwitchAttack(CombatStyle.RANGE.getSwingHandler(), new Animation(2989), new Graphics(499)));

	/**
	 * Constructs a new {@code SkeletalWyvernNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public SkeletalWyvernNPC(int id, Location location) {
		super(id, location);
	}

	/**
	 * Constructs a new {@code SkeletalWyvernNPC} {@code Object}.
	 */
	public SkeletalWyvernNPC() {
		super(0, null);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new SkeletalWyvernNPC(id, location);
	}

	@Override
	public void sendImpact(BattleState state) {
		if (state.getStyle() == CombatStyle.MAGIC && state.getVictim() != null && state.getVictim().isPlayer()) {
			Player p = state.getVictim().asPlayer();
			Item item = p.getEquipment().get(EquipmentContainer.SLOT_SHIELD);
			if (item != null && (item.getId() == 2890 || item.getId() == 9731) && state.getEstimatedHit() > 10) {
				state.setEstimatedHit(RandomFunction.random(10));
			}
		}
	}

	@Override
	public CombatSwingHandler getSwingHandler(boolean swing) {
		if (this.getProperties().getCombatPulse().getVictim() != null && this.getProperties().getCombatPulse().getVictim().getLocation().getDistance(this.getLocation()) >= 5) {
			return COMBAT_HANDLER_FAR;
		}
		return COMBAT_HANDLER;
	}

	@Override
	public int[] getIds() {
		return Tasks.SKELETAL_WYVERN.getTask().getNpcs();
	}

}
