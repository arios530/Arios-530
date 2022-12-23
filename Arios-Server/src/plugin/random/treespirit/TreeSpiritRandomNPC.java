package plugin.random.treespirit;

import org.arios.cache.def.impl.NPCDefinition;
import org.arios.game.content.ame.AntiMacroEvent;
import org.arios.game.content.ame.AntiMacroNPC;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.player.Player;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Animation;
import org.arios.tools.RandomFunction;

/**
 * Handles the Tree Spirit NPCs
 * @author Splinter
 */
public final class TreeSpiritRandomNPC extends AntiMacroNPC {

	/**
	 * The tree spirit npc ids.
	 */
	private static final int[] IDS = new int[] { 438, 439, 440, 441, 442, 443 };

	/**
	 * Constructs a new {@code TreeSpiritRandomNPC} {@code Object}.
	 * @param id the id.
	 * @param location the location.
	 * @param event the event.
	 * @param player the player.
	 * @param quotes the quotes.
	 */
	public TreeSpiritRandomNPC(int id, Location location, AntiMacroEvent event, Player player) {
		super(id, location, event, player);
	}

	@Override
	public void init() {
		NPCDefinition def = NPCDefinition.forId(getId());
		def.getCombatAnimations()[0] = new Animation(94);
		def.getCombatAnimations()[3] = new Animation(95);
		def.getCombatAnimations()[4] = new Animation(96);
		super.init();
		setRespawn(false);
		getProperties().getCombatPulse().attack(player);
		sendChat("Leave these woods and never return!");
	}

	@Override
	public void handleTickActions() {
		super.handleTickActions();
		if (!getProperties().getCombatPulse().isAttacking()) {
			getProperties().getCombatPulse().attack(player);
		}
		if (getProperties().getCombatPulse().isAttacking()) {
			if (RandomFunction.random(40) < 2) {
				sendChat("Leave these woods and never return!");
			}
		}
	}

	@Override
	public boolean isIgnoreMultiBoundaries(Entity victim) {
		return victim == player;
	}

	@Override
	public int[] getIds() {
		return IDS;
	}

}
