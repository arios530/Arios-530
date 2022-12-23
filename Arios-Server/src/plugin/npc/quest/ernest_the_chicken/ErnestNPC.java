package plugin.npc.quest.ernest_the_chicken;

import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;

/**
 * Represents the abstact npc class to handle ernest the chicken.
 * @author 'Vexia
 * @version 1.0
 */
public final class ErnestNPC extends AbstractNPC {

	/**
	 * The NPC ids of NPCs using this plugin.
	 */
	private static final int[] ID = { 287 };

	/**
	 * Constructs a new {@code ErnestChickenNPC} {@code Object}.
	 */
	public ErnestNPC() {
		super(0, null, false);
	}

	/**
	 * Constructs a new {@code ErnestChickenNPC} {@code Object}.
	 * @param id The NPC id.
	 * @param location The location.
	 */
	private ErnestNPC(int id, Location location) {
		super(id, location, false);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new ErnestNPC(id, location);
	}

	@Override
	public boolean isHidden(final Player player) {
		Player target = getAttribute("target", null);
		if (target != null && target.getQuestRepository().getQuest("Ernest the Chicken").getStage() == 100) {
			clear();
			return super.isHidden(player);
		}
		return target == null ? true : player != target;
	}

	@Override
	public int[] getIds() {
		return ID;
	}

}
