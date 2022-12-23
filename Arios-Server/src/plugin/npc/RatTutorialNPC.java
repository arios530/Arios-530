package plugin.npc;

import org.arios.game.content.global.quest.QuestState;
import org.arios.game.content.global.tutorial.TutorialSession;
import org.arios.game.content.global.tutorial.TutorialStage;
import org.arios.game.content.skill.Skills;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.node.item.GroundItemManager;
import org.arios.game.node.item.Item;
import org.arios.game.world.map.Location;

/**
 * Handles the tutorial rat npc.
 * @author 'Vexia
 */
public class RatTutorialNPC extends AbstractNPC {

	/**
	 * The NPC ids of NPCs using this plugin.
	 */
	private static final int[] ID = { 86 };

	/**
	 * Constructs a new {@code AlKharidWarriorPlugin} {@code Object}.
	 */
	public RatTutorialNPC() {
		super(0, null);
		this.setAggressive(false);
	}

	/**
	 * Constructs a new {@code AlKharidWarriorPlugin} {@code Object}.
	 * @param id The NPC id.
	 * @param location The location.
	 */
	private RatTutorialNPC(int id, Location location) {
		super(id, location, true);
	}

	@Override
	public void init() {
		super.init();
		setAggressive(false);
		getSkills().setLevel(Skills.HITPOINTS, 5);
		getSkills().setStaticLevel(Skills.HITPOINTS, 5);
		getSkills().setLifepoints(5);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new RatTutorialNPC(id, location);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void finalizeDeath(final Entity killer) {
		super.finalizeDeath(killer);
		final Player p = ((Player) killer);
		if (TutorialSession.getExtension(p).getStage() == 52) {
			TutorialStage.load(p, 53, false);
		}
		if (TutorialSession.getExtension(p).getStage() == 54) {
			TutorialStage.load(p, 55, false);
		}
		if (killer instanceof Player) {
			if (p.getQuestRepository().getQuest("Witch's Potion").getState() == QuestState.STARTED) {
				GroundItemManager.create(new Item(300), getLocation(), p);
			}
		}
	}

	@Override
	public int[] getIds() {
		return ID;
	}

}
