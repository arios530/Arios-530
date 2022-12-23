package plugin.quest.merlincrystal;

import org.arios.game.content.global.quest.Quest;
import org.arios.game.content.global.quest.impl.member.MerlinCrystal;
import org.arios.game.node.entity.Entity;
import org.arios.game.node.entity.npc.AbstractNPC;
import org.arios.game.node.entity.npc.NPC;
import org.arios.game.node.entity.player.Player;
import org.arios.game.system.task.Pulse;
import org.arios.game.world.GameWorld;
import org.arios.game.world.map.Location;
import org.arios.game.world.update.flag.context.Graphics;

/**
 * Handles Sir Mordred
 * @author Vexia
 * @author Splinter
 */
public class SirMordredNPC extends AbstractNPC {

	/**
	 * The NPC ids of NPCs using this plugin.
	 */
	private static final int[] ID = { 247 };

	/**
	 * Constructs a new {@code AlKharidWarriorPlugin} {@code Object}.
	 */
	public SirMordredNPC() {
		super(0, null);
	}

	/**
	 * Constructs a new {@code SirMordredNPC} {@code Object}
	 * @param id the id.
	 * @param location the location.
	 */
	private SirMordredNPC(int id, Location location) {
		super(id, location);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new SirMordredNPC(id, location);
	}

	@Override
	public void finalizeDeath(final Entity killer) {
		super.finalizeDeath(killer);
		super.getProperties().getCombatPulse().stop();
		super.getSkills().setLifepoints(50);
		if (killer != null && killer.isPlayer()) {
			final Player p = ((Player) killer);
			Quest quest = p.getQuestRepository().getQuest(MerlinCrystal.NAME);
			if (quest.getStage() == 40) {
				quest.setStage(50);
				p.getQuestRepository().update(p);
				p.getDialogueInterpreter().open("merlin_dialogue", 34, 248);
				final NPC npc = NPC.create(248, p.getLocation());
				p.setAttribute("morgan", npc);
				npc.init();
				npc.graphics(Graphics.create(86));
				npc.moveStep();
				npc.face(p);
				GameWorld.submit(new Pulse(100, p, npc) {

					@Override
					public boolean pulse() {
						return true;
					}

					@Override
					public void stop() {
						super.stop();
						if (npc != null && npc.isActive()) {
							npc.clear();
						}
					}

				});
			}
		}
	}

	@Override
	public int[] getIds() {
		return ID;
	}

}