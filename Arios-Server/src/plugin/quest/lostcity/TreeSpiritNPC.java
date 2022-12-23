package plugin.quest.lostcity;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.LostCity;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.combat.CombatStyle;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;

/**
 * Handles the tree spirit npc.
 * @author Vexia
 */
public final class TreeSpiritNPC extends AbstractNPC {

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * Constructs a new {@code TreeSpiritNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 */
	public TreeSpiritNPC(int id, Location location) {
		super(id, location);
	}

	/**
	 * Constructs a new {@code TreeSpiritNPC} {@code Object}.
	 */
	public TreeSpiritNPC() {
		super(0, null);
	}

	@Override
	public void handleTickActions() {
		super.handleTickActions();
		if (!inCombat()) {
			attack(player);
		}
		if (!player.isActive() || player.getLocation().getDistance(getLocation()) > 15) {
			clear();
		}
	}

	@Override
	public void clear() {
		super.clear();
		player.removeAttribute("treeSpawned");
	}

	@Override
	public boolean isAttackable(Entity entity, CombatStyle style) {
		if (entity != player) {
			return false;
		}
		return super.isAttackable(entity, style);
	}

	@Override
	public void finalizeDeath(Entity killer) {
		super.finalizeDeath(killer);
		if (killer instanceof Player) {
			Player player = (Player) killer;
			Quest quest = player.getQuestRepository().getQuest(LostCity.NAME);
			if (quest.getStage() == 20) {
				quest.setStage(21);
				player.getDialogueInterpreter().sendPlaneMessage(false, "With the Tree Spirit defeated you can now chop the tree.");
			}
		}
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new TreeSpiritNPC(id, location);
	}

	@Override
	public int[] getIds() {
		return new int[] { 655 };
	}

	/**
	 * Sets the player.
	 * @param player the player.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

}
